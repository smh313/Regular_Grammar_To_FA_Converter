
import GrammarToNFa.FA;
import GrammarToNFa.GrammarParser;
import GrammarToNFa.State;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author SMH We implemented an algorithm for converting a regular grammar RG
 * to FA
 */
public class Main {

    public static ArrayList<String> StartCreatingFA() {
        Scanner input = new Scanner(System.in);
        ArrayList<String> lines = new ArrayList<>();
        String line;
        do {
            line = input.nextLine();
            lines.add(line);
        } while (!line.isEmpty());
        return lines;
    }

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        ArrayList<String> lines = new ArrayList<>();
        String line;

        String startV = "S";

        String s = "S->Aab";
        lines.add(s);
        s = "A->Acd";
        lines.add(s);
        s = "A->Ba";
        lines.add(s);
        s = "B->ef";
        lines.add(s);

//        String s = "S->abA";
//        lines.add(s);
//        s = "A->cdA";
//        lines.add(s);
//        s = "A->eB";
//        lines.add(s);
//        s = "B->ac";
//        lines.add(s);
//        ArrayList<State> states = new ArrayList<>();
//        State s1 = new State("q1");
//        State s2 = new State("q2");
//        State s3 = new State("q3");
//        
//        states.add(s1);
//        states.add(s2);
//        states.add(s3);
//        ArrayList<State> qs = new ArrayList<>();
//        State q = new State("q" + 1);
//        State lastq = q;
//        qs.add(q);
//        System.out.println("q=" + q.getName());
//        System.out.println("lastq=" + lastq.getName());
//        System.out.println("qs=" + qs.get(0).getName());
//
//        q = new State("q" + 2);
//        System.out.println("q=" + q.getName());
//        System.out.println("lastq=" + lastq.getName());
//        System.out.println("qs=" + qs.get(0).getName());
        GrammarParser gp = new GrammarParser();
        FA fa = new FA();

        gp.ParseMultipleLines(lines);
        gp.toString();

        fa.setStartState(new State(startV));
        fa.convertPRsToTransition(gp.getPrs());
//        System.out.println(fa.toString());
//        FA fa2 = fa.reverse();
//        System.out.println(fa2.toString());
//        fa.reverse();
        FA fa3 = fa.reverseAndSortTransitions();
        System.out.println(fa3.toString());

        String test = "efacdcdcdab";
        System.out.println(fa3.acceptor(test));

    }

}
