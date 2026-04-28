package tile_interactive;

import Entity.Entity;
import main.GamePanel;

import java.awt.*;

public class TI_Tree extends InteractiveTile {

    GamePanel gp;

    public TI_Tree(GamePanel gp, int col, int row) {
        super(gp, col, row);
        this.gp = gp;

        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;
        down1 = setup("/tiles_interactive/dryTree0", gp.tileSize, gp.tileSize);
        destructible = true;
        life = 3;
    }

    public boolean requiredItem(Entity entity) {
        boolean requiredItem = false;

        if(entity.currentWeapon.type == type_axe) {
            requiredItem = true;
        }
        return requiredItem;
    }
    public  void playSE() {
        gp.playSE(12);
    }
    public InteractiveTile getDestroyedForm() {
        InteractiveTile tile = new TI_Trunk(gp, worldX / gp.tileSize, worldY / gp.tileSize);
        return tile;
    }
    public Color getParticleColor() {
        Color color = new Color(65, 50, 30);
        return color;
    }
    public int getParticleSize() {
        int size = 6;
        return size;
    }
    public int getParticleSpeed() {
        int speed = 1;
        return speed;
    }
    public int getParticleMaxLife() {
        int maxLife = 20;  // DURATION: HOW LONG THIS PARTICLE LAST
        return maxLife;
    }
}
