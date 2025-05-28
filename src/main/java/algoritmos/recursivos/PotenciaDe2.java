package algoritmos.recursivos;

public class PotenciaDe2 {

    //RECURSIVO
    public static int potenciaDe2Recursivo(int n) {
        if (n == 0) {
            return 1;
        }
        else {
            return 2 * potenciaDe2Recursivo(n-1);
        }
    }

    //INTERATIVO
    public static int potenciaDe2Iterativo(int n) {
        int potencia = 1;
        if (n == 0) {
            return 1;
        }
        else {
            for (int i=1; i<= n; i++) {
                potencia *= 2;
            }
            return potencia;
        }
    }

    public static void main(String[] args) {
        int n = 15;

        //Medição do tempo para versão recursiva
        long inicioRecursivo = System.nanoTime();
        int resultadoRecursivo = potenciaDe2Recursivo(n);
        long fimRecursivo = System.nanoTime();
        long tempoRecursivo = fimRecursivo - inicioRecursivo;

        //Medição do tempo para versão iterativa
        long inicioIterativo = System.nanoTime();
        int resultadoIterativo = potenciaDe2Iterativo(n);
        long fimIterativo = System.nanoTime();
        long tempoIterativo = fimIterativo - inicioIterativo;

        //Apresentação dos Resultados Medidos
        System.out.println("Resultado recursivo: " + resultadoRecursivo);
        System.out.println("Tempo recursivo (ns): " + tempoRecursivo);
        System.out.println("Resultado iterativo: " + resultadoIterativo);
        System.out.println("Tempo iterativo (ns): " + tempoIterativo);
        System.out.println("Diferença de tempo (ns): " + (tempoRecursivo - tempoIterativo));

    }

}