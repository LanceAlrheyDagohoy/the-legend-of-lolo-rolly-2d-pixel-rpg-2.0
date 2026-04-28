package object;

import Entity.Entity;
import main.GamePanel;

public class Obj_Table extends Entity {

    public Obj_Table(GamePanel gp) {
        super(gp);

        name = "Table";
        down1 = setup("/object/table", gp.tileSize, gp.tileSize);
        collision = true;
        type = type_obstacle;
    }
}
