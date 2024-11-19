package hashTable;

import java.util.LinkedList;

public class ClosedAddressingHashTable {
    private LinkedList<Integer>[] table;
    private int size;
    private int collisions;
    private final double loadFactorLimit = 0.75;  // Limite do fator de carga

    @SuppressWarnings("unchecked")
    public ClosedAddressingHashTable(int capacity) {
        table = new LinkedList[capacity];
        for (int i = 0; i < capacity; i++) {
            table[i] = new LinkedList<>();
        }
        size = 0;
        collisions = 0;
    }

    private int hash(int key) {
        return key % table.length;
    }

    public void insert(int key) {
        // Redimensiona se o fator de carga ultrapassar o limite
        if (getLoadFactor() >= loadFactorLimit) {
            resize();
        }

        int index = hash(key);
        if (!table[index].isEmpty()) {
            collisions++;
        }
        table[index].add(key);
        size++;
    }

    public boolean search(int key) {
        int index = hash(key);
        return table[index].contains(key);
    }

    public int getCollisions() {
        return this.collisions;
    }

    public int getSize() {
        return this.size;
    }

    public double getLoadFactor() {
        return (double) size / table.length;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        int newCapacity = table.length * 2;
        LinkedList<Integer>[] newTable = new LinkedList[newCapacity];
        for (int i = 0; i < newCapacity; i++) {
            newTable[i] = new LinkedList<>();
        }

        // Reinsere todos os elementos na nova tabela
        for (LinkedList<Integer> bucket : table) {
            for (int key : bucket) {
                int newIndex = key % newCapacity;
                newTable[newIndex].add(key);
            }
        }

        table = newTable;  // Atualiza a referência da tabela para a nova tabela
    }
}
