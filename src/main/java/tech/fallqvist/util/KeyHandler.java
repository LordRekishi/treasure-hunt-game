package tech.fallqvist.util;

import tech.fallqvist.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    private boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, spacePressed, projectileKeyPressed;
    private final GamePanel gamePanel;

    // DEBUG
    private boolean showDebugText = false;

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
            checkDialogueStateKeyPressed(code);

        } else if (gamePanel.getGameState() == gamePanel.getCharacterState()) {
            checkCharacterStateKeyPressed(code);

        } else if (gamePanel.getGameState() == gamePanel.getOptionState()) {
            checkOptionStateKeyPressed(code);

        } else if (gamePanel.getGameState() == gamePanel.getGameOverState()) {
            checkGameOverStateKeyPressed(code);
        } else if (gamePanel.getGameState() == gamePanel.getTradeState()) {
            checkTradeStateKeyPressed(code);
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
            gamePanel.playSoundEffect(9);
            if (gamePanel.getUi().getCommandNumber() < 0) {
                gamePanel.getUi().setCommandNumber(2);
            }
        }

        if (code == KeyEvent.VK_S) {
            gamePanel.getUi().setCommandNumber(gamePanel.getUi().getCommandNumber() + 1);
            gamePanel.playSoundEffect(9);
            if (gamePanel.getUi().getCommandNumber() > 2) {
                gamePanel.getUi().setCommandNumber(0);
            }
        }

        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
    }

    private void checkCharacterSelectionScreenKeyPressed(int code) {
        if (code == KeyEvent.VK_W) {
            gamePanel.getUi().setCommandNumber(gamePanel.getUi().getCommandNumber() - 1);
            gamePanel.playSoundEffect(9);
            if (gamePanel.getUi().getCommandNumber() < 0) {
                gamePanel.getUi().setCommandNumber(3);
            }
        }

        if (code == KeyEvent.VK_S) {
            gamePanel.getUi().setCommandNumber(gamePanel.getUi().getCommandNumber() + 1);
            gamePanel.playSoundEffect(9);
            if (gamePanel.getUi().getCommandNumber() > 3) {
                gamePanel.getUi().setCommandNumber(0);
            }
        }

        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
    }

    private void characterScreenEnterPressed() {
        if (gamePanel.getUi().getCommandNumber() == 0) {
            System.out.println("Fighter selected!");

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
        checkMovementKeys(code);
        checkGameStateKeys(code);
        checkInteractionKeys(code);
        checkAdminKeys(code);
    }

    private void checkMovementKeys(int code) {
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
    }

    private void checkGameStateKeys(int code) {
        if (code == KeyEvent.VK_P) {
            gamePanel.setGameState(gamePanel.getPauseState());
        }

        if (code == KeyEvent.VK_C) {
            gamePanel.setGameState(gamePanel.getCharacterState());
        }

        if (code == KeyEvent.VK_ESCAPE) {
            gamePanel.setGameState(gamePanel.getOptionState());
        }
    }

    private void checkInteractionKeys(int code) {
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }

        if (code == KeyEvent.VK_SPACE) {
            spacePressed = true;
        }

        if (code == KeyEvent.VK_F) {
            projectileKeyPressed = true;
        }
    }

    private void checkAdminKeys(int code) {
        // DEBUG
        if (code == KeyEvent.VK_T) {
            showDebugText = !showDebugText;
        }

        if (code == KeyEvent.VK_R) {
            switch (gamePanel.getCurrentMap()) {
                case 0 -> gamePanel.getTileManager().loadMap("/maps/worldV3.txt", 0);
                case 1 -> gamePanel.getTileManager().loadMap("/maps/interior01.txt", 1);
            }
        }
    }

    private void checkPauseStateKeyPressed(int code) {
        if (code == KeyEvent.VK_P) {
            gamePanel.setGameState(gamePanel.getPlayState());
        }
    }

    private void checkDialogueStateKeyPressed(int code) {
        if (code == KeyEvent.VK_ENTER) {
            gamePanel.setGameState(gamePanel.getPlayState());
        }
    }

    private void checkCharacterStateKeyPressed(int code) {
        if (code == KeyEvent.VK_C) {
            gamePanel.setGameState(gamePanel.getPlayState());
        }

        if (code == KeyEvent.VK_ENTER) {
            gamePanel.getPlayer().selectItem();
        }

        playerInventoryMovement(code);
    }

    private void playerInventoryMovement(int code) {
        if (code == KeyEvent.VK_W) {
            if (gamePanel.getUi().getPlayerSlotRow() != 0) {
                gamePanel.playSoundEffect(9);
                gamePanel.getUi().setPlayerSlotRow(gamePanel.getUi().getPlayerSlotRow() - 1);
            }
        }

        if (code == KeyEvent.VK_A) {
            if (gamePanel.getUi().getPlayerSlotCol() != 0) {
                gamePanel.playSoundEffect(9);
                gamePanel.getUi().setPlayerSlotCol(gamePanel.getUi().getPlayerSlotCol() - 1);
            }
        }

        if (code == KeyEvent.VK_S) {
            if (gamePanel.getUi().getPlayerSlotRow() != 3) {
                gamePanel.playSoundEffect(9);
                gamePanel.getUi().setPlayerSlotRow(gamePanel.getUi().getPlayerSlotRow() + 1);
            }
        }

        if (code == KeyEvent.VK_D) {
            if (gamePanel.getUi().getPlayerSlotCol() != 4) {
                gamePanel.playSoundEffect(9);
                gamePanel.getUi().setPlayerSlotCol(gamePanel.getUi().getPlayerSlotCol() + 1);
            }
        }
    }

    private void checkOptionStateKeyPressed(int code) {
        if (code == KeyEvent.VK_ESCAPE) {
            gamePanel.setGameState(gamePanel.getPlayState());
        }

        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }

        int maxCommandNumber;

        switch (gamePanel.getUi().getSubState()) {
            case 0 -> maxCommandNumber = 5;
            case 3 -> maxCommandNumber = 1;
            default -> maxCommandNumber = 0;
        }

        if (code == KeyEvent.VK_W) {
            gamePanel.getUi().setCommandNumber(gamePanel.getUi().getCommandNumber() - 1);
            gamePanel.playSoundEffect(9);
            if (gamePanel.getUi().getCommandNumber() < 0) {
                gamePanel.getUi().setCommandNumber(maxCommandNumber);
            }
        }

        if (code == KeyEvent.VK_S) {
            gamePanel.getUi().setCommandNumber(gamePanel.getUi().getCommandNumber() + 1);
            gamePanel.playSoundEffect(9);
            if (gamePanel.getUi().getCommandNumber() > maxCommandNumber) {
                gamePanel.getUi().setCommandNumber(0);
            }
        }

        if (code == KeyEvent.VK_A) {
            if (gamePanel.getUi().getSubState() == 0) {
                if (gamePanel.getUi().getCommandNumber() == 1 && gamePanel.getMusic().getVolumeScale() > 0) {
                    gamePanel.getMusic().setVolumeScale(gamePanel.getMusic().getVolumeScale() - 1);
                    gamePanel.getMusic().checkVolume();
                    gamePanel.playSoundEffect(9);
                }

                if (gamePanel.getUi().getCommandNumber() == 2 && gamePanel.getSoundEffect().getVolumeScale() > 0) {
                    gamePanel.getSoundEffect().setVolumeScale(gamePanel.getSoundEffect().getVolumeScale() - 1);
                    gamePanel.playSoundEffect(9);
                }
            }
        }

        if (code == KeyEvent.VK_D) {
            if (gamePanel.getUi().getSubState() == 0) {
                if (gamePanel.getUi().getCommandNumber() == 1 && gamePanel.getMusic().getVolumeScale() < 5) {
                    gamePanel.getMusic().setVolumeScale(gamePanel.getMusic().getVolumeScale() + 1);
                    gamePanel.getMusic().checkVolume();
                    gamePanel.playSoundEffect(9);
                }

                if (gamePanel.getUi().getCommandNumber() == 2 && gamePanel.getSoundEffect().getVolumeScale() < 5) {
                    gamePanel.getSoundEffect().setVolumeScale(gamePanel.getSoundEffect().getVolumeScale() + 1);
                    gamePanel.playSoundEffect(9);
                }
            }
        }
    }

    private void checkGameOverStateKeyPressed(int code) {
        if (code == KeyEvent.VK_W) {
            gamePanel.getUi().setCommandNumber(gamePanel.getUi().getCommandNumber() - 1);
            gamePanel.playSoundEffect(9);
            if (gamePanel.getUi().getCommandNumber() < 0) {
                gamePanel.getUi().setCommandNumber(1);
            }
        }

        if (code == KeyEvent.VK_S) {
            gamePanel.getUi().setCommandNumber(gamePanel.getUi().getCommandNumber() + 1);
            gamePanel.playSoundEffect(9);
            if (gamePanel.getUi().getCommandNumber() > 1) {
                gamePanel.getUi().setCommandNumber(0);
            }
        }

        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
    }

    private void checkTradeStateKeyPressed(int code) {
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }

        if (gamePanel.getUi().getSubState() == 0) {
            if (code == KeyEvent.VK_W) {
                gamePanel.getUi().setCommandNumber(gamePanel.getUi().getCommandNumber() - 1);
                if (gamePanel.getUi().getCommandNumber() < 0) {
                    gamePanel.getUi().setCommandNumber(2);
                }
                gamePanel.playSoundEffect(9);
            }

            if (code == KeyEvent.VK_S) {
                gamePanel.getUi().setCommandNumber(gamePanel.getUi().getCommandNumber() + 1);
                if (gamePanel.getUi().getCommandNumber() > 2) {
                    gamePanel.getUi().setCommandNumber(0);
                }
                gamePanel.playSoundEffect(9);
            }
        }

        if (gamePanel.getUi().getSubState() == 1) {
            npcInventoryMovement(code);
            if (code == KeyEvent.VK_ESCAPE) {
                gamePanel.getUi().setSubState(0);
            }
        }

        if (gamePanel.getUi().getSubState() == 2) {
            playerInventoryMovement(code);
            if (code == KeyEvent.VK_ESCAPE) {
                gamePanel.getUi().setSubState(0);
            }
        }
    }

    private void npcInventoryMovement(int code) {
        if (code == KeyEvent.VK_W) {
            if (gamePanel.getUi().getNpcSlotRow() != 0) {
                gamePanel.playSoundEffect(9);
                gamePanel.getUi().setNpcSlotRow(gamePanel.getUi().getNpcSlotRow() - 1);
            }
        }

        if (code == KeyEvent.VK_A) {
            if (gamePanel.getUi().getNpcSlotCol() != 0) {
                gamePanel.playSoundEffect(9);
                gamePanel.getUi().setNpcSlotCol(gamePanel.getUi().getNpcSlotCol() - 1);
            }
        }

        if (code == KeyEvent.VK_S) {
            if (gamePanel.getUi().getNpcSlotRow() != 3) {
                gamePanel.playSoundEffect(9);
                gamePanel.getUi().setNpcSlotRow(gamePanel.getUi().getNpcSlotRow() + 1);
            }
        }

        if (code == KeyEvent.VK_D) {
            if (gamePanel.getUi().getNpcSlotCol() != 4) {
                gamePanel.playSoundEffect(9);
                gamePanel.getUi().setNpcSlotCol(gamePanel.getUi().getNpcSlotCol() + 1);
            }
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

        if (code == KeyEvent.VK_F) {
            projectileKeyPressed = false;
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

    public boolean isShowDebugText() {
        return showDebugText;
    }

    public KeyHandler setShowDebugText(boolean showDebugText) {
        this.showDebugText = showDebugText;
        return this;
    }

    public boolean isSpacePressed() {
        return spacePressed;
    }

    public KeyHandler setSpacePressed(boolean spacePressed) {
        this.spacePressed = spacePressed;
        return this;
    }

    public boolean isProjectileKeyPressed() {
        return projectileKeyPressed;
    }

    public KeyHandler setProjectileKeyPressed(boolean projectileKeyPressed) {
        this.projectileKeyPressed = projectileKeyPressed;
        return this;
    }
}
