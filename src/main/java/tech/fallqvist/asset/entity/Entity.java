package tech.fallqvist.asset.entity;

import tech.fallqvist.GamePanel;
import tech.fallqvist.asset.Asset;
import tech.fallqvist.asset.entity.monster.MON_GreenSlime;
import tech.fallqvist.util.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public abstract class Entity implements Asset {

    private final GamePanel gamePanel;

    // CHARACTER INFO
    private int index;
    private String name;
    private int worldX, worldY;
    private int speed;

    // ANIMATION
    private BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    private BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
    private String direction;
    private int spriteCounter = 0;
    private int spriteNumber = 1;
    private int actionLockCounter = 0;

    // COLLISION
    private Rectangle collisionArea = new Rectangle(0, 0, 48, 48);
    private int collisionDefaultX, collisionDefaultY;
    private boolean collisionOn = false;

    // COMBAT
    private boolean invincible = false;
    private int invincibleCounter = 0;
    private boolean attacking = false;

    // DIALOGUE
    private String[] dialogues = new String[20];
    private int dialogueIndex;

    // CHARACTER STATUS
    private int maxLife;
    private int currentLife;

    public Entity(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public abstract void getAnimationImages();

    public abstract void getAttackImages();

    public void setAction() {
        actionLockCounter++;

        if (actionLockCounter == 120) {

            Random random = new Random();
            int i = random.nextInt(100) + 1;

            if (i <= 25) {
                setDirection("up");
            }

            if (i > 25 && i <= 50) {
                setDirection("down");
            }

            if (i > 50 && i <= 75) {
                setDirection("left");
            }

            if (i > 75) {
                setDirection("right");
            }

            setActionLockCounter(0);
        }
    }

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

    @Override
    public void update() {
        setAction();

        collisionOn = false;
        gamePanel.getCollisionChecker().checkTile(this);
        gamePanel.getCollisionChecker().checkObject(this, false);
        gamePanel.getCollisionChecker().checkEntity(this, gamePanel.getNpcs());
        gamePanel.getCollisionChecker().checkEntity(this, gamePanel.getMonsters());
        boolean contactPlayer = gamePanel.getCollisionChecker().checkPlayer(this);

        if (this instanceof MON_GreenSlime && contactPlayer) {
            if (!gamePanel.getPlayer().isInvincible()) {
                gamePanel.getPlayer().setCurrentLife(gamePanel.getPlayer().getCurrentLife() - 1);
                gamePanel.getPlayer().setInvincible(true);
            }
        }

        moveIfCollisionNotDetected();
        checkAndChangeSpriteAnimationImage();
    }

    public void moveIfCollisionNotDetected() {
        if (!isCollisionOn() && !gamePanel.getKeyHandler().isEnterPressed()) {
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

    @Override
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
                if (!isAttacking()) {
                    if (getSpriteNumber() == 1)
                        image = getUp1();
                    if (getSpriteNumber() == 2)
                        image = getUp2();
                }

                if (isAttacking()){
                    if (getSpriteNumber() == 1)
                        image = getAttackUp1();
                    if (getSpriteNumber() == 2)
                        image = getAttackUp2();
                }
            }
            case "down" -> {
                if (!isAttacking()) {
                    if (getSpriteNumber() == 1)
                        image = getDown1();
                    if (getSpriteNumber() == 2)
                        image = getDown2();
                }

                if (isAttacking()){
                    if (getSpriteNumber() == 1)
                        image = getAttackDown1();
                    if (getSpriteNumber() == 2)
                        image = getAttackDown2();
                }

            }
            case "left" -> {
                if (!isAttacking()) {
                    if (getSpriteNumber() == 1)
                        image = getLeft1();
                    if (getSpriteNumber() == 2)
                        image = getLeft2();
                }

                if (isAttacking()) {
                    if (getSpriteNumber() == 1)
                        image = getAttackLeft1();
                    if (getSpriteNumber() == 2)
                        image = getAttackLeft2();
                }

            }
            case "right" -> {
                if (!isAttacking()) {
                    if (getSpriteNumber() == 1)
                        image = getRight1();
                    if (getSpriteNumber() == 2)
                        image = getRight2();
                }

                if (isAttacking()) {
                    if (getSpriteNumber() == 1)
                        image = getAttackRight1();
                    if (getSpriteNumber() == 2)
                        image = getAttackRight2();
                }

            }
        }
        return image;
    }

    public BufferedImage setup(String imagePath, int width, int height) {
        BufferedImage image = null;

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath + ".png")));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return UtilityTool.scaleImage(image, width, height);
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public String getName() {
        return name;
    }

    public Entity setName(String name) {
        this.name = name;
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

    public BufferedImage getAttackUp1() {
        return attackUp1;
    }

    public Entity setAttackUp1(BufferedImage attackUp1) {
        this.attackUp1 = attackUp1;
        return this;
    }

    public BufferedImage getAttackUp2() {
        return attackUp2;
    }

    public Entity setAttackUp2(BufferedImage attackUp2) {
        this.attackUp2 = attackUp2;
        return this;
    }

    public BufferedImage getAttackDown1() {
        return attackDown1;
    }

    public Entity setAttackDown1(BufferedImage attackDown1) {
        this.attackDown1 = attackDown1;
        return this;
    }

    public BufferedImage getAttackDown2() {
        return attackDown2;
    }

    public Entity setAttackDown2(BufferedImage attackDown2) {
        this.attackDown2 = attackDown2;
        return this;
    }

    public BufferedImage getAttackLeft1() {
        return attackLeft1;
    }

    public Entity setAttackLeft1(BufferedImage attackLeft1) {
        this.attackLeft1 = attackLeft1;
        return this;
    }

    public BufferedImage getAttackLeft2() {
        return attackLeft2;
    }

    public Entity setAttackLeft2(BufferedImage attackLeft2) {
        this.attackLeft2 = attackLeft2;
        return this;
    }

    public BufferedImage getAttackRight1() {
        return attackRight1;
    }

    public Entity setAttackRight1(BufferedImage attackRight1) {
        this.attackRight1 = attackRight1;
        return this;
    }

    public BufferedImage getAttackRight2() {
        return attackRight2;
    }

    public Entity setAttackRight2(BufferedImage attackRight2) {
        this.attackRight2 = attackRight2;
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

    public boolean isInvincible() {
        return invincible;
    }

    public Entity setInvincible(boolean invincible) {
        this.invincible = invincible;
        return this;
    }

    public int getInvincibleCounter() {
        return invincibleCounter;
    }

    public Entity setInvincibleCounter(int invincibleCounter) {
        this.invincibleCounter = invincibleCounter;
        return this;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
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

    public boolean isAttacking() {
        return attacking;
    }

    public Entity setAttacking(boolean attacking) {
        this.attacking = attacking;
        return this;
    }
}
