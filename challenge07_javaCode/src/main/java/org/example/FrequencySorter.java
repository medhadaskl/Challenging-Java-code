package org.example;

import java.util.*;

public class FrequencySorter {

    public static List<Integer> sortByFrequency(int[] nums) {
        //Count frequencies
        Map<Integer, Integer> freqMap = new HashMap<>();
        for (int num : nums) {
            freqMap.put(num, freqMap.getOrDefault(num, 0) + 1);
        }

        //Max-heap to sort by frequency
        PriorityQueue<Map.Entry<Integer, Integer>> maxHeap =
                new PriorityQueue<>((a, b) -> {
                    if (!Objects.equals(b.getValue(), a.getValue())) {
                        return b.getValue() - a.getValue(); // frequency descending
                    } else {
                        return b.getKey() - a.getKey();     // number descending
                    }
                });
        maxHeap.addAll(freqMap.entrySet());
        List<Integer> result = new ArrayList<>();
        while (!maxHeap.isEmpty()) {
            Map.Entry<Integer, Integer> entry = maxHeap.poll();
            int num = entry.getKey();
            int freq = entry.getValue();
            for (int i = 0; i < freq; i++) {
                result.add(num);
            }
        }

        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // number of elements -user input
        System.out.print("Enter the number of elements: ");
        int n = scanner.nextInt();

        // array elements - user input
        int[] nums = new int[n];
        System.out.println("Enter " + n + " integer elements:");
        for (int i = 0; i < n; i++) {
            nums[i] = scanner.nextInt();
        }

        // Sort by frequency and print result
        List<Integer> sortedByFreq = sortByFrequency(nums);
        System.out.println("Sorted by frequency in descending : " + sortedByFreq);

        scanner.close();
    }
}
