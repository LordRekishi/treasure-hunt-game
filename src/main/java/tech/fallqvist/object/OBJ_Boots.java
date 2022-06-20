package tech.fallqvist.object;

import tech.fallqvist.GamePanel;
import tech.fallqvist.util.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Boots extends Object {

    public OBJ_Boots(GamePanel gamePanel) {
        setName("Boots");

        try {
            BufferedImage image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/images/objects/boots.png")));
            setImage(UtilityTool.scaleImage(image, gamePanel.getTileSize(), gamePanel.getTileSize()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
