package control.game;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import view.VerboseView;

/**
 * Handles all KeyEvents for when keys are pressed by the user.
 *
 * @author Rani Rafid
 * @date 02/06/19
 */


public class GameHandler implements EventHandler<KeyEvent> {

    private PlayerControl playerControl;

    public GameHandler(PlayerControl playerControl) {
        this.playerControl = playerControl;
    }

    /**
     * When the user presses ENTER, the KeyHandler triggers the playerControl to play the next turn.
     *
     * @param keyEvent
     */
    @Override
    public void handle(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            playerControl.doNextTurn();
        } else if (keyEvent.getCode() == KeyCode.V) {
            VerboseView.open();
        }
    }


}
