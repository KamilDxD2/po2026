package agh.po.lab3;

import java.util.Arrays;
import java.util.Map;

public class CodingBat {
    public boolean sleepIn(boolean weekday, boolean vacation) {
        return !weekday || vacation;
    }

    public int diff21(int n) {
        return n > 21 ? 2 * Math.abs(n - 21) : Math.abs(n - 21);
    }

    public int countEvens(int[] nums) {
        return (int) java.util.Arrays.stream(nums).filter(x -> x % 2 == 0).count();
    }

    public String helloName(String name) {
        return "Hello " + name + "!";
    }

    public Map<String, String> mapAB(Map<String, String> map) {
        return map.containsKey("a") && map.containsKey("b") && map.put("ab", map.get("a") + map.get("b")) == null ? map
                : map;
    }

    public Map<String, Integer> wordLen(String[] strings) {
        return Arrays.stream(strings)
                .distinct()
                .collect(java.util.stream.Collectors.toMap(s -> s, String::length));
    }

    public static void main(String[] args) {
        CodingBat bat = new CodingBat();

    }

}
