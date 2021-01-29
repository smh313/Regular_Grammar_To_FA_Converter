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
public class Transition implements Cloneable {

    private State srcState;
    private State destState;
    private char Terminal;

    public Transition(State srcState, State destState, char Terminal) {
        this.setSrcState(srcState);
        this.setDestState(destState);
        this.setTerminal(Terminal);
    }

    public Object clone() throws
            CloneNotSupportedException {
        return super.clone();
    }

    public Transition() {
    }

    @Override
    public String toString() {
        return "\n{ " + "" + srcState.getName() + " --" + Terminal + "-> " + destState.getName() + " }";
    }

    //getters & setters
    public State getSrcState() {
        return srcState;
    }

    public void setSrcState(State srcState) {
        this.srcState = srcState;
    }

    public State getDestState() {
        return destState;
    }

    public void setDestState(State destState) {
        this.destState = destState;
    }

    public char getTerminal() {
        return Terminal;
    }

    public void setTerminal(char Terminal) {
        this.Terminal = Terminal;
    }

}
