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
        setUp1(setup("/images/monster/greenslime_down_1"));
        setUp2(setup("/images/monster/greenslime_down_2"));
        setDown1(setup("/images/monster/greenslime_down_1"));
        setDown2(setup("/images/monster/greenslime_down_2"));
        setLeft1(setup("/images/monster/greenslime_down_1"));
        setLeft2(setup("/images/monster/greenslime_down_2"));
        setRight1(setup("/images/monster/greenslime_down_1"));
        setRight2(setup("/images/monster/greenslime_down_2"));
    }

    @Override
    public void setAction() {
        super.setAction();
    }

    @Override
    public boolean isCollision() {
        return false;
    }
}
