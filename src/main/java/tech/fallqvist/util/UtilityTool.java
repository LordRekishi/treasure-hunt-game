package tech.fallqvist.util;

import tech.fallqvist.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UtilityTool {

    public static BufferedImage scaleImage(BufferedImage original, int width, int height) {
        BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
        Graphics2D graphics2D = scaledImage.createGraphics();
        graphics2D.drawImage(original, 0, 0, width, height, null);
        graphics2D.dispose();

        return scaledImage;
    }

    public static int getXForCenterOfText(String text, GamePanel gamePanel, Graphics2D graphics2D) {
        int length = (int) graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();
        return gamePanel.getScreenWidth() / 2 - length / 2;
    }

    public static int getXForAlightToRightOfText(String text, int tailX, GamePanel gamePanel, Graphics2D graphics2D) {
        int length = (int) graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();
        return tailX - length;
    }

    public static boolean isInsidePlayerView(int worldX, int worldY, GamePanel gamePanel) {
        return worldX + gamePanel.getTileSize() > gamePanel.getPlayer().getWorldX() - gamePanel.getPlayer().getScreenX()
                && worldX - gamePanel.getTileSize() < gamePanel.getPlayer().getWorldX() + gamePanel.getPlayer().getScreenX()
                && worldY + gamePanel.getTileSize() > gamePanel.getPlayer().getWorldY() - gamePanel.getPlayer().getScreenY()
                && worldY - gamePanel.getTileSize() < gamePanel.getPlayer().getWorldY() + gamePanel.getPlayer().getScreenY();
    }

    public static void changeAlpha(Graphics2D graphics2D, float alphaValue) {
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }
}
