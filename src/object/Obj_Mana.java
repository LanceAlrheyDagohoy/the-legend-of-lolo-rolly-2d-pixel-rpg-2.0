package object;

import Entity.Entity;
import main.GamePanel;

public class Obj_Mana extends Entity {

    GamePanel gp;

    public Obj_Mana(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_collectable;
        name = "Mana";
        value = 1;
        down1 = setup("/object/manaCrystal", gp.tileSize, gp.tileSize);
        image = setup("/object/mana0", gp.tileSize, gp.tileSize);
        image1 = setup("/object/mana2", gp.tileSize, gp.tileSize);
    }
    public void use(Entity entity) {
        gp.playSE(1);
        gp.ui.addMessage("Mana +" + value);
        entity.mana += value;
    }
}
