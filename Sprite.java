/*  Name: Matthew Sherrill
 *  Date: 4/1/2023
 *	Description: Finally! A superclass to simplify all otherobjects in our game into one class that shares all variables.
 */ 

import java.awt.Graphics;

public abstract class Sprite
{
    public int x, y, w, h;
    
    public boolean isLink(){return false;}
    public boolean isPot(){return false;}
    public boolean isBoomerang(){return false;}
    public boolean isTile(){return false;}

    public abstract void draw(Graphics g, int scrollX, int scrollY);
    public abstract Json marshal();
    public abstract boolean update();

    @Override 
    public String toString()
    {
        if (isTile())
            return "Tile (x,y) = (" + x + ", " + y + "), w = " + w + ", h = " + h;
        else if (isLink())
            return "Link (x,y) = (" + x + ", " + y + "), w = " + w + ", h = " + h;
        else
            return "Sprite (x,y) = (" + x + ", " + y + "), w = " + w + ", h = " + h;
    }

    public boolean CheckExisting(int mX, int mY)
    {
        if (mX >= x && mX <= x && mY >= y && mY <= y) //x+w and y+h removed tiles down and to the right
            return true;
        else
            return false;
    }
}