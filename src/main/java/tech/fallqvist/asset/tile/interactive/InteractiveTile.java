package tech.fallqvist.asset.tile.interactive;

import tech.fallqvist.GamePanel;
import tech.fallqvist.asset.entity.Entity;
import tech.fallqvist.asset.object.equipment.Weapon;

public class InteractiveTile extends Entity {

    private boolean destructible;

    public InteractiveTile(GamePanel gamePanel) {
        super(gamePanel);
    }

    @Override
    public void update() {
        checkIfInvincible();
    }

    public void playSoundEffect() {

    }

    public InteractiveTile getDestroyedForm() {
        return null;
    }

    public boolean isDestructible() {
        return destructible;
    }

    public InteractiveTile setDestructible(boolean destructible) {
        this.destructible = destructible;
        return this;
    }

    public boolean isCorrectWeapon(Weapon weapon) {
        return false;
    }
}
