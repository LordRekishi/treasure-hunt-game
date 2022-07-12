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
        gamePanel.getMonsters()[0].setWorldX(gamePanel.getTileSize() * 21);
        gamePanel.getMonsters()[0].setWorldY(gamePanel.getTileSize() * 38);
        gamePanel.getMonsters()[0].setIndex(0);

        gamePanel.getMonsters()[1] = new MON_GreenSlime(gamePanel);
        gamePanel.getMonsters()[1].setWorldX(gamePanel.getTileSize() * 23);
        gamePanel.getMonsters()[1].setWorldY(gamePanel.getTileSize() * 42);
        gamePanel.getMonsters()[1].setIndex(1);

        gamePanel.getMonsters()[2] = new MON_GreenSlime(gamePanel);
        gamePanel.getMonsters()[2].setWorldX(gamePanel.getTileSize() * 24);
        gamePanel.getMonsters()[2].setWorldY(gamePanel.getTileSize() * 37);
        gamePanel.getMonsters()[2].setIndex(2);

        gamePanel.getMonsters()[3] = new MON_GreenSlime(gamePanel);
        gamePanel.getMonsters()[3].setWorldX(gamePanel.getTileSize() * 34);
        gamePanel.getMonsters()[3].setWorldY(gamePanel.getTileSize() * 42);
        gamePanel.getMonsters()[3].setIndex(3);

        gamePanel.getMonsters()[4] = new MON_GreenSlime(gamePanel);
        gamePanel.getMonsters()[4].setWorldX(gamePanel.getTileSize() * 38);
        gamePanel.getMonsters()[4].setWorldY(gamePanel.getTileSize() * 42);
        gamePanel.getMonsters()[4].setIndex(4);
    }
}
