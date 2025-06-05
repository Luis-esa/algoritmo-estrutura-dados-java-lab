package estruturas.lineares.estaticas.vetor;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException; // Import para o Iterator
import java.util.Objects;

public class VetorTamanhoFixo<T> implements TDAVetor<T>, Iterable<T> {

    private int tamanho;    // Número atual de elementos no vetor
    private T[] elementos;  // Array subjacente para armazenar os elementos

    /**
     * Construtor da classe VetorTamanhoFixo.
     * @param capacidade A capacidade máxima do vetor.
     * @throws IllegalArgumentException se a capacidade for negativa.
     */
    @SuppressWarnings("unchecked")
    public VetorTamanhoFixo(int capacidade) {
        if (capacidade < 0) {
            throw new IllegalArgumentException("A capacidade do vetor não pode ser negativa.");
        }
        this.tamanho = 0;
        this.elementos = (T[]) new Object[capacidade]; // Criação do array genérico
    }

    @Override
    public int tamanho() {
        return this.tamanho;
    }

    @Override
    public boolean estaVazio() {
        return this.tamanho == 0;
    }

    @Override
    public void verificarIndice(int indice, int minimo, int maximo) {
        if (indice < minimo || indice > maximo) {
            throw new IndexOutOfBoundsException(
                    String.format("Índice inválido: %d. O índice deve estar entre %d e %d (inclusivo). Tamanho atual: %d", indice, minimo, maximo, this.tamanho)
            );
        }
    }

    @Override
    public void verificarCapacidade() {
        if (this.tamanho == this.elementos.length) {
            throw new IllegalStateException("A capacidade máxima do vetor foi atingida: " + this.elementos.length);
        }
    }

    @Override
    public int obterIndiceDo(T elemento) {
        for (int i = 0; i < this.tamanho; i++) {
            if (Objects.equals(elemento, this.elementos[i])) { // Usa Objects.equals para tratar nulls corretamente
                return i;
            }
        }
        return -1; // Elemento não encontrado
    }

    @Override
    public boolean contem(T elemento) {
        return obterIndiceDo(elemento) != -1;
    }

    /**
     * Remove todos os elementos do vetor, mantendo sua capacidade.
     * Os elementos são definidos como null para permitir a coleta pelo Garbage Collector.
     */
    @Override
    public void limpar() {
        for (int i = 0; i < this.tamanho; i++) {
            this.elementos[i] = null; // Limpa a referência para o objeto
        }
        this.tamanho = 0; // Reseta o tamanho
    }

    @Override
    public T obter(int indice) throws IndexOutOfBoundsException {
        // Verifica se o índice está dentro dos limites dos elementos existentes (0 a tamanho-1)
        verificarIndice(indice, 0, this.tamanho - 1);
        return this.elementos[indice];
    }

    @Override
    public void atualizar(int indice, T elemento) throws IndexOutOfBoundsException {
        // Verifica se o índice está dentro dos limites dos elementos existentes (0 a tamanho-1)
        verificarIndice(indice, 0, this.tamanho - 1);
        this.elementos[indice] = elemento;
    }

    /**
     * Adiciona o elemento no fim do vetor.
     */
    @Override
    public void adicionar(T elemento) {
        verificarCapacidade(); // Lança exceção se o vetor estiver cheio
        this.elementos[this.tamanho] = elemento;
        this.tamanho++;
    }

    // [3] Implementação do método adicionar(int indice, T elemento)
    /**
     * Adiciona o elemento em um índice específico do vetor.
     * Os elementos a partir deste índice são deslocados para a direita.
     * @param indice O índice onde o elemento será adicionado.
     * @param elemento O elemento a ser adicionado.
     * @throws IndexOutOfBoundsException se o índice for inválido.
     * @throws IllegalStateException se o vetor estiver cheio.
     */
    @Override
    public void adicionar(int indice, T elemento) throws IndexOutOfBoundsException {
        verificarCapacidade();
        // Permite adicionar no índice 'this.tamanho' (final do vetor)
        verificarIndice(indice, 0, this.tamanho);

        // Se o índice não for o final do vetor, desloca os elementos para a direita
        if (indice < this.tamanho) {
            // System.arraycopy(origem, posOrigem, destino, posDestino, quantidade)
            System.arraycopy(this.elementos, indice, this.elementos, indice + 1, this.tamanho - indice);
        }
        this.elementos[indice] = elemento;
        this.tamanho++;
    }

    // [4] Implementação do método adicionarInicio(T elemento)
    /**
     * Adiciona o elemento no início do vetor.
     * @param elemento O elemento a ser adicionado.
     * @throws IllegalStateException se o vetor estiver cheio.
     */
    @Override
    public void adicionarInicio(T elemento) {
        // Reutiliza o método adicionar(indice, elemento) com indice 0
        adicionar(0, elemento);
    }

    // [5] Implementação do método remover(int indice)
    /**
     * Remove e retorna o elemento do índice passado por parâmetro.
     * Os elementos subsequentes são deslocados para a esquerda.
     * @param indice O índice do elemento a ser removido.
     * @return O elemento removido.
     * @throws IndexOutOfBoundsException se o índice for inválido.
     */
    @Override
    public T remover(int indice) throws IndexOutOfBoundsException {
        if (estaVazio()) {
            throw new IndexOutOfBoundsException("O vetor está vazio. Não é possível remover elementos.");
        }
        // Verifica se o índice está dentro dos limites dos elementos existentes (0 a tamanho-1)
        verificarIndice(indice, 0, this.tamanho - 1);

        T elementoRemovido = this.elementos[indice];

        // Se o elemento removido não for o último, desloca os elementos para a esquerda
        int numElementosADeslocar = this.tamanho - 1 - indice;
        if (numElementosADeslocar > 0) {
            System.arraycopy(this.elementos, indice + 1, this.elementos, indice, numElementosADeslocar);
        }

        this.tamanho--;
        this.elementos[this.tamanho] = null; // Limpa a última posição antiga para o GC

        return elementoRemovido;
    }

    // [6] Implementação do método removerElemento(T elemento)
    /**
     * Remove a primeira ocorrência do elemento passado por parâmetro do vetor.
     * @param elemento O elemento a ser removido.
     */
    @Override
    public void removerElemento(T elemento) {
        int indice = obterIndiceDo(elemento);
        if (indice != -1) {
            remover(indice); // Reutiliza o método remover(indice)
        }
        // Se o elemento não for encontrado, nenhuma ação é tomada.
    }

    // [7] Implementação do método removerInicio()
    /**
     * Remove o primeiro elemento do vetor.
     * @throws IndexOutOfBoundsException se o vetor estiver vazio.
     */
    @Override
    public void removerInicio() {
        if (estaVazio()) {
            // A chamada a remover(0) já lançaria IndexOutOfBoundsException se o vetor estivesse vazio
            // devido à verificação de índice (0 a tamanho-1).
            // Mas uma mensagem mais específica pode ser útil aqui, embora redundante com a de remover(0).
            throw new IllegalStateException("O vetor está vazio. Não é possível remover o primeiro elemento.");
        }
        remover(0); // Reutiliza o método remover(indice)
        // O valor retornado por remover(0) é ignorado, pois o método é void.
    }

    // [8] Implementação do método removerFim()
    /**
     * Remove o último elemento do vetor.
     * @throws IllegalStateException se o vetor estiver vazio.
     */
    @Override
    public void removerFim() {
        if (estaVazio()) {
            throw new IllegalStateException("O vetor está vazio. Não é possível remover o último elemento.");
        }
        this.tamanho--;
        this.elementos[this.tamanho] = null; // Limpa a referência para o GC
    }

    @Override
    public String imprimir() {
        if (this.tamanho == 0) {
            return "[]";
        }
        // Usa Arrays.copyOf para criar um array com apenas os elementos válidos
        // e depois Arrays.toString para formatá-lo.
        return Arrays.toString(Arrays.copyOf(this.elementos, this.tamanho));
    }

    // [9] Implementação do método iterator()
    /**
     * Retorna um iterador que possibilita percorrer cada elemento da coleção no vetor.
     * @return Um Iterator para os elementos do vetor.
     */
    @Override
    public Iterator<T> iterator() {
        return new VetorIterator();
    }

    // Classe interna privada para implementar o Iterator
    private class VetorIterator implements Iterator<T> {
        private int cursor; // Índice do próximo elemento a ser retornado

        public VetorIterator() {
            this.cursor = 0;
        }

        /**
         * Verifica se há um próximo elemento na iteração.
         * @return true se houver mais elementos, false caso contrário.
         */
        @Override
        public boolean hasNext() {
            return this.cursor < VetorTamanhoFixo.this.tamanho;
        }

        /**
         * Retorna o próximo elemento na iteração.
         * @return O próximo elemento.
         * @throws NoSuchElementException se não houver mais elementos.
         */
        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Não há mais elementos para iterar.");
            }
            return VetorTamanhoFixo.this.elementos[this.cursor++];
        }

        /**
         * A operação de remoção não é suportada por este iterador.
         * @throws UnsupportedOperationException sempre que chamado.
         */
        @Override
        public void remove() {
            // A remoção através do iterador é opcional.
            // Para vetores de tamanho fixo, a remoção pode ser complexa de sincronizar com o cursor.
            // Vamos manter simples e não suportar por enquanto.
            throw new UnsupportedOperationException("A operação de remoção não é suportada por este iterador.");
        }
    }
}