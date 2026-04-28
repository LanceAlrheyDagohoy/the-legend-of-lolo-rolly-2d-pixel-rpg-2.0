package Entity;

import main.GamePanel;
import object.Obj_Boots;
import object.Obj_HealthPotion;
import object.Obj_IronShield;
import object.Obj_Sword;

import java.awt.*;

public class NPC_Merchant extends Entity {

    public NPC_Merchant(GamePanel gp) {
        super(gp);
        type = type_npc ;
        direction = "down";
        speed = 0;

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidArea.width = 25;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
        setDialogue();
        setItem();
    }
    public void getImage() {
        down1 = setup("/npc/merchant0", gp.tileSize, gp.tileSize);
        down2 = setup("/npc/merchant1", gp.tileSize, gp.tileSize);
        left1 = setup("/npc/merchant0", gp.tileSize, gp.tileSize);
        left2 = setup("/npc/merchant1", gp.tileSize, gp.tileSize);
        right1 = setup("/npc/merchant0", gp.tileSize, gp.tileSize);
        right2 = setup("/npc/merchant1", gp.tileSize, gp.tileSize);
        up1 = setup("/npc/merchant0", gp.tileSize, gp.tileSize);
        up2 = setup("/npc/merchant1", gp.tileSize, gp.tileSize);
    }
    public void setDialogue() {
        dialogue[0] = "Welcome Boy'a! Would you like to purchase some of \nmy items?";
    }
    public void setItem() {
        inventory.add(new Obj_HealthPotion(gp));
        inventory.add(new Obj_Sword(gp));
        inventory.add(new Obj_Boots(gp));
        inventory.add(new Obj_IronShield(gp));
    }
    public void speak() {
        super.speak();
        gp.gameState = gp.tradeState;
        gp.ui.npc = this;
    }
}
