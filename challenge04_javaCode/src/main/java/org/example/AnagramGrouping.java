package org.example;

import java.util.*;

public class AnagramGrouping {
    static final int MAX_CHAR = 26;

    // Function to generate hash of word s
    static String getHash(String s) {
        StringBuilder hash = new StringBuilder();
        int[] freq = new int[MAX_CHAR];

        // Count frequency of each character
        for (char ch : s.toCharArray()) {
            freq[ch - 'a']++;
        }

        // Append the frequency to construct the hash
        for (int i = 0; i < MAX_CHAR; i++) {
            hash.append(freq[i]);
            hash.append("$");
        }
        return hash.toString();
    }
    static ArrayList<ArrayList<String>> anagrams(String[] arr) {
        ArrayList<ArrayList<String>> res = new ArrayList<>();
        Map<String, Integer> mp = new HashMap<>();

        for (int i = 0; i < arr.length; i++) {
            String key = getHash(arr[i]);

            // If key is not present in the hash map, add an empty List in the result and store the index in hash map
            if (!mp.containsKey(key)) {
                mp.put(key, res.size());
                res.add(new ArrayList<>());
            }

            res.get(mp.get(key)).add(arr[i]);
        }

        return res;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of strings: ");
        int n = scanner.nextInt();

        String[] arr = new String[n+1];
        System.out.println("Enter the strings:");
        for (int i = 0; i <=n; i++) {
            arr[i] = scanner.nextLine();
        }


        ArrayList<ArrayList<String>> res = anagrams(arr);

        for (List<String> group : res) {
            for (String word : group) {
                System.out.print(word + " ");
            }
            System.out.println();
        }
    }

}
