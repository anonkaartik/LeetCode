class Solution {
    public int rotatedDigits(int n) {
        int count = 0;

        for (int num = 1; num <= n; num++) {
            int x = num;
            boolean valid = true;
            boolean changed = false;

            while (x > 0) {
                int digit = x % 10;

                if (digit == 2 || digit == 5 || digit == 6 || digit == 9) {
                    changed = true;
                } else if (digit == 0 || digit == 1 || digit == 8) {
                } else {
                    valid = false;
                    break;
                }

                x /= 10;
            }

            if (valid && changed) {
                count++;
            }
        }

        return count;
    }
}