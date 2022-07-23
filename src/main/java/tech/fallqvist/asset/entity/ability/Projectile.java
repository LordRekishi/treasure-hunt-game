package tech.fallqvist.asset.entity.ability;

import tech.fallqvist.GamePanel;
import tech.fallqvist.asset.entity.Entity;

public class Projectile extends Entity {

    private Entity user;

    public Projectile(GamePanel gamePanel) {
        super(gamePanel);
    }

    public void set(int worldX, int worldY, String direction, boolean alive, Entity user) {
        this.setWorldX(worldX);
        this.setWorldY(worldY);
        this.setDirection(direction);
        this.setAlive(alive);
        this.user = user;
        this.setCurrentLife(this.getMaxLife());
    }

    @Override
    public void update() {

        if (user == getGamePanel().getPlayer()) {

            int monsterIndex = getGamePanel().getCollisionChecker().checkEntity(this, getGamePanel().getMonsters());
            if (monsterIndex != 999) {
                getGamePanel().getPlayer().damageMonster(monsterIndex, getAttackPower());
                setAlive(false);
            }

        } else {

        }

        switch (getDirection()) {
            case "up" -> setWorldY(getWorldY() - getSpeed());
            case "down" -> setWorldY(getWorldY() + getSpeed());
            case "left" -> setWorldX(getWorldX() - getSpeed());
            case "right" -> setWorldX(getWorldX() + getSpeed());
        }

        setCurrentLife(getCurrentLife() - 1);
        if (getCurrentLife() <= 0) {
            setAlive(false);
        }

        checkAndChangeSpriteAnimationImage();
    }
}
