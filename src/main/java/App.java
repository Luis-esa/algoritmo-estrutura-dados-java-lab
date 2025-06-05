import estruturas.lineares.estaticas.vetor.VetorTamanhoFixo;
import estruturas.lineares.estaticas.vetor.TDAVetor;

import java.util.Iterator;

public class App {
    public static void main(String[] args) {
        System.out.println("Iniciando testes para VetorTamanhoFixo...\n");

        //Interface TDAVetor para declarar, promovendo o polimorfismo
        TDAVetor<String> meuVetor = new VetorTamanhoFixo<>(5); // Vetor com capacidade 5

        System.out.println("Vetor recém-criado: " + meuVetor.imprimir());
        System.out.println("Tamanho: " + meuVetor.tamanho()); // Deve ser 0
        System.out.println("Está vazio? " + meuVetor.estaVazio()); // Deve ser true
        System.out.println("------------------------------------");

        // Testando adicionar(elemento) - adicionar ao final
        System.out.println("Adicionando elementos ao final:");
        meuVetor.adicionar("A");
        meuVetor.adicionar("B");
        meuVetor.adicionar("C");
        System.out.println("Vetor após adições: " + meuVetor.imprimir()); // [A, B, C]
        System.out.println("Tamanho: " + meuVetor.tamanho()); // Deve ser 3
        System.out.println("Está vazio? " + meuVetor.estaVazio()); // Deve ser false
        System.out.println("------------------------------------");

        // Testando adicionar(indice, elemento) e adicionarInicio(elemento)
        System.out.println("Testando adicionar em índice específico e no início:");
        try {
            meuVetor.adicionarInicio("INICIO"); // Adiciona no índice 0
            System.out.println("Após adicionarInicio: " + meuVetor.imprimir()); // [INICIO, A, B, C]

            meuVetor.adicionar(2, "MEIO"); // Adiciona "MEIO" no índice 2
            System.out.println("Após adicionar(2, \"MEIO\"): " + meuVetor.imprimir()); // [INICIO, A, MEIO, B, C]

            // Tentando adicionar além da capacidade (o vetor tem 5 elementos, capacidade 5)
            System.out.println("Tentando adicionar mais um elemento (deve estourar capacidade):");
            meuVetor.adicionar("FIM");
        } catch (IllegalStateException e) {
            System.err.println("ERRO ESPERADO (capacidade): " + e.getMessage());
        } catch (IndexOutOfBoundsException e) {
            System.err.println("ERRO ESPERADO (índice): " + e.getMessage());
        }
        System.out.println("Vetor atual: " + meuVetor.imprimir());
        System.out.println("Tamanho: " + meuVetor.tamanho());
        System.out.println("------------------------------------");

        // Testando obter(indice), obterIndiceDo(elemento), contem(elemento)
        System.out.println("Testando obtenção e verificação de elementos:");
        try {
            System.out.println("Elemento no índice 1: " + meuVetor.obter(1)); // Deve ser "A"
            System.out.println("Índice do elemento \"MEIO\": " + meuVetor.obterIndiceDo("MEIO")); // Deve ser 2
            System.out.println("Índice do elemento \"Z\" (não existe): " + meuVetor.obterIndiceDo("Z")); // Deve ser -1
            System.out.println("Contém \"B\"? " + meuVetor.contem("B")); // Deve ser true
            System.out.println("Contém \"Z\"? " + meuVetor.contem("Z")); // Deve ser false
            System.out.println("Tentando obter elemento em índice inválido (ex: 10):");
            System.out.println(meuVetor.obter(10));
        } catch (IndexOutOfBoundsException e) {
            System.err.println("ERRO ESPERADO (índice): " + e.getMessage());
        }
        System.out.println("------------------------------------");

        // Testando remoções: remover(indice), removerElemento, removerInicio, removerFim
        System.out.println("Testando remoções:");
        // Vetor atual: [INICIO, A, MEIO, B, C]
        try {
            System.out.println("Removendo do início (removerInicio):");
            meuVetor.removerInicio();
            System.out.println("Vetor após removerInicio: " + meuVetor.imprimir()); // [A, MEIO, B, C]

            System.out.println("Removendo do fim (removerFim):");
            meuVetor.removerFim();
            System.out.println("Vetor após removerFim: " + meuVetor.imprimir()); // [A, MEIO, B]

            System.out.println("Removendo elemento \"MEIO\" (removerElemento):");
            meuVetor.removerElemento("MEIO");
            System.out.println("Vetor após removerElemento(\"MEIO\"): " + meuVetor.imprimir()); // [A, B]

            System.out.println("Removendo do índice 0 (remover(0)):");
            String removido = meuVetor.remover(0);
            System.out.println("Elemento removido: " + removido); // Deve ser "A"
            System.out.println("Vetor após remover(0): " + meuVetor.imprimir()); // [B]
        } catch (IndexOutOfBoundsException | IllegalStateException e) {
            System.err.println("ERRO DURANTE REMOÇÃO: " + e.getMessage());
        }
        System.out.println("Tamanho após remoções: " + meuVetor.tamanho()); // Deve ser 1
        System.out.println("------------------------------------");

        // Testando Iterator
        System.out.println("Testando o Iterator:");
        // Adicionando mais alguns elementos para o iterator
        meuVetor.limpar(); // Limpa o vetor [B]
        System.out.println("Vetor após limpar para teste do iterator: " + meuVetor.imprimir() + ", Tamanho: " + meuVetor.tamanho());
        meuVetor.adicionar("Alfa");
        meuVetor.adicionar("Beta");
        meuVetor.adicionar("Gama");
        System.out.println("Vetor para iterar: " + meuVetor.imprimir());

        Iterator<String> it = meuVetor.iterator();
        System.out.print("Iterando: ");
        while (it.hasNext()) {
            String val = it.next();
            System.out.print(val + " ");
        }
        System.out.println("\nFim da iteração.");

        // Testando remove() do iterator (deve lançar UnsupportedOperationException)
        try {
            System.out.println("Tentando it.remove():");
            it = meuVetor.iterator(); // Novo iterador
            if (it.hasNext()) {
                it.next(); // Avança para o primeiro elemento
                it.remove(); // Tenta remover
            }
        } catch (UnsupportedOperationException e) {
            System.err.println("ERRO ESPERADO (iterator.remove): " + e.getMessage());
        }
        System.out.println("------------------------------------");

        // Testando limpar
        System.out.println("Testando limpar():");
        meuVetor.limpar();
        System.out.println("Vetor após limpar: " + meuVetor.imprimir()); // []
        System.out.println("Tamanho após limpar: " + meuVetor.tamanho()); // 0
        System.out.println("Está vazio após limpar? " + meuVetor.estaVazio()); // true
        System.out.println("------------------------------------");

        // Testando adicionar em vetor vazio após limpar, e adicionar em índice específico
        System.out.println("Testando adicionar em vetor vazio e em índice específico:");
        try {
            meuVetor.adicionar(0, "ElementoUnico");
            System.out.println("Após adicionar(0, \"ElementoUnico\"): " + meuVetor.imprimir()); // [ElementoUnico]
            meuVetor.adicionar(0, "NovoInicio");
            System.out.println("Após adicionar(0, \"NovoInicio\"): " + meuVetor.imprimir()); // [NovoInicio, ElementoUnico]
            // Testando adicionar em índice que é o tamanho atual
            meuVetor.adicionar(meuVetor.tamanho(), "NovoFim");
            System.out.println("Após adicionar no final por índice: " + meuVetor.imprimir()); // [NovoInicio, ElementoUnico, NovoFim]

            System.out.println("Tentando adicionar em índice inválido (ex: 5 em vetor de tamanho 3):");
            meuVetor.adicionar(5, "Invalido");

        } catch (IndexOutOfBoundsException e) {
            System.err.println("ERRO ESPERADO (índice): " + e.getMessage());
        } catch (IllegalStateException e) {
            System.err.println("ERRO ESPERADO (capacidade): " + e.getMessage());
        }
        System.out.println("------------------------------------");
        System.out.println("Fim dos testes.");
    }
}