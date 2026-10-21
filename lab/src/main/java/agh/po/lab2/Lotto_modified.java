package agh.po.lab2;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Lotto_modified {
    public static void main(String[] args) {

        Random r = new Random();
        int matches;
        int count = 0;

        long startTime = System.currentTimeMillis();

        do {

            ArrayList<Integer> Args = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6));
            ArrayList<Integer> list = new ArrayList<>();
            while (list.size() < 6) {
                int number = r.nextInt(49) + 1;
                if (!list.contains(number)) {
                    list.add(number);
                }
            }

            Args.retainAll(list);
            matches = Args.size();
            count++;

            if (count % 1_000_000 == 0) {
                System.out.println("Losowanie nr: " + count);
            }

        } while (matches != 6);

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        System.out.println(" ");
        System.out.println("Liczba trafień: " + matches);
        System.out.println("Liczba losowań: " + count);
        System.out.println("Czas trwania: " + (duration/1000.0) + " s");
    }
}
