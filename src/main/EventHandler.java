package main;

import Entity.Entity;

import java.awt.*;

public class EventHandler {

    GamePanel gp;
    EventRect[][][] eventRect;

    int previousEventX, previousEventY;
    boolean canTouchEvent = true;
    int tempMap, tempRow, tempCol;

    public EventHandler(GamePanel gp) {
        this.gp = gp;

        eventRect = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
        int map = 0;
        int col = 0;
        int row = 0;

        while(map < gp.maxMap && col < gp.maxWorldCol && row < gp.maxWorldRow) {
            eventRect[map][col][row] = new EventRect();
            eventRect[map][col][row].x = 0;
            eventRect[map][col][row].y = 17;
            eventRect[map][col][row].width = 50;
            eventRect[map][col][row].height = 35;
            eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
            eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;

            col++;

            if(col == gp.maxWorldCol) {
                col = 0;
                row++;

                if(row == gp.maxWorldRow) {
                    row = 0;
                    map++;
                }
            }
        }
    }

    public void checkEvent() {
        //CHECK IF THE PLAYER IS ONE TILE AWAY FROM THE EVENT
        int xDistance = Math.abs(gp.player.worldX - previousEventX);
        int yDistance = Math.abs(gp.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);

        if(distance > gp.tileSize) {
            canTouchEvent = true;
        }

        if(canTouchEvent) {
            if (hit(0, 31, 8, "right")) { damagePit(gp.dialogueState); }
            else if(hit(0,14, 3,"right")) { healPool(gp.dialogueState); }
            else if(hit(1, 17, 24, "down")) {teleport(0, 9, 8);}
            else if(hit(1, 17, 19, "up")) {speak(gp.npc[1][0]); }
        }
    }
    public boolean hit(int map, int col, int row, String reqDirection) {
        boolean hit = false;

        if(map == gp.currentMap) {
            gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
            gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
            eventRect[map][col][row].x = col * gp.tileSize + eventRect[map][col][row].x;
            eventRect[map][col][row].y = row * gp.tileSize + eventRect[map][col][row].y;

            if(gp.player.solidArea.intersects(eventRect[map][col][row]) && !eventRect[map][col][row].eventDone) {
                if(gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
                    hit = true;

                    previousEventX = gp.player.worldX;
                    previousEventY = gp.player.worldY;
                }
            }
            gp.player.solidArea.x = gp.player.solidAreaDefaultX;
            gp.player.solidArea.y = gp.player.solidAreaDefaultY;
            eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
            eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;
        }
        return hit;
    }
    public void teleport(int map, int col, int row) {
        gp.gameState = gp.transitionState;
        tempMap = map;
        tempCol = col;
        tempRow = row;
        canTouchEvent = false;
        gp.playSE(3);
    }
    public void damagePit(int gameState) {
        gp.gameState = gameState;
        gp.playSE(6);
        gp.ui.currentDialogue = "You've fall into a pit!";
        gp.player.life -= 1;
        //eventRect[col][row].eventDone = true;
        canTouchEvent = false;
    }
    public void healPool(int gameState) {
        if(gp.kh.enterPressed) {
            gp.gameState = gameState;
            gp.player.cancelAttack = true;
            gp.ui.currentDialogue = "You pray to the goddess of wo'ah. \nYour health and mana has been recovered!";
            gp.player.life = gp.player.maxLife;
            gp.player.mana = gp.player.maxMana;
            //WHEN YOU REST IN THE POOL, THE MONSTER SPAWNS
            gp.aSetter.setMonster();
        }
    }
    public void speak(Entity entity) {
        if(gp.kh.enterPressed) {
            gp.gameState = gp.dialogueState;
            gp.player.cancelAttack = true;
            entity.speak();
        }
    }

    public void draw(Graphics2D g2) {

        // We only want to draw events for the map we are currently on
        int map = gp.currentMap;

        for(int col = 0; col < gp.maxWorldCol; col++) {
            for(int row = 0; row < gp.maxWorldRow; row++) {

                // Calculate the Screen Position (Camera Logic)
                int worldX = col * gp.tileSize + eventRect[map][col][row].x;
                int worldY = row * gp.tileSize + eventRect[map][col][row].y;
                int screenX = worldX - gp.player.worldX + gp.player.screenX;
                int screenY = worldY - gp.player.worldY + gp.player.screenY;

                // Only draw if the event is inside the screen view
                if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                        worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                        worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                        worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

                    g2.setColor(new Color(255, 0, 0, 150)); // Semi-transparent Red
                    g2.drawRect(screenX, screenY, eventRect[map][col][row].width, eventRect[map][col][row].height);
                }
            }
        }
    }
}
