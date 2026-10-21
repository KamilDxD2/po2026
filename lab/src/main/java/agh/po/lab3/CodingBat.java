package agh.po.lab3;

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


    public static void main(String[] args) {
        CodingBat bat = new CodingBat();
        // do testow lokalnych (kod jest na tyle prosty ze nie byly wymagane)
    }

}
