package tile;

import main.GamePanel;
import main.UtilityTool;
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int[][][] mapTileNum;

    //ArrayList<String> fileNames = new ArrayList<>();
    //ArrayList<String> collisionStatus = new ArrayList<>();

    public TileManager(GamePanel gp) {
        this.gp = gp;

        tile = new Tile[50];
        mapTileNum = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        loadMap("/maps/world.txt", 0);
        loadMap("/maps/room1.txt", 1);
        /*
        //READ TILE DATA FILE
        InputStream is = getClass().getResourceAsStream("/maps/tileData.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        //GETTING TITLE NAME AND COLLISION INFO FROM THE FILE
        String line;
        try {
            while((line = br.readLine()) != null) {
                fileNames.add(line);
                collisionStatus.add(br.readLine());
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        tile = new Tile[fileNames.size()];
        getTileImage();

        is = getClass().getResourceAsStream("/maps/world2.txt");
        assert is != null;
        br = new BufferedReader(new InputStreamReader(is));

        try {
            String line2 = br.readLine();
            String[] maxTile = line2.split(" ");

            gp.maxWorldCol = maxTile.length;
            gp.maxWorldRow = maxTile.length;
            mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

            br.close();
        } catch (IOException e) {
            System.out.println("Exception!");
        }

         */

    }

    public void getTileImage() {
        /*
        for(int i = 0; i < fileNames.size(); i++) {
            String fileName;
            boolean collision;

            //GET FILENAME
            fileName = fileNames.get(i);

            //GET COLLISION STATUS
            if(collisionStatus.get(i).equals("true")) {
                collision = true;
            } else {
                collision = false;
            }

         */
        //PLACEHOLDER
        setup(0, "grass1", true);
        setup(1, "grass1", true);
        setup(2, "grass1", true);
        setup(3, "grass1", true);
        setup(4, "grass1", true);
        setup(5, "grass1", true);
        setup(6, "grass1", true);
        setup(7, "grass1", true);
        setup(8, "grass1", true);
        setup(9, "grass1", true);

        setup(10, "grass1", true);
        setup(11, "grass2", true);
        setup(12, "grass3", true);
        setup(13, "grass4", true);
        setup(14, "grass5", true);
        setup(15, "grass6", true);
        setup(16, "grass7", true);
        setup(17, "grass8", true);
        setup(18, "grass9", true);
        setup(19, "grass10", true);
        setup(20, "grass11", true);
        setup(21, "grass12", true);
        setup(22, "grass13", false);
        setup(23, "dp13", false);
        setup(24, "tree", true);
        setup(25, "sp5", false);
        setup(26, "wood", false);

        setup(27, "room0", true);
        setup(28, "room1", true);
        setup(29, "room2", true);
        setup(30, "room3", true);
        setup(31, "room4", false);
        setup(32, "room5", true);
        setup(33, "room6", true);
        setup(34, "room7", true);
        setup(35, "room8", true);
        setup(36, "room9", true);
        setup(37, "room10", true);
        setup(38, "room11", true);
        setup(39, "room12", true);
        setup(40, "room13", true);
        setup(41, "room14", true);

        setup(42, "floorMat0", false);
        setup(43, "floorMat1", false);
        setup(44, "floorMat2", false);

    }
    public void setup(int index, String imageName, boolean collision) {
        UtilityTool uTool = new UtilityTool();

        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName + ".png"));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath, int map) {

        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader((new InputStreamReader(is)));

            int col = 0;
            int row = 0;

            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {

                String line = br.readLine();
                if(line == null) break;

                String[] numbers = line.split(" ");
                while (col < gp.maxWorldCol) {

                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[map][col][row] = num;
                    col++;
                }
                if(col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void draw(Graphics2D g2) {

        int worldCol = 0;
        int worldRow = 0;


        while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

            int tileNum = mapTileNum[gp.currentMap][worldCol][worldRow];

            // Camera thing
            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                    worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                    worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                    worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

                    g2.drawImage(tile[tileNum].image, screenX, screenY, null);
            }
            worldCol++;

            if(worldCol == gp.maxWorldCol) {
                worldCol = 0;

                worldRow++;

            }
        }
    }
}
