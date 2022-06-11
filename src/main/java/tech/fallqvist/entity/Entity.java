package tech.fallqvist.entity;

import java.awt.*;

public abstract class Entity {

    private int x, y;
    private int speed;

    public int getX() {
        return x;
    }

    public Entity setX(int x) {
        this.x = x;
        return this;
    }

    public int getY() {
        return y;
    }

    public Entity setY(int y) {
        this.y = y;
        return this;
    }

    public int getSpeed() {
        return speed;
    }

    public Entity setSpeed(int speed) {
        this.speed = speed;
        return this;
    }

    public abstract void update();
    public abstract void draw(Graphics2D graphics2D);
}
