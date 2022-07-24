package tech.fallqvist.asset.object.ui;

import tech.fallqvist.GamePanel;
import tech.fallqvist.asset.object.Object;
import tech.fallqvist.util.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Heart extends Object {

    public OBJ_Heart(GamePanel gamePanel) {
        super(gamePanel);
        setName("Heart");

        try {
            BufferedImage image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/images/objects/heart_full.png")));
            setImage1(UtilityTool.scaleImage(image, gamePanel.getTileSize(), gamePanel.getTileSize()));

            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/images/objects/heart_half.png")));
            setImage2(UtilityTool.scaleImage(image, gamePanel.getTileSize(), gamePanel.getTileSize()));

            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/images/objects/heart_blank.png")));
            setImage3(UtilityTool.scaleImage(image, gamePanel.getTileSize(), gamePanel.getTileSize()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
