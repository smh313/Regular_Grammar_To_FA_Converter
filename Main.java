
import GrammarToNFa.FA;
import GrammarToNFa.GrammarParser;
import GrammarToNFa.ProductionRule;
import GrammarToNFa.State;
import GrammarToNFa.Transition;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SMH We implemented an algorithm for converting a regular grammar RG
 * to FA
 */
public class Main {

    public static String inputStart(Scanner input) {
        String s = input.next();
        return s;
    }

    public static ArrayList<String> inputLines(Scanner input) {
        ArrayList<String> lines = new ArrayList<>();
        String line;
        line = input.nextLine();
        line = input.nextLine();
        do {
            lines.add(line);
            line = input.nextLine();
        } while (!line.isEmpty());
        return lines;
    }

    public static void StartProcess(Scanner input, ArrayList<String> lines) {
        System.out.println("Enter Your Regular grammar:");

        String startV = inputStart(input);
        lines = inputLines(input);

        GrammarParser gp = new GrammarParser();
        FA fa = new FA();

        gp.ParseMultipleLines(lines);
//        gp.toString();

        for (ProductionRule pr : gp.getPrs()) {
            if (pr.isHasDestV()) {
                fa.setIsRL(pr.isIsRL());
                break;
            }
        }
        fa.setStartState(new State(startV));
        fa.convertPRsToTransition(gp.getPrs());
        if (!fa.isIsRL()) {
            fa = fa.reverseAndSortTransitions();
        }
        System.out.println("\nNFA for L is:");
        System.out.println(fa.toString());

        System.out.println("Enter your string:");
        String lString = input.next();
        fa.acceptor(lString);

        FA originalFa = fa;

        System.out.println("\nDFA for L is:");
        FA dfa = fa.convertNfaToDfa();
        System.out.println(dfa.toString());

        System.out.println("\nFA reverse of L is as below:");
        FA faReverse = fa.reverseAndSortTransitions();
        System.out.println(faReverse.toString());

        System.out.println("Enter your *reversed* string :");
        String lreversed = input.next();
        faReverse.acceptor(lreversed);

        System.out.println("\nFA Prime is as below:");
        FA dfaPrime = dfa.convertNfaToDfa();
        System.out.println(dfaPrime.toString());

//        System.out.println("Prime string result:");
//        dfaPrime.accept(fa, lString);

    }

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        ArrayList<String> lines = new ArrayList<>();

        StartProcess(input, lines);
    }

}
