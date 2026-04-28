package object;

import Entity.Entity;
import main.GamePanel;

public class Obj_Shield extends Entity {

    public Obj_Shield(GamePanel gp)  {
        super(gp);

        type = type_shield;
        name = "Normal Shield";
        down1 = setup("/object/starterShield", gp.tileSize, gp.tileSize);
        defenseValue = 1;
        description = "[" + name + "]\nA starter shield.";

    }
}
