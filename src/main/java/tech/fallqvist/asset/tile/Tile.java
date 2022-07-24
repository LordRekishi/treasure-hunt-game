package tech.fallqvist.asset.tile;

import java.awt.image.BufferedImage;

public class Tile {

    private BufferedImage image;
    private boolean collision;

    public BufferedImage getImage() {
        return image;
    }

    public Tile setImage(BufferedImage image) {
        this.image = image;
        return this;
    }

    public boolean isCollision() {
        return collision;
    }

    public Tile setCollision(boolean collision) {
        this.collision = collision;
        return this;
    }
}
