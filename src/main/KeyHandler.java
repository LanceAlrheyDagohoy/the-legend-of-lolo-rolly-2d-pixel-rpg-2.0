package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gp;
    public boolean upPressed, downPressed, rightPressed, leftPressed, enterPressed, shotKeyPressed;

    //DEBUG
    boolean checkDebugText = false;


    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        //TITLE STATE
        if(gp.gameState == gp.titleState) {
            if(gp.ui.titleScreenState == 0) { titleState(code); }
            //CHARACTER SELECTION
            else if(gp.ui.titleScreenState == 1) { characterSelection(code); }
        }
        //PLAYSTATE
        else if(gp.gameState == gp.playState) { playState(code); }
        //PAUSE STATE
        else if(gp.gameState == gp.pauseState) { pauseState(code); }
        //DIALOGUE STATE
        else if(gp.gameState == gp.dialogueState) { dialogueState(code); }
        //CHARACTER STATE
        else if(gp.gameState == gp.characterState) {  characterState(code); }
        //OPTION STATE
        else if(gp.gameState == gp.optionState) {  optionState(code); }
        //GAME OVER STATE
        else if(gp.gameState == gp.gameOverState) {  gameOverState(code); }
        //TRADE STATE
        else if(gp.gameState == gp.tradeState) {  tradeState(code); }
    }
    public void titleState(int code) {
        if(code == KeyEvent.VK_W) {
            gp.playSE(9);
            gp.ui.commandNum--;
            if(gp.ui.commandNum < 0) {
                gp.ui.commandNum = 2;
            }
        }
        if(code == KeyEvent.VK_S) {
            gp.playSE(9);
            gp.ui.commandNum++;

            if(gp.ui.commandNum > 2) {
                gp.ui.commandNum = 0;
            }
        }
        if(code == KeyEvent.VK_ENTER) {
            gp.playSE(9);
            if(gp.ui.commandNum == 0) {
                gp.ui.titleScreenState = 1;
            }
            if(gp.ui.commandNum == 1) {

            }
            if(gp.ui.commandNum == 2) {
                System.exit(0);
            }
        }
    }
    public void playState(int code) {
        if(code == KeyEvent.VK_W) {
            upPressed = true;
        }
        if(code == KeyEvent.VK_S) {
            downPressed = true;
        }
        if(code == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if(code == KeyEvent.VK_D) {
            rightPressed = true;
        }
        if(code == KeyEvent.VK_P) {
            gp.gameState = gp.pauseState;
            gp.stopMusic();
        }
        if(code == KeyEvent.VK_C) {
            gp.gameState = gp.characterState;
        }
        if(code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
        if(code == KeyEvent.VK_F) {
            shotKeyPressed = true;
        }
        if(code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.optionState;
        }

        //WAY TO APPLY CHANGES IN THE WORLD MAP WITHOUT REFRESHING = PRESS: CTRL + F9 TO SAVE THE WORLD CHANGES
        if(code == KeyEvent.VK_R) {
            switch (gp.currentMap) {
                case 0: gp.tm.loadMap("/maps/world.txt", 0); break;
                case 1:  gp.tm.loadMap("/maps/room1.txt", 1); break;
            }

        }

        if(code == KeyEvent.VK_T) {
            if(!checkDebugText) {
                checkDebugText = true;
            } else if(checkDebugText) {
                checkDebugText = false;
            }
        }

    }
    public void pauseState(int code) {
        if(code == KeyEvent.VK_P) {
            gp.ui.drawPausedScreen();
            gp.gameState = gp.playState;
            gp.playMusic(0);
        }
    }
    public void dialogueState(int code) {
        if(code == KeyEvent.VK_SPACE) {
            gp.gameState = gp.playState;
        }
    }
    public void characterState(int code) {
        if(code == KeyEvent.VK_C) {
            gp.gameState = gp.playState;
        }
        if(code == KeyEvent.VK_ENTER) {
            gp.player.selectItem();
        }
        playerInventory(code);

    }
    public void characterSelection(int code) {
        if(code == KeyEvent.VK_W) {
            gp.playSE(9);
            gp.ui.commandNum--;

            if(gp.ui.commandNum < 0) {
                gp.ui.commandNum = 3;
            }
        }
        if(code == KeyEvent.VK_S) {
            gp.playSE(9);
            gp.ui.commandNum++;

            if(gp.ui.commandNum > 3) {
                gp.ui.commandNum = 0;
            }
        }

        if(code == KeyEvent.VK_ENTER) {
            gp.playSE(9);
            if(gp.ui.commandNum == 0) {
                gp.gameState = gp.playState;
                gp.playMusic(0);
            }
            if(gp.ui.commandNum == 1) {
                gp.gameState = gp.playState;
                gp.playMusic(0);
            }
            if(gp.ui.commandNum == 2) {
                gp.gameState = gp.playState;
                gp.playMusic(0);
            }
            if(gp.ui.commandNum == 3) {
                gp.ui.titleScreenState = 0;
            }
        }
    }
    public void optionState(int code) {
        if(code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.playState;
        }
        if(code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }

        int maxCommandNum = 0;
        switch (gp.ui.subState) {
            case 0: maxCommandNum = 5; break;
            case 3: maxCommandNum = 1; break;
        }

        if(code == KeyEvent.VK_W) {
            gp.ui.commandNum--;
            gp.playSE(9);

            if(gp.ui.commandNum < 0) {
                gp.ui.commandNum = maxCommandNum;
            }
        }
        if(code == KeyEvent.VK_S) {
            gp.ui.commandNum++;
            gp.playSE(9);

            if(gp.ui.commandNum > maxCommandNum) {
                gp.ui.commandNum = 0;
            }
        }
        //VOLUME & SE
        if (code == KeyEvent.VK_A) {
            if(gp.ui.subState == 0) {
                if(gp.ui.commandNum == 1 && gp.music.volumeScale > 0) {
                    gp.music.volumeScale--;
                    gp.music.checkVolume();
                    gp.playSE(9);
                }
                if(gp.ui.commandNum == 2 && gp.sE.volumeScale > 0) {
                    gp.sE.volumeScale--;
                    gp.playSE(9);
                }
            }

        }
        if (code == KeyEvent.VK_D) {
            if(gp.ui.subState == 0) {
                if(gp.ui.commandNum == 1 && gp.music.volumeScale < 5) {
                    gp.music.volumeScale++;
                    gp.music.checkVolume();
                    gp.playSE(9);
                }
                if(gp.ui.commandNum == 2 && gp.sE.volumeScale < 5) {
                    gp.sE.volumeScale++;
                    gp.playSE(9);
                }
            }

        }
    }
    public void gameOverState(int code) {
        if(code == KeyEvent.VK_W) {
            gp.ui.commandNum--;
            if(gp.ui.commandNum < 0) {
                gp.ui.commandNum = 1;
            }
            gp.playSE(9);
        }
        if(code == KeyEvent.VK_S) {
            gp.ui.commandNum++;
            if(gp.ui.commandNum > 1) {
                gp.ui.commandNum = 0;
            }
            gp.playSE(9);
        }
        if(code == KeyEvent.VK_ENTER) {
            if(gp.ui.commandNum == 0) {
                gp.gameState = gp.playState;
                gp.retry();
                gp.playMusic(0);
            }
            else if (gp.ui.commandNum == 1) {
                gp.gameState = gp.titleState;
                gp.ui.titleScreenState = 0;
                gp.restart();
            }
        }

    }
    public void tradeState(int code) {
        if(code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
        if(gp.ui.subState == 0) {
            if(code == KeyEvent.VK_W) {
                gp.ui.commandNum--;
                if(gp.ui.commandNum < 0) {
                    gp.ui.commandNum  = 2;
                }
                gp.playSE(9);
            }
            if(code == KeyEvent.VK_S) {
                gp.ui.commandNum++;
                if(gp.ui.commandNum > 2) {
                    gp.ui.commandNum  = 0;
                }
                gp.playSE(9);
            }
        }
        if(gp.ui.subState == 1) {
            npcInventory(code);
            if(code == KeyEvent.VK_ESCAPE) {
                gp.ui.subState = 0;
            }
        }
    }
    public void playerInventory(int code) {
        if(code ==  KeyEvent.VK_W) {
            if(gp.ui.playerSlotRow != 0) {
                gp.ui.playerSlotRow--;
                gp.playSE(9);
            }
        }
        if(code ==  KeyEvent.VK_A) {
            if(gp.ui.playerSlotCol != 0) {
                gp.ui.playerSlotCol--;
                gp.playSE(9);
            }
        }
        if(code ==  KeyEvent.VK_S) {
            if(gp.ui.playerSlotRow != 3) {
                gp.ui.playerSlotRow++;
                gp.playSE(9);
            }
        }
        if(code ==  KeyEvent.VK_D) {
            if(gp.ui.playerSlotCol != 4) {
                gp.ui.playerSlotCol++;
                gp.playSE(9);
            }
        }
    }
    public void npcInventory(int code) {
        if(code ==  KeyEvent.VK_W) {
            if(gp.ui.npcSlotRow != 0) {
                gp.ui.npcSlotRow--;
                gp.playSE(9);
            }
        }
        if(code ==  KeyEvent.VK_A) {
            if(gp.ui.npcSlotCol != 0) {
                gp.ui.npcSlotCol--;
                gp.playSE(9);
            }
        }
        if(code ==  KeyEvent.VK_S) {
            if(gp.ui.npcSlotRow != 3) {
                gp.ui.npcSlotRow++;
                gp.playSE(9);
            }
        }
        if(code ==  KeyEvent.VK_D) {
            if(gp.ui.npcSlotCol != 4) {
                gp.ui.npcSlotCol++;
                gp.playSE(9);
            }
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if(code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if(code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if(code == KeyEvent.VK_D) {
            rightPressed = false;
        }
        if(code == KeyEvent.VK_F) {
            shotKeyPressed = false;
        }
    }
}
