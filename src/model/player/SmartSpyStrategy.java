package model.player;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import model.board.Card;
import model.board.CardType;
import model.board.Clue;

/**
 * Allows the spy to choose a clue that includes many words on the board
 * 
 * @author Simon Huang
 *
 */
public class SmartSpyStrategy implements SpyStrategy{

	private CardType team;
	
	/**
	 * Constructor for SmartSpyStrategy.
	 * @param team The spy's team
	 */
	public SmartSpyStrategy(CardType team) {
		this.team = team;
	}
	
	/**
	 * Selects a card from their team at random. Checks each clue for the optimal one
	 */
	@Override
	public Clue giveClue(List<Card> cards) {
		ArrayList<Card> toChooseFrom = new ArrayList<Card>();
		
		for(Card card : cards) {
			if(card.getTypeProperty() == team) {
				toChooseFrom.add(card);
			}
		}
		
		Random rand = new Random();
		
		String chosen = toChooseFrom.get(rand.nextInt(toChooseFrom.size())).getStringProperty(); //Choose a word
		String clue = "";
		
		try {
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(new FileReader(PATH.toString()));
        
			JSONObject jsonObj = (JSONObject) obj; //The actual .json
			JSONObject word = (JSONObject) jsonObj.get(chosen); //Narrow down to chosen word
			JSONArray syns = (JSONArray) word.get("syn"); //Narrow down to synonyms
			
			//Start checking for optimal clue
			HashMap<String, Integer> matches = new HashMap<String, Integer>();
			
			//O(n^3): Needs optimization
			for(Object syn : syns) { //Loop through clues of selected words
				for(Iterator it = jsonObj.keySet().iterator(); it.hasNext();) { //Loop through words
		        	String key = (String) it.next();
		        	if(key == chosen) continue; //If the key is the same as chosen word, ignore it
		        	JSONArray otherWordSynArray = (JSONArray) ((JSONObject) (jsonObj.get(key))).get("syn"); //Get the array of syn
		        	for(Object otherSyn : otherWordSynArray) {
		        		if((String) otherSyn == (String) syn) {
		        			matches.merge((String) syn, 1, Integer::sum);
		        		}
		        	}
		        }
			}
			
			
			
			
			clue = (String) syns.get(rand.nextInt(syns.size()));
			
			//DEBUG
			System.out.println();
			System.out.println("Turn: " + team);
			System.out.println("Chosen word: " + chosen);
			System.out.println("Available clues: " + syns);
			System.out.println();
			
		} catch (FileNotFoundException e) {
			System.err.println("File not found.");
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Error reading file.");
			System.exit(1);
		} catch (ParseException e) {
			System.err.println("Error parsing file.");
			System.exit(1);
		}
		return new Clue(clue.toUpperCase(), 1);
	}
}