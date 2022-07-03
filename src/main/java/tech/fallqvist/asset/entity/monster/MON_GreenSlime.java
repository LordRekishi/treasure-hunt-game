package tech.fallqvist.asset.entity.monster;

import tech.fallqvist.GamePanel;
import tech.fallqvist.asset.entity.Entity;

import java.awt.*;

public class MON_GreenSlime extends Entity {

    public MON_GreenSlime(GamePanel gamePanel) {
        super(gamePanel);

        setName("Green Slime");
        setDirection("down");
        setSpeed(1);
        setMaxLife(4);
        setCurrentLife(getMaxLife());

        setCollisionArea(new Rectangle(3, 18, 42, 30));
        setCollisionDefaultX(getCollisionArea().x);
        setCollisionDefaultY(getCollisionArea().y);

        getAnimationImages();
    }

    @Override
    public void getAnimationImages() {
        setUp1(setup("/images/monster/greenslime_down_1", getGamePanel().getTileSize(), getGamePanel().getTileSize()));
        setUp2(setup("/images/monster/greenslime_down_2", getGamePanel().getTileSize(), getGamePanel().getTileSize()));
        setDown1(setup("/images/monster/greenslime_down_1", getGamePanel().getTileSize(), getGamePanel().getTileSize()));
        setDown2(setup("/images/monster/greenslime_down_2", getGamePanel().getTileSize(), getGamePanel().getTileSize()));
        setLeft1(setup("/images/monster/greenslime_down_1", getGamePanel().getTileSize(), getGamePanel().getTileSize()));
        setLeft2(setup("/images/monster/greenslime_down_2", getGamePanel().getTileSize(), getGamePanel().getTileSize()));
        setRight1(setup("/images/monster/greenslime_down_1", getGamePanel().getTileSize(), getGamePanel().getTileSize()));
        setRight2(setup("/images/monster/greenslime_down_2", getGamePanel().getTileSize(), getGamePanel().getTileSize()));
    }

    @Override
    public void getAttackImages() {
        // Not used
    }

    @Override
    public void damageReaction() {
        setActionLockCounter(0);
        setDirection(getGamePanel().getPlayer().getDirection());
    }

    @Override
    public void setupAI() {
        super.setupAI();
    }

    @Override
    public boolean isCollision() {
        return false;
    }
}
