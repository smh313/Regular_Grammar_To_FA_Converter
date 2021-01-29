/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GrammarToNFa;

import java.util.ArrayList;

/**
 *
 * @author No1
 */
public class ProductionRule {

    // A --> xyB | Bxy | xy
    // here A is srcV & B is destV x,y are terminals
    //
    Variable srcV;
    Variable destV;
    ArrayList<Character> Terminals;
    boolean hasDestV = true;
    boolean isRL; //Leftlinear

    @Override
    public String toString() {
        String s;
        if (!hasDestV) {
            s = (srcV.symbol + " to " + "lambda" + " " + Terminals.toString());
        } else {
            s = (srcV.symbol + " to " + destV.symbol + " " + Terminals.toString());
        }
        return s; //To change body of generated methods, choose Tools | Templates.
    }

    public ProductionRule() {
        Terminals = new ArrayList<>();
    }

    //getters & setters
    public Variable getSrcV() {
        return srcV;
    }

    public void setSrtV(Variable srtV) {
        this.srcV = srtV;
    }

    public Variable getDestV() {
        return destV;
    }

    public void setDestV(Variable destV) {
        this.destV = destV;
    }

    public ArrayList<Character> getTerminals() {
        return Terminals;
    }

    public void setTerminals(ArrayList<Character> Terminals) {
        this.Terminals = Terminals;
    }

    public boolean isHasDestV() {
        return hasDestV;
    }

    public void setHasDestV(boolean hasDestV) {
        this.hasDestV = hasDestV;
    }

    public boolean isIsRL() {
        return isRL;
    }

    public void setIsRL(boolean isRL) {
        this.isRL = isRL;
    }
}
