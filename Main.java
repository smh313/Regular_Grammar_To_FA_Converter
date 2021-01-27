
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
    
    public static void main(String[] args) {
        
        Scanner input = new Scanner(System.in);
        
        ArrayList<String> lines = new ArrayList<>();
        String line;
//        while (!(line = input.nextLine()).isEmpty()) {
//            lines.add(line);
//        }

        GrammarParser gp = new GrammarParser();
        FA fa = new FA();
        
        String s = "S->abA";
        lines.add(s);
        s = "A->baA";
        lines.add(s);
        s = "A->acB";
        lines.add(s);
//        s = "B->ac";
//        lines.add(s);

        ArrayList<State> states = new ArrayList<>();
        State s1 = new State("q1");
        State s2 = new State("q2");
        State s3 = new State("q3");
        
        states.add(s1);
        states.add(s2);
        states.add(s3);
        
      
        

//        gp.ParseMultipleLines(lines);
//        gp.toString();
//
//        fa.convertPRsToTransition(gp.getPrs());
    }
    
}
