package tech.fallqvist.util;

import tech.fallqvist.GamePanel;
import tech.fallqvist.asset.Asset;
import tech.fallqvist.asset.entity.Entity;

public class CollisionChecker {

    private final GamePanel gamePanel;

    public CollisionChecker(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.getWorldX() + entity.getCollisionArea().x;
        int entityRightWorldX = entity.getWorldX() + entity.getCollisionArea().x + entity.getCollisionArea().width;
        int entityTopWorldY = entity.getWorldY() + entity.getCollisionArea().y;
        int entityBottomWorldY = entity.getWorldY() + entity.getCollisionArea().y + entity.getCollisionArea().height;

        int entityLeftCol = entityLeftWorldX / gamePanel.getTileSize();
        int entityRightCol = entityRightWorldX / gamePanel.getTileSize();
        int entityTopRow = entityTopWorldY / gamePanel.getTileSize();
        int entityBottomRow = entityBottomWorldY / gamePanel.getTileSize();

        int tileNum1, tileNum2;

        switch (entity.getDirection()) {
            case "up" -> {
                entityTopRow = (entityTopWorldY - entity.getSpeed()) / gamePanel.getTileSize();

                tileNum1 = gamePanel.getTileManager().getMapTileNumbers()[gamePanel.getCurrentMap()][entityLeftCol][entityTopRow];
                tileNum2 = gamePanel.getTileManager().getMapTileNumbers()[gamePanel.getCurrentMap()][entityRightCol][entityTopRow];

                if (gamePanel.getTileManager().getTiles()[tileNum1].isCollision() || gamePanel.getTileManager().getTiles()[tileNum2].isCollision()) {
                    entity.setCollisionOn(true);
                }
            }
            case "down" -> {
                entityBottomRow = (entityBottomWorldY + entity.getSpeed()) / gamePanel.getTileSize();

                tileNum1 = gamePanel.getTileManager().getMapTileNumbers()[gamePanel.getCurrentMap()][entityLeftCol][entityBottomRow];
                tileNum2 = gamePanel.getTileManager().getMapTileNumbers()[gamePanel.getCurrentMap()][entityRightCol][entityBottomRow];

                if (gamePanel.getTileManager().getTiles()[tileNum1].isCollision() || gamePanel.getTileManager().getTiles()[tileNum2].isCollision()) {
                    entity.setCollisionOn(true);
                }
            }
            case "left" -> {
                entityLeftCol = (entityLeftWorldX - entity.getSpeed()) / gamePanel.getTileSize();

                tileNum1 = gamePanel.getTileManager().getMapTileNumbers()[gamePanel.getCurrentMap()][entityLeftCol][entityTopRow];
                tileNum2 = gamePanel.getTileManager().getMapTileNumbers()[gamePanel.getCurrentMap()][entityLeftCol][entityBottomRow];

                if (gamePanel.getTileManager().getTiles()[tileNum1].isCollision() || gamePanel.getTileManager().getTiles()[tileNum2].isCollision()) {
                    entity.setCollisionOn(true);
                }
            }
            case "right" -> {
                entityRightCol = (entityRightWorldX + entity.getSpeed()) / gamePanel.getTileSize();

                tileNum1 = gamePanel.getTileManager().getMapTileNumbers()[gamePanel.getCurrentMap()][entityRightCol][entityTopRow];
                tileNum2 = gamePanel.getTileManager().getMapTileNumbers()[gamePanel.getCurrentMap()][entityRightCol][entityBottomRow];

                if (gamePanel.getTileManager().getTiles()[tileNum1].isCollision() || gamePanel.getTileManager().getTiles()[tileNum2].isCollision()) {
                    entity.setCollisionOn(true);
                }
            }
        }
    }

    public int checkObject(Entity entity, boolean isPlayer) {
        int index = 999;

        Asset[][] objects = gamePanel.getObjects();
        for (int i = 0; i < objects[1].length; i++) {

            if (objects[gamePanel.getCurrentMap()][i] != null) {
                entity.getCollisionArea().x = entity.getWorldX() + entity.getCollisionArea().x;
                entity.getCollisionArea().y = entity.getWorldY() + entity.getCollisionArea().y;

                objects[gamePanel.getCurrentMap()][i].getCollisionArea().x = objects[gamePanel.getCurrentMap()][i].getWorldX() + objects[gamePanel.getCurrentMap()][i].getCollisionArea().x;
                objects[gamePanel.getCurrentMap()][i].getCollisionArea().y = objects[gamePanel.getCurrentMap()][i].getWorldY() + objects[gamePanel.getCurrentMap()][i].getCollisionArea().y;

                checkFutureMovement(entity);

                if (entity.getCollisionArea().intersects(objects[gamePanel.getCurrentMap()][i].getCollisionArea())) {
                    if (objects[gamePanel.getCurrentMap()][i].isCollision()) {
                        entity.setCollisionOn(true);
                    }

                    if (isPlayer) {
                        index = objects[gamePanel.getCurrentMap()][i].getIndex();
                    }
                }

                entity.getCollisionArea().x = entity.getCollisionDefaultX();
                entity.getCollisionArea().y = entity.getCollisionDefaultY();
                objects[gamePanel.getCurrentMap()][i].getCollisionArea().x = objects[gamePanel.getCurrentMap()][i].getCollisionDefaultX();
                objects[gamePanel.getCurrentMap()][i].getCollisionArea().y = objects[gamePanel.getCurrentMap()][i].getCollisionDefaultY();
            }
        }

        return index;
    }

    public int checkEntity(Entity entity, Asset[][] targets) {

        int index = 999;

        for (int i = 0; i < targets[1].length; i++) {

            if (targets[gamePanel.getCurrentMap()][i] != null) {
                entity.getCollisionArea().x = entity.getWorldX() + entity.getCollisionArea().x;
                entity.getCollisionArea().y = entity.getWorldY() + entity.getCollisionArea().y;

                targets[gamePanel.getCurrentMap()][i].getCollisionArea().x = targets[gamePanel.getCurrentMap()][i].getWorldX() + targets[gamePanel.getCurrentMap()][i].getCollisionArea().x;
                targets[gamePanel.getCurrentMap()][i].getCollisionArea().y = targets[gamePanel.getCurrentMap()][i].getWorldY() + targets[gamePanel.getCurrentMap()][i].getCollisionArea().y;

                checkFutureMovement(entity);

                if (entity.getCollisionArea().intersects(targets[gamePanel.getCurrentMap()][i].getCollisionArea())) {
                    if (targets[gamePanel.getCurrentMap()][i] != entity) {
                        entity.setCollisionOn(true);
                        index = targets[gamePanel.getCurrentMap()][i].getIndex();
                    }
                }

                entity.getCollisionArea().x = entity.getCollisionDefaultX();
                entity.getCollisionArea().y = entity.getCollisionDefaultY();
                targets[gamePanel.getCurrentMap()][i].getCollisionArea().x = targets[gamePanel.getCurrentMap()][i].getCollisionDefaultX();
                targets[gamePanel.getCurrentMap()][i].getCollisionArea().y = targets[gamePanel.getCurrentMap()][i].getCollisionDefaultY();
            }
        }

        return index;
    }

    public boolean checkPlayer(Entity entity) {
        boolean contactPlayer = false;

        entity.getCollisionArea().x = entity.getWorldX() + entity.getCollisionArea().x;
        entity.getCollisionArea().y = entity.getWorldY() + entity.getCollisionArea().y;

        gamePanel.getPlayer().getCollisionArea().x = gamePanel.getPlayer().getWorldX() + gamePanel.getPlayer().getCollisionArea().x;
        gamePanel.getPlayer().getCollisionArea().y = gamePanel.getPlayer().getWorldY() + gamePanel.getPlayer().getCollisionArea().y;

        checkFutureMovement(entity);

        if (entity.getCollisionArea().intersects(gamePanel.getPlayer().getCollisionArea())) {
            entity.setCollisionOn(true);
            contactPlayer = true;
        }

        entity.getCollisionArea().x = entity.getCollisionDefaultX();
        entity.getCollisionArea().y = entity.getCollisionDefaultY();
        gamePanel.getPlayer().getCollisionArea().x = gamePanel.getPlayer().getCollisionDefaultX();
        gamePanel.getPlayer().getCollisionArea().y = gamePanel.getPlayer().getCollisionDefaultY();

        return contactPlayer;
    }

    private void checkFutureMovement(Entity entity) {
        switch (entity.getDirection()) {
            case "up" -> entity.getCollisionArea().y -= entity.getSpeed();
            case "down" -> entity.getCollisionArea().y += entity.getSpeed();
            case "left" -> entity.getCollisionArea().x -= entity.getSpeed();
            case "right" -> entity.getCollisionArea().x += entity.getSpeed();
        }
    }
}
