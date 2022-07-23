package tech.fallqvist.asset.object;

import tech.fallqvist.GamePanel;
import tech.fallqvist.asset.entity.ability.Projectile;

public class OBJ_Rock extends Projectile {

    public OBJ_Rock(GamePanel gamePanel) {
        super(gamePanel);

        setName("Rock Projectile");
        setSpeed(8);
        setMaxLife(80);
        setCurrentLife(getMaxLife());
        setAttackPower(2);
        setUseCost(1);
        setAlive(false);

        getAnimationImages();
    }

    public void getAnimationImages() {
        setUp1(setup("/images/ability/rock_down_1", getGamePanel().getTileSize(), getGamePanel().getTileSize()));
        setUp2(setup("/images/ability/rock_down_1", getGamePanel().getTileSize(), getGamePanel().getTileSize()));
        setDown1(setup("/images/ability/rock_down_1", getGamePanel().getTileSize(), getGamePanel().getTileSize()));
        setDown2(setup("/images/ability/rock_down_1", getGamePanel().getTileSize(), getGamePanel().getTileSize()));
        setLeft1(setup("/images/ability/rock_down_1", getGamePanel().getTileSize(), getGamePanel().getTileSize()));
        setLeft2(setup("/images/ability/rock_down_1", getGamePanel().getTileSize(), getGamePanel().getTileSize()));
        setRight1(setup("/images/ability/rock_down_1", getGamePanel().getTileSize(), getGamePanel().getTileSize()));
        setRight2(setup("/images/ability/rock_down_1", getGamePanel().getTileSize(), getGamePanel().getTileSize()));
    }
}
