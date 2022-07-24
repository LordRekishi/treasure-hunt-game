package tech.fallqvist.asset.object.usable.pickuponly;

import tech.fallqvist.GamePanel;
import tech.fallqvist.util.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class OBJ_ManaCrystal extends PickUpOnlyObject {

    private final GamePanel gamePanel;

    public OBJ_ManaCrystal(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        setName("Mana Crystal");
        setValue(1);
        setDescription("[" + getName() + "]\nWill restore " + getValue() + " mana");

        try {
            BufferedImage image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/images/objects/manacrystal_full.png")));
            setImage1(UtilityTool.scaleImage(image, gamePanel.getTileSize(), gamePanel.getTileSize()));

            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/images/objects/manacrystal_blank.png")));
            setImage2(UtilityTool.scaleImage(image, gamePanel.getTileSize(), gamePanel.getTileSize()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void use() {
        gamePanel.playSoundEffect(1);
        gamePanel.getUi().addMessage("Mana +" + getValue());
        gamePanel.getPlayer().setCurrentMana(gamePanel.getPlayer().getCurrentMana() + getValue());
    }
}
