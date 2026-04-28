package object;

import Entity.Entity;
import main.GamePanel;

import java.awt.*;

public class Obj_Door extends Entity {

    GamePanel gp;

    public Obj_Door(GamePanel gp) {

        super(gp);
        this.gp = gp;

        name = "Door";
        down1 = setup("/object/houseDoor", gp.tileSize, gp.tileSize);
        collision = true;
        type = type_door;

        solidArea.x = 9;
        solidArea.y = 15;
        solidArea.width = 30;
        solidArea.height = 20;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
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
