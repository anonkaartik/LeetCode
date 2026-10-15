import java.util.*;

class Solution {
    public int sumCounts(List<Integer> nums) {
        int n = nums.size();
        int total = 0;

        for (int i = 0; i < n; i++) {
            Set<Integer> seen = new HashSet<>();
            int distinct = 0;

            for (int j = i; j < n; j++) {
                if (seen.add(nums.get(j))) {
                    distinct++;
                }

                total += distinct * distinct;
            }
        }

        return total;
    }
}