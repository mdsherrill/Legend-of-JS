/*  Name: Matthew Sherrill
 *  Date: 4/1/2023
 *	Description: Class for the Pot object that shows on the screen that is now child of the Sprite class. 
                 Holds methods/parameter that are specific to Pot.
 */ 

import java.awt.image.BufferedImage;
import java.awt.Graphics;

public class Pot extends Sprite{
    BufferedImage potGood, potBroken;
    boolean broken = false;
    int dirX, dirY, imgSelect, countdown;
    static double speed = 6.9;

    int NUM_FRAMES = 20;

    Pot(int x, int y)
    {
        this.x = x;
        this.y = y;
        this.w = 36;
        this.h = 36;
        if(potGood == null)
            potGood = View.loadImage("newLinkSprites/pot.png");
        if(potBroken == null)
            potBroken = View.loadImage("newLinkSprites/pot_broken.png");
    }

    Pot(Json ob)
    {
        this.x = (int)ob.getLong("x");
        this.y = (int)ob.getLong("y");

        //THis is supposed to work, right?
        this.w = 36; //Fixing variable shadowing
        this.h = 36;
        if(this.potGood == null)
            this.potGood = View.loadImage("newLinkSprites/pot.png");
        if(this.potBroken == null)
            this.potBroken = View.loadImage("newLinkSprites/pot_broken.png");
        
        
    }

    @Override public boolean isPot(){return true;}
    
    public void draw(Graphics g, int scrollX, int scrollY)
    {
        if(broken)
            g.drawImage(potBroken, (this.x - scrollX), (this.y - scrollY), this.w, this.h, null);
        else
            g.drawImage(potGood, (this.x - scrollX), (this.y - scrollY), this.w, this.h, null);
    }
    public Json marshal()
    {
        Json ob = Json.newObject();
        ob.add("x", x); //THESE NEED TO BE THE SAME NAME AS THE CONSTRUC
        ob.add("y", y);
        // ob.add("w", w); //THESE NEED TO BE THE SAME NAME AS THE CONSTRUC
        // ob.add("h", h);
        // System.out.println("this.sprites.push(new Sprite(" + x + ", " + y + ",48, 48, 'newLinkSprites/pot.png', Sprite.prototype.update_pot, Sprite.prototype.pot_init, 'pot', Sprite.prototype.pot_draw));");
        System.out.println("self.sprites.append(Pot(" + x + "," + y + "))");
        return ob;
    }
    
    public boolean update()
    {
        x += dirX*speed;
        y += dirY*speed;
        if(broken)
            countdown++;
        if(countdown >= NUM_FRAMES)
            return false;
        else
            return true;

    }

    public void setMovement(Direction direct)
    {
        if(dirY == 0 && dirX == 0)
        {
        if(direct == Direction.DOWN)
            dirY = 1;
        if(direct == Direction.UP)
            dirY = -1;
        if(direct == Direction.LEFT)
            dirX = -1;
        if(direct == Direction.RIGHT)
            dirX = 1;
        }
    }

    public void setMovement(int a)
    {
        dirY = 0;
        dirX = 0;
    }
    // public Json marshal(){}

    @Override 
    public String toString()
    {
        return "Pot (x,y) = (" + x + ", " + y + ")";
    }
}
