/*  Name: Matthew Sherrill
 *  Date: 4/1/2023
 *	Description: Class for the Boomerang object that shows on the screen that is now child of the Sprite class. 
                 Holds methods/parameter that are specific to Boomerang.
 */ 

import java.awt.image.BufferedImage;
import java.awt.Graphics;

class Boomerang extends Sprite {
    
    BufferedImage boomerangs[];
    int dirX, dirY, imgSelect;
    double speed = 6.9;

    Boomerang(int x, int y)
    {
        this.x = x;
        this.y = y;
        this.w = 8;
        this.h = 12;

        boomerangs = new BufferedImage[5];
        // IMAGES: 1 Left, 2 Up, 3 Right, 4 Down
        boomerangs[0] = View.loadImage("newLinkSprites/boomerang" + 1 + ".png");
        for(int i=1; i < 5; i++)
        {
            boomerangs[i] = View.loadImage("newLinkSprites/boomerang" + i + ".png");
        }
    }
    //----------------------------------------
    // SPRITE METHODS
    //----------------------------------------
    @Override public boolean isBoomerang(){return true;}
    public void draw(Graphics g, int scrollX, int scrollY)
    {
        g.drawImage(boomerangs[imgSelect], (this.x - scrollX), (this.y - scrollY), null); // method in model, dont need w, h
    }
    public Json marshal()
    {
        Json ob = Json.newObject();
        ob.add("x", x); //THESE NEED TO BE THE SAME NAME AS THE CONSTRUC
        ob.add("y", y);
        ob.add("w", w); //THESE NEED TO BE THE SAME NAME AS THE CONSTRUC
        ob.add("h", h);
        return ob;
    }
    public boolean update()
    {
        x += dirX*speed;
        y += dirY*speed;
        return true;
    }

    // NON-SPRITE METHODS
    public void setMovement(Direction direct)
    {
        if(direct == Direction.DOWN)
        {
            dirY = 1;
            imgSelect = 4;
        }
        if(direct == Direction.UP)
        {
            dirY = -1;
            imgSelect = 2;
        }
        if(direct == Direction.LEFT)
        {
            dirX = -1;
            imgSelect = 1;
        }
        if(direct == Direction.RIGHT)
        {
            dirX = 1;
            imgSelect = 3;
        }
    }
    @Override 
    public String toString()
    {
        return "Boomerang (x,y) = (" + x + ", " + y + ")";
    }
}
