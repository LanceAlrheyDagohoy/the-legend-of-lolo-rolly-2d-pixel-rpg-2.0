package Entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Entity {
    GamePanel gp;

    public BufferedImage up1, up2, down1, down2, right1, right2, left1, left2;
    public BufferedImage atkUp1, atkUp2, atkDown1, atkDown2, atkLeft1, atkLeft2, atkRight1, atkRight2;
    public BufferedImage image, image1, image2;
    public Rectangle solidArea = new Rectangle(0, 0 , 48, 48);
    public Rectangle attackArea = new Rectangle(0, 0, 0 ,0);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    String[] dialogue = new String[20];

    //STATE
    public int worldX, worldY;
    public String direction = "down";
    public int spriteNum = 1;
    int dialogueIndex = 0;
    public boolean invincible = false;
    public boolean collision = false;
    public boolean attacking;
    public boolean alive = true;
    public boolean dying = false;
    public boolean healthBarOn = false;

    //COUNTER
    public int spriteCounter = 0;
    public int actionLockCounter = 0;
    public int invincibleCounter;
    public int dyingCounter = 0;
    public int healthBarCounter = 0;
    public int shotAvailableCounter = 0;

    //CHARACTER ATTRIBUTES
    public String name;
    public int speed;
    public int maxLife;
    public int life;
    public int maxMana;
    public int mana;
    public int ammo;
    public int level;
    public int dexterity;
    public int strength;
    public int attack;
    public int defense;
    public int exp;
    public int nextLevel;
    public int coin;
    public Entity currentWeapon;
    public Entity currentShield;
    public Projectile projectile;

    //ITEM ATTRIBUTES
    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxInventorySize = 20;
    public int value;
    public int attackValue;
    public int defenseValue;
    public String description = "";
    public int useCost;

    //TYPE
    public int type; //0 - player, 1 - npc, 2 - monster etc
    public final int type_player = 0;
    public final int type_npc = 1;
    public final int type_monster = 2;
    public final int type_sword = 3;
    public final int type_axe = 4;
    public final int type_shield = 5;
    public final int type_consumable = 6;
    public final int type_collectable = 7;
    public final int type_obstacle = 8;
    public final int type_door = 9;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }
    public void setAction() {}
    public void damageReaction() {}
    public void use(Entity entity) {}
    public void checkDrop() {}
    public void dropItem(Entity droppedItem) {
        for(int i = 0; i < gp.obj[1].length; i++) {
            if(gp.obj[gp.currentMap][i] == null) {
                gp.obj[gp.currentMap][i] = droppedItem;
                gp.obj[gp.currentMap][i].worldX = worldX; //(x,y) WHERE THE ITEMS WILL DROPPED
                gp.obj[gp.currentMap][i].worldY = worldY;
                break;
            }
        }
    }
    public void speak() {


        if(dialogue[dialogueIndex] == null) {
            dialogueIndex = 0;
        }
        gp.ui.currentDialogue = dialogue[dialogueIndex];
        dialogueIndex++;

        switch (gp.player.direction) {
            case "up": direction = "down"; break;
            case "down": direction = "up"; break;
            case "left": direction = "right"; break;
            case "right": direction = "left"; break;
        }
    }
    public void update() {
        setAction();

        collisionOn = false;
        gp.cc.checkTile(this);
        gp.cc.checkObject(this, false);
        gp.cc.checkEntity(this, gp.npc);
        gp.cc.checkEntity(this, gp.monster);
        gp.cc.checkEntity(this, gp.iTile);
        boolean contactPlayer = gp.cc.checkPlayer(this);

        if(this.type == type_monster && contactPlayer) {
            damagePlayer(attack);
        }

        if(!collisionOn) {
            switch (direction) {
                case "up": worldY -= speed; break;
                case "down": worldY += speed; break;
                case "left": worldX -= speed; break;
                case "right": worldX += speed; break;
            }
        }
        spriteCounter++;
        if(spriteCounter > 24) {
            if(spriteNum == 1) {
                spriteNum = 2;
            }
            else if(spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }

        if(invincible) {
            invincibleCounter++;
            if(invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
        if(shotAvailableCounter < 30) { // PREVENT SHOOTING 2 FIREBALLS IN CLOSE DISTANCE
            shotAvailableCounter++;
        }
    }
    public Color getParticleColor() {
        Color color = null;
        return color;
    }
    public int getParticleSize() {
        int size = 0;
        return size;
    }
    public int getParticleSpeed() {
        int speed = 0;
        return speed;
    }
    public int getParticleMaxLife() {
        int maxLife = 0;  // DURATION: HOW LONG THIS PARTICLE LAST
        return maxLife;
    }
    public void generateParticle(Entity generator, Entity target) {
        Color color = generator.getParticleColor();
        int size = generator.getParticleSize();
        int speed = generator.getParticleSpeed();
        int maxLife = generator.getParticleMaxLife();

        Particle p1 = new Particle(gp, target, color, size, speed, maxLife, -2, -1); // TOP LEFT
        Particle p2 = new Particle(gp, target, color, size, speed, maxLife, 2, -1); // TOP RIGHT
        Particle p3 = new Particle(gp, target, color, size, speed, maxLife, -2, 1); // DOWN LEFT
        Particle p4 = new Particle(gp, target, color, size, speed, maxLife, 2, 1); // DOWN RIGHT
        gp.particleList.add(p1);
        gp.particleList.add(p2);
        gp.particleList.add(p3);
        gp.particleList.add(p4);
    }

    public void damagePlayer(int attack) {
        if(!gp.player.invincible) {
            gp.playSE(6);
            int damage = attack - gp.player.defense;
            if(damage < 0) {
                damage = 0;
            }
            gp.player.life -= damage;
            gp.player.invincible = true;
        }
    }

    public void draw(Graphics2D g2) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

            BufferedImage image = null;

            switch (direction) {
                case "up":
                    if(spriteNum == 1) { image = up1; }
                    if(spriteNum == 2) { image = up2; }
                    break;
                case "down":
                    if(spriteNum == 1) { image = down1; }
                    if(spriteNum == 2) { image = down2; }
                    break;
                case "right":
                    if(spriteNum == 1) { image = right1; }
                    if(spriteNum == 2) { image = right2; }
                    break;
                case "left":
                    if(spriteNum == 1) { image = left1; }
                    if(spriteNum == 2) { image = left2; }
                    break;
            }

            //MONSTER HEALTH BAR
            if(type == 2 && healthBarOn) {

                double oneScale = (double)gp.tileSize / maxLife;
                double healthBarValue = oneScale * life;

                //OUTLINE
                g2.setColor(new Color(35, 35, 35));
                g2.fillRect(screenX - 1, screenY - 16, gp.tileSize + 2,  12);

                g2.setColor(new Color(255, 0, 30));
                g2.fillRect(screenX, screenY - 15, (int)healthBarValue, 10);

                healthBarCounter++;

                if(healthBarCounter > 600) { //600 = 10s
                    healthBarCounter = 0;
                    healthBarOn = false;
                }
            }


            if(invincible) {
                healthBarOn = true;
                healthBarCounter = 0;
                changeAlpha(g2, 0.4f);
            }
            if(dying) { dyingAnimation(g2);}

            g2.drawImage(image, screenX, screenY, null);
            changeAlpha(g2, 1f);

            // Temporary debug line to see collision box

            g2.setColor(Color.red);
            g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
        }
    }

    public void dyingAnimation(Graphics2D g2) {

        dyingCounter++;
        int i = 5;

        if(dyingCounter <= i) { changeAlpha(g2, 0f); }
        if(dyingCounter > i && dyingCounter <= i * 2) { changeAlpha(g2, 1f); }
        if(dyingCounter > i * 2 && dyingCounter <= i * 3) { changeAlpha(g2, 0f); }
        if(dyingCounter > i * 3 && dyingCounter <= i * 4) { changeAlpha(g2, 1f); }
        if(dyingCounter > i * 4 && dyingCounter <= i * 5) { changeAlpha(g2, 0f); }
        if(dyingCounter > i * 5 && dyingCounter <= i * 6) { changeAlpha(g2, 1f); }
        if(dyingCounter > i * 6 && dyingCounter <= i * 7) { changeAlpha(g2, 0f); }
        if(dyingCounter > i * 7 && dyingCounter <= i * 8) { changeAlpha(g2, 1f); }
        if(dyingCounter > i * 8) {

            alive = false;
        }
    }

    public void changeAlpha(Graphics2D g2, float alphaValue) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }


    public  BufferedImage setup(String imagePath, int width, int height) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResourceAsStream( imagePath + ".png"));
            image = uTool.scaleImage(image, width, height);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}

