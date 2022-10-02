package tech.fallqvist;

import tech.fallqvist.asset.Asset;
import tech.fallqvist.asset.AssetManager;
import tech.fallqvist.asset.entity.Entity;
import tech.fallqvist.asset.entity.player.Player;
import tech.fallqvist.asset.object.Object;
import tech.fallqvist.asset.tile.TileManager;
import tech.fallqvist.asset.tile.interactive.InteractiveTile;
import tech.fallqvist.event.EventHandler;
import tech.fallqvist.sound.SoundManager;
import tech.fallqvist.ui.UI;
import tech.fallqvist.util.CollisionChecker;
import tech.fallqvist.util.Config;
import tech.fallqvist.util.KeyHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GamePanel extends JPanel implements Runnable {

    // SCREEN SETTINGS
    private final int originalTileSize = 16; // 16x16 tile
    private final int scale = 3;

    private final int tileSize = originalTileSize * scale; // 48x48 tile
    private final int maxScreenColumns = 20;
    private final int maxScreenRows = 12;

    // Window mode
    private final int screenWidth = tileSize * maxScreenColumns; // 960 px
    private final int screenHeight = tileSize * maxScreenRows; // 576 px

    // Full screen mode
    private int fullScreenWidth = screenWidth;
    private int fullScreenHeight = screenHeight;
    private BufferedImage tempScreen;
    private Graphics2D graphics2D;

    private final int FPS = 60;

    // WORLD SETTINGS
    private final int maxWorldColumns = 50;
    private final int maxWorldRows = 50;
    private final int worldWidth = tileSize * maxWorldColumns;
    private final int worldHeight = tileSize * maxWorldRows;
    private final int maxMaps = 10;

    // SYSTEM
    private final KeyHandler keyHandler = new KeyHandler(this);
    private final CollisionChecker collisionChecker = new CollisionChecker(this);
    private final TileManager tileManager = new TileManager(this);
    private final AssetManager assetManager = new AssetManager(this);
    private final SoundManager music = new SoundManager();
    private final SoundManager soundEffect = new SoundManager();
    private final UI ui = new UI(this);
    private final EventHandler eventHandler = new EventHandler(this);
    private final Config config = new Config(this);
    private final int titleState = 0;
    private final int playState = 1;
    private final int pauseState = 2;
    private final int dialogueState = 3;
    private final int characterState = 4;
    private final int optionState = 5;
    private final int transitionState = 7;
    private final int gameOverState = 6;
    // GAME STATE
    private int gameState;
    private final int tradeState = 8;

    // ENTITIES & OBJECTS
    private final List<Asset> assets = new ArrayList<>();
    private boolean fullScreenOn;

    // GAME THREAD
    private Thread gameThread;
    private int currentMap = 0;
    private final Asset[][] objects = new Object[maxMaps][20];
    private final Player player = new Player(this, keyHandler);
    private final Asset[][] npcs = new Entity[maxMaps][10];
    private final Asset[][] monsters = new Entity[maxMaps][20];
    private final InteractiveTile[][] interactiveTiles = new InteractiveTile[maxMaps][50];
    private final List<Asset> projectiles = new ArrayList<>();
    private final List<Asset> particles = new ArrayList<>();

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
        assetManager.setMonsters();
        assetManager.setInteractiveTiles();
        gameState = titleState;

        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        graphics2D = (Graphics2D) tempScreen.getGraphics();

        if (fullScreenOn) {
            setFullScreen();
        }
    }

    public void setFullScreen() {

        // GET LOCAL SCREEN DEVICE
        GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();

        graphicsDevice.setFullScreenWindow(Main.window);

        // GET FULLSCREEN WIDTH & HEIGHT
        fullScreenWidth = Main.window.getWidth();
        fullScreenHeight = Main.window.getHeight();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void retry() {
        player.setDefaultPosition();
        player.restoreLifeAndMana();
        assetManager.setNPCs();
        assetManager.setMonsters();
        gameState = playState;
    }

    public void restart() {
        player.setItems();
        player.setDefaultValues();
        assetManager.setObjects();
        assetManager.setNPCs();
        assetManager.setMonsters();
        assetManager.setInteractiveTiles();
        gameState = titleState;
        stopMusic();
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
                drawToTempScreen();
                drawToScreen();
                delta--;
            }
        }
    }

    public void update() {
        if (gameState == playState) {
            player.update();
            updateNPCs();
            updateMonsters();
            updateProjectiles();
            updateParticles();
            updateInteractiveTiles();
        }

        if (gameState == pauseState) {
            // later update
        }
    }

    private void updateNPCs() {
        for (int i = 0; i < npcs[1].length; i++) {
            if (npcs[currentMap][i] != null) {
                npcs[currentMap][i].update();
            }
        }
    }

    private void updateMonsters() {
        for (int i = 0; i < monsters[1].length; i++) {
            if (monsters[currentMap][i] != null) {
                if (monsters[currentMap][i].isAlive() && !monsters[currentMap][i].isDying()) {
                    monsters[currentMap][i].update();
                }

                if (!monsters[currentMap][i].isAlive()) {
                    monsters[currentMap][i].checkDrop();
                    removeMonster(monsters[currentMap][i].getIndex());
                }
            }
        }
    }

    private void removeMonster(int index) {
        monsters[currentMap][index] = null;
    }

    private void updateProjectiles() {
        for (int i = 0; i < projectiles.size(); i++) {
            if (projectiles.get(i) != null) {
                if (projectiles.get(i).isAlive()) {
                    projectiles.get(i).update();
                }

                if (!projectiles.get(i).isAlive()) {
                    projectiles.remove(projectiles.get(i));
                }
            }
        }
    }

    private void updateParticles() {
        for (int i = 0; i < particles.size(); i++) {
            if (particles.get(i) != null) {
                if (particles.get(i).isAlive()) {
                    particles.get(i).update();
                }

                if (!particles.get(i).isAlive()) {
                    particles.remove(particles.get(i));
                }
            }
        }
    }

    private void updateInteractiveTiles() {
        for (int i = 0; i < interactiveTiles[1].length; i++) {
            if (interactiveTiles[currentMap][i] != null) {
                interactiveTiles[currentMap][i].update();
            }
        }
    }

    public void drawToTempScreen() {

        // DEBUG
        long drawStart = 0;
        if (keyHandler.isShowDebugText()) {
            drawStart = System.nanoTime();
        }

        if (gameState == titleState) {
            ui.draw(graphics2D);
        } else {

            // TILES
            tileManager.draw(graphics2D);
            drawInteractiveTiles(graphics2D);

            // ASSETS
            addAssets();
            sortAssets();
            drawAssets(graphics2D);
            assets.clear();

            // UI
            ui.draw(graphics2D);
        }

        // DEBUG
        if (keyHandler.isShowDebugText()) {
            drawDebugInfo(graphics2D, drawStart);
        }
    }

    private void drawInteractiveTiles(Graphics2D graphics2D) {
        for (int i = 0; i < interactiveTiles[1].length; i++) {
            if (interactiveTiles[currentMap][i] != null) {
                interactiveTiles[currentMap][i].draw(graphics2D);
            }
        }
    }

    private void addAssets() {
        assets.add(player);

        for (int i = 0; i < npcs[1].length; i++) {
            if (npcs[currentMap][i] != null) {
                assets.add(npcs[currentMap][i]);
            }
        }

        for (int i = 0; i < objects[1].length; i++) {
            if (objects[currentMap][i] != null) {
                assets.add(objects[currentMap][i]);
            }
        }

        for (int i = 0; i < monsters[1].length; i++) {
            if (monsters[currentMap][i] != null) {
                assets.add(monsters[currentMap][i]);
            }
        }

        for (Asset projectile : projectiles) {
            if (projectile != null) {
                assets.add(projectile);
            }
        }

        for (Asset particle : particles) {
            if (particle != null) {
                assets.add(particle);
            }
        }
    }

    private void sortAssets() {
        assets.sort(Comparator.comparingInt(Asset::getWorldY));
    }

    private void drawAssets(Graphics2D graphics2D) {
        for (Asset asset : assets) {
            asset.draw(graphics2D);
        }
    }

    private void drawDebugInfo(Graphics2D graphics2D, long drawStart) {
        long drawEnd = System.nanoTime();
        long passedTime = drawEnd - drawStart;
        int x = 10;
        int y = 400;
        int lineHeight = 20;

        graphics2D.setFont(new Font("Arial", Font.PLAIN, 20));
        graphics2D.setColor(Color.WHITE);

        graphics2D.drawString("WorldX: " + player.getWorldX(), x, y);
        y += lineHeight;
        graphics2D.drawString("WorldY: " + player.getWorldY(), x, y);
        y += lineHeight;
        graphics2D.drawString("Col: " + (player.getWorldX() + player.getCollisionArea().x) / tileSize, x, y);
        y += lineHeight;
        graphics2D.drawString("Row: " + (player.getWorldY() + player.getCollisionArea().y) / tileSize, x, y);
        y += lineHeight;
        graphics2D.drawString("Draw Time: " + passedTime, x, y);
    }

    public void drawToScreen() {
        Graphics graphics = getGraphics();
        graphics.drawImage(tempScreen, 0, 0, fullScreenWidth, fullScreenHeight, null);
        graphics.dispose();
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

    public boolean isFullScreenOn() {
        return fullScreenOn;
    }

    public GamePanel setFullScreenOn(boolean fullScreenOn) {
        this.fullScreenOn = fullScreenOn;
        return this;
    }

    public KeyHandler getKeyHandler() {
        return keyHandler;
    }

    public TileManager getTileManager() {
        return tileManager;
    }

    public AssetManager getAssetManager() {
        return assetManager;
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

    public Asset[][] getObjects() {
        return objects;
    }

    public Asset[][] getNpcs() {
        return npcs;
    }

    public Asset[][] getMonsters() {
        return monsters;
    }

    public UI getUi() {
        return ui;
    }

    public EventHandler getEventHandler() {
        return eventHandler;
    }

    public Config getConfig() {
        return config;
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

    public int getCharacterState() {
        return characterState;
    }

    public int getOptionState() {
        return optionState;
    }

    public int getGameOverState() {
        return gameOverState;
    }

    public List<Asset> getProjectiles() {
        return projectiles;
    }

    public InteractiveTile[][] getInteractiveTiles() {
        return interactiveTiles;
    }

    public List<Asset> getParticles() {
        return particles;
    }

    public SoundManager getMusic() {
        return music;
    }

    public SoundManager getSoundEffect() {
        return soundEffect;
    }

    public int getMaxMaps() {
        return maxMaps;
    }

    public int getCurrentMap() {
        return currentMap;
    }

    public GamePanel setCurrentMap(int currentMap) {
        this.currentMap = currentMap;
        return this;
    }

    public int getTransitionState() {
        return transitionState;
    }

    public int getTradeState() {
        return tradeState;
    }
}
