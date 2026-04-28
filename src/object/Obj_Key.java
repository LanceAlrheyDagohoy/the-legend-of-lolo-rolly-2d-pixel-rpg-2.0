package object;

import Entity.Entity;
import main.GamePanel;

public class Obj_Key extends Entity {

    public Obj_Key(GamePanel gp) {
        super(gp);

        name = "Key";
        down1 = setup("/object/key", gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nIt opens a door.";
        collision = true;
    }
}
