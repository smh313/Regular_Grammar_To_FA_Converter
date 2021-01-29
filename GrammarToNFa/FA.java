package GrammarToNFa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 *
 * @author No1
 */
public class FA {

    ArrayList<Transition> transitions;
    ArrayList<State> validStates;
    ArrayList<State> dumpStates;
    int tCount = 0; //state counter
    State startState, finalState, trapState;

    void importTransitions(ArrayList<Transition> t) {
        this.transitions = t;
    }

    public FA reverse() {
        State temp;
        for (Transition t : transitions) {
            temp = t.getSrcState();
            t.setSrcState(t.getDestState());
            t.setDestState(temp);
        }
        temp = startState;
        startState = finalState;
        finalState = temp;

        return this;
    }

    public FA reverseAndSortTransitions() {
        reverse();
        Collections.reverse(transitions);
        return this;
    }

    public ArrayList<Transition> findTransitionWithSrc(State s) {
        ArrayList<Transition> foundTransitions = new ArrayList<>();
        for (Transition t : transitions) {
            if (s.getName() == t.getSrcState().getName()) {
                foundTransitions.add(t);
            }
        }
        return foundTransitions;
    }

    public FA convertNfaToDfa() {

        FA dfa = new FA();
        return dfa;
    }

    public boolean nextTrans(State q, String s, int iterator) {
        ArrayList<Transition> currentTransitions = new ArrayList<>();
        currentTransitions = findTransitionWithSrc(q);
        if (s.length() <= iterator) {
            return false;
        }
        for (Transition t : currentTransitions) {
            if (t.getTerminal() == s.charAt(iterator)) {
                if (t.getDestState().getName().equals(getFinalState().getName())) {
                    return true;
                } else {
                    return nextTrans(t.getDestState(), s, iterator + 1);
                }
            }
        }
        return false;
    }

    public boolean acceptor(String s) {
//        String[] chars = s.split("");
        int iterator = 0;
        return nextTrans(startState, s, iterator);
    }

    public State findStateFromPR(ProductionRule p, boolean src) {
        if (src) { // we want to find a srcState
            for (State q : validStates) {
                if (q.getName().equals(p.getSrcV().getSymbol())) {
                    return q;
                }
            }
        } else { //// we want to find a destState
            for (State q : validStates) {
                if (p.isHasDestV() && q.getName().equals(p.getDestV().getSymbol())) {
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
            if (findStateFromPR(p, true) == null) {
                startState = new State(p.srcV.symbol);
                System.out.println("q" + startState.getName() + " is " + startState.name);
                validStates.add(startState);
            } else {
                startState = findStateFromPR(p, true);
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
        int qCount = 1;
        
        for (ProductionRule p : prs) {

            Transition tran = new Transition();
            State srcState, destState;
            srcState = findStateFromPR(p, true);
            destState = findStateFromPR(p, false);

            if (p.getTerminals().size() == 0) {

            } else if (p.getTerminals().size() == 1) {
                tran.setSrcState(srcState);
                tran.setTerminal(p.getTerminals().get(0));
                if (p.isHasDestV()) {
                    tran.setDestState(destState);
                } else {
                    tran.setDestState(finalState);
                }
                transitions.add(tran);
            } else { // greater than 1 : >=2 we would have more transitions
                State lastq;
                State q = new State("q" + qCount);
                for (int i = 0; i < p.getTerminals().size(); i++) {
                    tran = new Transition();
                    lastq = q;
                    q = new State("q" + qCount);
                    addDumpState(q);

                    if (i == 0) { // first terminal
                        tran.setSrcState(srcState);
                        tran.setTerminal(p.getTerminals().get(i));
                        tran.setDestState(q);
                        qCount++;
                        // last terminal
                    } else if (i == p.getTerminals().size() - 1) {
                        tran.setSrcState(lastq);
                        tran.setTerminal(p.getTerminals().get(i));
                        if (p.isHasDestV()) {
                            tran.setDestState(destState);
                        } else {
                            tran.setDestState(finalState);
                        }

                    } else {//mid terminals

                        tran.setSrcState(lastq);
                        tran.setTerminal(p.getTerminals().get(i));
                        tran.setDestState(q);
                        qCount++;
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
//        for (Transition t : transitions) {
//            System.out.println(t.toString());
//        }
//        System.out.println(this.toString());
    }

    public FA() {
        transitions = new ArrayList<>();
        validStates = new ArrayList<>();
        dumpStates = new ArrayList<>();
        finalState = new State("qf");
        trapState = new State("trap");
    }

    @Override
    public String toString() {
        return "FA{" + "transitions=" + transitions + ", startState=" + startState.getName() + ", finalState=" + finalState.getName() + '}';
    }

    public State getFinalState() {
        return finalState;
    }

    public void setStartState(State startState) {
        validStates.add(startState);
        this.startState = startState;
    }

}
