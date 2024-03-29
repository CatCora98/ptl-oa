package com.ptl.exercise.ig.magnitGlobal;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Given integer array and splits k
 * Split the array into k sub-arrays
 * find the max number in each sub-array and sum all the max number
 * find the smallest sum
 */
public class Algorithms_CompanyMarketingCampaigns {

    public static void main(String[] args) {
        List<Integer> costs1 = Arrays.asList(3000, 1000, 4000);
        int weeks10 = 1;
        System.out.println(minimumWeeklyInput(costs1, weeks10)); // 4000
        int weeks11 = 2;
        System.out.println(minimumWeeklyInput(costs1, weeks11)); // 7000
        int weeks12 = 3;
        System.out.println(minimumWeeklyInput(costs1, weeks12)); // 8000
    }

    //TRY IF THIS SOLUTION WILL PASS ALL THE TESTS.
    public static int minimumWeeklyInput(List<Integer> costs, int weeks) {
        int len = costs.size();

        // Initialize a 2D array for dynamic programming
        int totalSum = costs.stream().mapToInt(o -> o).sum();

        int[][] dp = new int[len][weeks + 1];
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < weeks + 1; j++) {
                dp[i][j] = Integer.MAX_VALUE - totalSum; //should be a big value, not sure how big it is
            }
        }

        // Calculate base cases
        dp[0][1] = costs.get(0);
        for (int i = 1; i < len; i++) {
            dp[i][1] = findSum(costs, 0, i + 1);
        }

        // Dynamic programming to fill the rest of the array
        for (int i = 1; i < len; i++) {
            for (int j = 2; j <= weeks; j++) {
                for (int k = 0; k < i; k++) {
                    dp[i][j] = Math.min(dp[i][j], dp[k][j - 1] + findSum(costs, k + 1, i + 1));
                }
            }
        }

        // Result: The minimum weights of each group
        return dp[len - 1][weeks];
    }

    private static int findSum(List<Integer> nums, int start, int end) {
        int sum = nums.get(start);
        for (int i = start; i < end; i++) {
            if (nums.get(i) > sum) {
                sum = nums.get(i);

            }
        }
        return sum;
    }


     
     // USE BELOW SOLUTION ONLY IF ABOVE SOLUTION DOES NOT PASS 3/15

    // NOT SURE IF THIS IS THE BEST SOLUTION, NOT SURE IF IT WILL PASS ALL TESTS
    // UPDATE: THIS IS BASIC SOLUIION WHICH ONLY PASS 3/15 TESTS
    public static int minimumWeeklyInput2(List<Integer> costs, int weeks) {
        List<List<List<Integer>>> allCombinations = findAllSplits(costs, weeks);
        int result = Integer.MAX_VALUE;
        for (List<List<Integer>> eachComb : allCombinations) {
            int sum = 0;
            for (List<Integer> eachSub : eachComb) {
                int maxValue = eachSub.stream().mapToInt(v -> v).max().orElse(0);
                sum += maxValue;
            }
            result = Math.min(sum, result);
        }
        return result;
    }

    /**
     * This is a generic method to split/partition array into k sub-arrays
     * 
     * @param arr
     * @param k
     * @return
     */
    public static List<List<List<Integer>>> findAllSplits(List<Integer> arr, int k) {
        List<List<List<Integer>>> allCombinations = new ArrayList<>();
        List<List<Integer>> eachComb = new ArrayList<>();
        List<Integer> eachSub = new ArrayList<>();
        int index = 0;

        splitHelper(arr, k, index, new ArrayList<>(eachSub), eachComb, allCombinations);

        return allCombinations;
    }

    static void splitHelper(List<Integer> arr, int k, int index, List<Integer> eachSub, List<List<Integer>> eachComb,
            List<List<List<Integer>>> allCombinations) {
        if (k == 1) {
            List<Integer> last = new ArrayList<>();
            for (int i = index; i < arr.size(); i++) {
                last.add(arr.get(i));
            }

            if (!last.isEmpty()) {
                eachComb.add(new ArrayList<>(last));
                allCombinations.add(new ArrayList<>(eachComb));
                eachComb.remove(eachComb.size() - 1);
            }

            return;
        }

        eachSub.clear();

        for (int i = index; i < arr.size(); i++) {
            eachSub.add(arr.get(i));
            eachComb.add(new ArrayList<>(eachSub));
            splitHelper(arr, k - 1, i + 1, new ArrayList<>(eachSub), eachComb, allCombinations);
            eachComb.remove(eachComb.size() - 1);
        }
    }

}