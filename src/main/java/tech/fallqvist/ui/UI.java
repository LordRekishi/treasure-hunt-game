package tech.fallqvist.ui;

import tech.fallqvist.GamePanel;
import tech.fallqvist.asset.Asset;
import tech.fallqvist.asset.object.Object;
import tech.fallqvist.asset.object.usable.pickuponly.OBJ_Heart;
import tech.fallqvist.asset.object.usable.pickuponly.OBJ_ManaCrystal;
import tech.fallqvist.util.UtilityTool;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UI {

    private final GamePanel gamePanel;
    private Graphics2D graphics2D;
    private final BufferedImage heart_full, heart_half, heart_blank, crystal_full, crystal_blank;
    private Font maruMonica;
    private List<String> messages = new ArrayList<>();
    private List<Integer> messageCounter = new ArrayList<>();
    private boolean gameFinished = false;
    private String currentDialogue;
    private int commandNumber;
    private int titleScreenState;
    private int slotCol;
    private int slotRow;

    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        setupFonts();

        Object heart = new OBJ_Heart(gamePanel);
        this.heart_full = heart.getImage1();
        this.heart_half = heart.getImage2();
        this.heart_blank = heart.getImage3();

        Object manaCrystal = new OBJ_ManaCrystal(gamePanel);
        this.crystal_full = manaCrystal.getImage1();
        this.crystal_blank = manaCrystal.getImage2();
    }

    private void setupFonts() {
        try {
            InputStream inputStream = getClass().getResourceAsStream("/fonts/x12y16pxMaruMonica.ttf");
            this.maruMonica = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(inputStream));
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D graphics2D) {
        this.graphics2D = graphics2D;

        setupDefaultGraphics(graphics2D);

        if (gamePanel.getGameState() == gamePanel.getTitleState()) {
            drawTitleScreen();
        }

        if (gamePanel.getGameState() == gamePanel.getPlayState()) {
            drawPlayerLife();
            drawPlayerMana();
            drawMessages();
        }

        if (gamePanel.getGameState() == gamePanel.getPauseState()) {
            drawPlayerLife();
            drawPlayerMana();
            drawPauseScreen();
        }

        if (gamePanel.getGameState() == gamePanel.getDialogueState()) {
            drawPlayerLife();
            drawPlayerMana();
            drawDialogueScreen();
        }

        if (gamePanel.getGameState() == gamePanel.getCharacterState()) {
            drawCharacterScreen();
            drawInventoryScreen();
        }
    }

    private void setupDefaultGraphics(Graphics2D graphics2D) {
        graphics2D.setFont(maruMonica);
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics2D.setColor(Color.WHITE);
    }

    private void drawTitleScreen() {
        if (titleScreenState == 0) {
            // TITLE TEXT
            graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 96F));

            String text = "Blue Boy Adventure";
            int x = UtilityTool.getXForCenterOfText(text, gamePanel, graphics2D);
            int y = gamePanel.getTileSize() * 3;

            // Shadow
            graphics2D.setColor(Color.GRAY);
            graphics2D.drawString(text, x + 5, y + 5);

            // Text
            graphics2D.setColor(Color.WHITE);
            graphics2D.drawString(text, x, y);

            // GAME IMAGE
            x = gamePanel.getScreenWidth() / 2 - (gamePanel.getTileSize() * 2) / 2;
            y += gamePanel.getTileSize() * 2;
            graphics2D.drawImage(gamePanel.getPlayer().getDown1(), x, y, gamePanel.getTileSize() * 2, gamePanel.getTileSize() * 2, null);

            // MENU
            graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 48F));

            text = "NEW GAME";
            x = UtilityTool.getXForCenterOfText(text, gamePanel, graphics2D);
            y += gamePanel.getTileSize() * 3.5;
            graphics2D.drawString(text, x, y);
            if (commandNumber == 0) {
                graphics2D.drawString(">", x - gamePanel.getTileSize(), y);
            }

            text = "LOAD GAME";
            x = UtilityTool.getXForCenterOfText(text, gamePanel, graphics2D);
            y += gamePanel.getTileSize();
            graphics2D.drawString(text, x, y);
            if (commandNumber == 1) {
                graphics2D.drawString(">", x - gamePanel.getTileSize(), y);
            }

            text = "QUIT";
            x = UtilityTool.getXForCenterOfText(text, gamePanel, graphics2D);
            y += gamePanel.getTileSize();
            graphics2D.drawString(text, x, y);
            if (commandNumber == 2) {
                graphics2D.drawString(">", x - gamePanel.getTileSize(), y);
            }
        } else if (titleScreenState == 1) {
            graphics2D.setColor(Color.WHITE);
            graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 42F));

            String text = "Select your class!";
            int x = UtilityTool.getXForCenterOfText(text, gamePanel, graphics2D);
            int y = gamePanel.getTileSize() * 3;
            graphics2D.drawString(text, x, y);

            text = "Fighter";
            x = UtilityTool.getXForCenterOfText(text, gamePanel, graphics2D);
            y += gamePanel.getTileSize() * 3;
            graphics2D.drawString(text, x, y);
            if (commandNumber == 0) {
                graphics2D.drawString(">", x - gamePanel.getTileSize(), y);
            }

            text = "Rogue";
            x = UtilityTool.getXForCenterOfText(text, gamePanel, graphics2D);
            y += gamePanel.getTileSize();
            graphics2D.drawString(text, x, y);
            if (commandNumber == 1) {
                graphics2D.drawString(">", x - gamePanel.getTileSize(), y);
            }

            text = "Sorcerer";
            x = UtilityTool.getXForCenterOfText(text, gamePanel, graphics2D);
            y += gamePanel.getTileSize();
            graphics2D.drawString(text, x, y);
            if (commandNumber == 2) {
                graphics2D.drawString(">", x - gamePanel.getTileSize(), y);
            }

            text = "Cancel";
            x = UtilityTool.getXForCenterOfText(text, gamePanel, graphics2D);
            y += gamePanel.getTileSize() * 2;
            graphics2D.drawString(text, x, y);
            if (commandNumber == 3) {
                graphics2D.drawString(">", x - gamePanel.getTileSize(), y);
            }
        }

    }

    private void drawPlayerLife() {
        int x = gamePanel.getTileSize() / 2;
        int y = gamePanel.getTileSize() / 2;

        for (int i = 0; i < gamePanel.getPlayer().getMaxLife() / 2; i++) {
            graphics2D.drawImage(heart_blank, x, y, null);
            x += gamePanel.getTileSize();
        }

        x = gamePanel.getTileSize() / 2;
        y = gamePanel.getTileSize() / 2;

        for (int i = 0; i < gamePanel.getPlayer().getCurrentLife(); i++) {
            graphics2D.drawImage(heart_half, x, y, null);
            i++;

            if (i < gamePanel.getPlayer().getCurrentLife()) {
                graphics2D.drawImage(heart_full, x, y, null);
            }

            x += gamePanel.getTileSize();
        }
    }

    private void drawPlayerMana() {
        int x = (gamePanel.getTileSize() / 2) - 5;
        int y = (int) (gamePanel.getTileSize() * 1.5);

        for (int i = 0; i < gamePanel.getPlayer().getMaxMana(); i++) {
            graphics2D.drawImage(crystal_blank, x, y, null);
            x += 35;
        }

        x = (gamePanel.getTileSize() / 2) - 5;
        y = (int) (gamePanel.getTileSize() * 1.5);

        for (int i = 0; i < gamePanel.getPlayer().getCurrentMana(); i++) {
            graphics2D.drawImage(crystal_full, x, y, null);
            x += 35;
        }
    }

    public void addMessage(String text) {
        messages.add(text);
        messageCounter.add(0);
    }

    private void drawMessages() {
        int messageX = gamePanel.getTileSize();
        int messageY = gamePanel.getTileSize() * 4;
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 32F));

        for (int i = 0; i < messages.size(); i++) {
            if (messages.get(i) != null) {
                graphics2D.setColor(Color.BLACK);
                graphics2D.drawString(messages.get(i), messageX + 2, messageY + 2);
                graphics2D.setColor(Color.WHITE);
                graphics2D.drawString(messages.get(i), messageX, messageY);

                int counter = messageCounter.get(i) + 1;
                messageCounter.set(i, counter);
                messageY += 50;

                if (messageCounter.get(i) > 180) {
                    messages.remove(i);
                    messageCounter.remove(i);
                }
            }
        }
    }

    private void drawPauseScreen() {
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 80F));

        String text = "PAUSED";
        int x = UtilityTool.getXForCenterOfText(text, gamePanel, graphics2D);
        int y = gamePanel.getScreenHeight() / 2;

        graphics2D.drawString(text, x, y);
    }

    private void drawDialogueScreen() {
        int x = gamePanel.getTileSize() * 2;
        int y = gamePanel.getTileSize() / 2;
        int width = gamePanel.getScreenWidth() - (gamePanel.getTileSize() * 4);
        int height = gamePanel.getTileSize() * 4;

        drawSubWindow(x, y, width, height);

        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 32F));
        x += gamePanel.getTileSize();
        y += gamePanel.getTileSize();

        for (String line : currentDialogue.split("\n")) {
            graphics2D.drawString(line, x, y);
            y += 40;
        }
    }

    private void drawCharacterScreen() {
        int frameX = gamePanel.getTileSize();
        int frameY = gamePanel.getTileSize();
        int frameWidth = gamePanel.getTileSize() * 5;
        int frameHeight = (int) (gamePanel.getTileSize() * 10.5);

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        graphics2D.setColor(Color.WHITE);
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 32F));

        int textX = frameX + 20;
        int textY = frameY + gamePanel.getTileSize();
        int lineHeight = 35;

        drawText(textX, textY, lineHeight);

        int tailX = (frameX + frameWidth) - 30;

        drawValues(textY, lineHeight, tailX);
    }

    private void drawText(int textX, int textY, int lineHeight) {
        graphics2D.drawString("Level", textX, textY);
        textY += lineHeight;
        graphics2D.drawString("Life", textX, textY);
        textY += lineHeight;
        graphics2D.drawString("Mana", textX, textY);
        textY += lineHeight;
        graphics2D.drawString("Strength", textX, textY);
        textY += lineHeight;
        graphics2D.drawString("Dexterity", textX, textY);
        textY += lineHeight;
        graphics2D.drawString("Attack", textX, textY);
        textY += lineHeight;
        graphics2D.drawString("Defense", textX, textY);
        textY += lineHeight;
        graphics2D.drawString("Exp", textX, textY);
        textY += lineHeight;
        graphics2D.drawString("Next Level", textX, textY);
        textY += lineHeight;
        graphics2D.drawString("Coins", textX, textY);
        textY += lineHeight + 20;
        graphics2D.drawString("Weapon", textX, textY);
        textY += lineHeight + 15;
        graphics2D.drawString("Shield", textX, textY);
    }

    private void drawValues(int textY, int lineHeight, int tailX) {
        int textX;
        String value;

        value = String.valueOf(gamePanel.getPlayer().getLevel());
        textX = UtilityTool.getXForAlightToRightOfText(value, tailX, gamePanel, graphics2D);
        graphics2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = gamePanel.getPlayer().getCurrentLife() + "/" + gamePanel.getPlayer().getMaxLife();
        textX = UtilityTool.getXForAlightToRightOfText(value, tailX, gamePanel, graphics2D);
        graphics2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = gamePanel.getPlayer().getCurrentMana() + "/" + gamePanel.getPlayer().getMaxMana();
        textX = UtilityTool.getXForAlightToRightOfText(value, tailX, gamePanel, graphics2D);
        graphics2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.getPlayer().getStrength());
        textX = UtilityTool.getXForAlightToRightOfText(value, tailX, gamePanel, graphics2D);
        graphics2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.getPlayer().getDexterity());
        textX = UtilityTool.getXForAlightToRightOfText(value, tailX, gamePanel, graphics2D);
        graphics2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.getPlayer().getAttackPower());
        textX = UtilityTool.getXForAlightToRightOfText(value, tailX, gamePanel, graphics2D);
        graphics2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.getPlayer().getDefensePower());
        textX = UtilityTool.getXForAlightToRightOfText(value, tailX, gamePanel, graphics2D);
        graphics2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.getPlayer().getExp());
        textX = UtilityTool.getXForAlightToRightOfText(value, tailX, gamePanel, graphics2D);
        graphics2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.getPlayer().getNextLevelExp());
        textX = UtilityTool.getXForAlightToRightOfText(value, tailX, gamePanel, graphics2D);
        graphics2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.getPlayer().getCoins());
        textX = UtilityTool.getXForAlightToRightOfText(value, tailX, gamePanel, graphics2D);
        graphics2D.drawString(value, textX, textY);
        textY += lineHeight;

        drawCurrentEquipment(textY, tailX);
    }

    private void drawCurrentEquipment(int textY, int tailX) {
        graphics2D.drawImage(gamePanel.getPlayer().getCurrentWeapon().getImage1(), tailX - gamePanel.getTileSize(), textY - 14, null);
        textY += gamePanel.getTileSize();

        graphics2D.drawImage(gamePanel.getPlayer().getCurrentShield().getImage1(), tailX - gamePanel.getTileSize(), textY - 14, null);
    }

    private void drawInventoryScreen() {

        // ITEM FRAME BOX
        int frameX = gamePanel.getTileSize() * 9;
        int frameY = gamePanel.getTileSize();
        int frameWidth = gamePanel.getTileSize() * 6;
        int frameHeight = gamePanel.getTileSize() * 5;

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // ITEM SLOTS
        final int slotXStart = frameX + 20;
        final int slotYStart = frameY + 20;
        int slotX = slotXStart;
        int slotY = slotYStart;
        int slotSize = gamePanel.getTileSize() + 3;

        List<Asset> inventory = gamePanel.getPlayer().getInventory();

        drawItemsInInventory(slotXStart, slotX, slotY, slotSize, inventory);
        drawSelectionBox(slotXStart, slotYStart, slotSize);

        // DESCRIPTION FRAME
        int descriptionFrameX = frameX;
        int descriptionFrameY = frameY + frameHeight;
        int descriptionFrameWidth = frameWidth;
        int descriptionFrameHeight = gamePanel.getTileSize() * 3;

        drawItemDescriptionText(inventory, descriptionFrameX, descriptionFrameY, descriptionFrameWidth, descriptionFrameHeight);
    }

    private void drawItemsInInventory(int slotXStart, int slotX, int slotY, int slotSize, List<Asset> inventory) {
        for (int i = 0; i < inventory.size(); i++) {
            Asset object = inventory.get(i);

            // EQUIPPED BOX COLOR
            if (object == gamePanel.getPlayer().getCurrentWeapon()
                    || object == gamePanel.getPlayer().getCurrentShield()) {
                graphics2D.setColor(new Color(240, 190, 90));
                graphics2D.fillRoundRect(slotX, slotY, gamePanel.getTileSize(), gamePanel.getTileSize(), 10, 10);
            }

            graphics2D.drawImage(object.getImage1(), slotX, slotY, null);

            slotX += slotSize;

            if (i == 4 || i == 9 || i == 14) {
                slotX = slotXStart;
                slotY += slotSize;
            }
        }
    }

    private void drawSelectionBox(int slotXStart, int slotYStart, int slotSize) {
        // CURSOR selection box
        int cursorX = slotXStart + (slotSize * slotCol);
        int cursorY = slotYStart + (slotSize * slotRow);
        int cursorWidth = gamePanel.getTileSize();
        int cursorHeight = gamePanel.getTileSize();

        graphics2D.setColor(Color.WHITE);
        graphics2D.setStroke(new BasicStroke(3));
        graphics2D.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
    }

    private void drawItemDescriptionText(List<Asset> inventory, int descriptionFrameX, int descriptionFrameY, int descriptionFrameWidth, int descriptionFrameHeight) {
        // DRAW DESCRIPTION TEXT
        int textX = descriptionFrameX + 20;
        int textY = descriptionFrameY + gamePanel.getTileSize();

        graphics2D.setFont(graphics2D.getFont().deriveFont(28F));

        int itemIndex = getItemIndexFromSlot();

        if (itemIndex < inventory.size()) {

            drawSubWindow(descriptionFrameX, descriptionFrameY, descriptionFrameWidth, descriptionFrameHeight);

            for (String line : inventory.get(itemIndex).getDescription().split("\n")) {
                graphics2D.drawString(line, textX, textY);
                textY += 32;
            }
        }
    }

    public int getItemIndexFromSlot() {
        int itemIndex = slotCol + (slotRow * 5);
        return itemIndex;
    }

    public void drawSubWindow(int x, int y, int width, int height) {
        Color color = new Color(0, 0, 0, 210);
        graphics2D.setColor(color);
        graphics2D.fillRoundRect(x, y, width, height, 35, 35);

        color = new Color(255, 255, 255);
        graphics2D.setColor(color);
        graphics2D.setStroke(new BasicStroke(5));
        graphics2D.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }


    public UI setGameFinished(boolean gameFinished) {
        this.gameFinished = gameFinished;
        return this;
    }

    public String getCurrentDialogue() {
        return currentDialogue;
    }

    public UI setCurrentDialogue(String currentDialogue) {
        this.currentDialogue = currentDialogue;
        return this;
    }

    public int getCommandNumber() {
        return commandNumber;
    }

    public UI setCommandNumber(int commandNumber) {
        this.commandNumber = commandNumber;
        return this;
    }

    public int getTitleScreenState() {
        return titleScreenState;
    }

    public UI setTitleScreenState(int titleScreenState) {
        this.titleScreenState = titleScreenState;
        return this;
    }

    public int getSlotCol() {
        return slotCol;
    }

    public UI setSlotCol(int slotCol) {
        this.slotCol = slotCol;
        return this;
    }

    public int getSlotRow() {
        return slotRow;
    }

    public UI setSlotRow(int slotRow) {
        this.slotRow = slotRow;
        return this;
    }
}
