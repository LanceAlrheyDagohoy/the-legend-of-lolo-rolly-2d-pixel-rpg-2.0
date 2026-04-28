package object;

import Entity.Entity;
import main.GamePanel;

public class Obj_IronShield  extends Entity {
    public Obj_IronShield(GamePanel gp)  {
        super(gp);

        type = type_shield;
        name = "Iron Shield";
        down1 = setup("/object/newShield", gp.tileSize, gp.tileSize);
        defenseValue = 2;
        description = "[" + name + "]\nA shield from Empire \nof Carmen.";

    }
}
