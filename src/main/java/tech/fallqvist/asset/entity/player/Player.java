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
        setPlayerAttackArea();
        setDefaultValues();
        getAnimationImages();
        getAttackImages();
    }

    private void setCollision() {
        setCollisionArea(new Rectangle(8, 16, 32, 32));
        setCollisionDefaultX(getCollisionArea().x);
        setCollisionDefaultY(getCollisionArea().y);
    }

    private void setPlayerAttackArea() {
        getAttackArea().width = 36;
        getAttackArea().height = 36;
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
        setUp1(setup("/images/player/boy_up_1", getGamePanel().getTileSize(), getGamePanel().getTileSize()));
        setUp2(setup("/images/player/boy_up_2", getGamePanel().getTileSize(), getGamePanel().getTileSize()));
        setDown1(setup("/images/player/boy_down_1", getGamePanel().getTileSize(), getGamePanel().getTileSize()));
        setDown2(setup("/images/player/boy_down_2", getGamePanel().getTileSize(), getGamePanel().getTileSize()));
        setLeft1(setup("/images/player/boy_left_1", getGamePanel().getTileSize(), getGamePanel().getTileSize()));
        setLeft2(setup("/images/player/boy_left_2", getGamePanel().getTileSize(), getGamePanel().getTileSize()));
        setRight1(setup("/images/player/boy_right_1", getGamePanel().getTileSize(), getGamePanel().getTileSize()));
        setRight2(setup("/images/player/boy_right_2", getGamePanel().getTileSize(), getGamePanel().getTileSize()));
    }

    @Override
    public void getAttackImages() {
        setAttackUp1(setup("/images/player/boy_attack_up_1", getGamePanel().getTileSize(), getGamePanel().getTileSize() * 2));
        setAttackUp2(setup("/images/player/boy_attack_up_2", getGamePanel().getTileSize(), getGamePanel().getTileSize() * 2));
        setAttackDown1(setup("/images/player/boy_attack_down_1", getGamePanel().getTileSize(), getGamePanel().getTileSize() * 2));
        setAttackDown2(setup("/images/player/boy_attack_down_2", getGamePanel().getTileSize(), getGamePanel().getTileSize() * 2));
        setAttackLeft1(setup("/images/player/boy_attack_left_1", getGamePanel().getTileSize() * 2, getGamePanel().getTileSize()));
        setAttackLeft2(setup("/images/player/boy_attack_left_2", getGamePanel().getTileSize() * 2, getGamePanel().getTileSize()));
        setAttackRight1(setup("/images/player/boy_attack_right_1", getGamePanel().getTileSize() * 2, getGamePanel().getTileSize()));
        setAttackRight2(setup("/images/player/boy_attack_right_2", getGamePanel().getTileSize() * 2, getGamePanel().getTileSize()));
    }

    @Override
    public void update() {

        if (isAttacking()) {
            attacking();

        } else if (keyHandler.isUpPressed()
                || keyHandler.isDownPressed()
                || keyHandler.isLeftPressed()
                || keyHandler.isRightPressed()
                || keyHandler.isEnterPressed()
                || keyHandler.isSpacePressed()) {

            if (keyHandler.isUpPressed()) {
                setDirection("up");
            } else if (keyHandler.isDownPressed()) {
                setDirection("down");
            } else if (keyHandler.isLeftPressed()) {
                setDirection("left");
            } else if (keyHandler.isRightPressed()) {
                setDirection("right");
            }

            checkIfAttacking();
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

    private void attacking() {
        setSpriteCounter(getSpriteCounter() + 1);

        if (getSpriteCounter() <= 5) {
            setSpriteNumber(1);
        }

        if (getSpriteCounter() > 5 && getSpriteCounter() <= 25) {
            setSpriteNumber(2);

            int currentWorldX = getWorldX();
            int currentWorldY = getWorldY();
            int collisionAreaWidth = getCollisionArea().width;
            int collisionAreaHeight = getCollisionArea().height;

            switch (getDirection()) {
                case "up" -> setWorldY(currentWorldY - getAttackArea().height);
                case "down" -> setWorldY(currentWorldY + getAttackArea().height);
                case "left" -> setWorldX(currentWorldX - getAttackArea().width);
                case "right" -> setWorldX(currentWorldX + getAttackArea().width);
            }

            getCollisionArea().width = getAttackArea().width;
            getCollisionArea().height = getAttackArea().height;

            int monsterIndex = getGamePanel().getCollisionChecker().checkEntity(this, getGamePanel().getMonsters());
            damageMonster(monsterIndex);

            setWorldX(currentWorldX);
            setWorldY(currentWorldY);
            getCollisionArea().width = collisionAreaWidth;
            getCollisionArea().height = collisionAreaHeight;
        }

        if (getSpriteCounter() > 25) {
            setSpriteNumber(1);
            setSpriteCounter(0);
            setAttacking(false);
        }
    }

    private void damageMonster(int index) {
        if (index != 999) {
            if (!getGamePanel().getMonsters()[index].isInvincible()) {
                getGamePanel().getMonsters()[index].setCurrentLife(getGamePanel().getMonsters()[index].getCurrentLife() - 1);
                getGamePanel().getMonsters()[index].setInvincible(true);

                if (getGamePanel().getMonsters()[index].getCurrentLife() <= 0) {
                    getGamePanel().getMonsters()[index] = null;
                }
            }
        }
    }

    private void checkIfAttacking() {
        if (getGamePanel().getKeyHandler().isSpacePressed()) {
            setAttacking(true);
        }
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

    @Override
    public void draw(Graphics2D graphics2D) {
        int rightOffset = getGamePanel().getScreenWidth() - screenX;
        int x = checkIfAtEdgeOfXAxis(rightOffset);

        int bottomOffset = getGamePanel().getScreenHeight() - screenY;
        int y = checkIfAtEdgeOfYAxis(bottomOffset);

        if (isInvincible()) {
            graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4F));
        }

        if (isAttacking()) {
            switch (getDirection()) {
                case "up" -> graphics2D.drawImage(getDirectionalAnimationImage(), x, y - getGamePanel().getTileSize(), null);
                case "left" -> graphics2D.drawImage(getDirectionalAnimationImage(), x - getGamePanel().getTileSize(), y, null);
                default -> graphics2D.drawImage(getDirectionalAnimationImage(), x, y, null);
            }
        } else {
            graphics2D.drawImage(getDirectionalAnimationImage(), x, y, null);
        }

        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F));
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

    @Override
    public boolean isCollision() {
        return false;
    }
}
