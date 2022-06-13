package tech.fallqvist.tile;

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
    private final int[][] mapTileNumbers;

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.tiles = new Tile[10];
        this.mapTileNumbers = new int[gamePanel.getMaxWorldColumns()][gamePanel.getMaxWorldRows()];

        getTileImage();
        loadMap("/maps/world01.txt");
    }

    public void getTileImage() {
            setup(0, "grass01", false);
            setup(1, "wall", true);
            setup(2, "water01", true);
            setup(3, "earth", false);
            setup(4, "tree", true);
            setup(5, "road00", false);
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

    public void loadMap(String mapPath) {
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

                    mapTileNumbers[column][row] = number;
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

            int tileNumber = mapTileNumbers[worldColumn][worldRow];

            int worldX = worldColumn * gamePanel.getTileSize();
            int worldY = worldRow * gamePanel.getTileSize();
            int screenX = worldX - gamePanel.getPlayer().getWorldX() + gamePanel.getPlayer().getScreenX();
            int screenY = worldY - gamePanel.getPlayer().getWorldY() + gamePanel.getPlayer().getScreenY();

            if (worldX + gamePanel.getTileSize() > gamePanel.getPlayer().getWorldX() - gamePanel.getPlayer().getScreenX() &&
                    worldX - gamePanel.getTileSize() < gamePanel.getPlayer().getWorldX() + gamePanel.getPlayer().getScreenX() &&
                    worldY + gamePanel.getTileSize() > gamePanel.getPlayer().getWorldY() - gamePanel.getPlayer().getScreenY() &&
                    worldY - gamePanel.getTileSize() < gamePanel.getPlayer().getWorldY() + gamePanel.getPlayer().getScreenY()) {

                graphics2D.drawImage(tiles[tileNumber].getImage(), screenX, screenY, null);
            }

            worldColumn++;

            if (worldColumn == gamePanel.getMaxWorldColumns()) {
                worldColumn = 0;
                worldRow++;
            }
        }
    }

    public Tile[] getTiles() {
        return tiles;
    }

    public int[][] getMapTileNumbers() {
        return mapTileNumbers;
    }
}
