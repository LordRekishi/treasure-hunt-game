package tech.fallqvist.asset.entity;

import tech.fallqvist.GamePanel;
import tech.fallqvist.util.KeyHandler;

import java.awt.*;

public class Player extends Entity {

    private final KeyHandler keyHandler;
    private final int screenX;
    private final int screenY;
    private int resetTimer;

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        super(gamePanel);
        this.keyHandler = keyHandler;

        this.screenX = gamePanel.getScreenWidth() / 2 - (gamePanel.getTileSize() / 2);
        this.screenY = gamePanel.getScreenHeight() / 2 - (gamePanel.getTileSize() / 2);

        setCollision();
        setDefaultValues();
        getAnimationImages();
    }

    private void setCollision() {
        setCollisionDefaultX(getCollisionArea().x);
        setCollisionDefaultY(getCollisionArea().y);
    }

    public void setDefaultValues() {
        setWorldX(getGamePanel().getTileSize() * 23);
        setWorldY(getGamePanel().getTileSize() * 21);
        setSpeed(4);
        setDirection("down");
        setMaxLife(6);
        setCurrentLife(getMaxLife());
    }

    public void getAnimationImages() {
            setUp1(setup("/images/player/boy_up_1"));
            setUp2(setup("/images/player/boy_up_2"));
            setDown1(setup("/images/player/boy_down_1"));
            setDown2(setup("/images/player/boy_down_2"));
            setLeft1(setup("/images/player/boy_left_1"));
            setLeft2(setup("/images/player/boy_left_2"));
            setRight1(setup("/images/player/boy_right_1"));
            setRight2(setup("/images/player/boy_right_2"));
    }

    @Override
    public void update() {

        if (keyHandler.isUpPressed() || keyHandler.isDownPressed()
                || keyHandler.isLeftPressed() || keyHandler.isRightPressed()) {

            if (keyHandler.isUpPressed()) {
                setDirection("up");
            } else if (keyHandler.isDownPressed()) {
                setDirection("down");
            } else if (keyHandler.isLeftPressed()) {
                setDirection("left");
            } else if (keyHandler.isRightPressed()) {
                setDirection("right");
            }

            checkCollision();
            checkEvent();
            resetEnterPressedValue();
            moveIfCollisionNotDetected();
            checkAndChangeSpriteAnimationImage();
        } else {
            resetSpriteToDefault();
        }
    }

    private void checkCollision() {
        setCollisionOn(false);

        checkTileCollision();
        checkObjectCollision();
        checkNPCCollision();
    }

    private void checkTileCollision() {
        getGamePanel().getCollisionChecker().checkTile(this);
    }

    private void checkObjectCollision() {
        int objectIndex = getGamePanel().getCollisionChecker().checkObject(this, true);
        pickUpObject(objectIndex);
    }

    private void pickUpObject(int index) {
        if (index != 999) {

        }
    }

    private void checkNPCCollision() {
        int npcIndex = getGamePanel().getCollisionChecker().checkEntity(this, getGamePanel().getNpcs());
        interactWithNPC(npcIndex);
    }

    private void interactWithNPC(int index) {
        if (index != 999) {
            if (getGamePanel().getKeyHandler().isEnterPressed()) {
                getGamePanel().setGameState(getGamePanel().getDialogueState());
                getGamePanel().getNpcs()[index].speak();
            }
        }
    }

    private void checkEvent() {
        getGamePanel().getEventHandler().checkEvent();
    }

    private void resetEnterPressedValue() {
        getGamePanel().getKeyHandler().setEnterPressed(false);
    }

    private void resetSpriteToDefault() {
        resetTimer++;
        if (resetTimer == 20) {
            setSpriteNumber(1);
            resetTimer = 0;
        }
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        int rightOffset = getGamePanel().getScreenWidth() - screenX;
        int x = checkIfAtEdgeOfXAxis(rightOffset);

        int bottomOffset = getGamePanel().getScreenHeight() - screenY;
        int y = checkIfAtEdgeOfYAxis(bottomOffset);

        graphics2D.drawImage(getDirectionalAnimationImage(), x, y, null);

    }

    @Override
    public boolean isCollision() {
        return false;
    }

    private int checkIfAtEdgeOfXAxis(int rightOffset) {
        if (screenX > getWorldX()) {
            return getWorldX();
        }

        if (rightOffset > getGamePanel().getWorldWidth() - getWorldX()) {
            return getGamePanel().getScreenWidth() - (getGamePanel().getWorldWidth() - getWorldX());
        }

        return screenX;
    }

    private int checkIfAtEdgeOfYAxis(int bottomOffset) {
        if (screenY > getWorldY()) {
            return getWorldY();
        }

        if (bottomOffset > getGamePanel().getWorldHeight() - getWorldY()) {
            return getGamePanel().getScreenHeight() - (getGamePanel().getWorldHeight() - getWorldY());
        }

        return screenY;
    }

    public int getScreenX() {
        return screenX;
    }

    public int getScreenY() {
        return screenY;
    }
}
