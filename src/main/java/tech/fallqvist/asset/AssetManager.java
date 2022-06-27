package tech.fallqvist.asset;

import tech.fallqvist.GamePanel;
import tech.fallqvist.asset.entity.NPC_OldMan;

public class AssetManager {

    private final GamePanel gamePanel;

    public AssetManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setObjects() {
        // Add objects
    }

    public void setNPCs() {
        gamePanel.getNpcs()[0] = new NPC_OldMan(gamePanel);
        gamePanel.getNpcs()[0].setWorldX(gamePanel.getTileSize() * 21);
        gamePanel.getNpcs()[0].setWorldY(gamePanel.getTileSize() * 21);
        gamePanel.getNpcs()[0].setIndex(0);
    }
}
