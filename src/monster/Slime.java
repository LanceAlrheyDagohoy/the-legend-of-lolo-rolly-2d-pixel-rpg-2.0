package monster;

import Entity.Entity;
import main.GamePanel;
import object.Obj_Coin;
import object.Obj_Health;
import object.Obj_Mana;
import object.Obj_Rock;

import java.awt.*;
import java.util.Random;

public class Slime extends Entity {
    GamePanel gp;
    public Slime(GamePanel gp) {
        super(gp);

        this.gp = gp;

        type = type_monster;
        name = "Blue Slime";
        speed = 1;
        maxLife = 6;
        life = maxLife;
        attack = 5;
        defense = 0;
        exp = 2;
        projectile = new Obj_Rock(gp);

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }

    public void getImage() {
        up1 = setup("/monster/slime0", gp.tileSize, gp.tileSize);
        up2 = setup("/monster/slime2", gp.tileSize, gp.tileSize);

        down1 = setup("/monster/slime0", gp.tileSize, gp.tileSize);
        down2 = setup("/monster/slime2", gp.tileSize, gp.tileSize);

        left1 = setup("/monster/slime0", gp.tileSize, gp.tileSize);
        left2 = setup("/monster/slime2", gp.tileSize, gp.tileSize);

        right1 = setup("/monster/slime0", gp.tileSize, gp.tileSize);
        right2 = setup("/monster/slime2", gp.tileSize, gp.tileSize);
    }

    public void setAction() {
        actionLockCounter++;

        if(actionLockCounter == 120) {
            Random rand = new Random();
            int i = rand.nextInt(100) + 1;

            if(i <= 25) {
                direction = "up";
            }
            if(i > 25 && i <= 50) {
                direction = "down";
            }
            if(i > 50 && i <= 75) {
                direction = "left";
            }
            if(i > 75) {
                direction = "right";
            }
            actionLockCounter = 0;
        }
        int i = new Random().nextInt(100) + 1;
        if (i > 99  && !projectile.alive && shotAvailableCounter == 30) {
            projectile.set(worldX, worldY, direction, true, this);
            gp.projectileList.add(projectile);
            shotAvailableCounter = 0;
        }
    }
    public void damageReaction() {

        actionLockCounter = 0;
        direction = gp.player.direction; // IF
    }

    public void checkDrop() {
        // CAST A DIE
        int i = new Random().nextInt(100) + 1;

        //SET THE MONSTER DROP
        if(i < 50) {
            dropItem(new Obj_Coin(gp));
        }
        if(i >= 50 && i < 75) {
            dropItem(new Obj_Health(gp));
        }
        if(i >= 75 && i < 100) {
            dropItem(new Obj_Mana(gp));
        }
    }
}

