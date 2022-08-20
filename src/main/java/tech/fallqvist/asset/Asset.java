package tech.fallqvist.asset;

import tech.fallqvist.asset.object.Object;

import java.awt.*;
import java.awt.image.BufferedImage;

public interface Asset {
    void update();

    void draw(Graphics2D graphics2D);

    String getName();

    void speak();

    void damageReaction();

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

    boolean isInvincible();

    void setInvincible(boolean invincible);

    int getCurrentLife();

    void setCurrentLife(int currentLife);

    boolean isAlive();

    void setAlive(boolean alive);

    boolean isDying();

    void setDying(boolean dying);

    int getLevel();

    int getStrength();

    int getDexterity();

    int getAttackPower();

    int getDefensePower();

    int getExp();

    int getNextLevelExp();

    int getCoins();

    Object getCurrentWeapon();

    Object getCurrentShield();

    BufferedImage getImage1();

    String getDescription();

    Rectangle getAttackArea();

    void setAttackArea(Rectangle attackArea);

    void use();

    int getMaxLife();

    void checkDrop();

    void dropObject(Asset droppedObject);

    Color getParticleColor();

    int getParticleSize();

    int getParticleSpeed();

    int getParticleMaxLife();

    int getPrice();
}
