package tech.fallqvist.asset.object;

import tech.fallqvist.GamePanel;
import tech.fallqvist.asset.entity.ability.Projectile;

public class OBJ_Fireball extends Projectile {

    public OBJ_Fireball(GamePanel gamePanel) {
        super(gamePanel);

        setName("Fireball");
        setSpeed(6);
        setMaxLife(80);
        setCurrentLife(getMaxLife());
        setAttackPower(2);
        setUseCost(1);
        setAlive(false);

        getAnimationImages();
    }

    public void getAnimationImages() {
        setUp1(setup("/images/ability/fireball_up_1", getGamePanel().getTileSize(), getGamePanel().getTileSize()));
        setUp2(setup("/images/ability/fireball_up_2", getGamePanel().getTileSize(), getGamePanel().getTileSize()));
        setDown1(setup("/images/ability/fireball_down_1", getGamePanel().getTileSize(), getGamePanel().getTileSize()));
        setDown2(setup("/images/ability/fireball_down_2", getGamePanel().getTileSize(), getGamePanel().getTileSize()));
        setLeft1(setup("/images/ability/fireball_left_1", getGamePanel().getTileSize(), getGamePanel().getTileSize()));
        setLeft2(setup("/images/ability/fireball_left_2", getGamePanel().getTileSize(), getGamePanel().getTileSize()));
        setRight1(setup("/images/ability/fireball_right_1", getGamePanel().getTileSize(), getGamePanel().getTileSize()));
        setRight2(setup("/images/ability/fireball_right_2", getGamePanel().getTileSize(), getGamePanel().getTileSize()));
    }
}
