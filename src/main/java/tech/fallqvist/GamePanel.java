package tech.fallqvist;

import tech.fallqvist.entity.Entity;
import tech.fallqvist.entity.Player;
import tech.fallqvist.util.AssetManager;
import tech.fallqvist.object.Object;
import tech.fallqvist.sound.SoundManager;
import tech.fallqvist.tile.TileManager;
import tech.fallqvist.ui.UI;
import tech.fallqvist.util.CollisionChecker;
import tech.fallqvist.util.EventHandler;
import tech.fallqvist.util.KeyHandler;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    // SCREEN SETTINGS
    private final int originalTileSize = 16; // 16x16 tile
    private final int scale = 3;

    private final int tileSize = originalTileSize * scale; // 48x48 tile
    private final int maxScreenColumns = 16;
    private final int maxScreenRows = 12;
    private final int screenWidth = tileSize * maxScreenColumns; // 768 px
    private final int screenHeight = tileSize * maxScreenRows; // 576 px

    private final int FPS = 60;

    // WORLD SETTINGS
    private final int maxWorldColumns = 50;
    private final int maxWorldRows = 50;
    private final int worldWidth = tileSize * maxWorldColumns;
    private final int worldHeight = tileSize * maxWorldRows;

    // SYSTEM
    private final KeyHandler keyHandler = new KeyHandler(this);
    private final CollisionChecker collisionChecker = new CollisionChecker(this);
    private final TileManager tileManager = new TileManager(this);
    private final AssetManager assetManager = new AssetManager(this);
    private final SoundManager music = new SoundManager();
    private final SoundManager soundEffect = new SoundManager();
    private final UI ui = new UI(this);
    private final EventHandler eventHandler = new EventHandler(this);

    // GAME STATE
    private int gameState;
    private final int titleState = 0;
    private final int playState = 1;
    private final int pauseState = 2;
    private final int dialogueState = 3;

    // GAME THREAD
    private Thread gameThread;

    // ENTITIES & OBJECTS
    private final Player player = new Player(this, keyHandler);
    private final Object[] objects = new Object[10];
    private final Entity[] npcs = new Entity[10];

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void setUpGame() {
        assetManager.setObjects();
        assetManager.setNPCs();
        gameState = titleState;
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1_000_000_000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();

                delta--;
            }
        }
    }

    public void update() {
        if (gameState == playState) {
            player.update();
            for (Entity npc : npcs) {
                if (npc != null) {
                    npc.update();
                }
            }
        }

        if (gameState == pauseState) {
            // later update
        }
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;

        // DEBUG
        long drawStart = 0;
        if (keyHandler.isCheckDrawTime()) {
            drawStart = System.nanoTime();
        }

        if (gameState == titleState) {
            ui.draw(graphics2D);
        } else {

            // TILES
            tileManager.draw(graphics2D);

            // OBJECTS
            for (Object object : objects) {
                if (object != null) {
                    object.draw(graphics2D, this);
                }
            }

            // NPCS
            for (Entity npc : npcs) {
                if (npc != null) {
                    npc.draw(graphics2D);
                }
            }

            // PLAYER
            player.draw(graphics2D);

            // UI
            ui.draw(graphics2D);
        }

        // DEBUG
        if (keyHandler.isCheckDrawTime()) {
            long drawEnd = System.nanoTime();
            long passedTime = drawEnd - drawStart;

            graphics2D.setColor(Color.WHITE);
            graphics2D.drawString("Draw Time: " + passedTime, 10, 400);
            System.out.println("Draw Time: " + passedTime);
        }

        // CLOSE
        graphics2D.dispose();
    }

    public void playMusic(int index) {
        music.setFile(index);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    public void playSoundEffect(int index) {
        soundEffect.setFile(index);
        soundEffect.play();
    }

    public int getTileSize() {
        return tileSize;
    }

    public int getMaxScreenColumns() {
        return maxScreenColumns;
    }

    public int getMaxScreenRows() {
        return maxScreenRows;
    }

    public int getWorldWidth() {
        return worldWidth;
    }

    public int getWorldHeight() {
        return worldHeight;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public int getMaxWorldColumns() {
        return maxWorldColumns;
    }

    public int getMaxWorldRows() {
        return maxWorldRows;
    }

    public KeyHandler getKeyHandler() {
        return keyHandler;
    }

    public TileManager getTileManager() {
        return tileManager;
    }

    public CollisionChecker getCollisionChecker() {
        return collisionChecker;
    }

    public AssetManager getObjectManager() {
        return assetManager;
    }

    public Thread getGameThread() {
        return gameThread;
    }

    public GamePanel setGameThread(Thread gameThread) {
        this.gameThread = gameThread;
        return this;
    }

    public Player getPlayer() {
        return player;
    }

    public Object[] getObjects() {
        return objects;
    }

    public Entity[] getNpcs() {
        return npcs;
    }

    public UI getUi() {
        return ui;
    }

    public EventHandler getEventHandler() {
        return eventHandler;
    }

    public int getGameState() {
        return gameState;
    }

    public GamePanel setGameState(int gameState) {
        this.gameState = gameState;
        return this;
    }

    public int getTitleState() {
        return titleState;
    }

    public int getPlayState() {
        return playState;
    }

    public int getPauseState() {
        return pauseState;
    }

    public int getDialogueState() {
        return dialogueState;
    }
}
