package Entity;

import main.GamePanel;

import java.awt.*;
import java.util.Random;

public class NPC_Red extends Entity{

    public NPC_Red(GamePanel gp) {
        super(gp);
        type = type_npc;
        direction = "down";
        speed = 1;

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        getImage();
        setDialogue();
    }
    public void getImage() {
        down1 = setup("/npc/npc0", gp.tileSize, gp.tileSize);
        down2 = setup("/npc/npc1", gp.tileSize, gp.tileSize);
        left1 = setup("/npc/npc2", gp.tileSize, gp.tileSize);
        left2 = setup("/npc/npc3", gp.tileSize, gp.tileSize);
        right1 = setup("/npc/npc4", gp.tileSize, gp.tileSize);
        right2 = setup("/npc/npc5", gp.tileSize, gp.tileSize);
        up1 = setup("/npc/npc6", gp.tileSize, gp.tileSize);
        up2 = setup("/npc/npc7", gp.tileSize, gp.tileSize);



    }
    public void setDialogue() {
        dialogue[0] = "Welcome! Adventurer!";
        dialogue[1] = "Dakdak apo Archival adto Il Corso, \nuyog lubot murag dubai chewy choco";
        dialogue[2] = "nya ana si rolly tukori McDo adto il Corso,";
        dialogue[3] = "kay gigutom iya apo!";
    }

    public void setAction() {

        actionLockCounter++;

        if(actionLockCounter == 120) {
            Random rand = new Random();
            int i = rand.nextInt(100) + 1;

            if(i <= 25) {
                direction = "up";
            }
            if(i > 25 && i <= 50) {
                direction = "down";
            }
            if(i > 50 && i <= 75) {
                direction = "left";
            }
            if(i > 75) {
                direction = "right";
            }
            actionLockCounter = 0;
        }
    }
    public void speak() {
        super.speak();
    }
}
