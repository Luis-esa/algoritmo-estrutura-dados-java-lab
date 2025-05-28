package algoritmos.recursivos;

import java.util.Arrays;

public class Fibonacci {

    //RECURSIVO
    public static int fibonacciRecursivo(int n) {
        if ((n == 0) || (n == 1)) {
            return n;
        }
        else {
            return fibonacciRecursivo(n-1) + fibonacciRecursivo(n-2);
        }
    }

    //ITERATIVO
    public static int fibonacciIterativo(int n) {
        int fibonacci = 0;
        int numAnterior = 0;
        int numPosterior = 1;
        if ((n == 0) || (n == 1)) {
            return n;
        }
        else{
            for (int i=2 ; i<=n ; i++) {
                fibonacci = numAnterior + numPosterior;
                numAnterior = numPosterior;
                numPosterior = fibonacci;
            }
            return fibonacci;
        }
    }

    // 3. MÉTODO RECURSIVO COM MEMOIZAÇÃO
    public static int fibonacciRecursivoMemorizado(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("O índice de Fibonacci não pode ser negativo.");
        }
        int[] memo = new int[n + 1];
        Arrays.fill(memo, -1);
        return fibonacciRecursivoMemorizadoAux(n, memo);
    }

    private static int fibonacciRecursivoMemorizadoAux(int n, int[] memo) {
        if ((n == 0) || (n == 1)) {
            memo[n] = n;
            return n;
        }
        if (memo[n] != -1) {
            return memo[n];
        }
        memo[n] = fibonacciRecursivoMemorizadoAux(n - 1, memo) + fibonacciRecursivoMemorizadoAux(n - 2, memo);
        return memo[n];
    }


    //MAIN
    public static void main(String[] args) {
        int n = 16; // Usar o mesmo 'n' para todos os métodos para comparação direta

        //Medição do tempo para versão recursiva (ingênua)
        long inicioRecursivo = System.nanoTime();
        int resultadoRecursivo = fibonacciRecursivo(n);
        long fimRecursivo = System.nanoTime();
        long tempoRecursivo = fimRecursivo - inicioRecursivo;

        //Medição do tempo para versão iterativa
        long inicioIterativo = System.nanoTime();
        int resultadoIterativo = fibonacciIterativo(n);
        long fimIterativo = System.nanoTime();
        long tempoIterativo = fimIterativo - inicioIterativo;

        //Medição do tempo para versão recursiva memorizada
        long inicioRecursivoMemorizado = System.nanoTime();
        int resultadoRecursivoMemorizado = fibonacciRecursivoMemorizado(n);
        long fimRecursivoMemorizado = System.nanoTime();
        long tempoRecursivoMemorizado = fimRecursivoMemorizado - inicioRecursivoMemorizado;

        //Apresentação dos Resultados Medidos
        System.out.println("--- Fibonacci para n = " + n + " ---");

        System.out.println("Resultado recursivo (ingênuo): " + resultadoRecursivo);
        System.out.println("Tempo recursivo (ingênuo) (ns): " + tempoRecursivo);

        System.out.println("Resultado iterativo: " + resultadoIterativo);
        System.out.println("Tempo iterativo (ns): " + tempoIterativo);

        System.out.println("Resultado recursivo memorizado: " + resultadoRecursivoMemorizado);
        System.out.println("Tempo recursivo memorizado (ns): " + tempoRecursivoMemorizado);

        System.out.println("\n--- Diferenças de Tempo (para n=" + n + ") ---");
        System.out.println("Diferença (Recursivo Ingênuo vs. Iterativo) (ns): " +
                (tempoRecursivo - tempoIterativo) +
                " (positivo: Ingênuo mais lento)");

        System.out.println("Diferença (Recursivo Ingênuo vs. Recursivo Memorizado) (ns): " +
                (tempoRecursivo - tempoRecursivoMemorizado) +
                " (positivo: Ingênuo mais lento)");

        System.out.println("Diferença (Iterativo vs. Recursivo Memorizado) (ns): " +
                (tempoIterativo - tempoRecursivoMemorizado) +
                " (positivo: Iterativo mais lento, negativo: Memorizado mais lento)");
    }
}