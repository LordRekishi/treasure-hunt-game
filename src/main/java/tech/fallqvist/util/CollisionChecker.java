package tech.fallqvist.util;

import tech.fallqvist.GamePanel;
import tech.fallqvist.entity.Entity;
import tech.fallqvist.object.Object;

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

    public int checkObject(Entity entity, boolean isPlayer) {
        int index = 999;

        for (Object object : gamePanel.getObjects()) {

            if (object != null) {
                entity.getCollisionArea().x = entity.getWorldX() + entity.getCollisionArea().x;
                entity.getCollisionArea().y = entity.getWorldY() + entity.getCollisionArea().y;

                object.getCollisionArea().x = object.getWorldX() + object.getCollisionArea().x;
                object.getCollisionArea().y = object.getWorldY() + object.getCollisionArea().y;

                switch (entity.getDirection()) {
                    case "up" -> {
                        entity.getCollisionArea().y -= entity.getSpeed();

                        if (entity.getCollisionArea().intersects(object.getCollisionArea())) {
                            if (object.isCollision()) {
                                entity.setCollisionOn(true);
                            }

                            if (isPlayer) {
                                index = object.getIndex();
                            }
                        }
                    }
                    case "down" -> {
                        entity.getCollisionArea().y += entity.getSpeed();

                        if (entity.getCollisionArea().intersects(object.getCollisionArea())) {
                            if (object.isCollision()) {
                                entity.setCollisionOn(true);
                            }

                            if (isPlayer) {
                                index = object.getIndex();
                            }
                        }
                    }
                    case "left" -> {
                        entity.getCollisionArea().x -= entity.getSpeed();

                        if (entity.getCollisionArea().intersects(object.getCollisionArea())) {
                            if (object.isCollision()) {
                                entity.setCollisionOn(true);
                            }

                            if (isPlayer) {
                                index = object.getIndex();
                            }
                        }
                    }
                    case "right" -> {
                        entity.getCollisionArea().x += entity.getSpeed();

                        if (entity.getCollisionArea().intersects(object.getCollisionArea())) {
                            if (object.isCollision()) {
                                entity.setCollisionOn(true);
                            }

                            if (isPlayer) {
                                index = object.getIndex();
                            }
                        }
                    }
                }

                entity.getCollisionArea().x = entity.getCollisionDefaultX();
                entity.getCollisionArea().y = entity.getCollisionDefaultY();
                object.getCollisionArea().x = object.getCollisionDefaultX();
                object.getCollisionArea().y = object.getCollisionDefaultY();
            }
        }

        return index;
    }

    public int checkEntity(Entity entity, Entity[] targets) {

        int index = 999;

        for (Entity target : targets) {

            if (target != null) {
                entity.getCollisionArea().x = entity.getWorldX() + entity.getCollisionArea().x;
                entity.getCollisionArea().y = entity.getWorldY() + entity.getCollisionArea().y;

                target.getCollisionArea().x = target.getWorldX() + target.getCollisionArea().x;
                target.getCollisionArea().y = target.getWorldY() + target.getCollisionArea().y;

                switch (entity.getDirection()) {
                    case "up" -> {
                        entity.getCollisionArea().y -= entity.getSpeed();

                        if (entity.getCollisionArea().intersects(target.getCollisionArea())) {
                            entity.setCollisionOn(true);
                            index = target.getIndex();
                        }
                    }
                    case "down" -> {
                        entity.getCollisionArea().y += entity.getSpeed();

                        if (entity.getCollisionArea().intersects(target.getCollisionArea())) {
                            entity.setCollisionOn(true);
                            index = target.getIndex();
                        }
                    }
                    case "left" -> {
                        entity.getCollisionArea().x -= entity.getSpeed();

                        if (entity.getCollisionArea().intersects(target.getCollisionArea())) {
                            entity.setCollisionOn(true);
                            index = target.getIndex();
                        }
                    }
                    case "right" -> {
                        entity.getCollisionArea().x += entity.getSpeed();

                        if (entity.getCollisionArea().intersects(target.getCollisionArea())) {
                            entity.setCollisionOn(true);
                            index = target.getIndex();
                        }
                    }
                }

                entity.getCollisionArea().x = entity.getCollisionDefaultX();
                entity.getCollisionArea().y = entity.getCollisionDefaultY();
                target.getCollisionArea().x = target.getCollisionDefaultX();
                target.getCollisionArea().y = target.getCollisionDefaultY();
            }
        }

        return index;
    }

    public void checkPlayer(Entity entity) {
        entity.getCollisionArea().x = entity.getWorldX() + entity.getCollisionArea().x;
        entity.getCollisionArea().y = entity.getWorldY() + entity.getCollisionArea().y;

        gamePanel.getPlayer().getCollisionArea().x = gamePanel.getPlayer().getWorldX() + gamePanel.getPlayer().getCollisionArea().x;
        gamePanel.getPlayer().getCollisionArea().y = gamePanel.getPlayer().getWorldY() + gamePanel.getPlayer().getCollisionArea().y;

        switch (entity.getDirection()) {
            case "up" -> {
                entity.getCollisionArea().y -= entity.getSpeed();

                if (entity.getCollisionArea().intersects(gamePanel.getPlayer().getCollisionArea())) {
                    entity.setCollisionOn(true);
                }
            }
            case "down" -> {
                entity.getCollisionArea().y += entity.getSpeed();

                if (entity.getCollisionArea().intersects(gamePanel.getPlayer().getCollisionArea())) {
                    entity.setCollisionOn(true);
                }
            }
            case "left" -> {
                entity.getCollisionArea().x -= entity.getSpeed();

                if (entity.getCollisionArea().intersects(gamePanel.getPlayer().getCollisionArea())) {
                    entity.setCollisionOn(true);
                }
            }
            case "right" -> {
                entity.getCollisionArea().x += entity.getSpeed();

                if (entity.getCollisionArea().intersects(gamePanel.getPlayer().getCollisionArea())) {
                    entity.setCollisionOn(true);
                }
            }
        }

        entity.getCollisionArea().x = entity.getCollisionDefaultX();
        entity.getCollisionArea().y = entity.getCollisionDefaultY();
        gamePanel.getPlayer().getCollisionArea().x = gamePanel.getPlayer().getCollisionDefaultX();
        gamePanel.getPlayer().getCollisionArea().y = gamePanel.getPlayer().getCollisionDefaultY();
    }
}
