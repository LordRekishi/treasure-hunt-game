package tech.fallqvist.asset;

import tech.fallqvist.GamePanel;
import tech.fallqvist.asset.entity.NPC_OldMan;
import tech.fallqvist.asset.object.OBJ_Door;

public class AssetManager {

    private final GamePanel gamePanel;

    public AssetManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setObjects() {
        gamePanel.getObjects()[0] = new OBJ_Door(gamePanel);
        gamePanel.getObjects()[0].setWorldX(gamePanel.getTileSize() * 21);
        gamePanel.getObjects()[0].setWorldY(gamePanel.getTileSize() * 22);
        gamePanel.getObjects()[0].setIndex(0);

        gamePanel.getObjects()[1] = new OBJ_Door(gamePanel);
        gamePanel.getObjects()[1].setWorldX(gamePanel.getTileSize() * 23);
        gamePanel.getObjects()[1].setWorldY(gamePanel.getTileSize() * 25);
        gamePanel.getObjects()[1].setIndex(1);


    }

    public void setNPCs() {
        gamePanel.getNpcs()[0] = new NPC_OldMan(gamePanel);
        gamePanel.getNpcs()[0].setWorldX(gamePanel.getTileSize() * 21);
        gamePanel.getNpcs()[0].setWorldY(gamePanel.getTileSize() * 21);
        gamePanel.getNpcs()[0].setIndex(0);

        gamePanel.getNpcs()[1] = new NPC_OldMan(gamePanel);
        gamePanel.getNpcs()[1].setWorldX(gamePanel.getTileSize() * 11);
        gamePanel.getNpcs()[1].setWorldY(gamePanel.getTileSize() * 21);
        gamePanel.getNpcs()[1].setIndex(1);

        gamePanel.getNpcs()[2] = new NPC_OldMan(gamePanel);
        gamePanel.getNpcs()[2].setWorldX(gamePanel.getTileSize() * 11);
        gamePanel.getNpcs()[2].setWorldY(gamePanel.getTileSize() * 31);
        gamePanel.getNpcs()[2].setIndex(2);
    }
}
