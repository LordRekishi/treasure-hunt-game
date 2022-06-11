package tech.fallqvist;

import tech.fallqvist.entity.Entity;

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

                tileNum1 = gamePanel.getTileManager().getMapTileNumbers()[entityLeftCol][entityTopRow];
                tileNum2 = gamePanel.getTileManager().getMapTileNumbers()[entityRightCol][entityTopRow];

                if (gamePanel.getTileManager().getTiles()[tileNum1].isCollision() || gamePanel.getTileManager().getTiles()[tileNum2].isCollision()) {
                    entity.setCollisionOn(true);
                }
            }
            case "down" -> {
                entityBottomRow = (entityBottomWorldY + entity.getSpeed()) / gamePanel.getTileSize();

                tileNum1 = gamePanel.getTileManager().getMapTileNumbers()[entityLeftCol][entityBottomRow];
                tileNum2 = gamePanel.getTileManager().getMapTileNumbers()[entityRightCol][entityBottomRow];

                if (gamePanel.getTileManager().getTiles()[tileNum1].isCollision() || gamePanel.getTileManager().getTiles()[tileNum2].isCollision()) {
                    entity.setCollisionOn(true);
                }
            }
            case "left" -> {
                entityLeftCol = (entityLeftWorldX - entity.getSpeed()) / gamePanel.getTileSize();

                tileNum1 = gamePanel.getTileManager().getMapTileNumbers()[entityLeftCol][entityTopRow];
                tileNum2 = gamePanel.getTileManager().getMapTileNumbers()[entityLeftCol][entityBottomRow];

                if (gamePanel.getTileManager().getTiles()[tileNum1].isCollision() || gamePanel.getTileManager().getTiles()[tileNum2].isCollision()) {
                    entity.setCollisionOn(true);
                }
            }
            case "right" -> {
                entityRightCol = (entityRightWorldX + entity.getSpeed()) / gamePanel.getTileSize();

                tileNum1 = gamePanel.getTileManager().getMapTileNumbers()[entityRightCol][entityTopRow];
                tileNum2 = gamePanel.getTileManager().getMapTileNumbers()[entityRightCol][entityBottomRow];

                if (gamePanel.getTileManager().getTiles()[tileNum1].isCollision() || gamePanel.getTileManager().getTiles()[tileNum2].isCollision()) {
                    entity.setCollisionOn(true);
                }
            }
        }
    }
}
