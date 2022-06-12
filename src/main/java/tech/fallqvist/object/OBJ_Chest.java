package tech.fallqvist.object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Chest extends SuperObject {

    public OBJ_Chest() {
        setName("Chest");

        try {
            setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/images/objects/chest.png"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
