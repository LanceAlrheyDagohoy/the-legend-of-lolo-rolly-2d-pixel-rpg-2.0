package object;

import Entity.Entity;
import main.GamePanel;

public class Obj_Health extends Entity {

    GamePanel gp;

    public Obj_Health(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_collectable;
        name = "Health";
        value = 2;

        down1 = setup("/object/heart", gp.tileSize, gp.tileSize);
        image = setup("/object/health0", gp.tileSize, gp.tileSize);
        image1 = setup("/object/health1", gp.tileSize, gp.tileSize);
        image2 = setup("/object/health2", gp.tileSize, gp.tileSize);
    }
    public void use(Entity entity) {
        gp.playSE(1);
        gp.ui.addMessage("Life +" + value);
        entity.life += value;
    }

}
