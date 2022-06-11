package tech.fallqvist;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    // SCREEN SETTINGS
    private final int originalTileSize = 16; // 16x16 tile
    private final int scale = 3;

    private final int tileSize = originalTileSize * scale; // 48x48 tile
    private final int maxScreenColumn = 16; // Tiles vertically
    private final int maxScreenRow = 12; // Tiles horizontally
    private final int screenWidth = tileSize * maxScreenColumn; // 768 px
    private final int screenHeight = tileSize * maxScreenRow; // 576 px

    private Thread gameThread;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
    }

    public void startGameThread() {
        this.gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        while (gameThread != null) {
            // 1: Update information, such as character position
            update();

            // 2: Draw the screen with updated information
            repaint(); // Calls the paintComponent() method

        }

    }

    public void update() {

    }

    public void paintComponent(Graphics graphics) {

        super.paintComponent(graphics);

        Graphics2D graphics2D = (Graphics2D) graphics;

        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRect(100, 100, tileSize, tileSize);
        graphics2D.dispose();
    }
}
