package tech.fallqvist.asset.tile;

import tech.fallqvist.GamePanel;
import tech.fallqvist.util.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {

    private final GamePanel gamePanel;
    private final Tile[] tiles;
    private final int[][][] mapTileNumbers;

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        this.tiles = new Tile[50];
        this.mapTileNumbers = new int[gamePanel.getMaxMaps()][gamePanel.getMaxWorldColumns()][gamePanel.getMaxWorldRows()];

        getTileImage();
        loadMap("/maps/worldV3.txt", 0);
        loadMap("/maps/interior01.txt", 1);
    }

    public void getTileImage() {
        // PLACEHOLDER
        setup(0, "grass00", false);
        setup(1, "grass00", false);
        setup(2, "grass00", false);
        setup(3, "grass00", false);
        setup(4, "grass00", false);
        setup(5, "grass00", false);
        setup(6, "grass00", false);
        setup(7, "grass00", false);
        setup(8, "grass00", false);
        setup(9, "grass00", false);
        // PLACEHOLDER

        // TILES LOAD IN
        setup(10, "grass00", false);
        setup(11, "grass01", false);
        setup(12, "water00", true);
        setup(13, "water01", true);
        setup(14, "water02", true);
        setup(15, "water03", true);
        setup(16, "water04", true);
        setup(17, "water05", true);
        setup(18, "water06", true);
        setup(19, "water07", true);
        setup(20, "water08", true);
        setup(21, "water09", true);
        setup(22, "water10", true);
        setup(23, "water11", true);
        setup(24, "water12", true);
        setup(25, "water13", true);
        setup(26, "road00", false);
        setup(27, "road01", false);
        setup(28, "road02", false);
        setup(29, "road03", false);
        setup(30, "road04", false);
        setup(31, "road05", false);
        setup(32, "road06", false);
        setup(33, "road07", false);
        setup(34, "road08", false);
        setup(35, "road09", false);
        setup(36, "road10", false);
        setup(37, "road11", false);
        setup(38, "road12", false);
        setup(39, "earth", false);
        setup(40, "wall", true);
        setup(41, "tree", true);
        setup(42, "hut", false);
        setup(43, "floor01", false);
        setup(44, "table01", true);
    }

    public void setup(int index, String imageName, boolean collision) {
        try {
            tiles[index] = new Tile();
            tiles[index].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/images/tiles/" + imageName + ".png"))));
            tiles[index].setImage(UtilityTool.scaleImage(tiles[index].getImage(), gamePanel.getTileSize(), gamePanel.getTileSize()));
            tiles[index].setCollision(collision);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String mapPath, int map) {
        try {
            InputStream inputStream = getClass().getResourceAsStream(mapPath);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            int column = 0;
            int row = 0;

            while (column < gamePanel.getMaxWorldColumns() && row < gamePanel.getMaxWorldRows()) {
                String line = bufferedReader.readLine();

                while (column < gamePanel.getMaxWorldColumns()) {
                    String[] numbers = line.split(" ");
                    int number = Integer.parseInt(numbers[column]);

                    mapTileNumbers[map][column][row] = number;
                    column++;
                }
                if (column == gamePanel.getMaxWorldColumns()) {
                    column = 0;
                    row++;
                }
            }

            bufferedReader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D graphics2D) {
        int worldColumn = 0;
        int worldRow = 0;

        while (worldColumn < gamePanel.getMaxWorldColumns() && worldRow < gamePanel.getMaxWorldRows()) {

            int tileNumber = mapTileNumbers[gamePanel.getCurrentMap()][worldColumn][worldRow];

            int worldX = worldColumn * gamePanel.getTileSize();
            int worldY = worldRow * gamePanel.getTileSize();
            int screenX = worldX - gamePanel.getPlayer().getWorldX() + gamePanel.getPlayer().getScreenX();
            int screenY = worldY - gamePanel.getPlayer().getWorldY() + gamePanel.getPlayer().getScreenY();

            // Stop moving the camera at world edge
            int rightOffset = gamePanel.getScreenWidth() - gamePanel.getPlayer().getScreenX();
            screenX = checkIfAtEdgeOfXAxis(worldX, screenX, rightOffset);

            int bottomOffset = gamePanel.getScreenHeight() - gamePanel.getPlayer().getScreenY();
            screenY = checkIfAtEdgeOfYAxis(worldY, screenY, bottomOffset);

            if (UtilityTool.isInsidePlayerView(worldX, worldY, gamePanel)) {
                graphics2D.drawImage(tiles[tileNumber].getImage(), screenX, screenY, null);

            } else if (gamePanel.getPlayer().getScreenX() > gamePanel.getPlayer().getWorldX()
                    || gamePanel.getPlayer().getScreenY() > gamePanel.getPlayer().getWorldY()
                    || rightOffset > gamePanel.getWorldWidth() - gamePanel.getPlayer().getWorldX()
                    || bottomOffset > gamePanel.getWorldHeight() - gamePanel.getPlayer().getWorldY()) {
                graphics2D.drawImage(tiles[tileNumber].getImage(), screenX, screenY, null);
            }

            worldColumn++;

            if (worldColumn == gamePanel.getMaxWorldColumns()) {
                worldColumn = 0;
                worldRow++;
            }
        }
    }

    private int checkIfAtEdgeOfXAxis(int worldX, int screenX, int rightOffset) {
        if (gamePanel.getPlayer().getScreenX() > gamePanel.getPlayer().getWorldX()) {
            return worldX;
        }

        if (rightOffset > gamePanel.getWorldWidth() - gamePanel.getPlayer().getWorldX()) {
            return gamePanel.getScreenWidth() - (gamePanel.getWorldWidth() - worldX);
        }

        return screenX;
    }

    private int checkIfAtEdgeOfYAxis(int worldY, int screenY, int bottomOffset) {
        if (gamePanel.getPlayer().getScreenY() > gamePanel.getPlayer().getWorldY()) {
            return worldY;
        }

        if (bottomOffset > gamePanel.getWorldHeight() - gamePanel.getPlayer().getWorldY()) {
            return gamePanel.getScreenHeight() - (gamePanel.getWorldHeight() - worldY);
        }

        return screenY;
    }

    public Tile[] getTiles() {
        return tiles;
    }

    public int[][][] getMapTileNumbers() {
        return mapTileNumbers;
    }
}
