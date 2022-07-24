package tech.fallqvist.asset.object.equipment;

import tech.fallqvist.GamePanel;
import tech.fallqvist.asset.object.Object;

public class Shield extends Object {
    private int defenseValue;

    public Shield(GamePanel gamePanel) {
        super(gamePanel);
    }

    public int getDefenseValue() {
        return defenseValue;
    }

    public void setDefenseValue(int defenseValue) {
        this.defenseValue = defenseValue;
    }
}
