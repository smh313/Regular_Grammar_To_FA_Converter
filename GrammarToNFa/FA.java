package GrammarToNFa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author No1
 */
public class FA implements Cloneable {

    ArrayList<Transition> transitions;
    ArrayList<State> validStates;
    ArrayList<State> dummyStates;
    ArrayList<Character> terminals;
    int tCount = 0; //state counter
    State startState, finalState, trapState;
    boolean isPrime = false;
    boolean isRL = true;
    

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

    public Object clone() throws
            CloneNotSupportedException {
        return super.clone();
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
        boolean result = nextTrans(startState, s, iterator);

        if (result) {
            System.out.println("Accepted");
        } else {
            System.out.println("Rejected");
        }
        return result;
    }

    public boolean accept(FA fa, String s) {
        boolean result = fa.acceptor(s);
        result = !result;
        return false;
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
//                System.out.println("q" + startState.getName() + " is " + startState.name);
                validStates.add(startState);
            } else {
                startState = findStateFromPR(p, true);
//                System.out.println("q" + startState.getName() + " has been created" + startState.name);
            }
        }
    }

    public State getDumpState(int index) {// 0 => last q created (qn) , 1=> pre-last(qn-1)
        return dummyStates.get(dummyStates.size() - 1 - index);
    }

    public void addDumpState(State q) {// 0 => last q created (qn) , 1=> pre-last(qn-1)
        dummyStates.add(q);
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
        dummyStates = new ArrayList<>();
        finalState = new State("qf");
        trapState = new State("trap");
    }

    public FA convertNfaToDfa() {

        FA dfa = new FA();
        try {
            dfa = (FA) this.clone();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(FA.class.getName()).log(Level.SEVERE, null, ex);
        }

        ArrayList<Transition> trs = new ArrayList<>();
        ArrayList<Transition> alltrs = new ArrayList<>();
        alltrs = (ArrayList<Transition>) transitions.clone();
        dfa.transitions = alltrs;
        dfa.terminals = new ArrayList<>();

        for (Transition t : transitions) {
            if (!dfa.terminals.contains(t.getTerminal())) {
                dfa.terminals.add(t.getTerminal());
            }
            if (t.getDestState().getName().equals(finalState.getName())) {
                t.getDestState().setFinal(true);
            } else {
                t.getDestState().setFinal(false);
            }
        }

        for (State s : validStates) {
            trs = findTransitionWithSrc(s);
            ArrayList<Character> trsChars = new ArrayList<>();
            for (Transition tr : trs) {
                trsChars.add(tr.getTerminal());
            }
            for (char ch : dfa.terminals) {
                if (!trsChars.contains(ch)) {
                    Transition t = new Transition(s, trapState, ch);
                    dfa.transitions.add(t);
                }
            }
        }
        for (State s : dummyStates) {
            trs = findTransitionWithSrc(s);
            ArrayList<Character> trsChars = new ArrayList<>();
            for (Transition tr : trs) {
                trsChars.add(tr.getTerminal());
            }
            for (char ch : dfa.terminals) {
                if (!trsChars.contains(ch)) {
                    Transition t = new Transition(s, trapState, ch);
                    dfa.transitions.add(t);
                }
            }
        }

        return dfa;
    }

    public FA convertDfaToPrime() {
        isPrime = true;
        return null;
    }

    @Override
    public String toString() {
        if (!isPrime) {
            return "FA{" + "transitions=" + transitions + ",\n" + '{' + startState.getName() + '}' + " is initial & " + '{' + finalState.getName() + '}' + " is final. ";
        } else {
            return "FA{" + "transitions=" + transitions + ",\n" + '{' + startState.getName() + '}' + " is initial & " + '{' + finalState.getName() + ",trap" + '}' + " is final. ";

        }
    }

    public ArrayList<State> getValidStates() {
        return validStates;
    }

    public void setValidStates(ArrayList<State> validStates) {
        this.validStates = validStates;
    }

    public ArrayList<State> getDummyStates() {
        return dummyStates;
    }

    public void setDummyStates(ArrayList<State> dummyStates) {
        this.dummyStates = dummyStates;
    }

    public ArrayList<Character> getTerminals() {
        return terminals;
    }

    public void setTerminals(ArrayList<Character> terminals) {
        this.terminals = terminals;
    }

    public int gettCount() {
        return tCount;
    }

    public void settCount(int tCount) {
        this.tCount = tCount;
    }

    public State getTrapState() {
        return trapState;
    }

    public void setTrapState(State trapState) {
        this.trapState = trapState;
    }

    public boolean isIsPrime() {
        return isPrime;
    }

    public void setIsPrime(boolean isPrime) {
        this.isPrime = isPrime;
    }

    public ArrayList<Transition> getTransitions() {
        return transitions;
    }

    public void setTransitions(ArrayList<Transition> transitions) {
        this.transitions = transitions;
    }

    public State getFinalState() {
        return finalState;
    }

    public void setStartState(State startState) {
        validStates.add(startState);
        this.startState = startState;
    }

    public State getStartState() {
        return startState;
    }

    public boolean isIsRL() {
        return isRL;
    }

    public void setIsRL(boolean isRL) {
        this.isRL = isRL;
    }

    public void setFinalState(State finalState) {
        this.finalState = finalState;
    }

    
}
