package tech.fallqvist.ui;

import tech.fallqvist.GamePanel;
import tech.fallqvist.util.UtilityTool;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class UI {

    private final GamePanel gamePanel;
    private Graphics2D graphics2D;
    private Font maruMonica, purisaB;
    private boolean messageOn = false;
    private String message;
    private int messageCounter;
    private boolean gameFinished = false;
    private String currentDialogue;
    private int commandNumber;

    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        try {
            InputStream inputStream = getClass().getResourceAsStream("/fonts/x12y16pxMaruMonica.ttf");
            this.maruMonica = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(inputStream));
            inputStream = getClass().getResourceAsStream("/fonts/Purisa Bold.ttf");
            this.purisaB = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(inputStream));
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public UI setGameFinished(boolean gameFinished) {
        this.gameFinished = gameFinished;
        return this;
    }

    public void draw(Graphics2D graphics2D) {
        this.graphics2D = graphics2D;

        graphics2D.setFont(maruMonica);
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics2D.setColor(Color.WHITE);

        if (gamePanel.getGameState() == gamePanel.getTitleState()) {
            drawTitleScreen();
        }

        if (gamePanel.getGameState() == gamePanel.getPlayState()) {
            // Playstate stuff
        }

        if (gamePanel.getGameState() == gamePanel.getPauseState()) {
            drawPauseScreen();
        }

        if (gamePanel.getGameState() == gamePanel.getDialogueState()) {
            drawDialogueScreen();
        }
    }

    private void drawTitleScreen() {
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

    public void drawSubWindow(int x, int y, int width, int height) {
        Color color = new Color(0, 0, 0, 210);
        graphics2D.setColor(color);
        graphics2D.fillRoundRect(x, y, width, height, 35, 35);

        color = new Color(255, 255, 255);
        graphics2D.setColor(color);
        graphics2D.setStroke(new BasicStroke(5));
        graphics2D.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
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
}
