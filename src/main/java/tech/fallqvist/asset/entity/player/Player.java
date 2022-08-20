package tech.fallqvist.asset.entity.player;

import tech.fallqvist.GamePanel;
import tech.fallqvist.asset.Asset;
import tech.fallqvist.asset.entity.Entity;
import tech.fallqvist.asset.object.ability.OBJ_Fireball;
import tech.fallqvist.asset.object.equipment.*;
import tech.fallqvist.asset.object.usable.inventory.OBJ_Key;
import tech.fallqvist.asset.object.usable.inventory.OBJ_Potion_Red;
import tech.fallqvist.asset.object.usable.pickuponly.PickUpOnlyObject;
import tech.fallqvist.util.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

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

        setItems();
        setDefaultValues();
        setCollision();
        getAnimationImages();
    }

    public void setDefaultValues() {
        setDefaultPosition();

        setSpeed(4);
        setMaxLife(6);
        setCurrentLife(getMaxLife());
        setMaxMana(4);
        setCurrentMana(getMaxMana());
        setMaxAmmo(10);
        setCurrentAmmo(getMaxAmmo());
        setLevel(1);
        setStrength(1);
        setDexterity(1);
        setExp(0);
        setNextLevelExp(5);
        setCoins(0);
        setAttackPower(getAttack());
        setDefensePower(getDefense());

    }

    public void setItems() {
        getInventory().clear();
        setDefaultWeapon();
        setCurrentShield(new OBJ_Shield_Wood(getGamePanel()));
        setProjectile(new OBJ_Fireball(getGamePanel()));
        getInventory().add(getCurrentWeapon());
        getInventory().add(getCurrentShield());
        getInventory().add(new OBJ_Key(getGamePanel()));
    }

    public void setDefaultPosition() {
        setWorldX(getGamePanel().getTileSize() * 23);
        setWorldY(getGamePanel().getTileSize() * 21);
        setDirection("down");
    }

    private void setDefaultWeapon() {
        setCurrentWeapon(new OBJ_Sword_Normal(getGamePanel()));
        setPlayerAttackArea();
        getAttackImages();
    }

    public void restoreLifeAndMana() {
        setCurrentLife(getMaxLife());
        setCurrentMana(getMaxMana());
        setInvincible(false);
    }

    public int getAttack() {
        return getStrength() * getCurrentWeapon().getAttackValue();
    }

    public int getDefense() {
        return getDexterity() * getCurrentShield().getDefenseValue();
    }

    private void setCollision() {
        setCollisionArea(new Rectangle(8, 16, 32, 32));
        setCollisionDefaultX(getCollisionArea().x);
        setCollisionDefaultY(getCollisionArea().y);
    }

    private void setPlayerAttackArea() {
        setAttackArea(getCurrentWeapon().getAttackArea());
    }

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

    public void getAttackImages() {
        if (getCurrentWeapon() instanceof OBJ_Sword_Normal) {
            setAttackUp1(setup("/images/player/boy_attack_up_1", getGamePanel().getTileSize(), getGamePanel().getTileSize() * 2));
            setAttackUp2(setup("/images/player/boy_attack_up_2", getGamePanel().getTileSize(), getGamePanel().getTileSize() * 2));
            setAttackDown1(setup("/images/player/boy_attack_down_1", getGamePanel().getTileSize(), getGamePanel().getTileSize() * 2));
            setAttackDown2(setup("/images/player/boy_attack_down_2", getGamePanel().getTileSize(), getGamePanel().getTileSize() * 2));
            setAttackLeft1(setup("/images/player/boy_attack_left_1", getGamePanel().getTileSize() * 2, getGamePanel().getTileSize()));
            setAttackLeft2(setup("/images/player/boy_attack_left_2", getGamePanel().getTileSize() * 2, getGamePanel().getTileSize()));
            setAttackRight1(setup("/images/player/boy_attack_right_1", getGamePanel().getTileSize() * 2, getGamePanel().getTileSize()));
            setAttackRight2(setup("/images/player/boy_attack_right_2", getGamePanel().getTileSize() * 2, getGamePanel().getTileSize()));
        }

        if (getCurrentWeapon() instanceof OBJ_Axe) {
            setAttackUp1(setup("/images/player/boy_axe_up_1", getGamePanel().getTileSize(), getGamePanel().getTileSize() * 2));
            setAttackUp2(setup("/images/player/boy_axe_up_2", getGamePanel().getTileSize(), getGamePanel().getTileSize() * 2));
            setAttackDown1(setup("/images/player/boy_axe_down_1", getGamePanel().getTileSize(), getGamePanel().getTileSize() * 2));
            setAttackDown2(setup("/images/player/boy_axe_down_2", getGamePanel().getTileSize(), getGamePanel().getTileSize() * 2));
            setAttackLeft1(setup("/images/player/boy_axe_left_1", getGamePanel().getTileSize() * 2, getGamePanel().getTileSize()));
            setAttackLeft2(setup("/images/player/boy_axe_left_2", getGamePanel().getTileSize() * 2, getGamePanel().getTileSize()));
            setAttackRight1(setup("/images/player/boy_axe_right_1", getGamePanel().getTileSize() * 2, getGamePanel().getTileSize()));
            setAttackRight2(setup("/images/player/boy_axe_right_2", getGamePanel().getTileSize() * 2, getGamePanel().getTileSize()));
        }
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

        fireProjectileIfKeyPressed();
        checkIfInvincible();
        updateLifeAndMana();
        checkIfAlive();
    }

    private void attacking() {
        setSpriteCounter(getSpriteCounter() + 1);

        if (getSpriteCounter() <= 5) {
            setSpriteNumber(1);
        }

        if (getSpriteCounter() > 5 && getSpriteCounter() <= 25) {
            setSpriteNumber(2);

            // Save current worldX, worldY and CollisionArea
            int currentWorldX = getWorldX();
            int currentWorldY = getWorldY();
            int collisionAreaWidth = getCollisionArea().width;
            int collisionAreaHeight = getCollisionArea().height;

            // Adjust player's worldX/Y to the attackArea
            switch (getDirection()) {
                case "up" -> setWorldY(currentWorldY - getAttackArea().height);
                case "down" -> setWorldY(currentWorldY + getAttackArea().height);
                case "left" -> setWorldX(currentWorldX - getAttackArea().width);
                case "right" -> setWorldX(currentWorldX + getAttackArea().width);
            }

            // Make collisionArea into attackArea
            getCollisionArea().width = getAttackArea().width;
            getCollisionArea().height = getAttackArea().height;

            // Check monster collision with updated collisionArea
            int monsterIndex = getGamePanel().getCollisionChecker().checkEntity(this, getGamePanel().getMonsters());
            damageMonster(monsterIndex, getAttackPower());

            // Check interactiveTile collision
            int interactiveTileIndex = getGamePanel().getCollisionChecker().checkEntity(this, getGamePanel().getInteractiveTiles());
            damageInteractiveTile(interactiveTileIndex);

            // Reset collisionArea to player
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

    public void damageMonster(int index, int attackPower) {
        if (index != 999) {
            if (!getGamePanel().getMonsters()[getGamePanel().getCurrentMap()][index].isInvincible()) {

                getGamePanel().playSoundEffect(5);

                int damage = attackPower - getGamePanel().getMonsters()[getGamePanel().getCurrentMap()][index].getDefensePower();
                if (damage < 0) {
                    damage = 0;
                }

                getGamePanel().getMonsters()[getGamePanel().getCurrentMap()][index].setCurrentLife(getGamePanel().getMonsters()[getGamePanel().getCurrentMap()][index].getCurrentLife() - damage);
                getGamePanel().getUi().addMessage(damage + " damage!");

                getGamePanel().getMonsters()[getGamePanel().getCurrentMap()][index].setInvincible(true);
                getGamePanel().getMonsters()[getGamePanel().getCurrentMap()][index].damageReaction();

                if (getGamePanel().getMonsters()[getGamePanel().getCurrentMap()][index].getCurrentLife() <= 0) {
                    getGamePanel().getMonsters()[getGamePanel().getCurrentMap()][index].setDying(true);
                    getGamePanel().getUi().addMessage("Killed the " + getGamePanel().getMonsters()[getGamePanel().getCurrentMap()][index].getName() + "!");
                    setExp(getExp() + getGamePanel().getMonsters()[getGamePanel().getCurrentMap()][index].getExp());
                    getGamePanel().getUi().addMessage("Exp + " + getGamePanel().getMonsters()[getGamePanel().getCurrentMap()][index].getExp());

                    checkLevelUp();
                }
            }
        }
    }

    private void damageInteractiveTile(int index) {
        if (index != 999
                && getGamePanel().getInteractiveTiles()[getGamePanel().getCurrentMap()][index].isDestructible()
                && getGamePanel().getInteractiveTiles()[getGamePanel().getCurrentMap()][index].isCorrectWeapon(getCurrentWeapon())
                && !getGamePanel().getInteractiveTiles()[getGamePanel().getCurrentMap()][index].isInvincible()) {

            getGamePanel().getInteractiveTiles()[getGamePanel().getCurrentMap()][index].playSoundEffect();
            getGamePanel().getInteractiveTiles()[getGamePanel().getCurrentMap()][index].setCurrentLife(getGamePanel().getInteractiveTiles()[getGamePanel().getCurrentMap()][index].getCurrentLife() - 1);
            getGamePanel().getInteractiveTiles()[getGamePanel().getCurrentMap()][index].setInvincible(true);

            generateParticle(getGamePanel().getInteractiveTiles()[getGamePanel().getCurrentMap()][index], getGamePanel().getInteractiveTiles()[getGamePanel().getCurrentMap()][index]);

            if (getGamePanel().getInteractiveTiles()[getGamePanel().getCurrentMap()][index].getCurrentLife() == 0) {
                getGamePanel().getInteractiveTiles()[getGamePanel().getCurrentMap()][index] = getGamePanel().getInteractiveTiles()[getGamePanel().getCurrentMap()][index].getDestroyedForm();
            }
        }
    }

    private void checkLevelUp() {
        if (getExp() >= getNextLevelExp()) {
            setLevel(getLevel() + 1);
            setNextLevelExp(getNextLevelExp() * 3);
            setMaxLife(getMaxLife() + 2);
            setStrength(getStrength() + 1);
            setDexterity(getDexterity() + 1);
            setAttackPower(getAttack());
            setDefensePower(getDefense());

            getGamePanel().playSoundEffect(8);
            getGamePanel().setGameState(getGamePanel().getDialogueState());
            getGamePanel().getUi().setCurrentDialogue("You are level " + getLevel() + " now!\n" +
                    "You feel stronger!");
        }
    }

    private void checkIfAttacking() {
        if (getGamePanel().getKeyHandler().isSpacePressed()) {
            getGamePanel().playSoundEffect(7);
            setAttacking(true);
        }
    }

    private void checkCollision() {
        setCollisionOn(false);

        checkTileCollision();
        checkInteractiveTileCollision();
        checkObjectCollision();
        checkNPCCollision();
        checkMonsterCollision();
    }

    private void checkTileCollision() {
        getGamePanel().getCollisionChecker().checkTile(this);
    }

    private void checkInteractiveTileCollision() {
        getGamePanel().getCollisionChecker().checkEntity(this, getGamePanel().getInteractiveTiles());
    }

    private void checkObjectCollision() {
        int objectIndex = getGamePanel().getCollisionChecker().checkObject(this, true);
        pickUpObject(objectIndex);
    }

    private void pickUpObject(int index) {
        if (index != 999) {

            // PICK-UP ONLY ITEMS
            if (getGamePanel().getObjects()[getGamePanel().getCurrentMap()][index] instanceof PickUpOnlyObject) {
                getGamePanel().getObjects()[getGamePanel().getCurrentMap()][index].use();
            }

            // INVENTORY ITEMS
            else {
                String text;

                if (getInventory().size() != getMaxInventorySize()) {
                    getInventory().add(getGamePanel().getObjects()[getGamePanel().getCurrentMap()][index]);
                    getGamePanel().playSoundEffect(1);
                    text = "Got a " + getGamePanel().getObjects()[getGamePanel().getCurrentMap()][index].getName() + "!";
                } else {
                    text = "You cannot carry anymore!";
                }

                getGamePanel().getUi().addMessage(text);
            }

            getGamePanel().getObjects()[getGamePanel().getCurrentMap()][index] = null;
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
                getGamePanel().getNpcs()[getGamePanel().getCurrentMap()][index].speak();
            }
        }
    }

    private void checkMonsterCollision() {
        int monsterIndex = getGamePanel().getCollisionChecker().checkEntity(this, getGamePanel().getMonsters());
        interactWithMonster(monsterIndex);
    }

    private void interactWithMonster(int index) {
        if (index != 999) {
            if (!isInvincible() && !getGamePanel().getMonsters()[getGamePanel().getCurrentMap()][index].isDying()) {
                getGamePanel().playSoundEffect(6);

                int damage = getGamePanel().getMonsters()[getGamePanel().getCurrentMap()][index].getAttackPower() - getDefensePower();
                if (damage < 0) {
                    damage = 0;
                }

                setCurrentLife(getCurrentLife() - damage);
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

    private void fireProjectileIfKeyPressed() {
        if (getGamePanel().getKeyHandler().isProjectileKeyPressed()
                && !getProjectile().isAlive()
                && getProjectileAvailableCounter() == 30
                && getProjectile().haveEnoughResource(this)) {

            // Set default coordinates, direction and user
            getProjectile().set(getWorldX(), getWorldY(), getDirection(), true, this);

            // Subtract use cost
            getProjectile().subtractResource(this);

            // Add it to the projectiles list
            getGamePanel().getProjectiles().add(getProjectile());

            setProjectileAvailableCounter(0);

            getGamePanel().playSoundEffect(10);
        }

        if (getProjectileAvailableCounter() < 30) {
            setProjectileAvailableCounter(getProjectileAvailableCounter() + 1);
        }
    }

    private void updateLifeAndMana() {
        if (getCurrentLife() > getMaxLife()) {
            setCurrentLife(getMaxLife());
        }

        if (getCurrentLife() < 0) {
            setCurrentLife(0);
        }

        if (getCurrentMana() > getMaxMana()) {
            setCurrentMana(getMaxMana());
        }

        if (getCurrentMana() < 0) {
            setCurrentMana(0);
        }
    }

    private void checkIfAlive() {
        if (getCurrentLife() <= 0) {
            getGamePanel().playSoundEffect(11);
            getGamePanel().setGameState(getGamePanel().getGameOverState());
            setInvincible(false);
        }
    }

    public void selectItem() {
        int itemIndex = getGamePanel().getUi().getItemIndexFromSlot(getGamePanel().getUi().getPlayerSlotCol(), getGamePanel().getUi().getPlayerSlotRow());

        if (itemIndex < getInventory().size()) {
            Asset selectedItem = getInventory().get(itemIndex);

            if (selectedItem instanceof Weapon) {
                setCurrentWeapon((Weapon) selectedItem);
                setAttackPower(getAttack());
                setPlayerAttackArea();
                getAttackImages();
            }

            if (selectedItem instanceof Shield) {
                setCurrentShield((Shield) selectedItem);
                setDefensePower(getDefense());
            }

            if (selectedItem instanceof OBJ_Potion_Red) {
                selectedItem.use();
                getInventory().remove(itemIndex);
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
            graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4F));
        }

        if (isAttacking()) {
            switch (getDirection()) {
                case "up" ->
                        graphics2D.drawImage(getDirectionalAnimationImage(), x, y - getGamePanel().getTileSize(), null);
                case "left" ->
                        graphics2D.drawImage(getDirectionalAnimationImage(), x - getGamePanel().getTileSize(), y, null);
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
    public BufferedImage getImage1() {
        return getDown1();
    }


    // NOT USED
    @Override
    public void damageReaction() {
        // Not used yet
    }

    @Override
    public boolean isCollision() {
        return false;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public void use() {
        // Not used
    }
}
