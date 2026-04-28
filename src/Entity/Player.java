package Entity;

import main.GamePanel;
import main.KeyHandler;
import object.Obj_Fireball;
import object.Obj_Key;
import object.Obj_Shield;
import object.Obj_Sword;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class Player extends Entity {

    KeyHandler kh;
    public final int screenX;
    public final int screenY;
    int standCounter = 0;
    public boolean cancelAttack = false;



    public Player(GamePanel gp, KeyHandler kh) {
        super(gp);
        this.kh = kh;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2); //center coordinates of character

        solidArea = new Rectangle(18, 20, 10, 20);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
        setItems();
    }
    public void setDefaultValues() {
        worldX = gp.tileSize * 7; //starting position
        worldY = gp.tileSize * 7; // or player spawn point
        speed = 4;
        direction = "down";

        //PLAYER STATUS
        level = 1;
        maxLife = 100;
        life = maxLife;
        maxMana = 5;
        mana = maxMana;
        ammo = 10;
        strength = 1;
        dexterity = 1;
        exp = 0;
        nextLevel = 5;
        coin = 0;
        currentWeapon = new Obj_Sword(gp);
        currentShield = new Obj_Shield(gp);
        projectile = new Obj_Fireball(gp);
        attack = getAttack();   //THE TOTAL ATTACK IS DETERMINED BY THE STRENGTH AND WEAPON
        defense = getDefense(); //TOTAL DEFENSE VALUE IS DETERMINED BY THE DEXTERITY AND SHIELD
    }

    public void setDefaultPosition() {
        worldX = gp.tileSize * 7; //starting position
        worldY = gp.tileSize * 7; // or player spawn point
        direction = "down";
    }
    public void restoreLifeAndMana() {
        life = maxLife;
        mana = maxMana;
        invincible = false;
    }
    public void setItems() {
        inventory.clear();
        inventory.add(currentWeapon);
        inventory.add(currentShield);
        inventory.add(new Obj_Key(gp));
        inventory.add(new Obj_Key(gp));
    }
    public int getAttack() {
        attackArea = currentWeapon.attackArea;
        return  attack = strength * currentWeapon.attackValue;
    }
    public int getDefense() {
        return defense = dexterity * currentShield.defenseValue;
    }
    public void getPlayerImage() {

        up1 = setup("/player/U_right", gp.tileSize, gp.tileSize);
        up2 = setup("/player/U_left", gp.tileSize, gp.tileSize);
        down1 = setup("/player/D_right", gp.tileSize, gp.tileSize);
        down2 = setup("/player/D_left", gp.tileSize, gp.tileSize);
        right1 = setup("/player/R_right", gp.tileSize, gp.tileSize);
        right2 = setup("/player/R_left", gp.tileSize, gp.tileSize);
        left1 = setup("/player/L_right", gp.tileSize, gp.tileSize);
        left2 = setup("/player/L_left", gp.tileSize, gp.tileSize);
    }
    public void getPlayerAttackImage() {
        if(currentWeapon.type == type_sword) {
            atkDown1 = setup("/player/atkY0", gp.tileSize, gp.tileSize * 2); //64x32
            atkDown2= setup("/player/atkY1", gp.tileSize, gp.tileSize * 2);
            atkUp1 = setup("/player/atkY2", gp.tileSize, gp.tileSize * 2);
            atkUp2 = setup("/player/atkY3", gp.tileSize, gp.tileSize * 2);
            atkLeft1 = setup("/player/atkX0", gp.tileSize * 2, gp.tileSize);
            atkLeft2 = setup("/player/atkX1", gp.tileSize * 2, gp.tileSize);
            atkRight1 = setup("/player/atkX2", gp.tileSize * 2, gp.tileSize);
            atkRight2 = setup("/player/atkX3", gp.tileSize * 2, gp.tileSize);
        }
        if(currentWeapon.type == type_axe) {
            atkDown1 = setup("/player/axeY0", gp.tileSize, gp.tileSize * 2); //64x32
            atkDown2= setup("/player/axeY1", gp.tileSize, gp.tileSize * 2);
            atkUp1 = setup("/player/axeY2", gp.tileSize, gp.tileSize * 2);
            atkUp2 = setup("/player/axeY3", gp.tileSize, gp.tileSize * 2);

            atkLeft1 = setup("/player/axeX0", gp.tileSize * 2, gp.tileSize);
            atkLeft2 = setup("/player/axeX1", gp.tileSize * 2, gp.tileSize);
            atkRight1 = setup("/player/axeX2", gp.tileSize * 2, gp.tileSize);
            atkRight2 = setup("/player/axeX3", gp.tileSize * 2, gp.tileSize);
        }
    }
    public void update() {

        if(attacking) {
            attacking();
        }

        else if(kh.upPressed || kh.downPressed || kh.rightPressed || kh.leftPressed || kh.enterPressed) {
            if(kh.upPressed) { //  if(kh.upPressed == true)*
                direction = "up";
            }
            else if(kh.downPressed) {
                direction = "down";
            }
            else if(kh.leftPressed) {
                direction = "left";
            }
            else if(kh.rightPressed) {
                direction = "right";
            }
            //Check Tile Collision
            collisionOn = false;
            gp.cc.checkTile(this);

            //CHECK OBJECT COLLISION
            int objIndex = gp.cc.checkObject(this, true);
            pickUpObject(objIndex);

            //CHECK NPC COLLISION
            int npcIndex = gp.cc.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            //CHECK MONSTER COLLISION
            int monsterIndex = gp.cc.checkEntity(this, gp.monster);
            contactMonster(monsterIndex);

            //CHECK INTERACTIVE TILE COLLISION
            int iTileIndex = gp.cc.checkEntity(this, gp.iTile);

            //CHECK EVENT
            gp.eHandler.checkEvent();

            //if collision is false, player can move
            if(!collisionOn && !kh.enterPressed) {
                switch (direction) {
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "left": worldX -= speed; break;
                    case "right": worldX += speed; break;
                }
            }
            if(kh.enterPressed && !cancelAttack) {
                    gp.playSE(5);
                    attacking = true;
                    spriteCounter = 0;
            }
            cancelAttack = false;
            gp.kh.enterPressed = false;

            spriteCounter++;
            if(spriteCounter > 12) {
                if(spriteNum == 1) {
                    spriteNum = 2;
                }
                else if(spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        } else {
            standCounter++;
            if(standCounter == 20) {
                spriteNum = 1;
                standCounter = 0;
            }
        }
        if(gp.kh.shotKeyPressed && !projectile.alive && shotAvailableCounter == 30 && projectile.hasResource(this)) { // THIS CONDITION IS YOU CAN SHOOT ONE AT A TIME
            //SET DEFAULT COORDINATES, DIRECTION, AND USER
            projectile.set(worldX, worldY, direction, true, this);

            //SUBTRACT COST (EX. MANA, AMMO, etc)
            projectile.subtractResource(this);
            // ADD IT TO THE LIST
            gp.projectileList.add(projectile);
            shotAvailableCounter = 0;
            gp.playSE(11);
        }
        //THIS NEEDS TO BE OUTSIDE OF IF(KEY) STATEMENT
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
        if(life > maxLife) {
           life = maxLife;
        }
        if(mana > maxMana) {
            mana = maxMana;
        }
        if(life <= 0) {
            gp.gameState = gp.gameOverState;
            gp.ui.commandNum = -1;
            gp.stopMusic();
            gp.playSE(13);
        }
    }
    public void attacking() {
        spriteCounter++;

        //ATK ANIMATION
        if(spriteCounter <= 5) {
            spriteNum = 1;
        }
        if(spriteCounter > 5 && spriteCounter <= 25) {
            spriteNum = 2;
            //SAVE CURRENT wX, wY, SOLID AREA
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            //ADJUST PLAYER'S WORLD X/Y FOR THE ATTACK AREA
            switch (direction) {
                case "up": worldY -= attackArea.height; break;
                case "down": worldY += attackArea.height; break;
                case "left": worldX -= attackArea.width; break;
                case "right": worldX += attackArea.width; break;
            }
            //ATTACK AREA BECOMES SOLID
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            //CHECK MONSTER COLLISION WITH THE UPDATED WORLD X/Y AND SOLID AREA
            int monsterIndex = gp.cc.checkEntity(this, gp.monster);
            damageMonster(monsterIndex, attack);

            int iTileIndex = gp.cc.checkEntity(this, gp.iTile);
            damageInteractiveTile(iTileIndex);

            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }
        if(spriteCounter > 25) {
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }
    public void pickUpObject(int i) {
        if (i != 999) {
            //COLLECTABLE ONLY OBJECTS
            if(gp.obj[gp.currentMap][i].type == type_collectable) {
                gp.obj[gp.currentMap][i].use(this);
                gp.obj[gp.currentMap][i] = null;
            }
            //OBSTACLE TYPE
            else if(gp.obj[gp.currentMap][i].type == type_obstacle) {
            }
            else if(gp.obj[gp.currentMap][i].type == type_door) {
                gp.eHandler.teleport(1, 17, 24);
            }
            // INVENTORY ITEMS
            else {
                String text;

                if(inventory.size() != maxInventorySize) {
                    inventory.add(gp.obj[gp.currentMap][i]);
                    gp.playSE(2);

                    text = "You got a " + gp.obj[gp.currentMap][i].name + "!";
                }
                else {
                    text = "Your inventory is full!";
                }
                gp.ui.addMessage(text);
                gp.obj[gp.currentMap][i] = null;
            }
        }
    }
    public void interactNPC(int i) {
        if(gp.kh.enterPressed) {
            if(i != 999) {
                cancelAttack = true;
                gp.gameState = gp.dialogueState;
                gp.npc[gp.currentMap][i].speak();
            }
        }
    }
    public void contactMonster(int i) {
        //RECEIVE DAMAGE
        if(i != 999) {
            if (!invincible && !gp.monster[gp.currentMap][i].dying) {
                gp.playSE(6);

                int damage = gp.monster[gp.currentMap][i].attack - defense;
                if (damage < 0) {
                    damage = 0;
                }
                life -= damage;
                invincible = true;
            }

        }
    }
    public void damageMonster(int i, int attack) {
        if( i != 999) {
            if(!gp.monster[gp.currentMap][i].invincible) {

                gp.playSE(5);

                int damage = attack - gp.monster[gp.currentMap][i].defense;
                if(damage < 0) {
                    damage = 0;
                }
                gp.monster[gp.currentMap][i].life -= damage;
                gp.ui.addMessage(damage + " damage!");

                gp.monster[gp.currentMap][i].invincible = true;
                gp.monster[gp.currentMap][i].damageReaction();
                //MONSTER: KILLED
                if(gp.monster[gp.currentMap][i].life <= 0) {
                    gp.monster[gp.currentMap][i].dying = true;
                    gp.ui.addMessage("You killed the " + gp.monster[gp.currentMap][i].name + "!");
                    gp.ui.addMessage("You gained +" + gp.monster[gp.currentMap][i].exp + " exp!");
                    exp += gp.monster[gp.currentMap][i].exp;
                    checkLevelUp();
                }
            }
        }
    }
    public void damageInteractiveTile(int i) {
        if(i != 999 && gp.iTile[gp.currentMap][i].destructible && gp.iTile[gp.currentMap][i].requiredItem(this) && !gp.iTile[gp.currentMap][i].invincible) {
            gp.iTile[gp.currentMap][i].playSE();
            gp.iTile[gp.currentMap][i].life--;
            gp.iTile[gp.currentMap][i].invincible = true;

            //GENERATE PARTICLE
            generateParticle(gp.iTile[gp.currentMap][i], gp.iTile[gp.currentMap][i]);

            if(gp.iTile[gp.currentMap][i].life == 0 ) {
                gp.iTile[gp.currentMap][i] = gp.iTile[gp.currentMap][i].getDestroyedForm();
            }
        }
    }
    public void checkLevelUp() {
        if(exp >= nextLevel) {
            level++;
            nextLevel = nextLevel*2;
            maxLife += 2;
            maxMana += 1;
            strength++;
            dexterity++;
            attack = getAttack();
            defense = getDefense();

            gp.playSE(8);
            gp.gameState = gp.dialogueState;
            gp.ui.currentDialogue = "You're now level " + level + " now\n";

        }
    }
    public void selectItem() {
        int itemIndex = gp.ui.getItemIndexOnSlot(gp.ui.playerSlotCol, gp.ui.playerSlotRow);
        if(itemIndex <= inventory.size()) {
            Entity selectedItem = inventory.get(itemIndex);

            if(selectedItem.type == type_sword || selectedItem.type == type_axe) {
                currentWeapon = selectedItem;
                attack = getAttack();
                getPlayerAttackImage();
            }
            if(selectedItem.type == type_shield) {
                currentShield = selectedItem;
                defense = getDefense();
            }
            if(selectedItem.type == type_consumable) {
                //later
                selectedItem.use(this);
                inventory.remove(itemIndex);
            }
        }
    }
    public void draw(Graphics2D g2) {

        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        switch (direction) {
            case "up":
                if(!attacking) {
                    if(spriteNum == 1) { image = up1; }
                    if(spriteNum == 2) { image = up2; }
                }
                if(attacking) {
                    tempScreenY = screenY - gp.tileSize;
                    if(spriteNum == 1) { image = atkUp1; }
                    if(spriteNum == 2) { image = atkUp2; }
                }
                break;
            case "down":
                if(!attacking) {
                    if(spriteNum == 1) { image = down1; }
                    if(spriteNum == 2) { image = down2; }
                }
                if(attacking) {
                    if(spriteNum == 1) { image = atkDown1; }
                    if(spriteNum == 2) { image = atkDown2; }
                }
                break;
            case "right":
                if(!attacking) {
                    if(spriteNum == 1) { image = right1; }
                    if(spriteNum == 2) { image = right2; }
                }
                if(attacking) {
                    if(spriteNum == 1) { image = atkRight1; }
                    if(spriteNum == 2) { image = atkRight2; }
                }
                break;
            case "left":
                if(!attacking) {
                    if(spriteNum == 1) { image = left1; }
                    if(spriteNum == 2) { image = left2; }
                }

                if(attacking) {
                    tempScreenX = screenX - gp.tileSize;
                    if(spriteNum == 1) { image = atkLeft1; }
                    if(spriteNum == 2) { image = atkLeft2; }
                }
                break;
        }
        if(invincible) {
            changeAlpha(g2, 0.3f);
        }

        g2.drawImage(image, tempScreenX, tempScreenY, null);
        changeAlpha(g2, 1f);



        /*
        //CHECK THE COLLISION BOX (SHOWS RED OUTLINE TO YOUR CHARACTER)
        g2.setColor(Color.RED);
        g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);

        //SHOW INVINCIBLE COUNTER
        g2.setFont(new Font("Arial", Font.PLAIN, 24));
        g2.setColor(Color.WHITE);
        g2.drawString("Invincible: " + invincibleCounter, 10 ,400);

         */

    }
}






// pickUP code for treasure hunting game
/*
String objName = gp.obj[i].name;

            switch (objName) {
                case "Key":
                    gp.playSE(1);
                    hasKey++;
                    gp.obj[i] = null;
                    gp.ui.showMessage("You got a key!");
                    break;
                case "Door":
                    gp.playSE(3);
                    if(hasKey > 0) {
                        gp.obj[i] = null;
                        hasKey--;
                        gp.ui.showMessage("You've opened a door!");
                    } else {
                        gp.ui.showMessage("You need a key to open the door!");
                    }

                    break;
                case "Achilles Boots":
                    gp.playSE(2);
                    playerSpeed += 2;
                    gp.obj[i] = null; // make the item disappear
                    gp.ui.showMessage("Speed Up!");
                    break;
                case "Chest":
                    gp.ui.gameFinished = true;
                    gp.stopMusic();
                    gp.playSE(4);
                    break;

            }
 */