package tech.fallqvist.ui;

import tech.fallqvist.GamePanel;
import tech.fallqvist.object.OBJ_Key;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class UI {

    private final GamePanel gamePanel;
    private final Font arial_40, arial_80B;
    private final BufferedImage keyImage;
    private boolean messageOn = false;
    private String message;
    private int messageCounter;
    private boolean gameFinished = false;
    private double playTime;
    private final DecimalFormat decimalFormat;

    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.arial_40 = new Font("Arial", Font.PLAIN, 40);
        this.arial_80B = new Font("Arial", Font.BOLD, 80);
        OBJ_Key key = new OBJ_Key();
        this.keyImage = key.getImage();
        this.decimalFormat = new DecimalFormat("#0.00", new DecimalFormatSymbols(Locale.ENGLISH));
    }

    public void draw(Graphics2D graphics2D) {

        if (gameFinished) {
            drawFoundTreasureMessage(graphics2D);
            drawCongratulationsMessage(graphics2D);
            drawPlayTimeMessage(graphics2D);
            stopGame();

        } else {
            updateKeyUiElement(graphics2D);
            displayPlayTime(graphics2D);
            checkToDisplayMessage(graphics2D);
        }
    }

    private void drawFoundTreasureMessage(Graphics2D graphics2D) {
        String text;
        int textLength;
        int x, y;

        graphics2D.setFont(arial_40);
        graphics2D.setColor(Color.WHITE);

        text = "You found the treasure!";

        textLength = (int) graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();
        x = gamePanel.getScreenWidth() / 2 - textLength / 2;
        y = gamePanel.getScreenHeight() / 2 - (gamePanel.getTileSize() * 3);

        graphics2D.drawString(text, x, y);
    }

    private void drawCongratulationsMessage(Graphics2D graphics2D) {
        String text;
        int textLength;
        int x, y;

        graphics2D.setFont(arial_80B);
        graphics2D.setColor(Color.YELLOW);

        text = "Congratulations!";

        textLength = (int) graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();
        x = gamePanel.getScreenWidth() / 2 - textLength / 2;
        y = gamePanel.getScreenHeight() / 2 + (gamePanel.getTileSize() * 2);

        graphics2D.drawString(text, x, y);
    }

    private void drawPlayTimeMessage(Graphics2D graphics2D) {
        String text;
        int textLength;
        int x, y;

        graphics2D.setFont(arial_40);
        graphics2D.setColor(Color.WHITE);

        text = "Your Time is: " + decimalFormat.format(playTime) + "!";

        textLength = (int) graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();
        x = gamePanel.getScreenWidth() / 2 - textLength / 2;
        y = gamePanel.getScreenHeight() / 2 + (gamePanel.getTileSize() * 4);

        graphics2D.drawString(text, x, y);
    }

    private void stopGame() {
        gamePanel.setGameThread(null);
    }

    private void updateKeyUiElement(Graphics2D graphics2D) {
        graphics2D.setFont(arial_40);
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawImage(keyImage, gamePanel.getTileSize() / 2, gamePanel.getTileSize() / 2, gamePanel.getTileSize(), gamePanel.getTileSize(), null);
        graphics2D.drawString("x " + gamePanel.getPlayer().getNumberOfKeys(), 74, 65);
    }

    private void displayPlayTime(Graphics2D graphics2D) {
        playTime += (double) 1/60;
        graphics2D.drawString("Time: " + decimalFormat.format(playTime), gamePanel.getTileSize() * 11, 65);
    }

    private void checkToDisplayMessage(Graphics2D graphics2D) {
        if (messageOn) {
            graphics2D.setFont(graphics2D.getFont().deriveFont(30F));
            graphics2D.drawString(message, gamePanel.getTileSize() / 2, gamePanel.getTileSize() * 5);

            messageCounter++;

            if (messageCounter > 120) {
                messageCounter = 0;
                messageOn = false;
            }
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
}
