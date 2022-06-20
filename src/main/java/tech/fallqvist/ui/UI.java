package tech.fallqvist.ui;

import tech.fallqvist.GamePanel;
import tech.fallqvist.util.UtilityTool;

import java.awt.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class UI {

    private final GamePanel gamePanel;
    private Graphics2D graphics2D;
    private final Font arial_40, arial_80B;
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
        this.decimalFormat = new DecimalFormat("#0.00", new DecimalFormatSymbols(Locale.ENGLISH));
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

        graphics2D.setFont(arial_40);
        graphics2D.setColor(Color.WHITE);

        if (gamePanel.getGameState() == gamePanel.getPlayState()) {
            // Playstate stuff
        }

        if (gamePanel.getGameState() == gamePanel.getPauseState()) {
            drawPauseScreen();
        }
    }

    private void drawPauseScreen() {
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 80F));

        String text = "PAUSED";
        int x = UtilityTool.getXForCenterOfText(text, gamePanel, graphics2D);
        int y = gamePanel.getScreenHeight() / 2;

        graphics2D.drawString(text, x, y);
    }
}
