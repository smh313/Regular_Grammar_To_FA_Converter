package GrammarToNFa;

import java.util.ArrayList;

/**
 *
 * @author No1
 */
public class FA {

    ArrayList<Transition> transitions;
    ArrayList<State> states;
    int tCount = 0; //state counter

    void importTransitions(ArrayList<Transition> t) {
        this.transitions = t;
    }

    // in thist function we create a state for each variable
    public void initialStatesForVars(ArrayList<ProductionRule> prs) {
        for (ProductionRule p : prs) {
            //process for creating states for variables
            //check if this V does not exist
            boolean exists = false;
            for (State q : states) {
                if (q.getName().equals(p.getSrcV().getSymbol())) {
                    exists = true;
                }
            }
            
            if (!exists) {
                State q = new State(p.srcV.symbol);
                System.out.println("q" + q + " is " + q.name);
                states.add(q);
            }

            for (int j = 0; j < p.Terminals.size(); j++) {
                String name = ("Q" + tCount);
                //State q = new State(name);
                tCount++;
            }
        }
//        
//        for (int i = 0; i < prs.size(); i++) {
//            //process for creating states for variables
//            //check if this V does not exist
//            boolean exists = false;
//            for (int j = 0; j < states.size(); j++) {
//                if (states.get(j).getName().equals(prs.get(i).getSrcV().getSymbol())) {
//                    exists = true;
//                }
//            }
//            if (!exists) {
//                State q = new State(prs.get(i).srcV.symbol);
//                System.out.println("q" + i + " is " + q.name);
//                states.add(q);
//            }
//
//            for (int j = 0; j < prs.get(i).Terminals.size(); j++) {
//                String name = ("Q" + tCount);
//                //State q = new State(name);
//                tCount++;
//            }
//        }
    }

    public void middleStatesForVars(ArrayList<ProductionRule> prs) {
        for (int i = 0; i < prs.size(); i++) {
           Transition t = new Transition();
//           t.srcState = 
        }
    }

    public void convertPRsToTransition(ArrayList<ProductionRule> prs) {
        initialStatesForVars(prs);
        middleStatesForVars(prs);
    }

    public FA() {
        transitions = new ArrayList<>();
        states = new ArrayList<>();
    }

}
