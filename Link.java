/*  Name: Matthew Sherrill
 *  Date: 4/1/2023
 *	Description: Class for the Link object that shows on the screen that is now child of the Sprite class. 
                 Holds methods/parameter that are specific to Link.
 */ 

import java.awt.image.BufferedImage;
import java.awt.Graphics;

enum Direction 
{
    LEFT(1), 
    RIGHT(0), 
    UP(2),
    DOWN(3);
    public final int direction;

    private Direction(int d)
    {
        this.direction = d;
    }
}

//MOVE SEQUENCE - RIGHT[0-7], LEFT[8-15], UP[16-25], DOWN[26-32]
public class Link extends Sprite {
    int prevx, prevy, currentImage, dir;
    final int MAX_IMAGES = 32;
    final int MOVE_INC = 8;

    double speed = 6.9;
    Direction heading;
    BufferedImage link_images[];
    
    public Link(int x, int y)
    {
        this.x = x;
        this.y = y;
        this.w = 18*2;
        this.h = 24*2;
        this.prevx = 0;
        this.prevy = 0;
        heading = Direction.RIGHT;
        loadLink();
    }
    public void setDirection(Direction input){
        heading = input;
    }
    public Direction getDirection(){
        return heading;
    }

    @Override public boolean isLink(){return true;}

    public void loadLink()
    {
        link_images = new BufferedImage[33];
        for(int i = 0; i <= 32; i++){
        //Complicated due to new sprite file names
            if(link_images[i] == null){
                if(i < 8){
                    link_images[i] = View.loadImage("newLinkSprites/linkRight" + i + ".png");
                    // System.out.println("Loaded newLinkSprites/linkRight" + i + ".png");
                    System.out.println("self.link_images.append(pygame.transform.scale2x(pygame.image.load('newLinkSprites/linkRight" + i + ".png')))");
                }
                if(i >= 8 && i < 16){
                    link_images[i] = View.loadImage("newLinkSprites/linkLeft" + (i-8) + ".png");
                    // System.out.println("newLinkSprites/linkLeft" + (i-8) + ".png");
                    System.out.println("self.link_images.append(pygame.transform.scale2x(pygame.image.load('newLinkSprites/linkLeft" + (i-8) + ".png')))");

                }
                if(i >= 16 && i < 24){
                    link_images[i] = View.loadImage("newLinkSprites/linkUp" + (i-16) + ".png");
                    // System.out.println("newLinkSprites/linkUp" + (i-16) + ".png");
                    System.out.println("self.link_images.append(pygame.transform.scale2x(pygame.image.load('newLinkSprites/linkUp" + (i-16) + ".png')))");

                }
                if(i >= 24 && i < 32){
                    link_images[i] = View.loadImage("newLinkSprites/linkDown" + (i-24) + ".png");
                    // System.out.println("newLinkSprites/linkDown" + (i-24) + ".png");
                    System.out.println("self.link_images.append(pygame.transform.scale2x(pygame.image.load('newLinkSprites/linkDown" + (i-24) + ".png')))");

                }
            }
            // System.out.println("self.link_images.append(pygame.image.load(" + ));
        }
    }

    public void setBack()
    {
        x=prevx;
        y=prevy;
    }
    
    public void setPrevious()
    {
        this.prevx = x;
        this.prevy = y;
    }

    public void updateImageNum(Direction input)
	{
		heading = input;
		currentImage++;
		if (currentImage >= MOVE_INC)
			currentImage = 0;	
	}
    
    public void draw(Graphics g, int scrollX, int scrollY)
    {
        if(heading != null)
            g.drawImage(link_images[currentImage + heading.direction*MOVE_INC], (this.x - scrollX), (this.y - scrollY), this.w, this.h, null); // method in model, dont need w, h
    }

    // @Override
    public boolean update(){return true;}

    public Json marshal()
    {
        Json ob = Json.newObject();
        ob.add("x", x); //THESE NEED TO BE THE SAME NAME AS THE CONSTRUC
        ob.add("y", y);
        ob.add("w", w); //THESE NEED TO BE THE SAME NAME AS THE CONSTRUC
        ob.add("h", h);
        return ob;
    }
}
