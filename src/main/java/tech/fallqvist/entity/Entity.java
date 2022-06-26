package tech.fallqvist.entity;

import tech.fallqvist.GamePanel;
import tech.fallqvist.util.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public abstract class Entity {

    private final GamePanel gamePanel;

    // CHARACTER INFO
    private int index;
    private int worldX, worldY;
    private int speed;
    private BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    private String direction;
    private int spriteCounter = 0;
    private int spriteNumber = 1;
    private Rectangle collisionArea = new Rectangle(8, 16, 32, 32);
    private int collisionDefaultX, collisionDefaultY;
    private boolean collisionOn = false;
    private int actionLockCounter = 0;
    private String[] dialogues = new String[20];
    private int dialogueIndex;

    // CHARACTER STATUS
    private int maxLife;
    private int currentLife;

    public Entity(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setAction() {}

    public void speak() {
        if (dialogues[dialogueIndex] == null) {
            setDialogueIndex(0);
        }

        gamePanel.getUi().setCurrentDialogue(getDialogues()[dialogueIndex]);
        dialogueIndex++;

        switch (gamePanel.getPlayer().getDirection()) {
            case "up" -> setDirection("down");
            case "down" -> setDirection("up");
            case "left" -> setDirection("right");
            case "right" -> setDirection("left");
        }
    }

    public void update() {
        setAction();

        collisionOn = false;
        gamePanel.getCollisionChecker().checkTile(this);
        gamePanel.getCollisionChecker().checkObject(this, false);
        gamePanel.getCollisionChecker().checkPlayer(this);

        moveIfCollisionNotDetected();
        checkAndChangeSpriteAnimationImage();
    }

    public void moveIfCollisionNotDetected() {
        if (!isCollisionOn()) {
            switch (getDirection()) {
                case "up" -> setWorldY(getWorldY() - getSpeed());
                case "down" -> setWorldY(getWorldY() + getSpeed());
                case "left" -> setWorldX(getWorldX() - getSpeed());
                case "right" -> setWorldX(getWorldX() + getSpeed());
            }
        }
    }

    public void checkAndChangeSpriteAnimationImage() {
        spriteCounter++;
        if (spriteCounter > 12) {
            if (spriteNumber == 1) {
                setSpriteNumber(2);
            } else if (spriteNumber == 2) {
                setSpriteNumber(1);
            }
            setSpriteCounter(0);
        }
    }

    public void draw(Graphics2D graphics2D) {
        int screenX = worldX - gamePanel.getPlayer().getWorldX() + gamePanel.getPlayer().getScreenX();
        int screenY = worldY - gamePanel.getPlayer().getWorldY() + gamePanel.getPlayer().getScreenY();

        if (UtilityTool.isInsidePlayerView(worldX, worldY, gamePanel)) {
            graphics2D.drawImage(getDirectionalAnimationImage(), screenX, screenY, gamePanel.getTileSize(), gamePanel.getTileSize(), null);
        }
    }

    public BufferedImage getDirectionalAnimationImage() {
        BufferedImage image = null;

        switch (getDirection()) {
            case "up" -> {
                if (getSpriteNumber() == 1)
                    image = getUp1();
                if (getSpriteNumber() == 2)
                    image = getUp2();
            }
            case "down" -> {
                if (getSpriteNumber() == 1)
                    image = getDown1();
                if (getSpriteNumber() == 2)
                    image = getDown2();
            }
            case "left" -> {
                if (getSpriteNumber() == 1)
                    image = getLeft1();
                if (getSpriteNumber() == 2)
                    image = getLeft2();
            }
            case "right" -> {
                if (getSpriteNumber() == 1)
                    image = getRight1();
                if (getSpriteNumber() == 2)
                    image = getRight2();
            }
        }
        return image;
    }

    public BufferedImage setup(String imagePath) {
        BufferedImage image = null;

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath + ".png")));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return UtilityTool.scaleImage(image, getGamePanel().getTileSize(), getGamePanel().getTileSize());
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public int getWorldX() {
        return worldX;
    }

    public Entity setWorldX(int worldX) {
        this.worldX = worldX;
        return this;
    }

    public int getWorldY() {
        return worldY;
    }

    public Entity setWorldY(int worldY) {
        this.worldY = worldY;
        return this;
    }

    public int getSpeed() {
        return speed;
    }

    public Entity setSpeed(int speed) {
        this.speed = speed;
        return this;
    }

    public BufferedImage getUp1() {
        return up1;
    }

    public Entity setUp1(BufferedImage up1) {
        this.up1 = up1;
        return this;
    }

    public BufferedImage getUp2() {
        return up2;
    }

    public Entity setUp2(BufferedImage up2) {
        this.up2 = up2;
        return this;
    }

    public BufferedImage getDown1() {
        return down1;
    }

    public Entity setDown1(BufferedImage down1) {
        this.down1 = down1;
        return this;
    }

    public BufferedImage getDown2() {
        return down2;
    }

    public Entity setDown2(BufferedImage down2) {
        this.down2 = down2;
        return this;
    }

    public BufferedImage getLeft1() {
        return left1;
    }

    public Entity setLeft1(BufferedImage left1) {
        this.left1 = left1;
        return this;
    }

    public BufferedImage getLeft2() {
        return left2;
    }

    public Entity setLeft2(BufferedImage left2) {
        this.left2 = left2;
        return this;
    }

    public BufferedImage getRight1() {
        return right1;
    }

    public Entity setRight1(BufferedImage right1) {
        this.right1 = right1;
        return this;
    }

    public BufferedImage getRight2() {
        return right2;
    }

    public Entity setRight2(BufferedImage right2) {
        this.right2 = right2;
        return this;
    }

    public String getDirection() {
        return direction;
    }

    public Entity setDirection(String direction) {
        this.direction = direction;
        return this;
    }

    public int getSpriteCounter() {
        return spriteCounter;
    }

    public Entity setSpriteCounter(int spriteCounter) {
        this.spriteCounter = spriteCounter;
        return this;
    }

    public int getSpriteNumber() {
        return spriteNumber;
    }

    public Entity setSpriteNumber(int spriteNumber) {
        this.spriteNumber = spriteNumber;
        return this;
    }

    public Rectangle getCollisionArea() {
        return collisionArea;
    }

    public Entity setCollisionArea(Rectangle collisionArea) {
        this.collisionArea = collisionArea;
        return this;
    }

    public int getCollisionDefaultX() {
        return collisionDefaultX;
    }

    public Entity setCollisionDefaultX(int collisionDefaultX) {
        this.collisionDefaultX = collisionDefaultX;
        return this;
    }

    public int getCollisionDefaultY() {
        return collisionDefaultY;
    }

    public Entity setCollisionDefaultY(int collisionDefaultY) {
        this.collisionDefaultY = collisionDefaultY;
        return this;
    }

    public boolean isCollisionOn() {
        return collisionOn;
    }

    public Entity setCollisionOn(boolean collisionOn) {
        this.collisionOn = collisionOn;
        return this;
    }

    public int getActionLockCounter() {
        return actionLockCounter;
    }

    public Entity setActionLockCounter(int actionLockCounter) {
        this.actionLockCounter = actionLockCounter;
        return this;
    }

    public int getIndex() {
        return index;
    }

    public Entity setIndex(int index) {
        this.index = index;
        return this;
    }

    public String[] getDialogues() {
        return dialogues;
    }

    public Entity setDialogues(String[] dialogues) {
        this.dialogues = dialogues;
        return this;
    }

    public int getDialogueIndex() {
        return dialogueIndex;
    }

    public Entity setDialogueIndex(int dialogueIndex) {
        this.dialogueIndex = dialogueIndex;
        return this;
    }

    public int getMaxLife() {
        return maxLife;
    }

    public Entity setMaxLife(int maxLife) {
        this.maxLife = maxLife;
        return this;
    }

    public int getCurrentLife() {
        return currentLife;
    }

    public Entity setCurrentLife(int currentLife) {
        this.currentLife = currentLife;
        return this;
    }
}
