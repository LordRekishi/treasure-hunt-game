package tech.fallqvist.asset.entity.particle;

import tech.fallqvist.GamePanel;
import tech.fallqvist.asset.Asset;
import tech.fallqvist.asset.entity.Entity;

import java.awt.*;

public class Particle extends Entity {

    private Asset generator;
    private Color color;
    private int size;
    private int xd, yd;

    public Particle(GamePanel gamePanel, Asset generator, Color color, int size, int speed, int maxLife, int xd, int yd) {
        super(gamePanel);

        this.generator = generator;
        this.color = color;
        this.size = size;
        this.setSpeed(speed);
        this.setMaxLife(maxLife);
        this.xd = xd;
        this.yd = yd;

        setCurrentLife(maxLife);
        int offSet = (gamePanel.getTileSize() / 2) - (size / 2);
        setWorldX(generator.getWorldX() + offSet);
        setWorldY(generator.getWorldY() + offSet);
    }

    @Override
    public void update() {

        setCurrentLife(getCurrentLife() - 1);

        if (getCurrentLife() < getMaxLife() / 3) {
            yd++;
        }

        setWorldX(getWorldX() + (xd * getSpeed()));
        setWorldY(getWorldY() + (yd * getSpeed()));

        if (getCurrentLife() == 0) {
            setAlive(false);
        }
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        int screenX = getWorldX() - getGamePanel().getPlayer().getWorldX() + getGamePanel().getPlayer().getScreenX();
        int screenY = getWorldY() - getGamePanel().getPlayer().getWorldY() + getGamePanel().getPlayer().getScreenY();

        graphics2D.setColor(color);
        graphics2D.fillRect(screenX, screenY, size, size);
    }
}
