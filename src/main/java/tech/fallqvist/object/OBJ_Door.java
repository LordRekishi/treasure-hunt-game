package tech.fallqvist.object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Door extends SuperObject {

    public OBJ_Door() {
        setName("Door");
        setCollision(true);

        try {
            setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/images/objects/door.png"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
