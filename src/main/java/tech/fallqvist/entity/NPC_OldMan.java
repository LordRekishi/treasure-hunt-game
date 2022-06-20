package tech.fallqvist.entity;

import tech.fallqvist.GamePanel;

import java.util.Random;

public class NPC_OldMan extends Entity {

    public NPC_OldMan(GamePanel gamePanel) {
        super(gamePanel);

        setDirection("down");
        setSpeed(1);

        getAnimationImages();
    }

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
        setActionLockCounter(getActionLockCounter() + 1);

        if (getActionLockCounter() == 120) {

            Random random = new Random();
            int i = random.nextInt(100) + 1;

            if (i <= 25) {
                setDirection("up");
            }

            if (i > 25 && i <= 50) {
                setDirection("down");
            }

            if (i > 50 && i <= 75) {
                setDirection("left");
            }

            if (i > 75) {
                setDirection("right");
            }

            setActionLockCounter(0);
        }
    }
}
