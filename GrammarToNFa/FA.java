package GrammarToNFa;

import java.util.ArrayList;

/**
 *
 * @author No1
 */
public class FA {

    ArrayList<Transition> transitions;
    ArrayList<State> validStates;
    ArrayList<State> dumpStates;
    int tCount = 0; //state counter

    void importTransitions(ArrayList<Transition> t) {
        this.transitions = t;
    }

    public State findState(ProductionRule p, boolean src) {
        if (src) { // we want to find a srcState
            for (State q : validStates) {
                if (q.getName().equals(p.getSrcV().getSymbol())) {
                    return q;
                }
            }
        } else { //// we want to find a destState
            for (State q : validStates) {
                if (q.getName().equals(p.getDestV().getSymbol())) {
                    return q;
                }
            }
        }
        return null;
    }

    // in thist function we create a state for each variable
    public void createInitialStatesForVars(ArrayList<ProductionRule> prs) {
        for (ProductionRule p : prs) {
            //process for creating validStates for variables
            //check if this V does not exist

//            if (!exists) {
            State startState;
            if (findState(p, true) == null) {
                startState = new State(p.srcV.symbol);
                System.out.println("q" + startState.getName() + " is " + startState.name);
                validStates.add(startState);
            } else {
                startState = findState(p, true);
                System.out.println("q" + startState.getName() + " has been created" + startState.name);
            }
        }
    }

    public State getDumpState(int index) {// 0 => last q created (qn) , 1=> pre-last(qn-1)
        return dumpStates.get(dumpStates.size() - 1 - index);
    }

    public void addDumpState(State q) {// 0 => last q created (qn) , 1=> pre-last(qn-1)
        dumpStates.add(q);
    }

    public void createTransitions(ArrayList<ProductionRule> prs) {

        for (ProductionRule p : prs) {

            Transition tran = new Transition();
            State srcState, destState;
            srcState = findState(p, true);
            destState = findState(p, false);

            if (p.getTerminals().size() == 0) {

            } else if (p.getTerminals().size() == 1) {
                tran.setSrcState(srcState);
                tran.setTerminal(p.getTerminals().get(0));
                tran.setDestState(destState);
                transitions.add(tran);
            } else { // greater than 1 : >=2 we would have more transitions
                int qCount = 1;
                State lastq;
                State q = new State("q" + qCount);;
                for (int i = 0; i < p.getTerminals().size(); i++) {
                    tran = new Transition();
                    lastq = q;
                    q = new State("q" + qCount);
                    addDumpState(q);
                    qCount++;

                    if (i == 0) {
                        tran.setSrcState(srcState);
                        tran.setTerminal(p.getTerminals().get(i));
                        tran.setDestState(q);

                    } else if (i == p.getTerminals().size() - 1) {
                        tran.setSrcState(lastq);
                        tran.setTerminal(p.getTerminals().get(i));
                        tran.setDestState(destState);

                    } else {

                        tran.setSrcState(lastq);
                        tran.setTerminal(p.getTerminals().get(i));
                        tran.setDestState(q);

                        lastq = q;
                    }
                    transitions.add(tran);

                }
            }
        }
    }

    public void convertPRsToTransition(ArrayList<ProductionRule> prs) {
        createInitialStatesForVars(prs);
        createTransitions(prs);
        for (Transition t : transitions) {
            System.out.println(t.toString());
        }
    }

    public FA() {
        transitions = new ArrayList<>();
        validStates = new ArrayList<>();
        dumpStates = new ArrayList<>();
    }

}
