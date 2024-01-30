/*  Name: Matthew Sherrill
 *  Date: 4/1/2023
 *	Description: Controls the user input to talk to control Link, cahge rooms, shoot boomerangs, and create tiles and pots.
 */ 
//Mouse Input Classes
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
//Keyboard Input Classes
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;


class Controller implements MouseListener, KeyListener
{
	//Objects
	View view;
	Model model;
	boolean editMode, potMode;
	boolean scrollUp, scrollDown, scrollLeft, scrollRight;
	boolean moveUp, moveDown, moveLeft, moveRight;

	//Methods
	Controller(Model m)
	{
		model = m;
		Json loadFile = Json.load("map.json");
		model.unmarshal(loadFile);
	}

	void setView(View v)
	{
		view = v;
	}

	public void mouseReleased(MouseEvent e) {    }
	public void mouseEntered(MouseEvent e) {    }
	public void mouseExited(MouseEvent e) {    }
	public void mouseClicked(MouseEvent e){		}

	public void mousePressed(MouseEvent e)
	{
		
		if(editMode && potMode){
			model.addSprite(e.getX() + view.scrollPosX, e.getY() + view.scrollPosY, 'p');
		}
		else if(editMode){
			model.addSprite(e.getX() + view.scrollPosX, e.getY() + view.scrollPosY, 't');
		}
		// else{
		// 	System.out.println("You can only edit while in edit mode!");
		// 	System.out.println(e.getX() + " " + e.getY());
		// }
	}
	public void keyPressed(KeyEvent e){
		switch(e.getKeyCode())
		{			
			case KeyEvent.VK_RIGHT: 
			moveRight = true; 
			break;
			case KeyEvent.VK_LEFT: moveLeft = true; break;
			case KeyEvent.VK_UP: moveUp = true; break;
			case KeyEvent.VK_DOWN: moveDown = true; break;
			case KeyEvent.VK_E:
				editMode = !editMode;
				view.printMsg = !view.printMsg;
				if(potMode)
				{
					potMode = false;
					view.printPot = false;
				}
				break;
			case KeyEvent.VK_P:
				if(editMode)
				{
					potMode = !potMode;
					view.printPot = !view.printPot;
				}
				else
				break;
			
		}
	}
	public void keyReleased(KeyEvent e)
	{
		switch(e.getKeyCode())
			{
				case KeyEvent.VK_Q:
				case KeyEvent.VK_ESCAPE:
					System.exit(0);
				case KeyEvent.VK_S:
					System.out.println("Saving...");
					Json temp = model.marshal();
					temp.save("map.json");
					System.out.println("Saved!");
				break;
				case KeyEvent.VK_L: 
					System.out.println("Loading map...");
					Json loadFile = Json.load("map.json");
					System.out.println("Loaded map correctly. Good luck!");
					model.unmarshal(loadFile);
				break;
				case KeyEvent.VK_RIGHT: moveRight = false; break;
				case KeyEvent.VK_LEFT: moveLeft = false; break;
				case KeyEvent.VK_UP: moveUp = false; break;
				case KeyEvent.VK_DOWN: moveDown = false; break;
				case KeyEvent.VK_CONTROL: model.launchBoomerang();
				break;
			}
		if(editMode){
			switch(e.getKeyCode()){
				case KeyEvent.VK_D: 
					scrollRight = false; 
					if (view.scrollPosX < 700)
						view.scrollPosX += 700;
					break;
				case KeyEvent.VK_A: 
					scrollLeft = false;
					if (view.scrollPosX > 0)
						view.scrollPosX -= 700;
					break;
				case KeyEvent.VK_W:
					scrollUp = false; 
					if (view.scrollPosY > 0)
						view.scrollPosY -= 450; 
					break;
				case KeyEvent.VK_X: 
					scrollDown = false; 
					if(view.scrollPosY < 450)
						view.scrollPosY += 450;
					break;
			}
		}
	}
	public void keyTyped(KeyEvent e){	}
	
	void update(){
		if(moveRight)
		{
			model.link.setPrevious();
			model.link.x += model.link.speed;
			model.link.updateImageNum(Direction.RIGHT);
			model.link.setDirection(Direction.RIGHT);
			if(model.link.x > 700)
				view.scrollPosX = 700;
			return;
		}
		if(moveLeft)
		{
			model.link.setPrevious();
			model.link.x -= model.link.speed;
			model.link.updateImageNum(Direction.LEFT);
			model.link.setDirection(Direction.LEFT);
			if(model.link.x < 700)
				view.scrollPosX = 0;
			return;
		}
		if(moveUp)
		{
			model.link.setPrevious();
			model.link.y -= model.link.speed;
			model.link.updateImageNum(Direction.UP);
			model.link.setDirection(Direction.UP);
			if(model.link.y < 450)
				view.scrollPosY = 0;
			return;
		}
		if (moveDown)
		{
			model.link.setPrevious();
			model.link.y += model.link.speed;
			model.link.updateImageNum(Direction.DOWN);
			model.link.setDirection(Direction.DOWN);
			if(model.link.y > 450)
				view.scrollPosY = 450;
			return;
		}
	}
}