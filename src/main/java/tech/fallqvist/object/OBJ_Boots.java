package tech.fallqvist.object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Boots extends SuperObject {

    public OBJ_Boots() {
        setName("Boots");

        try {
            setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/images/objects/boots.png"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
