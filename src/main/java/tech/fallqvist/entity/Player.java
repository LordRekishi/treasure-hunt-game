package tech.fallqvist.entity;

import tech.fallqvist.GamePanel;
import tech.fallqvist.KeyHandler;

import java.awt.*;

public class Player extends Entity {

    private final GamePanel gamePanel;
    private final KeyHandler keyHandler;

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        setDefaultValues();
    }

    public void setDefaultValues() {
        setX(100);
        setY(100);
        setSpeed(4);
    }

    @Override
    public void update() {
        if (keyHandler.upPressed) {
            setY(getY() - getSpeed());
        }
        else if (keyHandler.downPressed) {
            setY(getY() + getSpeed());
        }
        else if (keyHandler.leftPressed) {
            setX(getX() - getSpeed());
        }
        else if (keyHandler.rightPressed) {
            setX(getX() + getSpeed());
        }
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRect(getX(), getY(), gamePanel.getTileSize(), gamePanel.getTileSize());
    }
}
