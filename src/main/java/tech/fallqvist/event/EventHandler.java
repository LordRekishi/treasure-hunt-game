package tech.fallqvist.event;

import tech.fallqvist.GamePanel;

public class EventHandler {

    private final GamePanel gamePanel;
    private final EventRectangle[][] eventRect;
    private int previousEventX, previousEventY;
    private boolean canTouchEvent = true;

    public EventHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        this.eventRect = new EventRectangle[gamePanel.getMaxWorldColumns()][gamePanel.getMaxWorldRows()];

        int col = 0;
        int row = 0;

        while (col < gamePanel.getMaxWorldColumns() && row < gamePanel.getMaxWorldRows()) {
            eventRect[col][row] = new EventRectangle();
            eventRect[col][row].x = 23;
            eventRect[col][row].y = 23;
            eventRect[col][row].width = 2;
            eventRect[col][row].height = 2;
            eventRect[col][row].setEventRectDefaultX(eventRect[col][row].x);
            eventRect[col][row].setEventRectDefaultY(eventRect[col][row].y);

            col++;
            if (col == gamePanel.getMaxWorldColumns()) {
                col = 0;
                row++;
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
            if (hit(27, 16, "right")) {
                damagePit(27, 16, gamePanel.getDialogueState());
            }

            if (hit(23, 19, "any")) {
                damagePit(23, 19, gamePanel.getDialogueState());
            }

            if (hit(23, 12, "up")) {
                healingPool(23, 12, gamePanel.getDialogueState());
            }

            if (hit(25, 12, "any")) {
                teleport(25, 12, gamePanel.getDialogueState());
            }
        }
    }

    public boolean hit(int col, int row, String requiredDirection) {
        boolean hit = false;

        gamePanel.getPlayer().getCollisionArea().x = gamePanel.getPlayer().getWorldX() + gamePanel.getPlayer().getCollisionArea().x;
        gamePanel.getPlayer().getCollisionArea().y = gamePanel.getPlayer().getWorldY() + gamePanel.getPlayer().getCollisionArea().y;

        eventRect[col][row].x = col * gamePanel.getTileSize() + eventRect[col][row].x;
        eventRect[col][row].y = row * gamePanel.getTileSize() + eventRect[col][row].y;

        if (gamePanel.getPlayer().getCollisionArea().intersects(eventRect[col][row]) && !eventRect[col][row].isEventDone()) {
            if (gamePanel.getPlayer().getDirection().contentEquals(requiredDirection) || requiredDirection.contentEquals("any")) {
                hit = true;

                previousEventX = gamePanel.getPlayer().getWorldX();
                previousEventY = gamePanel.getPlayer().getWorldY();
            }
        }

        gamePanel.getPlayer().getCollisionArea().x = gamePanel.getPlayer().getCollisionDefaultX();
        gamePanel.getPlayer().getCollisionArea().y = gamePanel.getPlayer().getCollisionDefaultY();

        eventRect[col][row].x = eventRect[col][row].getEventRectDefaultX();
        eventRect[col][row].y = eventRect[col][row].getEventRectDefaultY();

        return hit;
    }

    private void damagePit(int col, int row, int gameState) {
        gamePanel.setGameState(gameState);
        gamePanel.playSoundEffect(6);
        gamePanel.getUi().setCurrentDialogue("You fell into a pit!");
        gamePanel.getPlayer().setCurrentLife(gamePanel.getPlayer().getCurrentLife() - 1);
        canTouchEvent = false;
    }

    private void healingPool(int col, int row, int gameState) {
        if (gamePanel.getKeyHandler().isEnterPressed()) {
            gamePanel.setGameState(gameState);
            gamePanel.playSoundEffect(2);
            gamePanel.getUi().setCurrentDialogue("You drink the water. \nYour life and mana has been restored.");
            gamePanel.getPlayer().setCurrentLife(gamePanel.getPlayer().getMaxLife());
            gamePanel.getPlayer().setCurrentMana(gamePanel.getPlayer().getMaxMana());
            gamePanel.getAssetManager().setMonsters();
        }
    }

    private void teleport(int col, int row, int gameState) {
        gamePanel.setGameState(gameState);
        gamePanel.playSoundEffect(4);
        gamePanel.getUi().setCurrentDialogue("You teleport!");
        gamePanel.getPlayer().setWorldX(gamePanel.getTileSize() * 37);
        gamePanel.getPlayer().setWorldY(gamePanel.getTileSize() * 10);
        eventRect[col][row].setEventDone(true);
    }
}
