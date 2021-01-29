/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GrammarToNFa;

/**
 *
 * @author No1
 */
public class State {

    String name;
    boolean Final;

    public String getName() {
        return name;
    }

    public boolean isFinal() {
        return Final;
    }

    public void setFinal(boolean Final) {
        this.Final = Final;
    }

    
    
    public State(String name) {
        this.name = name;
    }
    
    
}
