package tech.fallqvist.object;

import tech.fallqvist.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObject {

    private int index;
    private BufferedImage image;
    private String name;
    private boolean collision = false;
    private int worldX, worldY;
    private final Rectangle collisionArea = new Rectangle(0, 0, 48, 48);
    private final int collisionDefaultX = 0;
    private final int collisionDefaultY = 0;

    public void draw(Graphics2D graphics2D, GamePanel gamePanel) {
        int screenX = worldX - gamePanel.getPlayer().getWorldX() + gamePanel.getPlayer().getScreenX();
        int screenY = worldY - gamePanel.getPlayer().getWorldY() + gamePanel.getPlayer().getScreenY();

        if (worldX + gamePanel.getTileSize() > gamePanel.getPlayer().getWorldX() - gamePanel.getPlayer().getScreenX() &&
                worldX - gamePanel.getTileSize() < gamePanel.getPlayer().getWorldX() + gamePanel.getPlayer().getScreenX() &&
                worldY + gamePanel.getTileSize() > gamePanel.getPlayer().getWorldY() - gamePanel.getPlayer().getScreenY() &&
                worldY - gamePanel.getTileSize() < gamePanel.getPlayer().getWorldY() + gamePanel.getPlayer().getScreenY()) {

            graphics2D.drawImage(image, screenX, screenY, gamePanel.getTileSize(), gamePanel.getTileSize(), null);
        }
    }

    public int getIndex() {
        return index;
    }

    public SuperObject setIndex(int index) {
        this.index = index;
        return this;
    }

    public BufferedImage getImage() {
        return image;
    }

    public SuperObject setImage(BufferedImage image) {
        this.image = image;
        return this;
    }

    public String getName() {
        return name;
    }

    public SuperObject setName(String name) {
        this.name = name;
        return this;
    }

    public boolean isCollision() {
        return collision;
    }

    public SuperObject setCollision(boolean collision) {
        this.collision = collision;
        return this;
    }

    public int getWorldX() {
        return worldX;
    }

    public SuperObject setWorldX(int worldX) {
        this.worldX = worldX;
        return this;
    }

    public int getWorldY() {
        return worldY;
    }

    public SuperObject setWorldY(int worldY) {
        this.worldY = worldY;
        return this;
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
}
