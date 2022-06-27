package tech.fallqvist.asset;

import java.awt.*;

public interface Asset {
    void update();
    void draw(Graphics2D graphics2D);
    Rectangle getCollisionArea();
    int getWorldX();
    int getWorldY();
    boolean isCollision();
    int getIndex();
    int getCollisionDefaultX();
    int getCollisionDefaultY();
    void speak();

    void setWorldX(int i);

    void setWorldY(int i);

    void setIndex(int i);
}
