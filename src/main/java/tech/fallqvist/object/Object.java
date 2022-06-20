package tech.fallqvist.object;

import tech.fallqvist.GamePanel;
import tech.fallqvist.util.UtilityTool;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Object {

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

        if (UtilityTool.isInsidePlayerView(worldX, worldY, gamePanel)) {
            graphics2D.drawImage(image, screenX, screenY, gamePanel.getTileSize(), gamePanel.getTileSize(), null);
        }
    }

    public int getIndex() {
        return index;
    }

    public Object setIndex(int index) {
        this.index = index;
        return this;
    }

    public BufferedImage getImage() {
        return image;
    }

    public Object setImage(BufferedImage image) {
        this.image = image;
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

    public Object setWorldX(int worldX) {
        this.worldX = worldX;
        return this;
    }

    public int getWorldY() {
        return worldY;
    }

    public Object setWorldY(int worldY) {
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
