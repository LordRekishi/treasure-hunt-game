package tech.fallqvist.asset.object.interactive;

import tech.fallqvist.GamePanel;
import tech.fallqvist.asset.object.Object;
import tech.fallqvist.util.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Door extends Object {

    public OBJ_Door(GamePanel gamePanel) {
        super(gamePanel);

        setName("Door");
        setCollision(true);

        getCollisionArea().x = 0;
        getCollisionArea().y = 16;
        getCollisionArea().width = 48;
        getCollisionArea().height = 32;
        setCollisionDefaultX(getCollisionArea().x);
        setCollisionDefaultY(getCollisionArea().y);

        try {
            BufferedImage image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/images/objects/door.png")));
            setImage1(UtilityTool.scaleImage(image, gamePanel.getTileSize(), gamePanel.getTileSize()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
