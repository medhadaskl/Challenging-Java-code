package org.example;

import java.util.*;

public class LPU_Cache <K, V> extends LinkedHashMap<K, V> {
    private final int capacity;

    public LPU_Cache(int capacity) {
        super(capacity, 0.75f, true);
        this.capacity = capacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > capacity;
    }

    @Override
    public V get(Object key) {
        return super.getOrDefault(key, null);
    }

    public V put(K key, V value) {
        return super.put(key, value);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter LRU Cache capacity: ");
        int capacity = scanner.nextInt();
        LPU_Cache<Integer, String> cache = new LPU_Cache<>(capacity);

        while (true) {
            System.out.println("\nChoose operation: 1) Put  2) Get  3) Print Cache  4) Exit");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter key (integer): ");
                    int keyPut = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter value (string): ");
                    String valuePut = scanner.nextLine();
                    cache.put(keyPut, valuePut);
                    System.out.println("Put (" + keyPut + ", " + valuePut + ")");
                    break;
                case 2:
                    System.out.print("Enter key to get: ");
                    int keyGet = scanner.nextInt();
                    String result = cache.get(keyGet);
                    if (result != null)
                        System.out.println("Value = " + result);
                    else
                        System.out.println("Key not found");
                    break;
                case 3:
                    System.out.println("Current Cache: " + cache);
                    break;
                case 4:
                    System.out.println("Exiting");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}
