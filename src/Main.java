
import algorithms.Algorithms;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException, IOException {

        String alg;
        String path;
        float mutatePercent = 0f;
        int nPop = 0;
        int execTime = 0;

        if (args.length == 2) {
            alg = args[0];
            path = args[1];
            Algorithms.valueOf(alg).run(path, mutatePercent, nPop, execTime);

        } else if (args.length == 4) {
            alg = args[0];
            path = args[1];
            mutatePercent = Float.parseFloat(args[2]);
            nPop = Integer.parseInt(args[3]);
            execTime = Integer.parseInt(args[4]);
            Algorithms.valueOf(alg).run(path, mutatePercent, nPop, execTime);

        } else {
            System.out.println("Informe o algoritmo <greedy, genetic, psr> e caminho apra o grafo de entrada!");

        }
    }

}
