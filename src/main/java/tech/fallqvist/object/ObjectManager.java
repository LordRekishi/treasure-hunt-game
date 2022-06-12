package tech.fallqvist.object;

import tech.fallqvist.GamePanel;

public class ObjectManager {

    private final GamePanel gamePanel;

    public ObjectManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setObjects() {

        // KEYS
        gamePanel.getObjects()[0] = new OBJ_Key();
        gamePanel.getObjects()[0].setWorldX(23 * gamePanel.getTileSize());
        gamePanel.getObjects()[0].setWorldY(7 * gamePanel.getTileSize());
        gamePanel.getObjects()[0].setIndex(0);

        gamePanel.getObjects()[1] = new OBJ_Key();
        gamePanel.getObjects()[1].setWorldX(23 * gamePanel.getTileSize());
        gamePanel.getObjects()[1].setWorldY(40 * gamePanel.getTileSize());
        gamePanel.getObjects()[1].setIndex(1);

        gamePanel.getObjects()[2] = new OBJ_Key();
        gamePanel.getObjects()[2].setWorldX(37 * gamePanel.getTileSize());
        gamePanel.getObjects()[2].setWorldY(9 * gamePanel.getTileSize());
        gamePanel.getObjects()[2].setIndex(2);

        // DOORS
        gamePanel.getObjects()[3] = new OBJ_Door();
        gamePanel.getObjects()[3].setWorldX(10 * gamePanel.getTileSize());
        gamePanel.getObjects()[3].setWorldY(11 * gamePanel.getTileSize());
        gamePanel.getObjects()[3].setIndex(3);

        gamePanel.getObjects()[4] = new OBJ_Door();
        gamePanel.getObjects()[4].setWorldX(8 * gamePanel.getTileSize());
        gamePanel.getObjects()[4].setWorldY(28 * gamePanel.getTileSize());
        gamePanel.getObjects()[4].setIndex(4);

        gamePanel.getObjects()[5] = new OBJ_Door();
        gamePanel.getObjects()[5].setWorldX(12 * gamePanel.getTileSize());
        gamePanel.getObjects()[5].setWorldY(22 * gamePanel.getTileSize());
        gamePanel.getObjects()[5].setIndex(5);

        // CHESTS
        gamePanel.getObjects()[6] = new OBJ_Chest();
        gamePanel.getObjects()[6].setWorldX(10 * gamePanel.getTileSize());
        gamePanel.getObjects()[6].setWorldY(7 * gamePanel.getTileSize());
        gamePanel.getObjects()[6].setIndex(6);

        // BOOTS
        gamePanel.getObjects()[7] = new OBJ_Boots();
        gamePanel.getObjects()[7].setWorldX(37 * gamePanel.getTileSize());
        gamePanel.getObjects()[7].setWorldY(42 * gamePanel.getTileSize());
        gamePanel.getObjects()[7].setIndex(7);

    }
}
