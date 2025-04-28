package org.example;
import  java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
// test case of Anagram Grouping
public class AnagramGroupingTest {
    @Test
    void testAnagramGroups() {
        String[] input = {"act", "god", "cat", "dog", "tac"};
        ArrayList<ArrayList<String>> expectedGroups = new ArrayList<>();

        expectedGroups.add(new ArrayList<>(Arrays.asList("act", "cat", "tac")));
        expectedGroups.add(new ArrayList<>(Arrays.asList("god", "dog")));

        ArrayList<ArrayList<String>> actualGroups = AnagramGrouping.anagrams(input);

        // Convert groups to Set<Set<String>> for order-insensitive comparison
        Set<Set<String>> expectedSet = convertToSetOfSets(expectedGroups);
        Set<Set<String>> actualSet = convertToSetOfSets(actualGroups);

        assertEquals(expectedSet, actualSet);
    }
    private Set<Set<String>> convertToSetOfSets(ArrayList<ArrayList<String>> groups) {
        Set<Set<String>> setOfSets = new HashSet<>();
        for (List<String> group : groups) {
            setOfSets.add(new HashSet<>(group));
        }
        return setOfSets;
    }

}
