package tech.fallqvist.util;

import tech.fallqvist.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    private boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, spacePressed;
    private final GamePanel gamePanel;

    // DEBUG
    private boolean checkDrawTime = false;

    public KeyHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (gamePanel.getGameState() == gamePanel.getTitleState()) {
            checkTitleStateKeyPressed(code);
        } else if (gamePanel.getGameState() == gamePanel.getPlayState()) {
            checkPlayStateKeyPressed(code);
        } else if (gamePanel.getGameState() == gamePanel.getPauseState()) {
            checkPauseStateKeyPressed(code);
        } else if (gamePanel.getGameState() == gamePanel.getDialogueState()) {
            checkDialogeStateKeyPressed(code);
        } else if (gamePanel.getGameState() == gamePanel.getCharacterState()) {
            checkCharacterStateKeyPressed(code);
        }
    }

    private void checkTitleStateKeyPressed(int code) {
        if (gamePanel.getUi().getTitleScreenState() == 0) {
            checkMainTitleScreenKeyPressed(code);

        } else if (gamePanel.getUi().getTitleScreenState() == 1) {
            checkCharacterSelectionScreenKeyPressed(code);
        }
    }

    private void checkMainTitleScreenKeyPressed(int code) {
        if (code == KeyEvent.VK_W) {
            gamePanel.getUi().setCommandNumber(gamePanel.getUi().getCommandNumber() - 1);
            if (gamePanel.getUi().getCommandNumber() < 0) {
                gamePanel.getUi().setCommandNumber(2);
            }
        }

        if (code == KeyEvent.VK_S) {
            gamePanel.getUi().setCommandNumber(gamePanel.getUi().getCommandNumber() + 1);
            if (gamePanel.getUi().getCommandNumber() > 2) {
                gamePanel.getUi().setCommandNumber(0);
            }
        }

        if (code == KeyEvent.VK_ENTER) {
            titleScreenEnterPressed();
        }
    }

    private void titleScreenEnterPressed() {
        if (gamePanel.getUi().getCommandNumber() == 0) {
            gamePanel.getUi().setTitleScreenState(1);
        }

        if (gamePanel.getUi().getCommandNumber() == 1) {
            // load game
        }

        if (gamePanel.getUi().getCommandNumber() == 2) {
            System.exit(0);
        }
    }

    private void checkCharacterSelectionScreenKeyPressed(int code) {
        if (code == KeyEvent.VK_W) {
            gamePanel.getUi().setCommandNumber(gamePanel.getUi().getCommandNumber() - 1);
            if (gamePanel.getUi().getCommandNumber() < 0) {
                gamePanel.getUi().setCommandNumber(3);
            }
        }

        if (code == KeyEvent.VK_S) {
            gamePanel.getUi().setCommandNumber(gamePanel.getUi().getCommandNumber() + 1);
            if (gamePanel.getUi().getCommandNumber() > 3) {
                gamePanel.getUi().setCommandNumber(0);
            }
        }

        if (code == KeyEvent.VK_ENTER) {
            characterScreenEnterPressed();
        }
    }

    private void characterScreenEnterPressed() {
        if (gamePanel.getUi().getCommandNumber() == 0) {
            System.out.println("Fighter selected!");
            gamePanel.setGameState(gamePanel.getPlayState());
            gamePanel.playMusic(0);
        }

        if (gamePanel.getUi().getCommandNumber() == 1) {
            System.out.println("Rogue selected!");
            gamePanel.setGameState(gamePanel.getPlayState());
            gamePanel.playMusic(0);
        }

        if (gamePanel.getUi().getCommandNumber() == 2) {
            System.out.println("Sorcerer selected!");
            gamePanel.setGameState(gamePanel.getPlayState());
            gamePanel.playMusic(0);
        }

        if (gamePanel.getUi().getCommandNumber() == 3) {
            gamePanel.getUi().setTitleScreenState(0);
        }
    }

    private void checkPlayStateKeyPressed(int code) {
        if (code == KeyEvent.VK_W) {
            upPressed = true;
        }

        if (code == KeyEvent.VK_S) {
            downPressed = true;
        }

        if (code == KeyEvent.VK_A) {
            leftPressed = true;
        }

        if (code == KeyEvent.VK_D) {
            rightPressed = true;
        }

        if (code == KeyEvent.VK_P) {
            gamePanel.setGameState(gamePanel.getPauseState());
        }

        if (code == KeyEvent.VK_C) {
            gamePanel.setGameState(gamePanel.getCharacterState());
        }

        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }

        if (code == KeyEvent.VK_SPACE) {
            spacePressed = true;
        }

        // DEBUG
        if (code == KeyEvent.VK_T) {
            checkDrawTime = !checkDrawTime;
        }
    }

    private void checkPauseStateKeyPressed(int code) {
        if (code == KeyEvent.VK_P) {
            gamePanel.setGameState(gamePanel.getPlayState());
        }
    }

    private void checkDialogeStateKeyPressed(int code) {
        if (code == KeyEvent.VK_ENTER) {
            gamePanel.setGameState(gamePanel.getPlayState());
        }
    }

    private void checkCharacterStateKeyPressed(int code) {
        if (code == KeyEvent.VK_C) {
            gamePanel.setGameState(gamePanel.getPlayState());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }

        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }

        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }

        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }

        if (code == KeyEvent.VK_SPACE) {
            spacePressed = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }

    public boolean isUpPressed() {
        return upPressed;
    }

    public KeyHandler setUpPressed(boolean upPressed) {
        this.upPressed = upPressed;
        return this;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public KeyHandler setDownPressed(boolean downPressed) {
        this.downPressed = downPressed;
        return this;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public KeyHandler setLeftPressed(boolean leftPressed) {
        this.leftPressed = leftPressed;
        return this;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public KeyHandler setRightPressed(boolean rightPressed) {
        this.rightPressed = rightPressed;
        return this;
    }

    public boolean isEnterPressed() {
        return enterPressed;
    }

    public KeyHandler setEnterPressed(boolean enterPressed) {
        this.enterPressed = enterPressed;
        return this;
    }

    public boolean isCheckDrawTime() {
        return checkDrawTime;
    }

    public KeyHandler setCheckDrawTime(boolean checkDrawTime) {
        this.checkDrawTime = checkDrawTime;
        return this;
    }

    public boolean isSpacePressed() {
        return spacePressed;
    }

    public KeyHandler setSpacePressed(boolean spacePressed) {
        this.spacePressed = spacePressed;
        return this;
    }
}
