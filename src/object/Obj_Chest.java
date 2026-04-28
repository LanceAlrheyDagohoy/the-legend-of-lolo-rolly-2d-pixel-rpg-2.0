package object;

import Entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Obj_Chest extends Entity {

    public Obj_Chest(GamePanel gp) {
        super(gp);

        name = "Chest";
        down1 = setup("/object/chest", gp.tileSize, gp.tileSize);
    }
}
