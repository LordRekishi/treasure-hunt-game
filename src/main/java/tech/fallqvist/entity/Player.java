package tech.fallqvist.entity;

import tech.fallqvist.GamePanel;
import tech.fallqvist.util.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity {

    private final GamePanel gamePanel;
    private final KeyHandler keyHandler;
    private final int screenX;
    private final int screenY;
    private int numberOfKeys = 0;

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        this.screenX = gamePanel.getScreenWidth() / 2 - (gamePanel.getTileSize() / 2);
        this.screenY = gamePanel.getScreenHeight() / 2 - (gamePanel.getTileSize() / 2);

        setCollision();
        setDefaultValues();
        getPlayerImage();
    }

    private void setCollision() {
        setCollisionArea(new Rectangle(8, 16, 32, 32));
        setCollisionDefaultX(getCollisionArea().x);
        setCollisionDefaultY(getCollisionArea().y);
    }

    public void setDefaultValues() {
        setWorldX(gamePanel.getTileSize() * 23);
        setWorldY(gamePanel.getTileSize() * 21);
        setSpeed(4);
        setDirection("down");
    }

    public void getPlayerImage() {

        try {
            setUp1(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/images/player/boy_up_1.png"))));
            setUp2(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/images/player/boy_up_2.png"))));
            setDown1(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/images/player/boy_down_1.png"))));
            setDown2(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/images/player/boy_down_2.png"))));
            setLeft1(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/images/player/boy_left_1.png"))));
            setLeft2(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/images/player/boy_left_2.png"))));
            setRight1(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/images/player/boy_right_1.png"))));
            setRight2(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/images/player/boy_right_2.png"))));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {

        if (keyHandler.isUpPressed() || keyHandler.isDownPressed()
                || keyHandler.isLeftPressed() || keyHandler.isRightPressed()) {

            if (keyHandler.isUpPressed()) {
                setDirection("up");
            } else if (keyHandler.isDownPressed()) {
                setDirection("down");
            } else if (keyHandler.isLeftPressed()) {
                setDirection("left");
            } else if (keyHandler.isRightPressed()) {
                setDirection("right");
            }

            checkCollision();
            moveIfCollisionNotDetected();
            checkAndChangeSpriteAnimationImage();
        }
    }

    private void checkCollision() {
        setCollisionOn(false);
        gamePanel.getCollisionChecker().checkTile(this);
        int objectIndex = gamePanel.getCollisionChecker().checkObject(this, true);
        pickUpObject(objectIndex);
    }

    private void pickUpObject(int index) {

        if (index != 999) {
            String objectName = gamePanel.getObjects()[index].getName();

            switch (objectName) {
                case "Key" -> {
                    gamePanel.playSoundEffect(1);
                    numberOfKeys++;
                    gamePanel.getObjects()[index] = null;
                    gamePanel.getUi().showMessage("You got a key!");
                }
                case "Door" -> {
                    if (numberOfKeys > 0) {
                        gamePanel.playSoundEffect(3);
                        numberOfKeys--;
                        gamePanel.getObjects()[index] = null;
                        gamePanel.getUi().showMessage("You opened the door!");
                    } else {
                        gamePanel.getUi().showMessage("You need a key!");
                    }
                }
                case "Boots" -> {
                    gamePanel.playSoundEffect(2);
                    setSpeed(getSpeed() + 2);
                    gamePanel.getObjects()[index] = null;
                    gamePanel.getUi().showMessage("Speed Up!");
                }

                case "Chest" -> {
                    gamePanel.getUi().setGameFinished(true);
                    gamePanel.stopMusic();
                    gamePanel.playSoundEffect(4);
                }
            }
        }
    }

    private void moveIfCollisionNotDetected() {
        if (!isCollisionOn()) {
            switch (getDirection()) {
                case "up" -> setWorldY(getWorldY() - getSpeed());
                case "down" -> setWorldY(getWorldY() + getSpeed());
                case "left" -> setWorldX(getWorldX() - getSpeed());
                case "right" -> setWorldX(getWorldX() + getSpeed());
            }
        }
    }

    private void checkAndChangeSpriteAnimationImage() {
        setSpriteCounter(getSpriteCounter() + 1);
        if (getSpriteCounter() > 12) {
            if (getSpriteNumber() == 1) {
                setSpriteNumber(2);
            } else if (getSpriteNumber() == 2) {
                setSpriteNumber(1);
            }
            setSpriteCounter(0);
        }
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        graphics2D.drawImage(getDirectionalAnimationImage(), screenX, screenY, gamePanel.getTileSize(), gamePanel.getTileSize(), null);

    }

    private BufferedImage getDirectionalAnimationImage() {
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

    public int getScreenX() {
        return screenX;
    }

    public int getScreenY() {
        return screenY;
    }

    public int getNumberOfKeys() {
        return numberOfKeys;
    }

    public Player setNumberOfKeys(int numberOfKeys) {
        this.numberOfKeys = numberOfKeys;
        return this;
    }
}
