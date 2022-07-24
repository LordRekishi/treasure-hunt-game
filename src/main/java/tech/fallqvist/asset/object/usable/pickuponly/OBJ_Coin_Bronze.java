package tech.fallqvist.asset.object.usable.pickuponly;

import tech.fallqvist.GamePanel;
import tech.fallqvist.util.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Coin_Bronze extends PickUpOnlyObject {

    private final GamePanel gamePanel;

    public OBJ_Coin_Bronze(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        setName("Bronze Coin");
        setValue(1);
        setDescription("[" + getName() + "]\nA coin worth " + getValue());

        try {
            BufferedImage image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/images/objects/coin_bronze.png")));
            setImage1(UtilityTool.scaleImage(image, gamePanel.getTileSize(), gamePanel.getTileSize()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void use() {
        gamePanel.playSoundEffect(1);
        gamePanel.getUi().addMessage("Coin +" + getValue());
        gamePanel.getPlayer().setCoins(gamePanel.getPlayer().getCoins() + getValue());
    }
}
