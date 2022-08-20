package tech.fallqvist.asset.object;

import tech.fallqvist.GamePanel;
import tech.fallqvist.asset.Asset;
import tech.fallqvist.util.UtilityTool;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Object implements Asset {

    private final GamePanel gamePanel;

    // OBJECT STATS
    private int index;
    private String name;
    private String description;
    private int value;
    private int worldX, worldY;
    private int price;

    // IMAGES
    private BufferedImage image1, image2, image3;

    // COLLISION
    private boolean collision = false;
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

    public String getDescription() {
        return description;
    }

    public Object setDescription(String description) {
        this.description = description;
        return this;
    }

    public int getValue() {
        return value;
    }

    public Object setValue(int value) {
        this.value = value;
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
    public int getPrice() {
        return price;
    }

    public Object setPrice(int price) {
        this.price = price;
        return this;
    }

    // NOT USED
    @Override
    public Rectangle getAttackArea() {
        return null;
    }

    @Override
    public void setAttackArea(Rectangle attackArea) {
    }

    @Override
    public void speak() {
        // Not used
    }

    @Override
    public void damageReaction() {
        // Not used
    }

    @Override
    public boolean isInvincible() {
        return false;
    }

    @Override
    public void setInvincible(boolean invincible) {
        // Not used
    }

    @Override
    public int getCurrentLife() {
        return 0;
    }

    @Override
    public void setCurrentLife(int currentLife) {
        // Not used
    }

    @Override
    public boolean isAlive() {
        return false;
    }

    @Override
    public void setAlive(boolean alive) {
        // Not used
    }

    @Override
    public boolean isDying() {
        return false;
    }

    @Override
    public void setDying(boolean dying) {
        // Not used
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public int getStrength() {
        return 0;
    }

    @Override
    public int getDexterity() {
        return 0;
    }

    @Override
    public int getAttackPower() {
        return 0;
    }

    @Override
    public int getDefensePower() {
        return 0;
    }

    @Override
    public int getExp() {
        return 0;
    }

    @Override
    public int getNextLevelExp() {
        return 0;
    }

    @Override
    public int getCoins() {
        return 0;
    }

    @Override
    public Object getCurrentWeapon() {
        return null;
    }

    @Override
    public Object getCurrentShield() {
        return null;
    }

    @Override
    public void use() {
        // not used
    }

    @Override
    public int getMaxLife() {
        return 0;
    }

    @Override
    public void checkDrop() {
    }

    @Override
    public void dropObject(Asset droppedObject) {
    }

    @Override
    public Color getParticleColor() {
        return null;
    }

    @Override
    public int getParticleSize() {
        return 0;
    }

    @Override
    public int getParticleSpeed() {
        return 0;
    }

    @Override
    public int getParticleMaxLife() {
        return 0;
    }
}
