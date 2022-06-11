package tech.fallqvist.entity;

import tech.fallqvist.GamePanel;
import tech.fallqvist.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity {

    private final GamePanel gamePanel;
    private final KeyHandler keyHandler;

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        setX(100);
        setY(100);
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

        if (keyHandler.upPressed) {
            setDirection("up");
            setY(getY() - getSpeed());
        }
        else if (keyHandler.downPressed) {
            setDirection("down");
            setY(getY() + getSpeed());
        }
        else if (keyHandler.leftPressed) {
            setDirection("left");
            setX(getX() - getSpeed());
        }
        else if (keyHandler.rightPressed) {
            setDirection("right");
            setX(getX() + getSpeed());
        }
    }

    @Override
    public void draw(Graphics2D graphics2D) {

        BufferedImage image = null;

        switch (getDirection()) {
            case "up" -> image = getUp1();
            case "down" -> image = getDown1();
            case "left" -> image = getLeft1();
            case "right" -> image = getRight1();
        }

        graphics2D.drawImage(image, getX(), getY(), gamePanel.getTileSize(), gamePanel.getTileSize(), null);

    }
}
