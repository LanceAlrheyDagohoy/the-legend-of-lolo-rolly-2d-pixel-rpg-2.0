package object;

import Entity.Entity;
import main.GamePanel;

public class Obj_HealthPotion extends Entity {

    GamePanel gp;

    public Obj_HealthPotion(GamePanel gp)  {
        super(gp);
        this.gp = gp;

        type = type_consumable;
        name = "Health Potion";
        value = 5; // HEALING VALUE
        down1 = setup("/object/healthPotion", gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nRestores your HP by " + value + ".";

    }
    public void use(Entity entity) {
        gp.gameState = gp.dialogueState;
        gp.ui.currentDialogue = "You drink the " + name + ", you restored your \nhealth by " + value +  "!";
        entity.life += value;
        gp.playSE(10);


    }
}
