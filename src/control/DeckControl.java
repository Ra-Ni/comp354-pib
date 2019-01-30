package control;

import model.Card;
import model.Deck;
import model.constant.CardType;
import ui.listener.Listener;

import java.io.IOException;
import java.util.EmptyStackException;

public class DeckControl {
    private Deck deck;
    private int nextSubscription;

    public DeckControl() {
        try {
            deck = new Deck();
        } catch (IOException e) {
            e.printStackTrace();
        }
        nextSubscription = 0;
    }

    public void addSubscriber(Listener listener) throws IndexOutOfBoundsException {
        Card c = deck.at(nextSubscription);
        c.bind(listener);
        c.push(0, c.word);
        nextSubscription++;
    }

    CardType pick() {
        try {
            return deck.draw();
        } catch (EmptyStackException e) {
            System.err.println("Can no longer pick a card.");
            return null;
        }
    }
}
