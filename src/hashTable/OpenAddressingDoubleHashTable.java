package hashTable;

public class OpenAddressingDoubleHashTable {
    private Integer[] table;
    private int size;
    private int collisions;
    private int rehashCount;

    public OpenAddressingDoubleHashTable(int capacity) {
        table = new Integer[capacity];
        size = 0;
        collisions = 0;
        rehashCount = 0;
    }

    private int hash1(int key) {
        return key % table.length;
    }

    private int hash2(int key) {
        return 1 + (key % (table.length - 2));
    }

    public void insert(int key) {
        if (size >= table.length / 2) {
            rehash();
        }
        int index = hash1(key);
        int stepSize = hash2(key);
        while (table[index] != null) {
            index = (index + stepSize) % table.length;
            collisions++;
        }
        table[index] = key;
        size++;
    }

    public boolean search(int key) {
        int index = hash1(key);
        int stepSize = hash2(key);
        while (table[index] != null) {
            if (table[index].equals(key)) {
                return true;
            }
            index = (index + stepSize) % table.length;
        }
        return false;
    }

    private void rehash() {
        rehashCount++;
        Integer[] oldTable = table;
        table = new Integer[oldTable.length * 2];
        size = 0;
        collisions = 0;
        for (Integer key : oldTable) {
            if (key != null) {
                insert(key);
            }
        }
    }

    public int getCollisions() {
        return collisions;
    }

    public int getRehashCount() {
        return rehashCount;
    }
}