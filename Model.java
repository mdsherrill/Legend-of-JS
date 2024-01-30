/*  Name: Matthew Sherrill
 *  Date: 4/1/2023
 *	Description: Model class is creating methods that uses an ArrayList to insert Tiles,
				 as well as the methods to move them in and out of a Json files for saving/loading.
				 Additionally, we also have booleans to check for the collisions Sprites, as well as checking for 
				 specific methods called by each one.
 */ 
import java.util.ArrayList;
import java.util.Iterator;
import java.io.IOException;
import java.io.File;
 
class Model
{
	ArrayList<Sprite> sprites;
	Iterator<Sprite> itrBoomer, itrPot;
	final int WINDOW_HEIGHT = 485, WINDOW_WIDTH = 660;
	
	Link link;
	Model()
	{
		sprites = new ArrayList<Sprite>();
		link = new Link(60,60);
		sprites.add(link);
		try 
        {
            File myObj = new File("tiles.txt");
            if (myObj.createNewFile()) {
            //   System.out.println("File created: " + myObj.getName());
            } else {
            //   System.out.println("File already exists.");
            }
          } 
          catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
	}

	public void update()
	{
		link.update();
		for (int i=0; i < sprites.size(); i++)
		{
			boolean toBeRemoved = false;
			if(!(sprites.get(i).update()))
			{
				sprites.remove(i);
				break;
			}
			if(sprites.get(i).isTile())	
			{
				if(SpriteCollision(link, sprites.get(i)))
				{
					// System.out.println("Collision with Tile, Direction: " + link.getDirection());
					link.setBack();
				}
			}
			if(sprites.get(i).isPot())	
			{
				if(SpriteCollision(link, sprites.get(i)))
				{
					// System.out.println("Collision with Pot");
					((Pot)sprites.get(i)).setMovement(link.getDirection());
				}
				itrPot = sprites.iterator();
				while(itrPot.hasNext())
				{
					Sprite p = itrPot.next();
					if(p.isTile())
						if(SpriteCollision(sprites.get(i), p))
						{
							((Pot)sprites.get(i)).setMovement(0);
							((Pot)sprites.get(i)).broken = true;
							break;
						}
				}
			}
			if(sprites.get(i).isBoomerang())
			{
				itrBoomer = sprites.iterator();
				while(itrBoomer.hasNext())
				{
					Sprite s = itrBoomer.next();
					if(s.isTile())
						if(SpriteCollision(sprites.get(i), s))
						{
							// System.out.println("Boomerang collision detected. Removing boomerang.");
							toBeRemoved = true;
							break;
						}
					if(s.isPot())
						if(SpriteCollision(sprites.get(i), s))
						{
							// System.out.println("Boomerang collision detected. Removing boomerang.");
							toBeRemoved = true;
							((Pot)s).broken = true;
							break;
						}
				}
				if(toBeRemoved)
					sprites.remove(i);
			}
		}
	}

	public void addSprite(int mousex, int mousey, char check)
	{
		boolean foundSprite = false;
		int x = mousex - mousex % 50;
		int y = mousey - mousey % 50;
		

		for (int i=0; i < sprites.size(); i++){
			if(sprites.get(i).CheckExisting(x, y))
			{
				foundSprite = true;
				sprites.remove(i);
				break;
			}	
		}
		if (!foundSprite && check == 't') {
			sprites.add(new Tile(x,y));
		}
		else if (!foundSprite && check == 'p') {
			sprites.add(new Pot(mousex,mousey));
		}
	}

	Json marshal(){ //create enchanced for and make if statements for marshalling different sprites
		Json ob = Json.newObject();
		Json tmpList = Json.newList();
		Json tmpList2 = Json.newList();
		ob.add("tiles", tmpList);
		ob.add("pots", tmpList2);
		for (int i = 0; i < sprites.size(); i++)
		{
			if(sprites.get(i).isTile())
				tmpList.add(sprites.get(i).marshal());
			if(sprites.get(i).isPot())
				tmpList2.add(sprites.get(i).marshal());
			else continue;
		}
		return ob;
	}

	public void unmarshal(Json ob)
	{
		sprites.clear();
		sprites.add(link);
		Json tmpList = ob.get("tiles");
		for(int i = 0; i < tmpList.size(); i++){
			sprites.add(new Tile(tmpList.get(i)));
		}
		
		Json tmpList2 = ob.get("pots");
		for(int i = 0; i < tmpList2.size(); i++)
			sprites.add(new Pot(tmpList2.get(i)));
	}
	
	public boolean checkPrev()
    {
		// System.out.println("in check");
		for(int i = 1; i < sprites.size(); i++)
		{
			if(link.prevx + link.w < sprites.get(i).x)
			{
				continue;
			}
			if(link.prevx > sprites.get(i).x + sprites.get(i).w)
			{
				continue;
			}
			if(link.prevy + link.h < sprites.get(i).y)
			{
				continue;
			}
			if(link.prevy > sprites.get(i).y + sprites.get(i).h)
			{
				continue;
			}
			return true;
		}
		return false;
    }
	public boolean checkForDoor()
	{
		if(link.x >= 700 || (link.x + link.w) >= 700)
			return true;
		if(link.y >= 450 || (link.y + link.h) >= 450)
			return true;
		else
			return false;
	}

	public boolean SpriteCollision(Sprite a, Sprite b)
	{
		boolean colliding = true;
		if(a.isLink() && b.isLink())
		{
			System.out.println("Link is colliding with Link");
			return false;
		}

		// boolean colliding = false;
			if((a.x + a.w < b.x) ||(a.x > b.x + b.w) || (a.y + a.h < b.y) || (a.y > b.y + b.h))
			{
				colliding = false;
			}
			else if(a.x + a.w < b.x)
			{
				link.setDirection(Direction.RIGHT);
			}
			else if(a.x > b.x + b.w)
			{
				link.setDirection(Direction.LEFT);
			}
			else if(a.y + a.h < b.y)
			{
				link.setDirection(Direction.DOWN);
			}
			else if(a.y > b.y + b.h)
			{
				link.setDirection(Direction.UP);
			}
			{
				// System.out.println("Colliding");
				return colliding;
			}

	}

	public void launchBoomerang()
	{
		Boomerang b = new Boomerang(link.x,link.y);
		b.setMovement(link.heading);
		// b = new Boomerang((link.x + link.w)/2,(link.y + link.h)/2);
		sprites.add(b);
	}
}