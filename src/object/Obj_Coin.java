package object;

import Entity.Entity;
import main.GamePanel;

public class Obj_Coin extends Entity {

    GamePanel gp;

    public Obj_Coin(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_collectable;
        name = "Silver Coin";
        value = 1;
        down1 = setup("/object/coin", gp.tileSize, gp.tileSize);
    }

    public void use(Entity entity) {
        gp.playSE(1);
        gp.ui.addMessage("Coin: " + value);
        gp.player.coin += value;
    }

}
