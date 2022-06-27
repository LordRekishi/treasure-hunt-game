package tech.fallqvist.asset.object;

import tech.fallqvist.GamePanel;
import tech.fallqvist.asset.Asset;
import tech.fallqvist.util.UtilityTool;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Object implements Asset {

    private final GamePanel gamePanel;

    private int index;
    private BufferedImage image1, image2, image3;
    private String name;
    private boolean collision = false;
    private int worldX, worldY;
    private Rectangle collisionArea = new Rectangle(0, 0, 48, 48);
    private int collisionDefaultX = 0;
    private int collisionDefaultY = 0;

    public Object(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        int screenX = worldX - gamePanel.getPlayer().getWorldX() + gamePanel.getPlayer().getScreenX();
        int screenY = worldY - gamePanel.getPlayer().getWorldY() + gamePanel.getPlayer().getScreenY();

        if (UtilityTool.isInsidePlayerView(worldX, worldY, gamePanel)) {
            graphics2D.drawImage(image1, screenX, screenY, gamePanel.getTileSize(), gamePanel.getTileSize(), null);
        }
    }

    @Override
    public void update() {
        // Not used
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public BufferedImage getImage1() {
        return image1;
    }

    public Object setImage1(BufferedImage image1) {
        this.image1 = image1;
        return this;
    }

    public BufferedImage getImage2() {
        return image2;
    }

    public Object setImage2(BufferedImage image2) {
        this.image2 = image2;
        return this;
    }

    public BufferedImage getImage3() {
        return image3;
    }

    public Object setImage3(BufferedImage image3) {
        this.image3 = image3;
        return this;
    }

    public String getName() {
        return name;
    }

    public Object setName(String name) {
        this.name = name;
        return this;
    }

    public boolean isCollision() {
        return collision;
    }

    public Object setCollision(boolean collision) {
        this.collision = collision;
        return this;
    }

    public int getWorldX() {
        return worldX;
    }

    public void setWorldX(int worldX) {
        this.worldX = worldX;
    }

    public int getWorldY() {
        return worldY;
    }

    public void setWorldY(int worldY) {
        this.worldY = worldY;
    }

    public Rectangle getCollisionArea() {
        return collisionArea;
    }

    public int getCollisionDefaultX() {
        return collisionDefaultX;
    }

    public int getCollisionDefaultY() {
        return collisionDefaultY;
    }

    public Object setCollisionDefaultX(int collisionDefaultX) {
        this.collisionDefaultX = collisionDefaultX;
        return this;
    }

    public Object setCollisionDefaultY(int collisionDefaultY) {
        this.collisionDefaultY = collisionDefaultY;
        return this;
    }

    @Override
    public void speak() {
        // Not used
    }
}
