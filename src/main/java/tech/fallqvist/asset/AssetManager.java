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

    public AssetManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.tileSize = gamePanel.getTileSize();
    }

    public void setObjects() {
        gamePanel.getObjects()[0] = new OBJ_Coin_Bronze(gamePanel);
        gamePanel.getObjects()[0].setWorldX(tileSize * 25);
        gamePanel.getObjects()[0].setWorldY(tileSize * 23);
        gamePanel.getObjects()[0].setIndex(0);

        gamePanel.getObjects()[1] = new OBJ_Coin_Bronze(gamePanel);
        gamePanel.getObjects()[1].setWorldX(tileSize * 21);
        gamePanel.getObjects()[1].setWorldY(tileSize * 19);
        gamePanel.getObjects()[1].setIndex(1);

        gamePanel.getObjects()[2] = new OBJ_Coin_Bronze(gamePanel);
        gamePanel.getObjects()[2].setWorldX(tileSize * 26);
        gamePanel.getObjects()[2].setWorldY(tileSize * 21);
        gamePanel.getObjects()[2].setIndex(2);

        gamePanel.getObjects()[3] = new OBJ_Axe(gamePanel);
        gamePanel.getObjects()[3].setWorldX(tileSize * 33);
        gamePanel.getObjects()[3].setWorldY(tileSize * 21);
        gamePanel.getObjects()[3].setIndex(3);

        gamePanel.getObjects()[4] = new OBJ_Shield_Blue(gamePanel);
        gamePanel.getObjects()[4].setWorldX(tileSize * 35);
        gamePanel.getObjects()[4].setWorldY(tileSize * 21);
        gamePanel.getObjects()[4].setIndex(4);

        gamePanel.getObjects()[5] = new OBJ_Potion_Red(gamePanel);
        gamePanel.getObjects()[5].setWorldX(tileSize * 22);
        gamePanel.getObjects()[5].setWorldY(tileSize * 27);
        gamePanel.getObjects()[5].setIndex(5);

        gamePanel.getObjects()[6] = new OBJ_Heart(gamePanel);
        gamePanel.getObjects()[6].setWorldX(tileSize * 22);
        gamePanel.getObjects()[6].setWorldY(tileSize * 29);
        gamePanel.getObjects()[6].setIndex(6);

        gamePanel.getObjects()[7] = new OBJ_ManaCrystal(gamePanel);
        gamePanel.getObjects()[7].setWorldX(tileSize * 22);
        gamePanel.getObjects()[7].setWorldY(tileSize * 31);
        gamePanel.getObjects()[7].setIndex(7);
    }

    public void setNPCs() {
        gamePanel.getNpcs()[0] = new NPC_OldMan(gamePanel);
        gamePanel.getNpcs()[0].setWorldX(tileSize * 21);
        gamePanel.getNpcs()[0].setWorldY(tileSize * 21);
        gamePanel.getNpcs()[0].setIndex(0);
    }

    public void setMonsters() {
        gamePanel.getMonsters()[0] = new MON_GreenSlime(gamePanel);
        gamePanel.getMonsters()[0].setWorldX(tileSize * 21);
        gamePanel.getMonsters()[0].setWorldY(tileSize * 38);
        gamePanel.getMonsters()[0].setIndex(0);

        gamePanel.getMonsters()[1] = new MON_GreenSlime(gamePanel);
        gamePanel.getMonsters()[1].setWorldX(tileSize * 23);
        gamePanel.getMonsters()[1].setWorldY(tileSize * 42);
        gamePanel.getMonsters()[1].setIndex(1);

        gamePanel.getMonsters()[2] = new MON_GreenSlime(gamePanel);
        gamePanel.getMonsters()[2].setWorldX(tileSize * 24);
        gamePanel.getMonsters()[2].setWorldY(tileSize * 37);
        gamePanel.getMonsters()[2].setIndex(2);

        gamePanel.getMonsters()[3] = new MON_GreenSlime(gamePanel);
        gamePanel.getMonsters()[3].setWorldX(tileSize * 34);
        gamePanel.getMonsters()[3].setWorldY(tileSize * 42);
        gamePanel.getMonsters()[3].setIndex(3);

        gamePanel.getMonsters()[4] = new MON_GreenSlime(gamePanel);
        gamePanel.getMonsters()[4].setWorldX(tileSize * 38);
        gamePanel.getMonsters()[4].setWorldY(tileSize * 42);
        gamePanel.getMonsters()[4].setIndex(4);
    }

    public void setInteractiveTiles() {
        gamePanel.getInteractiveTiles()[0] = new IT_DryTree(gamePanel);
        gamePanel.getInteractiveTiles()[0].setWorldX(tileSize * 27);
        gamePanel.getInteractiveTiles()[0].setWorldY(tileSize * 12);
        gamePanel.getInteractiveTiles()[0].setIndex(0);

        gamePanel.getInteractiveTiles()[1] = new IT_DryTree(gamePanel);
        gamePanel.getInteractiveTiles()[1].setWorldX(tileSize * 28);
        gamePanel.getInteractiveTiles()[1].setWorldY(tileSize * 12);
        gamePanel.getInteractiveTiles()[1].setIndex(1);

        gamePanel.getInteractiveTiles()[2] = new IT_DryTree(gamePanel);
        gamePanel.getInteractiveTiles()[2].setWorldX(tileSize * 29);
        gamePanel.getInteractiveTiles()[2].setWorldY(tileSize * 12);
        gamePanel.getInteractiveTiles()[2].setIndex(2);

        gamePanel.getInteractiveTiles()[3] = new IT_DryTree(gamePanel);
        gamePanel.getInteractiveTiles()[3].setWorldX(tileSize * 30);
        gamePanel.getInteractiveTiles()[3].setWorldY(tileSize * 12);
        gamePanel.getInteractiveTiles()[3].setIndex(3);

        gamePanel.getInteractiveTiles()[4] = new IT_DryTree(gamePanel);
        gamePanel.getInteractiveTiles()[4].setWorldX(tileSize * 31);
        gamePanel.getInteractiveTiles()[4].setWorldY(tileSize * 12);
        gamePanel.getInteractiveTiles()[4].setIndex(4);

        gamePanel.getInteractiveTiles()[5] = new IT_DryTree(gamePanel);
        gamePanel.getInteractiveTiles()[5].setWorldX(tileSize * 32);
        gamePanel.getInteractiveTiles()[5].setWorldY(tileSize * 12);
        gamePanel.getInteractiveTiles()[5].setIndex(5);

        gamePanel.getInteractiveTiles()[6] = new IT_DryTree(gamePanel);
        gamePanel.getInteractiveTiles()[6].setWorldX(tileSize * 33);
        gamePanel.getInteractiveTiles()[6].setWorldY(tileSize * 12);
        gamePanel.getInteractiveTiles()[6].setIndex(6);
    }
}
