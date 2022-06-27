package tech.fallqvist.asset;

import java.awt.*;

public interface Asset {
    void update();
    void draw(Graphics2D graphics2D);
    void speak();
    void setIndex(int i);
    int getIndex();
    Rectangle getCollisionArea();
    int getCollisionDefaultX();
    int getCollisionDefaultY();
    boolean isCollision();
    int getWorldX();
    int getWorldY();
    void setWorldX(int i);
    void setWorldY(int i);
}
