package tech.fallqvist.asset.object.usable.inventory;

import tech.fallqvist.GamePanel;
import tech.fallqvist.asset.object.Object;
import tech.fallqvist.util.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Potion_Red extends Object {

    private final GamePanel gamePanel;

    public OBJ_Potion_Red(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        setName("Red Potion");
        setValue(5);
        setDescription("[" + getName() + "]\nRestores " + getValue() + " health");
        setPrice(25);

        try {
            BufferedImage image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/images/objects/potion_red.png")));
            setImage1(UtilityTool.scaleImage(image, gamePanel.getTileSize(), gamePanel.getTileSize()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void use() {
        gamePanel.setGameState(gamePanel.getDialogueState());
        gamePanel.getUi().setCurrentDialogue("You drink the " + getName() + "!\n" +
                "You have restored " + getValue() + " life!");

        gamePanel.getPlayer().setCurrentLife(gamePanel.getPlayer().getCurrentLife() + getValue());

        gamePanel.playSoundEffect(2);
    }
}
