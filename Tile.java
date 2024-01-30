/*  Name: Matthew Sherrill
 *  Date: 4/1/2023
 *	Description: Class for the Tile object that shows on the screen that is now child of the Sprite class. 
                 Holds methods/parameter that are specific to Tile.
 */ 
import java.awt.Graphics;
import java.awt.image.BufferedImage;

class Tile extends Sprite{
    BufferedImage tileImage;

    //Methods
    Tile(int x, int y){
        this.x = x;
        this.y = y;
        this.w = 50; //Fixing variable shadowing
        this.h = 50;
        if(tileImage == null)
            tileImage = View.loadImage("newLinkSprites/tile.jpg");
    }

    Tile(Json ob)
    {
        this.x = (int)ob.getLong("x");
        this.y = (int)ob.getLong("y");

        //THis is supposed to work, right?
        this.w = 50; //Fixing variable shadowing
        this.h = 50;
        if(this.tileImage == null)
            this.tileImage = View.loadImage("newLinkSprites/tile.jpg");
        // System.out.println("Tile unmarshalled");
        // try {
        //     System.out.println("this.sprites.push(new Sprite(" + this.x + ", " + this.y + ", 50, 50, 'newLinkSprites/tile.jpg', Sprite.prototype.sit_still, Sprite.prototype.ignore_click));\r");
        //     // System.out.println("Successfully wrote to the file.");
        //   } catch (IOException e) {
        //     // System.out.println("An error occurred.");
        //     e.printStackTrace();
        //   }
    }

    @Override public boolean isTile(){return true;}

    public Json marshal()
    {
        Json ob = Json.newObject();
        ob.add("x", x); //THESE NEED TO BE THE SAME NAME AS THE CONSTRUC
        ob.add("y", y);
        // ob.add("w", w);
        // ob.add("h", h);
        //System.out.println("this.sprites.push(new Sprite(" + x + ", " + y + ", 50, 50, 'newLinkSprites/tile.jpg', Sprite.prototype.sit_still, Sprite.prototype.tile_init, 'tile', Sprite.prototype.draw_tile));");
        System.out.println("self.sprites.append(Tile(" + x + "," + y + "))");
        return ob;
    }
    
    public void draw(Graphics g, int scrollX, int scrollY)
    {
       g.drawImage(tileImage, (this.x - scrollX), (this.y - scrollY), null); // method in model, dont need w, h
    }
    
    public boolean update(){return true;}
}
