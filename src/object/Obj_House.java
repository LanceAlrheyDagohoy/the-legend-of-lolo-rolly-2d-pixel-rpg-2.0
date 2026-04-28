package object;

import Entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Obj_House extends Entity {
    GamePanel gp;

    public Obj_House(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "House";
        // Since your image is 96x96 and standard tiles are usually 48x48,
        // this house is 2 tiles wide and 2 tiles high.
        // If your tiles are 32x32, this is 3x3 tiles.
        // Adjust multiplier based on your tile size

        down1 = setup("/object/house", gp.tileSize * 3, gp.tileSize * 3);
        collision = true;
        type = type_obstacle;

        // COLLISION BOX ADJUSTMENT
        // We want the solid part to be just the bottom walls
        solidArea.x = 23; // 8 pixels in from the left
        solidArea.y = 43; // 56 pixels down from the top (adjust this based on your roof height)
        solidArea.width = (gp.tileSize * 2) ; // width minus 8 pixels on each side
        solidArea.height = (gp.tileSize * 2) - 10; // height minus the roof area

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    // Override draw because we don't want the house to "animate"
    // or change based on direction like a character does.
    @Override
    public void draw(java.awt.Graphics2D g2) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        // Only draw if it's on screen
        if (worldX + gp.tileSize * 3 > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize * 3 > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

            g2.drawImage(down1, screenX, screenY, null);
        }
        // Temporary debug line to see collision box
        g2.setColor(Color.red);
        g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
    }
}
