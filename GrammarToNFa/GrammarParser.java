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
public class GrammarParser {

    ArrayList<ProductionRule> prs;

    public GrammarParser() {
        prs = new ArrayList<>();
    }

    @Override
    public String toString() {
        for (int i = 0; i < prs.size(); i++) {
            System.out.println("pr" + i + "=" + prs.get(i).toString());
        }
        return String.format("end");
    }

    public void ParseMultipleLines(ArrayList<String> s) {
        boolean isRL = ParseLine(s.get(0), false);
        for (int i = 1; i < s.size(); i++) {
            ParseLine(s.get(i), isRL);
        }
    }

    public ProductionRule convertRLtoLL(ProductionRule pr) {
        return null;
    }

    public boolean ParseLine(String s, boolean isRL) {
        ProductionRule pr = new ProductionRule();
        Variable srcV = new Variable(String.valueOf(s.charAt(0)));
        Variable destV = null;
        // S->Aab here s.charat(3) is A
        if (java.lang.Character.isUpperCase(s.charAt(3))) { //Left Linear
            pr.setIsRL(false); // must be checked
        } else if (java.lang.Character.isLowerCase(s.charAt(3))) {//Right Linear
            if (java.lang.Character.isUpperCase(s.charAt(s.length() - 1))) {
                pr.setIsRL(true);
            } else {
                pr.setIsRL(isRL);
            }
        }

        if (pr.isIsRL()) {
            for (int i = 3; i < s.length() - 1; i++) {
                pr.Terminals.add(s.charAt(i));
            }
            //Just Terminals
            if (java.lang.Character.isLowerCase(s.charAt(s.length() - 1))) {
                pr.setHasDestV(false);
                pr.Terminals.add(s.charAt(s.length() - 1));
            } else { // ends with a variable
                pr.setHasDestV(true);
                destV = new Variable(String.valueOf(s.charAt(s.length() - 1)));
            }
        } else {//pr is ll
            for (int i = s.length() - 1; i > 3; i--) {
                pr.Terminals.add(s.charAt(i));
            }
            if (java.lang.Character.isLowerCase(s.charAt(3))) {
                pr.setHasDestV(false);
                pr.Terminals.add(s.charAt(3));
            } else { // ends with a variable
                pr.setHasDestV(true);
                destV = new Variable(String.valueOf(s.charAt(3)));
            }
        }

        pr.setSrtV(srcV);
        pr.setDestV(destV);
        this.prs.add(pr);
        return pr.isIsRL();
    }

    public ArrayList<ProductionRule> getPrs() {
        return prs;
    }

}
