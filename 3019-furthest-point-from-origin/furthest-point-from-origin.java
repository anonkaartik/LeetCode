class Solution {
    public int furthestDistanceFromOrigin(String moves) {
        int left = 0;
        int right = 0;
        int blank = 0;

        for (char c : moves.toCharArray()) {
            if (c == 'L') {
                left++;
            } else if (c == 'R') {
                right++;
            } else {
                blank++;
            }
        }

        return Math.abs(right - left) + blank;
    }
}