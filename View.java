/*  Name: Matthew Sherrill
 *  Date: 4/1/2023
 *	Description: The View.java class/file is used for outputting this assignment to the screen
				 as well as assigning variables for creating the scroll to move to other "rooms",
				 as well as using methods from Sprites to draw onto the screen.
 */ 
import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Iterator;

class View extends JPanel	
{
	Model model;
	int scrollPosX, scrollPosY;
	boolean printMsg = false;
	boolean printPot = false;
	Iterator<Sprite> move;

	
	View(Controller c, Model m)
	{
		c.setView(this);
		model = m;
	}

	public void paintComponent(Graphics g) //Creates image on screen
	{
		//Setting Window
		g.setColor(new Color(128, 255, 255));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		move = model.sprites.iterator();
		while(move.hasNext()){
			Sprite s = move.next();
			s.draw(g, scrollPosX, scrollPosY);
		}

		//Edit Mode Message
		if(printMsg){
			g.setColor(new Color(255,255, 255));
			g.setFont(new Font("default", Font.BOLD, 16));
			g.drawString("EDIT MODE", this.getWidth()/12, this.getHeight()/12);
		}
		if(printPot){
			g.setColor(new Color(0,0, 0));
			g.setFont(new Font("default", Font.ITALIC, 16));
			g.drawString("POT MODE", this.getWidth()/12, this.getHeight()/8);
		}
	}

	public static BufferedImage loadImage(String filename)
	{
		BufferedImage image = null;
		try{
			image = ImageIO.read(new File(filename));
		} catch(Exception e){
			e.printStackTrace(System.err);
			System.exit(1);
		}
		return image;
	}
}