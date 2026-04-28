package object;

import Entity.Entity;
import main.GamePanel;


public class Obj_Boots extends Entity {
    public Obj_Boots(GamePanel gp) {

        super(gp);
        name = "Boots";
        down1 = setup("/object/boots", gp.tileSize, gp.tileSize);
    }
}
