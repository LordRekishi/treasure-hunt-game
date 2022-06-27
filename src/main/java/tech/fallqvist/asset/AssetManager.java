package tech.fallqvist.asset;

import tech.fallqvist.GamePanel;
import tech.fallqvist.asset.entity.monster.MON_GreenSlime;
import tech.fallqvist.asset.entity.npc.NPC_OldMan;

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

    public void setMonsters() {
        gamePanel.getMonsters()[0] = new MON_GreenSlime(gamePanel);
        gamePanel.getMonsters()[0].setWorldX(gamePanel.getTileSize() * 23);
        gamePanel.getMonsters()[0].setWorldY(gamePanel.getTileSize() * 36);
        gamePanel.getMonsters()[0].setIndex(0);

        gamePanel.getMonsters()[1] = new MON_GreenSlime(gamePanel);
        gamePanel.getMonsters()[1].setWorldX(gamePanel.getTileSize() * 23);
        gamePanel.getMonsters()[1].setWorldY(gamePanel.getTileSize() * 37);
        gamePanel.getMonsters()[1].setIndex(1);
    }
}
