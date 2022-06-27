package tech.fallqvist.asset.entity.npc;

import tech.fallqvist.GamePanel;
import tech.fallqvist.asset.entity.Entity;

import java.util.Random;

public class NPC_OldMan extends Entity {

    public NPC_OldMan(GamePanel gamePanel) {
        super(gamePanel);

        setName("Old Man");
        setDirection("down");
        setSpeed(1);

        getAnimationImages();
        setDialogue();
    }

    @Override
    public void getAnimationImages() {
        setUp1(setup("/images/npc/oldman_up_1"));
        setUp2(setup("/images/npc/oldman_up_2"));
        setDown1(setup("/images/npc/oldman_down_1"));
        setDown2(setup("/images/npc/oldman_down_2"));
        setLeft1(setup("/images/npc/oldman_left_1"));
        setLeft2(setup("/images/npc/oldman_left_2"));
        setRight1(setup("/images/npc/oldman_right_1"));
        setRight2(setup("/images/npc/oldman_right_2"));
    }

    @Override
    public void setAction() {
        super.setAction();
    }

    public void setDialogue() {
        getDialogues()[0] = "Hello, lad.";
        getDialogues()[1] = "So you've come to this island to find \nthe treasure?";
        getDialogues()[2] = "I used to be a great wizard, but now... \nI'm a bit too old for adventuring.";
        getDialogues()[3] = "Well, good luck to you lad.";
    }

    @Override
    public boolean isCollision() {
        return false;
    }

    @Override
    public void speak() {
        super.speak();
    }
}
