package tech.fallqvist.asset.object;

import tech.fallqvist.GamePanel;
import tech.fallqvist.asset.Asset;
import tech.fallqvist.util.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Potion_Red extends Object {

    private final GamePanel gamePanel;
    private final int healingValue = 5;

    public OBJ_Potion_Red(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        setName("Red Potion");
        setDescription("[" + getName() + "]\nRestores " + healingValue + " health");

        try {
            BufferedImage image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/images/objects/potion_red.png")));
            setImage1(UtilityTool.scaleImage(image, gamePanel.getTileSize(), gamePanel.getTileSize()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void use(Asset asset) {
        gamePanel.setGameState(gamePanel.getDialogueState());
        gamePanel.getUi().setCurrentDialogue("You drink the " + getName() + "!\n" +
                "You have restored " + healingValue + " life!");

        asset.setCurrentLife(asset.getCurrentLife() + healingValue);

        if (asset.getCurrentLife() > asset.getMaxLife()) {
            asset.setCurrentLife(asset.getMaxLife());
        }

        gamePanel.playSoundEffect(2);
    }
}
