package object;

import Entity.Entity;
import Entity.Projectile;
import main.GamePanel;

import java.awt.*;

public class Obj_Rock extends Projectile {

    GamePanel gp;

    public Obj_Rock(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Rock";
        speed = 7;
        maxLife = 40; // RANGE OF THE PROJECTILE
        life = maxLife;
        attack = 2;
        useCost  = 1;
        alive = false;
        getImage();
    }
    public void getImage() {
        down1 = setup("/projectile/rock", gp.tileSize, gp.tileSize);
        down2 = setup("/projectile/rock", gp.tileSize, gp.tileSize);
        left1 = setup("/projectile/rock", gp.tileSize, gp.tileSize);
        left2 = setup("/projectile/rock", gp.tileSize, gp.tileSize);
        right1 = setup("/projectile/rock", gp.tileSize, gp.tileSize);
        right2 = setup("/projectile/rock", gp.tileSize, gp.tileSize);
        up1 = setup("/projectile/rock", gp.tileSize, gp.tileSize);
        up2 = setup("/projectile/rock", gp.tileSize, gp.tileSize);
    }
    public boolean hasResource(Entity user) {
        boolean hasResource = false;
        if(user.ammo >= useCost) {
            hasResource = true;
        }
        return hasResource;
    }
    public void subtractResource(Entity user) {
        user.ammo -= useCost;
    }
    public Color getParticleColor() {
        Color color = new Color(60, 46, 27);
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
