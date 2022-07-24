package tech.fallqvist.asset.object.ui;

import tech.fallqvist.GamePanel;
import tech.fallqvist.asset.object.Object;
import tech.fallqvist.util.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class OBJ_ManaCrystal extends Object {

    public OBJ_ManaCrystal(GamePanel gamePanel) {
        super(gamePanel);
        setName("Mana Crystal");

        try {
            BufferedImage image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/images/objects/manacrystal_full.png")));
            setImage1(UtilityTool.scaleImage(image, gamePanel.getTileSize(), gamePanel.getTileSize()));

            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/images/objects/manacrystal_blank.png")));
            setImage2(UtilityTool.scaleImage(image, gamePanel.getTileSize(), gamePanel.getTileSize()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
