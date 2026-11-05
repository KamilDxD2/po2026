package agh.po.lab4.zadania;

import agh.po.lab4.animals.Animal;
import agh.po.lab4.animals.Dog;
import agh.po.lab4.animals.Parrot;
import agh.po.lab4.animals.Snake;

import java.util.Random;

public class Zoo {
    Animal[] animals = new Animal[100];

    public void fill() {
        Random random = new Random();
        for (int i = 0; i < animals.length; i++) {
            int r = random.nextInt(3);
            switch (r) {
                case 0:
                    animals[i] = new Parrot("Parrot" + i);
                    break;
                case 1:
                    animals[i] = new Dog("Dog" + i);
                    break;
                default:
                    animals[i] = new Snake("Snake" + i);
            }
        }
    }

    public int sumLegs() {
        int sum = 0;
        for (Animal animal : animals) {
            sum += animal.getLegs();
        }
        return sum;
    }

    public void showAll() {
        for (Animal animal : animals) {
            int legs = animal.getLegs();
            String description = animal.getDescription();
            System.out.println(description);
            animal.makeSound();
        }
    }

    public static void main(String[] args) {
        Zoo zoo = new Zoo();
        zoo.fill();
        zoo.showAll();
        System.out.println("Total legs: " + zoo.sumLegs());
    }
}
