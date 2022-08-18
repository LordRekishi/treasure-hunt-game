package tech.fallqvist.asset;

import tech.fallqvist.GamePanel;
import tech.fallqvist.asset.entity.monster.MON_GreenSlime;
import tech.fallqvist.asset.entity.npc.NPC_OldMan;
import tech.fallqvist.asset.object.equipment.OBJ_Axe;
import tech.fallqvist.asset.object.equipment.OBJ_Shield_Blue;
import tech.fallqvist.asset.object.usable.inventory.OBJ_Potion_Red;
import tech.fallqvist.asset.object.usable.pickuponly.OBJ_Coin_Bronze;
import tech.fallqvist.asset.object.usable.pickuponly.OBJ_Heart;
import tech.fallqvist.asset.object.usable.pickuponly.OBJ_ManaCrystal;
import tech.fallqvist.asset.tile.interactive.IT_DryTree;

public class AssetManager {

    private final GamePanel gamePanel;
    private final int tileSize;
    private int map = 0;

    public AssetManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.tileSize = gamePanel.getTileSize();
    }

    public void setObjects() {
        gamePanel.getObjects()[map][0] = new OBJ_Coin_Bronze(gamePanel);
        gamePanel.getObjects()[map][0].setWorldX(tileSize * 25);
        gamePanel.getObjects()[map][0].setWorldY(tileSize * 23);
        gamePanel.getObjects()[map][0].setIndex(0);

        gamePanel.getObjects()[map][1] = new OBJ_Coin_Bronze(gamePanel);
        gamePanel.getObjects()[map][1].setWorldX(tileSize * 21);
        gamePanel.getObjects()[map][1].setWorldY(tileSize * 19);
        gamePanel.getObjects()[map][1].setIndex(1);

        gamePanel.getObjects()[map][2] = new OBJ_Coin_Bronze(gamePanel);
        gamePanel.getObjects()[map][2].setWorldX(tileSize * 26);
        gamePanel.getObjects()[map][2].setWorldY(tileSize * 21);
        gamePanel.getObjects()[map][2].setIndex(2);

        gamePanel.getObjects()[map][3] = new OBJ_Axe(gamePanel);
        gamePanel.getObjects()[map][3].setWorldX(tileSize * 33);
        gamePanel.getObjects()[map][3].setWorldY(tileSize * 21);
        gamePanel.getObjects()[map][3].setIndex(3);

        gamePanel.getObjects()[map][4] = new OBJ_Shield_Blue(gamePanel);
        gamePanel.getObjects()[map][4].setWorldX(tileSize * 35);
        gamePanel.getObjects()[map][4].setWorldY(tileSize * 21);
        gamePanel.getObjects()[map][4].setIndex(4);

        gamePanel.getObjects()[map][5] = new OBJ_Potion_Red(gamePanel);
        gamePanel.getObjects()[map][5].setWorldX(tileSize * 22);
        gamePanel.getObjects()[map][5].setWorldY(tileSize * 27);
        gamePanel.getObjects()[map][5].setIndex(5);

        gamePanel.getObjects()[map][6] = new OBJ_Heart(gamePanel);
        gamePanel.getObjects()[map][6].setWorldX(tileSize * 22);
        gamePanel.getObjects()[map][6].setWorldY(tileSize * 29);
        gamePanel.getObjects()[map][6].setIndex(6);

        gamePanel.getObjects()[map][7] = new OBJ_ManaCrystal(gamePanel);
        gamePanel.getObjects()[map][7].setWorldX(tileSize * 22);
        gamePanel.getObjects()[map][7].setWorldY(tileSize * 31);
        gamePanel.getObjects()[map][7].setIndex(7);
    }

    public void setNPCs() {
        gamePanel.getNpcs()[map][0] = new NPC_OldMan(gamePanel);
        gamePanel.getNpcs()[map][0].setWorldX(tileSize * 21);
        gamePanel.getNpcs()[map][0].setWorldY(tileSize * 21);
        gamePanel.getNpcs()[map][0].setIndex(0);
    }

    public void setMonsters() {
        gamePanel.getMonsters()[map][0] = new MON_GreenSlime(gamePanel);
        gamePanel.getMonsters()[map][0].setWorldX(tileSize * 21);
        gamePanel.getMonsters()[map][0].setWorldY(tileSize * 38);
        gamePanel.getMonsters()[map][0].setIndex(0);

        gamePanel.getMonsters()[map][1] = new MON_GreenSlime(gamePanel);
        gamePanel.getMonsters()[map][1].setWorldX(tileSize * 23);
        gamePanel.getMonsters()[map][1].setWorldY(tileSize * 42);
        gamePanel.getMonsters()[map][1].setIndex(1);

        gamePanel.getMonsters()[map][2] = new MON_GreenSlime(gamePanel);
        gamePanel.getMonsters()[map][2].setWorldX(tileSize * 24);
        gamePanel.getMonsters()[map][2].setWorldY(tileSize * 37);
        gamePanel.getMonsters()[map][2].setIndex(2);

        gamePanel.getMonsters()[map][3] = new MON_GreenSlime(gamePanel);
        gamePanel.getMonsters()[map][3].setWorldX(tileSize * 34);
        gamePanel.getMonsters()[map][3].setWorldY(tileSize * 42);
        gamePanel.getMonsters()[map][3].setIndex(3);

        gamePanel.getMonsters()[map][4] = new MON_GreenSlime(gamePanel);
        gamePanel.getMonsters()[map][4].setWorldX(tileSize * 38);
        gamePanel.getMonsters()[map][4].setWorldY(tileSize * 42);
        gamePanel.getMonsters()[map][4].setIndex(4);
    }

    public void setInteractiveTiles() {
        gamePanel.getInteractiveTiles()[map][0] = new IT_DryTree(gamePanel);
        gamePanel.getInteractiveTiles()[map][0].setWorldX(tileSize * 27);
        gamePanel.getInteractiveTiles()[map][0].setWorldY(tileSize * 12);
        gamePanel.getInteractiveTiles()[map][0].setIndex(0);

        gamePanel.getInteractiveTiles()[map][1] = new IT_DryTree(gamePanel);
        gamePanel.getInteractiveTiles()[map][1].setWorldX(tileSize * 28);
        gamePanel.getInteractiveTiles()[map][1].setWorldY(tileSize * 12);
        gamePanel.getInteractiveTiles()[map][1].setIndex(1);

        gamePanel.getInteractiveTiles()[map][2] = new IT_DryTree(gamePanel);
        gamePanel.getInteractiveTiles()[map][2].setWorldX(tileSize * 29);
        gamePanel.getInteractiveTiles()[map][2].setWorldY(tileSize * 12);
        gamePanel.getInteractiveTiles()[map][2].setIndex(2);

        gamePanel.getInteractiveTiles()[map][3] = new IT_DryTree(gamePanel);
        gamePanel.getInteractiveTiles()[map][3].setWorldX(tileSize * 30);
        gamePanel.getInteractiveTiles()[map][3].setWorldY(tileSize * 12);
        gamePanel.getInteractiveTiles()[map][3].setIndex(3);

        gamePanel.getInteractiveTiles()[map][4] = new IT_DryTree(gamePanel);
        gamePanel.getInteractiveTiles()[map][4].setWorldX(tileSize * 31);
        gamePanel.getInteractiveTiles()[map][4].setWorldY(tileSize * 12);
        gamePanel.getInteractiveTiles()[map][4].setIndex(4);

        gamePanel.getInteractiveTiles()[map][5] = new IT_DryTree(gamePanel);
        gamePanel.getInteractiveTiles()[map][5].setWorldX(tileSize * 32);
        gamePanel.getInteractiveTiles()[map][5].setWorldY(tileSize * 12);
        gamePanel.getInteractiveTiles()[map][5].setIndex(5);

        gamePanel.getInteractiveTiles()[map][6] = new IT_DryTree(gamePanel);
        gamePanel.getInteractiveTiles()[map][6].setWorldX(tileSize * 33);
        gamePanel.getInteractiveTiles()[map][6].setWorldY(tileSize * 12);
        gamePanel.getInteractiveTiles()[map][6].setIndex(6);
    }
}
