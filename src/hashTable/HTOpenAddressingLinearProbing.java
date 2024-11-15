package hashTable;

public class HTOpenAddressingLinearProbing {
    private Integer[] table;
    private int size;
    private int collisions;
    private int rehashCount;

    public HTOpenAddressingLinearProbing(int capacity) {
        table = new Integer[capacity];
        size = 0;
        collisions = 0;
        rehashCount = 0;
    }

    private int hash(int key) {
        return key % table.length;
    }

    public void insert(int key) {
        if (size >= table.length / 2) {
            rehash();
        }
        int index = hash(key);
        while (table[index] != null) {
            index = (index + 1) % table.length;
            collisions++;
        }
        table[index] = key;
        size++;
    }

    public boolean search(int key) {
        int index = hash(key);
        while (table[index] != null) {
            if (table[index].equals(key)) {
                return true;
            }
            index = (index + 1) % table.length;
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