package main;

import Entity.Entity;
import object.Obj_Health;
import object.Obj_Mana;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class UI {

    GamePanel gp;
    Graphics2D g2;
    Font maruMonica, osdMono;
    BufferedImage healthFull, healthHalf, healthEmpty, manaFull, manaEmpty;
    public boolean messageOn = false;
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();
    public boolean gameFinished = false;
    public String currentDialogue;
    public int commandNum = 0;
    public int titleScreenState = 0; //0: first screen, 1: 2nd screen
    public int playerSlotCol = 0;
    public int playerSlotRow = 0;
    public int npcSlotCol = 0;
    public int npcSlotRow = 0;
    int subState = 0;
    int counter;
    public Entity npc;


    public UI(GamePanel gp) {
        this.gp = gp;

        try {
            InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);

            is = getClass().getResourceAsStream("/font/VCR_OSD_MONO_1.001.ttf");
            osdMono = Font.createFont(Font.TRUETYPE_FONT, is);

        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }

        //CREATE HUD OBJECT
        //HEALTH BAR
        Entity health = new Obj_Health(gp);
        healthFull = health.image;
        healthHalf = health.image1;
        healthEmpty = health.image2;

        //MANA BAR
        Entity mana = new Obj_Mana(gp);
        manaFull = mana.image;
        manaEmpty = mana.image1;
    }
    public void addMessage(String text) {
        message.add(text);
        messageCounter.add(Integer.valueOf(0));
    }
    public void draw(Graphics2D g2) {

        this.g2 = g2;

        g2.setFont(maruMonica);
        g2.setColor(Color.WHITE);

        //TITLE STATE
        if(gp.gameState == gp.titleState) {
            drawTitleScreen();
        }
        //PLAY STATE
        if(gp.gameState == gp.playState) {
            //do play State stuff
            drawPlayerHealth();
            drawMessage();
        }
        //PAUSE STATE
        if(gp.gameState == gp.pauseState) {
            drawPausedScreen();
            drawPlayerHealth();
        }
        //DIALOGUE STATE
        if(gp.gameState == gp.dialogueState) {
            drawPlayerHealth();
            drawDialogueScreen();
            return;
        }
        //CHARACTER STATE
        if(gp.gameState == gp.characterState) {
            drawCharacterScreen();
            drawInventory(gp.player, true);
        }
        //OPTION STATE
        if(gp.gameState == gp.optionState) {
            drawOptionScreen();
        }
        //GAME OVER STATE
        if(gp.gameState == gp.gameOverState) {
            drawGameOverScreen();
        }
        //TRANSITION STATE
        if(gp.gameState == gp.transitionState) {
            drawTransition();
        }
        //TRADE STATE
        if(gp.gameState == gp.tradeState) {
            drawTradeScreen();
        }

    }
    public void drawPlayerHealth() {

        int x = gp.tileSize / 2;
        int y = gp.tileSize / 2;
        int i = 0;

        //DRAW PLAYER BLANK LIFE
        while(i < gp.player.maxLife / 2) {
            g2.drawImage(healthEmpty, x, y, null);
            i++;
            x += gp.tileSize;
        }
        // RESET
        x = gp.tileSize / 2;
        y = gp.tileSize / 2;
        i = 0;

        //DRAW CURRENT LIFE
        while(i < gp.player.life) {
            g2.drawImage(healthHalf, x ,y, null);
            i++;
            if(i < gp.player.life) {
                g2.drawImage(healthFull, x, y, null);
            }
            i++;
            x+= gp.tileSize;
        }

        //DRAW MANA BAR
        x = gp.tileSize / 2;
        y = gp.tileSize;
        i = 0;
        while(i < gp.player.maxMana) {
            g2.drawImage(manaEmpty, x, y, null);
            i++;
            x += gp.tileSize;
        }
        //DRAW MAX MANA

        // RESET
        x = gp.tileSize / 2;
        y = gp.tileSize;
        i = 0;

        while(i < gp.player.mana) {
            g2.drawImage(manaFull, x, y, null);
            i++;
            x += gp.tileSize;
        }

    }
    public void drawMessage() {
        int messageX = gp.tileSize;
        int messageY = gp.tileSize * 4;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));

        for(int i = 0; i < message.size(); i++) {
            if(message.get(i) != null) {
                g2.setColor(Color.black);
                g2.drawString(message.get(i), messageX + 2, messageY + 2);

                g2.setColor(Color.white);
                g2.drawString(message.get(i), messageX, messageY);

                int counter = messageCounter.get(i) + 1; // messageCounter++
                messageCounter.set(i, Integer.valueOf(counter)); //set the counter to the array
                messageY += 50;

                if(messageCounter.get(i) > 180) {
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
        }
    }
    public void drawTitleScreen() {

        if(titleScreenState == 0) {
            //TITLE BACKGROUND COLOR
            g2.setColor( new Color(0, 0, 0));
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

            //TITLE NAME
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110F));
            String text = "Adventure Mode";
            int x = getCenteredText(text);
            int y = gp.tileSize * 3;

            //SHADOW(shadow text)
            g2.setColor(Color.gray);
            g2.drawString(text, x + 5, y +  5);

            //MAIN COLOR
            g2.setColor(Color.WHITE);
            g2.drawString(text, x, y);

            //Character Image
            x = gp.screenWidth / 2 - (gp.tileSize * 2) / 2; // center point
            y += gp.tileSize * 2;
            g2.drawImage(gp.player.down1, x, y, gp.tileSize * 2, gp.tileSize * 2, null);

            //MENU
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
            text = "NEW GAME";
            x = getCenteredText(text);
            y += gp.tileSize * 3.5;
            g2.drawString(text, x, y);

            if(commandNum == 0) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "LOAD GAME";
            x = getCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);

            if(commandNum == 1) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "QUIT";
            x = getCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);

            if(commandNum == 2) {
                g2.drawString(">", x - gp.tileSize, y);
            }
        }
        else if(titleScreenState == 1) {

            g2.setColor( new Color(0, 0, 0));
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

            g2.setColor(Color.WHITE);
            g2.setFont(g2.getFont().deriveFont(42F));

            String text = "Select your Character!";
            int x = getCenteredText(text);
            int y = gp.tileSize * 3;
            g2.drawString(text, x, y);

            text = "Ton";
            x = getCenteredText(text);
            y += gp.tileSize * 3;
            g2.drawString(text, x, y);
            if(commandNum == 0) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "Jay";
            x = getCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 1) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "Jon";
            x = getCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 2) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "Back";
            x = getCenteredText(text);
            y += gp.tileSize * 2;
            g2.drawString(text, x, y);
            if(commandNum == 3) {
                g2.drawString(">", x - gp.tileSize, y);
            }
        }
    }
    public void drawPausedScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 60));
        String text = "PAUSED";
        int x = getCenteredText(text);
        int y = gp.screenHeight / 2;

        g2.drawString(text, x, y);
    }
    public void drawDialogueScreen() {

        //WINDOW
        int x = gp.tileSize * 2;
        int y = gp.tileSize / 2;
        int width = gp.screenWidth - (gp.tileSize * 6);
        int height = gp.tileSize * 4;

        drawSubWindow(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 30F));
        x += gp.tileSize;
        y += gp.tileSize;

        for(String line : currentDialogue.split("\n")) {// can use any indicator to split the text
            g2.drawString(line, x, y);
            y += 40;
        }
    }
    public void drawCharacterScreen() {
        //CREATE FRAME
        final int frameX = gp.tileSize;
        final int frameY = gp.tileSize;
        final int frameWidth = gp.tileSize * 5;
        final int frameHeight = gp.tileSize * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        //TEXT
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32F));

        int textX = frameX + 20;
        int textY = frameY + gp.tileSize;
        final int lineHeight = 35;

        //DISPLAY ATTRIBUTES
        g2.drawString("Level", textX, textY); textY += lineHeight;
        g2.drawString("HP", textX, textY); textY += lineHeight;
        g2.drawString("Mana", textX, textY); textY += lineHeight;
        g2.drawString("Strength", textX, textY); textY += lineHeight;
        g2.drawString("Dexterity", textX, textY); textY += lineHeight;
        g2.drawString("Attack", textX, textY); textY += lineHeight;
        g2.drawString("Defense", textX, textY); textY += lineHeight;
        g2.drawString("EXP", textX, textY); textY += lineHeight;
        g2.drawString("Coin", textX, textY); textY += lineHeight + 20;
        g2.drawString("Weapon", textX, textY); textY += lineHeight + 20;
        g2.drawString("Shield", textX, textY);

        //DISPLAY ATTRIBUTES VALUES
        int tailX = (frameX + frameWidth) - 30;
        textY = frameY + gp.tileSize;
        String value;

        value = String.valueOf(gp.player.level);
        textX = getXAlignmentRightText(value, tailX);
        g2.drawString(value, textX,textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.life + "/" + gp.player.maxLife);
        textX = getXAlignmentRightText(value, tailX);
        g2.drawString(value, textX,textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.mana + "/" + gp.player.maxMana);
        textX = getXAlignmentRightText(value, tailX);
        g2.drawString(value, textX,textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.strength);
        textX = getXAlignmentRightText(value, tailX);
        g2.drawString(value, textX,textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.dexterity);
        textX = getXAlignmentRightText(value, tailX);
        g2.drawString(value, textX,textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.attack);
        textX = getXAlignmentRightText(value, tailX);
        g2.drawString(value, textX,textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.defense);
        textX = getXAlignmentRightText(value, tailX);
        g2.drawString(value, textX,textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.exp + "/" + gp.player.nextLevel);
        textX = getXAlignmentRightText(value, tailX);
        g2.drawString(value, textX,textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.coin);
        textX = getXAlignmentRightText(value, tailX);
        g2.drawString(value, textX,textY);
        textY += lineHeight;

        g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.tileSize, textY - 14, null);
        textY += gp.tileSize;

        g2.drawImage(gp.player.currentShield.down1, tailX - gp.tileSize, textY - 14, null);
    }
    public void drawInventory(Entity entity, boolean cursor) {

        int frameX = 0;
        int frameY = 0;
        int frameWidth = 0;
        int frameHeight = 0;
        int slotCol = 0;
        int slotRow = 0;

        if(entity == gp.player) {
            frameX = gp.tileSize * 13;
            frameY = gp.tileSize;
            frameWidth = gp.tileSize * 6;
            frameHeight = gp.tileSize * 5;
            slotCol = playerSlotCol;
            slotRow = playerSlotRow;
        } else {
            frameX = gp.tileSize * 2;
            frameY = gp.tileSize;
            frameWidth = gp.tileSize * 6;
            frameHeight = gp.tileSize * 5;
            slotCol = npcSlotCol;
            slotRow = npcSlotRow;
        }

        //FRAME
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        //CREATE SLOT
        final int slotXStart = frameX + 20;
        final int slotYStart = frameY + 20;
        int slotX = slotXStart;
        int slotY = slotYStart;
        int slotSize = gp.tileSize + 3;

        //DRAW PLAYER ITEMS
        for(int i = 0; i < entity.inventory.size(); i++) {

            if(entity.inventory.get(i) == gp.player.currentWeapon ||
                    entity.inventory.get(i) == entity.currentShield) {
                //HIGHLIGHTS CURRENT EQUIPMENT
                g2.setColor(new Color(240, 190, 90));
                g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
            }

            g2.drawImage(entity.inventory.get(i).down1, slotX, slotY, null);
            slotX += slotSize;

            if(i == 4 || i == 9 || i == 14) {
                slotX = slotXStart;
                slotY += slotSize;
            }
        }

        //CURSOR: IN ORDER TO SELECT AND ITEM
        if(cursor) {
            int cursorX = slotXStart + (slotSize * slotCol);
            int cursorY = slotYStart + (slotSize * slotRow);
            int cursorWidth = gp.tileSize;
            int cursorHeight = gp.tileSize;

            //DRAW CURSOR
            g2.setColor(Color.white);
            g2.setStroke(new BasicStroke(3));
            g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

            //ITEM DESCRIPTION FRAME
            int dFrameX = frameX;
            int dFrameY = frameY + frameHeight;
            int dFrameWidth = frameWidth;
            int dFrameHeight = gp.tileSize * 3;

            //DRAW DESCRIPTION TEXT
            int textX = dFrameX + 20;
            int textY = dFrameY + gp.tileSize;
            g2.setFont(g2.getFont().deriveFont(28F));

            int itemIndex = getItemIndexOnSlot(slotCol, slotRow);
            if(itemIndex < entity.inventory.size()) {
                drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);
                for(String line : entity.inventory.get(itemIndex).description.split("\n")) {
                    g2.drawString(line, textX, textY);
                    textY += 32;
                }


            }
        }
    }
    public void drawGameOverScreen() {
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        int x;
        int y;
        String text;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110f));

        text = "GAME OVER";
        // SHADOW
        g2.setColor(Color.black);
        x = getCenteredText(text);
        y = gp.tileSize * 4;
        g2.drawString(text, x, y);

        // MAIN TEXT
        g2.setColor(Color.white);
        g2.drawString(text, x -4, y - 4);

        //RETRY
        g2.setFont(g2.getFont().deriveFont(50F));
        text = "Retry";
        x = getCenteredText(text);
        y += gp.tileSize * 4;
        g2.drawString(text, x, y);
        if(commandNum == 0) {
            g2.drawString(">", x - 40, y);
        }

        //BACK TO TITLE SCREEN
        text = "Quit";
        x = getCenteredText(text);
        y += 55;
        g2.drawString(text, x, y);
        if(commandNum == 1) {
            g2.drawString(">", x - 40, y);
        }

    }
    public void drawOptionScreen() {
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(32F));

        //SUB WINDOW
        int frameX = gp.tileSize * 6;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 8;
        int frameHeight = gp.tileSize * 10;

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        switch (subState) {
            case 0: option_top(frameX, frameY);break;
            case 1: option_fullScreenNotification(frameX, frameY);break;
            case 2: option_control(frameX, frameY); break;
            case 3: option_quitGame(frameX, frameY); break;
        }
        gp.kh.enterPressed = false;
    }
    public void option_top(int frameX, int frameY) {
        int textX;
        int textY;

        //TITLE
        String text = "Options";
        textX = getCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        // FULLSCREEN OPTION ON | OFF
        textX = frameX + gp.tileSize;
        textY += gp.tileSize * 2;
        g2.drawString("Full Screen", textX, textY);
        if(commandNum == 0) {
            g2.drawString(">", textX - 25, textY);
            if(gp.kh.enterPressed) {
                if (!gp.fullScreenOn) {
                    gp.fullScreenOn = true;
                } else {
                    gp.fullScreenOn = false;
                }
                subState = 1;
            }
        }

        // MUSIC
        textY += gp.tileSize;
        g2.drawString("Music", textX, textY);
        if(commandNum == 1) {
            g2.drawString(">", textX - 25, textY);
        }

        // SE
        textY += gp.tileSize;
        g2.drawString("Sound Effect", textX, textY);
        if(commandNum == 2) {
            g2.drawString(">", textX - 25, textY);
        }

        // CONTROL
        textY += gp.tileSize;
        g2.drawString("Control", textX, textY);
        if(commandNum == 3) {
            g2.drawString(">", textX - 25, textY);

            if(gp.kh.enterPressed) {
                subState = 2;
                commandNum = 0;
            }
        }
        //QUIT GAME
        textY += gp.tileSize;
        g2.drawString("Quit Game", textX, textY);
        if(commandNum == 4) {
            g2.drawString(">", textX - 25, textY);
            if(gp.kh.enterPressed) {
                subState = 3;
                commandNum = 0;
            }
        }

        // BACK
        textY += gp.tileSize * 2;
        g2.drawString("Back", textX, textY);
        if(commandNum == 5) {
            g2.drawString(">", textX - 25, textY);
            if(gp.kh.enterPressed) {
                gp.gameState = gp.playState;
                commandNum = 0;
            }
        }


        //FULL SCREEN CHECK BOX
        textX = frameX + (int)(gp.tileSize * 4.5);
        textY = frameY + gp.tileSize * 2 + 23;
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(textX, textY, 24, 24);
        if(gp.fullScreenOn) {
            g2.fillRect(textX, textY, 24, 24);
        }
        //MUSIC VOLUME
        textY += gp.tileSize;
        g2.drawRect(textX, textY, 120, 24);// 120 / 5 = 24
        int volumeWidth = 24 * gp.music.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);

        //SOUND EFFECT VOLUME
        textY += gp.tileSize;
        g2.drawRect(textX, textY, 120, 24);
        volumeWidth = 24 * gp.sE.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);

        gp.config.saveConfig();
    }
    public void option_fullScreenNotification(int frameX, int frameY) {
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize * 3;

        currentDialogue = "The change will take \neffect after restarting the game";
        for(String line : currentDialogue.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }
        //BACK
        textY = frameY + gp.tileSize * 9;
        g2.drawString("Back", textX, textY);
        if(commandNum == 0) {
            g2.drawString(">", textX - 25, textY);
            if(gp.kh.enterPressed) {
                subState = 0;
            }
        }

    }
    public void option_control(int frameX, int frameY) {
        int textX;
        int textY;

        //TITLE
        String text = "Control";
        textX = getCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        textX = frameX + gp.tileSize;
        textY += gp.tileSize;
        g2.drawString("Move", textX, textY); textY += gp.tileSize;
        g2.drawString("Attack", textX, textY); textY += gp.tileSize;
        g2.drawString("Shoot", textX, textY); textY += gp.tileSize;
        g2.drawString("Character Screen", textX, textY); textY += gp.tileSize;
        g2.drawString("Pause", textX, textY); textY += gp.tileSize;
        g2.drawString("Options", textX, textY); textY += gp.tileSize;


        textX = frameX + gp.tileSize * 6;
        textY = frameY + gp.tileSize * 2;
        g2.drawString("WASD", textX, textY); textY += gp.tileSize;
        g2.drawString("ENTER", textX, textY); textY += gp.tileSize;
        g2.drawString("F", textX, textY); textY += gp.tileSize;
        g2.drawString("C", textX, textY); textY += gp.tileSize;
        g2.drawString("P", textX, textY); textY += gp.tileSize;
        g2.drawString("ESC", textX, textY); textY += gp.tileSize;

        //BACK
        textX = frameX + gp.tileSize;
        textY = frameY + gp.tileSize * 9;
        g2.drawString("BACK", textX, textY);
        if(commandNum == 0) {
            g2.drawString(">", textX - 25,textY);
            if(gp.kh.enterPressed) {
                subState = 0;
                commandNum = 3;
            }
        }
    }
    public void option_quitGame(int frameX, int frameY) {
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize * 3;

        currentDialogue = "Do you wish to quit the \ngame and return to the \ntitle screen?";
        for(String line : currentDialogue.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }
        // YES | NO

        String text = "Yes";
        textX = getCenteredText(text);
        textY += gp.tileSize * 3;
        g2.drawString(text, textX, textY);
        if(commandNum == 0) {
            g2.drawString(">", textX - 25, textY);
            if(gp.kh.enterPressed) {
                subState = 0;
                gp.gameState = gp.titleState;
                titleScreenState = 0;
                gp.stopMusic();
            }
        }

        text = "NO";
        textX = getCenteredText(text);
        textY += gp.tileSize;
        g2.drawString(text, textX, textY);
        if(commandNum == 1) {
            g2.drawString(">", textX - 25, textY);
            if(gp.kh.enterPressed) {
                subState = 0;
                commandNum = 4;
            }
        }
    }
    public void drawTransition() {
        counter++;
        g2.setColor(new Color(0, 0 ,0, counter * 5));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        if(counter == 50) {
            counter = 0;
            gp.gameState = gp.playState;
            gp.currentMap = gp.eHandler.tempMap;
            gp.player.worldX = gp.tileSize * gp.eHandler.tempCol;
            gp.player.worldY = gp.tileSize * gp.eHandler.tempRow;
            gp.eHandler.previousEventX = gp.player.worldX;
            gp.eHandler.previousEventY = gp.player.worldY;
        }
    }
    public void drawTradeScreen() {
        switch(subState) {
            case 0: trade_select(); break;
            case 1: trade_buy(); break;
            case 2: trade_sell(); break;
        }
        gp.kh.enterPressed = false;
    }
    public void trade_select() {
        drawDialogueScreen();

        //DDRAW WINDOW
        int x = gp.tileSize * 15;
        int y = gp.tileSize * 4;
        int width = gp.tileSize * 3;
        int height = (int) (gp.tileSize * 3.5);
        drawSubWindow(x, y, width, height);

        //DRAW TEXTS
        x += gp.tileSize;
        y += gp.tileSize;
        g2.drawString("Buy", x, y);
        if(commandNum == 0) {
            g2.drawString(">", x - 24, y);
            if(gp.kh.enterPressed) { subState = 1; }
        }
        y += gp.tileSize;
        g2.drawString("Sell", x, y);
        if(commandNum == 1) {
            g2.drawString(">", x - 24, y);
            if(gp.kh.enterPressed) { subState = 2; }
        }
        y += gp.tileSize;
        g2.drawString("Exit", x, y);
        if(commandNum == 2) {
            g2.drawString(">", x - 24, y);
            if(gp.kh.enterPressed) {
                commandNum = 0;
                gp.gameState = gp.dialogueState;
                currentDialogue = "Thanks for purchasing1";
            }
        }

    }
    public void trade_buy() {
        //DRAW PLAYER INVENTORY
        drawInventory(gp.player, false);
        //DRAW NPC INVENTORY
        drawInventory(npc, true);
    }
    public void trade_sell() {

    }
    public int getItemIndexOnSlot(int slotCol, int slotRow) {
        int itemIndex = slotCol + (slotRow * 5);
        return itemIndex;
    }
    public void drawSubWindow(int x, int y, int width, int height) {

        Color c = new Color(0, 0, 0, 200);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }
    public int getCenteredText(String text) { // GET CENTERED TEXT
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth / 2 - length / 2;
        return x;
    }
    public int getXAlignmentRightText(String text, int tailX) { // GET CENTERED TEXT
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = tailX - length;
        return x;
    }
}












//treasure hunting game

/*
if(gameFinished) {
            String text;
            int textLength;
            int x;
            int y;

            g2.setFont(arial_40);
            g2.setColor(Color.WHITE);
            text = "You found One Piece!";
            textLength= (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();

            x = gp.screenWidth / 2 - textLength / 2;
            y = gp.screenHeight / 2 - (gp.tileSize * 3);
            g2.drawString(text, x, y);

            g2.setFont(arial_40);
            g2.setColor(Color.WHITE);
            text = "Your time is: " + df.format(playTime) + "!";
            textLength= (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();

            x = gp.screenWidth / 2 - textLength / 2;
            y = gp.screenHeight / 2 + (gp.tileSize * 4);
            g2.drawString(text, x, y);

            g2.setFont(arial_60B);
            g2.setColor(Color.YELLOW);
            text = "Congratulations!";
            textLength= (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();

            x = gp.screenWidth / 2 - textLength / 2;
            y = gp.screenHeight / 2 + (gp.tileSize * 2);
            g2.drawString(text, x, y);

            gp.gameThread = null;

        } else {
            //SET FONT
            g2.setFont(arial_40);
            g2.setColor(Color.WHITE);
            g2.drawImage(keyImage, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize, gp.tileSize, null );
            g2.drawString("x " + gp.player.hasKey, 74, 60);

            //TIME
            playTime += (double)1/60;
            g2.drawString("Time: " + df.format(playTime), gp.tileSize * 11, 60);

            //MESSAGE
            if(messageOn) {
                g2.setFont(g2.getFont().deriveFont(25F));
                g2.drawString(message, gp.tileSize / 2, gp.tileSize * 11);

                messageCounter++;

                if(messageCounter > 120) { // 120 = 2s, means the message will end in 2s
                    messageCounter = 0;
                    messageOn = false;
                }
            }
        }
 */