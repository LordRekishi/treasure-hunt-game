package tech.fallqvist.asset.object;

import tech.fallqvist.GamePanel;
import tech.fallqvist.util.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Shield_Wood extends Object {

    public OBJ_Shield_Wood(GamePanel gamePanel) {
        super(gamePanel);

        setName("Wooden Shield");
        setDescription("[" + getName() + "]\nMade of wood");
        setDefenseValue(1);

        try {
            BufferedImage image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/images/objects/shield_wood.png")));
            setImage1(UtilityTool.scaleImage(image, gamePanel.getTileSize(), gamePanel.getTileSize()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
