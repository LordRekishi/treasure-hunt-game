package tech.fallqvist.event;

import tech.fallqvist.GamePanel;

public class EventHandler {

    private final GamePanel gamePanel;
    private final EventRectangle[][][] eventRect;
    private int previousEventX, previousEventY;
    private boolean canTouchEvent = true;

    public EventHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        this.eventRect = new EventRectangle[gamePanel.getMaxMaps()][gamePanel.getMaxWorldColumns()][gamePanel.getMaxWorldRows()];

        int map = 0;
        int col = 0;
        int row = 0;

        while (map < gamePanel.getMaxMaps() && col < gamePanel.getMaxWorldColumns() && row < gamePanel.getMaxWorldRows()) {
            eventRect[map][col][row] = new EventRectangle();
            eventRect[map][col][row].x = 23;
            eventRect[map][col][row].y = 23;
            eventRect[map][col][row].width = 2;
            eventRect[map][col][row].height = 2;
            eventRect[map][col][row].setEventRectDefaultX(eventRect[map][col][row].x);
            eventRect[map][col][row].setEventRectDefaultY(eventRect[map][col][row].y);

            col++;
            if (col == gamePanel.getMaxWorldColumns()) {
                col = 0;
                row++;

                if (row == gamePanel.getMaxWorldRows()) {
                    row = 0;
                    map++;
                }
            }
        }
    }

    public void checkEvent() {
        int xDistance = Math.abs(gamePanel.getPlayer().getWorldX() - previousEventX);
        int yDistance = Math.abs(gamePanel.getPlayer().getWorldY() - previousEventY);
        int distance = Math.max(xDistance, yDistance);

        if (distance > gamePanel.getTileSize()) {
            canTouchEvent = true;
        }

        if (canTouchEvent) {
            if (hit(0, 27, 16, "right")) {
                damagePit(gamePanel.getDialogueState());
            } else if (hit(0, 23, 19, "any")) {
                damagePit(gamePanel.getDialogueState());
            } else if (hit(0, 23, 12, "up")) {
                healingPool(gamePanel.getDialogueState());
            } else if (hit(0, 10, 39, "any")) {
                teleport(1, 12, 13);
            } else if (hit(1, 12, 13, "any")) {
                teleport(0, 10, 39);
            }
        }
    }

    public boolean hit(int map, int col, int row, String requiredDirection) {
        boolean hit = false;

        if (map == gamePanel.getCurrentMap()) {
            gamePanel.getPlayer().getCollisionArea().x = gamePanel.getPlayer().getWorldX() + gamePanel.getPlayer().getCollisionArea().x;
            gamePanel.getPlayer().getCollisionArea().y = gamePanel.getPlayer().getWorldY() + gamePanel.getPlayer().getCollisionArea().y;

            eventRect[map][col][row].x = col * gamePanel.getTileSize() + eventRect[map][col][row].x;
            eventRect[map][col][row].y = row * gamePanel.getTileSize() + eventRect[map][col][row].y;

            if (gamePanel.getPlayer().getCollisionArea().intersects(eventRect[map][col][row]) && !eventRect[map][col][row].isEventDone()) {
                if (gamePanel.getPlayer().getDirection().contentEquals(requiredDirection) || requiredDirection.contentEquals("any")) {
                    hit = true;

                    previousEventX = gamePanel.getPlayer().getWorldX();
                    previousEventY = gamePanel.getPlayer().getWorldY();
                }
            }

            gamePanel.getPlayer().getCollisionArea().x = gamePanel.getPlayer().getCollisionDefaultX();
            gamePanel.getPlayer().getCollisionArea().y = gamePanel.getPlayer().getCollisionDefaultY();

            eventRect[map][col][row].x = eventRect[map][col][row].getEventRectDefaultX();
            eventRect[map][col][row].y = eventRect[map][col][row].getEventRectDefaultY();
        }

        return hit;
    }

    private void damagePit(int gameState) {
        gamePanel.setGameState(gameState);
        gamePanel.playSoundEffect(6);
        gamePanel.getUi().setCurrentDialogue("You fell into a pit!");
        gamePanel.getPlayer().setCurrentLife(gamePanel.getPlayer().getCurrentLife() - 1);
        canTouchEvent = false;
    }

    private void healingPool(int gameState) {
        if (gamePanel.getKeyHandler().isEnterPressed()) {
            gamePanel.setGameState(gameState);
            gamePanel.playSoundEffect(2);
            gamePanel.getUi().setCurrentDialogue("You drink the water. \nYour life and mana has been restored.");
            gamePanel.getPlayer().setCurrentLife(gamePanel.getPlayer().getMaxLife());
            gamePanel.getPlayer().setCurrentMana(gamePanel.getPlayer().getMaxMana());
            gamePanel.getAssetManager().setMonsters();
        }
    }

    private void teleport(int map, int col, int row) {
        gamePanel.setCurrentMap(map);
        gamePanel.getPlayer().setWorldX(gamePanel.getTileSize() * col);
        gamePanel.getPlayer().setWorldY(gamePanel.getTileSize() * row);
        previousEventX = gamePanel.getPlayer().getWorldX();
        previousEventY = gamePanel.getPlayer().getWorldY();
        canTouchEvent = false;
        gamePanel.playSoundEffect(12);
    }
}
