package tile_interactive;

import Entity.Entity;
import main.GamePanel;

public class TI_Trunk extends InteractiveTile{

    GamePanel gp;

    public TI_Trunk(GamePanel gp, int col, int row) {
        super(gp, col, row);
        this.gp = gp;

        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;
        down1 = setup("/tiles_interactive/dryTree1", gp.tileSize, gp.tileSize);

        solidArea.x = 0;
        solidArea.y = 9;
        solidArea.width = 0;
        solidArea.height = 0;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}
