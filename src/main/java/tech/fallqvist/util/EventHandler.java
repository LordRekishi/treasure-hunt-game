package tech.fallqvist.util;

import tech.fallqvist.GamePanel;

import java.awt.*;

public class EventHandler {

    private final GamePanel gamePanel;
    private Rectangle eventRect;
    private int eventRectDefaultX, eventRectDefaultY;

    public EventHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        this.eventRect = new Rectangle(23, 23, 2, 2);
        this.eventRectDefaultX = eventRect.x;
        this.eventRectDefaultY = eventRect.y;
    }

    public void checkEvent() {
        if (hit(27, 16, "right")) {
            damagePit(gamePanel.getDialogueState());
        }

        if (hit(23, 12, "up")) {
            healingPool(gamePanel.getDialogueState());
        }

    }

    public boolean hit(int eventCol, int eventRow, String requiredDirection) {
        boolean hit = false;

        gamePanel.getPlayer().getCollisionArea().x = gamePanel.getPlayer().getWorldX() + gamePanel.getPlayer().getCollisionArea().x;
        gamePanel.getPlayer().getCollisionArea().y = gamePanel.getPlayer().getWorldY() + gamePanel.getPlayer().getCollisionArea().y;

        eventRect.x = eventCol * gamePanel.getTileSize() + eventRect.x;
        eventRect.y = eventRow * gamePanel.getTileSize() + eventRect.y;

        if (gamePanel.getPlayer().getCollisionArea().intersects(eventRect)) {
            if (gamePanel.getPlayer().getDirection().contentEquals(requiredDirection) || requiredDirection.contentEquals("any")) {
                hit = true;
            }
        }

        gamePanel.getPlayer().getCollisionArea().x = gamePanel.getPlayer().getCollisionDefaultX();
        gamePanel.getPlayer().getCollisionArea().y = gamePanel.getPlayer().getCollisionDefaultY();

        eventRect.x = eventRectDefaultX;
        eventRect.y = eventRectDefaultY;

        return hit;
    }

    private void damagePit(int gameState) {
        gamePanel.setGameState(gameState);
        gamePanel.getUi().setCurrentDialogue("You fell into a pit!");
        gamePanel.getPlayer().setCurrentLife(gamePanel.getPlayer().getCurrentLife() - 1);
    }

    private void healingPool(int gameState) {
        if (gamePanel.getKeyHandler().isEnterPressed()) {
            gamePanel.setGameState(gameState);
            gamePanel.getUi().setCurrentDialogue("You drink the water. \nYour life has been restored.");
            gamePanel.getPlayer().setCurrentLife(gamePanel.getPlayer().getMaxLife());
        }
    }
}
