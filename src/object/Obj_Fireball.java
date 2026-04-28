package object;

import Entity.Entity;
import Entity.Projectile;
import main.GamePanel;

import java.awt.*;

public class Obj_Fireball extends Projectile {

    GamePanel gp;

    public Obj_Fireball(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "FireBall";
        speed = 5;
        maxLife = 80; // RANGE OF THE PROJECTILE
        life = maxLife;
        attack = 3;
        useCost  = 1;
        alive = false;
        getImage();
    }
    public void getImage() {
        down1 = setup("/projectile/fireball0", gp.tileSize, gp.tileSize);
        down2 = setup("/projectile/fireball1", gp.tileSize, gp.tileSize);
        left1 = setup("/projectile/fireball2", gp.tileSize, gp.tileSize);
        left2 = setup("/projectile/fireball3", gp.tileSize, gp.tileSize);
        right1 = setup("/projectile/fireball4", gp.tileSize, gp.tileSize);
        right2 = setup("/projectile/fireball5", gp.tileSize, gp.tileSize);
        up1 = setup("/projectile/fireball6", gp.tileSize, gp.tileSize);
        up2 = setup("/projectile/fireball7", gp.tileSize, gp.tileSize);
    }
    public boolean hasResource(Entity user) {
        boolean hasResource = false;
        if(user.mana >= useCost) {
            hasResource = true;
        }
        return hasResource;
    }
    public void subtractResource(Entity user) {
        user.mana -= useCost;
    }
    public Color getParticleColor() {
        Color color = new Color(163, 53, 3);
        return color;
    }
    public int getParticleSize() {
        int size = 10;
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
