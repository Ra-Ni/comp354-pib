/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;
import model.Card;

public class Player {
    private int id;
    private String name;
    private int score;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
    public int getScore(){
       return score;
       //if (player == card.type && card.isOpen())
                //return 1 else return 0
    }
}
