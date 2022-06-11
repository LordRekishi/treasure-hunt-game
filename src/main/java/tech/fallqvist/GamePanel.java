package tech.fallqvist;

import tech.fallqvist.entity.Entity;
import tech.fallqvist.entity.Player;

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

    // FPS
    private int FPS = 60;

    private KeyHandler keyHandler = new KeyHandler();
    private Thread gameThread;
    private Entity player = new Player(this, keyHandler);


    // Set player default position
    private int playerX = 100;
    private int playerY = 100;
    private int playerSpeed = 4;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void startGameThread() {
        this.gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                // 1: Update information, such as character position
                update();

                // 2: Draw the screen with updated information
                repaint(); // Calls the paintComponent() method

                delta--;
                drawCount++;
            }

            if (timer >= 1000000000) {
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }

        }

    }

    public void update() {
        player.update();
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D graphics2D = (Graphics2D) graphics;

        player.draw(graphics2D);

        graphics2D.dispose();
    }

    public int getTileSize() {
        return tileSize;
    }
}
