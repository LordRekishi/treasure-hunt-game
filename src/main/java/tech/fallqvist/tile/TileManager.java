package tech.fallqvist.tile;

import tech.fallqvist.GamePanel;

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
        this.mapTileNumbers = new int[gamePanel.getMaxScreenColumns()][gamePanel.getMaxScreenRows()];

        getTileImage();
        loadMap("/maps/map01.txt");
    }

    public void getTileImage() {
        try {
            tiles[0] = new Tile();
            tiles[0].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/images/tiles/grass01.png"))));

            tiles[1] = new Tile();
            tiles[1].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/images/tiles/wall.png"))));

            tiles[2] = new Tile();
            tiles[2].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/images/tiles/water01.png"))));

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

            while (column < gamePanel.getMaxScreenColumns() && row < gamePanel.getMaxScreenRows()) {
                String line = bufferedReader.readLine();

                while (column < gamePanel.getMaxScreenColumns()) {
                    String[] numbers = line.split(" ");
                    int number = Integer.parseInt(numbers[column]);

                    mapTileNumbers[column][row] = number;
                    column++;
                }
                if (column == gamePanel.getMaxScreenColumns()) {
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
        int column = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while (column < gamePanel.getMaxScreenColumns() && row < gamePanel.getMaxScreenRows()) {

            int tileNumber = mapTileNumbers[column][row];

            graphics2D.drawImage(tiles[tileNumber].getImage(), x, y, gamePanel.getTileSize(), gamePanel.getTileSize(), null);
            column++;
            x += gamePanel.getTileSize();

            if (column == gamePanel.getMaxScreenColumns()) {
                column = 0;
                x = 0;
                row++;
                y += gamePanel.getTileSize();
            }
        }
    }
}
