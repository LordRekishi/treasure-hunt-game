package tech.fallqvist.asset.entity.player;

import tech.fallqvist.GamePanel;
import tech.fallqvist.asset.entity.Entity;
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
        setCollisionArea(new Rectangle(8, 16, 32, 32));
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

    @Override
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

        if (keyHandler.isUpPressed()
                || keyHandler.isDownPressed()
                || keyHandler.isLeftPressed()
                || keyHandler.isRightPressed()
                || keyHandler.isEnterPressed()) {

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
            moveIfCollisionNotDetected();
            resetEnterPressedValue();
            checkAndChangeSpriteAnimationImage();
        } else {
            resetSpriteToDefault();
        }

        checkIfInvincible();
    }

    private void checkCollision() {
        setCollisionOn(false);

        checkTileCollision();
        checkObjectCollision();
        checkNPCCollision();
        checkMonsterCollision();
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

    private void checkMonsterCollision() {
        int monsterIndex = getGamePanel().getCollisionChecker().checkEntity(this, getGamePanel().getMonsters());
        interactWithMonster(monsterIndex);
    }

    private void interactWithMonster(int index) {
        if (index != 999) {
            if (!isInvincible()) {
                setCurrentLife(getCurrentLife() - 1);
                setInvincible(true);
            }
        }
    }

    private void checkEvent() {
        getGamePanel().getEventHandler().checkEvent();
    }

    private void resetEnterPressedValue() {
        keyHandler.setEnterPressed(false);
    }

    private void resetSpriteToDefault() {
        resetTimer++;
        if (resetTimer == 20) {
            setSpriteNumber(1);
            resetTimer = 0;
        }
    }

    private void checkIfInvincible() {
        if (isInvincible()) {
            setInvincibleCounter(getInvincibleCounter() + 1);

            if (getInvincibleCounter() > 60) {
                setInvincible(false);
                setInvincibleCounter(0);
            }
        }
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        int rightOffset = getGamePanel().getScreenWidth() - screenX;
        int x = checkIfAtEdgeOfXAxis(rightOffset);

        int bottomOffset = getGamePanel().getScreenHeight() - screenY;
        int y = checkIfAtEdgeOfYAxis(bottomOffset);

        if (isInvincible()) {
            graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3F));
        }

        graphics2D.drawImage(getDirectionalAnimationImage(), x, y, null);

        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F));
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
