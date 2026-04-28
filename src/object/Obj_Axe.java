package object;

import Entity.Entity;
import main.GamePanel;

public class Obj_Axe extends Entity {

    public Obj_Axe(GamePanel gp) {
        super(gp);

        type = type_axe;
        name = "Old Axe";
        down1 = setup("/object/axe", gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nA cool old axe";
        attackValue = 2;
        attackArea.width = 30;
        attackArea.height = 30;
    }
}

