package main;

import Entity.NPC_Merchant;
import Entity.NPC_Red;
import monster.Slime;
import object.*;
import tile_interactive.TI_Tree;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        int mapNum = 0;
        int i = 0;
        gp.obj[mapNum][i] = new Obj_Health(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 4;
        gp.obj[mapNum][i].worldY = gp.tileSize * 4;
        i++;

        gp.obj[mapNum][i] = new Obj_House(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 8;
        gp.obj[mapNum][i].worldY = gp.tileSize * 5;
        i++;
        gp.obj[mapNum][i] = new Obj_Door(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 9;
        gp.obj[mapNum][i].worldY = gp.tileSize * 7;
        i++;

        gp.obj[mapNum][i] = new Obj_Coin(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 37;
        gp.obj[mapNum][i].worldY = gp.tileSize * 3;
        i++;

        gp.obj[mapNum][i] = new Obj_Coin(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 16;
        gp.obj[mapNum][i].worldY = gp.tileSize * 6;
        i++;

        gp.obj[mapNum][i] = new Obj_Coin(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 5;
        gp.obj[mapNum][i].worldY = gp.tileSize * 12;
        i++;

        gp.obj[mapNum][i] = new Obj_Mana(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 30;
        gp.obj[mapNum][i].worldY = gp.tileSize * 12;
        i++;

        gp.obj[mapNum][i] = new Obj_Mana(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 15;
        gp.obj[mapNum][i].worldY = gp.tileSize * 10;
        i++;

        gp.obj[mapNum][i] = new Obj_Mana(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 14;
        gp.obj[mapNum][i].worldY = gp.tileSize * 3;
        i++;

        gp.obj[mapNum][i] = new Obj_Axe(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 8;
        gp.obj[mapNum][i].worldY = gp.tileSize * 18;
        i++;

        gp.obj[mapNum][i] = new Obj_IronShield(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 32;
        gp.obj[mapNum][i].worldY = gp.tileSize * 6;
        i++;

        gp.obj[mapNum][i] = new Obj_HealthPotion(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 17;
        gp.obj[mapNum][i].worldY = gp.tileSize * 18;
        i++;

        mapNum = 1;
        i = 0;
        gp.obj[mapNum][i] = new Obj_Table(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 17;
        gp.obj[mapNum][i].worldY = gp.tileSize * 18;
        i++;

    }

    public void setNPC() {
        int mapNum = 0;
        int i = 0;

        gp.npc[mapNum][i] = new NPC_Red(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize * 20;
        gp.npc[mapNum][i].worldY = gp.tileSize * 12;
        i++;

        mapNum = 1;
        i = 0;
        gp.npc[mapNum][i] = new NPC_Merchant(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize * 17;
        gp.npc[mapNum][i].worldY = gp.tileSize * 17;
        i++;
    }

    public void setMonster() {
        int mapNum = 0;
        int i = 0;
        gp.monster[mapNum][i] = new Slime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 14;
        gp.monster[mapNum][i].worldY = gp.tileSize * 9;
        i++;

        gp.monster[mapNum][i] = new Slime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 25;
        gp.monster[mapNum][i].worldY = gp.tileSize * 20;
        i++;

        gp.monster[mapNum][i] = new Slime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 7;
        gp.monster[mapNum][i].worldY = gp.tileSize * 9;
        i++;

        gp.monster[mapNum][i] = new Slime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 21;
        gp.monster[mapNum][i].worldY = gp.tileSize * 21;
        i++;

        gp.monster[mapNum][i] = new Slime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 24;
        gp.monster[mapNum][i].worldY = gp.tileSize * 19;
        i++;

        gp.monster[mapNum][i] = new Slime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 6;
        gp.monster[mapNum][i].worldY = gp.tileSize * 6;
        i++;

        gp.monster[mapNum][i] = new Slime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 26;
        gp.monster[mapNum][i].worldY = gp.tileSize * 15;


        //mapNum = 1; : if you want to put monster in the other map
    }

    public void setInteractiveTile() {
        int mapNum = 0;
        int i = 0;
        gp.iTile[mapNum][i] = new TI_Tree(gp, 15, 23); i++;
        gp.iTile[mapNum][i] = new TI_Tree(gp, 34, 12); i++;
        gp.iTile[mapNum][i] = new TI_Tree(gp, 12, 19); i++;
    }
}
