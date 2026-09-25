import java.util.*;

class Solution {
    public List<String> braceExpansionII(String expression) {
        Set<String> result = parse(expression, 0, expression.length()).set;
        List<String> answer = new ArrayList<>(result);
        Collections.sort(answer);
        return answer;
    }

    static class Result {
        Set<String> set;
        int index;

        Result(Set<String> set, int index) {
            this.set = set;
            this.index = index;
        }
    }

    private Result parse(String s, int start, int n) {
        Set<String> result = new HashSet<>();
        Set<String> current = new HashSet<>();
        current.add("");

        int i = start;

        while (i < n && s.charAt(i) != '}') {
            char c = s.charAt(i);

            if (c == ',') {
                result.addAll(current);
                current = new HashSet<>();
                current.add("");
                i++;
            } else {
                Result part;

                if (c == '{') {
                    part = parse(s, i + 1, n);
                    i = part.index + 1;
                } else {
                    Set<String> single = new HashSet<>();
                    single.add(String.valueOf(c));
                    part = new Result(single, i);
                    i++;
                }

                Set<String> next = new HashSet<>();

                for (String a : current) {
                    for (String b : part.set) {
                        next.add(a + b);
                    }
                }

                current = next;
            }
        }

        result.addAll(current);

        return new Result(result, i);
    }
}