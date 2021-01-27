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
        for (int i = 0; i < s.size(); i++) {
            ParseLine(s.get(i));
        }
    }

    public void ParseLine(String s) {
        ProductionRule pr = new ProductionRule();
        Variable srcV = new Variable(String.valueOf(s.charAt(0)));
        Variable destV = null;
        if (java.lang.Character.isUpperCase(s.charAt(3))) { //Right Linear
            pr.setIsLL(true); // must be checked
        } else if (java.lang.Character.isLowerCase(s.charAt(3))) {//left Linear
            pr.setIsLL(true);
        }

        if (pr.isIsLL()) {
            for (int i = 3; i < s.length() - 1; i++) {
                pr.Terminals.add(s.charAt(i));
            }
            if (java.lang.Character.isLowerCase(s.charAt(s.length() - 1))) {
                pr.setHasDestV(false);
                pr.Terminals.add(s.charAt(s.length() - 1));

            } else { // ends with a variable
                pr.setHasDestV(true);
                destV = new Variable(String.valueOf(s.charAt(s.length() - 1)));
            }
        }
        pr.setSrtV(srcV);
        pr.setDestV(destV);
        this.prs.add(pr);
    }

    public ArrayList<ProductionRule> getPrs() {
        return prs;
    }
    
}
