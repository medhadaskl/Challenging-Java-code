package org.example;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
// test case of frequency Sorter
public class FrequencySorterTest {

    @Test
    public void testSortByFrequency() {
        int[] input = {4, 5, 6, 5, 4, 3, 2, 2, 2, 4};
        List<Integer> actual = FrequencySorter.sortByFrequency(input);

        // Count the frequency of each element
        Map<Integer, Integer> freq = new HashMap<>();
        for (int num : input) {
            freq.put(num, freq.getOrDefault(num, 0) + 1);
        }

        //check the frequency order in actual result
        for (int i = 0; i < actual.size(); ) {
            int val = actual.get(i);
            int count = freq.get(val);
            for (int j = 0; j < count; j++) {
                assertEquals(val, actual.get(i + j));
            }
            i += count;
        }
    }

    @Test
    public void testAllSameFrequency() {
        int[] input = {8,9,0};
        List<Integer> expected = Arrays.asList(8,9,0);
        // all have same freq
        List<Integer> actual = FrequencySorter.sortByFrequency(input);
        Collections.sort(actual); // Sorting both to match order
        Collections.sort(expected);

        assertEquals(expected, actual, "All elements have the same frequency, so order may vary.");
    }

    @Test
    public void testEmptyArray() {
        int[] input = {};
        List<Integer> expected = new ArrayList<>();

        List<Integer> actual = FrequencySorter.sortByFrequency(input);
        assertEquals(expected, actual, "Empty input should return empty list.");
    }
}
