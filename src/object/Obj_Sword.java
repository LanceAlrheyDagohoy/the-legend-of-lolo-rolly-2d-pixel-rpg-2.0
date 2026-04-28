package object;

import Entity.Entity;
import main.GamePanel;

public class Obj_Sword extends Entity {

    public Obj_Sword(GamePanel gp)  {
        super(gp);

        type = type_sword;
        name = "Normal Sword";
        down1 = setup("/object/starterSword", gp.tileSize, gp.tileSize);
        attackValue = 2;
        description = "[" + name + "]\nA starter sword";
        attackArea.width = 36;
        attackArea.height = 36;
    }
}
