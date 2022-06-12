package tech.fallqvist.object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Key extends SuperObject {

    public OBJ_Key() {
        setName("Key");

        try {
            setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/images/objects/key.png"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
