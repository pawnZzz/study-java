package com.example;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            generateWealthDLT();

        }
        System.out.println("---------------------");
        for (int i = 0; i < 5; i++) {
            generateWealthSSQ();
        }
    }

    public static void generateWealthDLT() {
        List<Integer> redBalls = new ArrayList<>();
        int greenBall1;
        int greenBall2;
        Random random = new SecureRandom();
        for (int i = 0; i < 5; i++) {
            int red = random.nextInt(34) + 1;
            while (redBalls.contains(red)) {
                red = random.nextInt(34) + 1;
            }
            redBalls.add(red);
        }

        greenBall1 = random.nextInt(11) + 1;

        greenBall2 = random.nextInt(11) + 1;
        while (greenBall1 == greenBall2) {
            greenBall2 = random.nextInt(11) + 1;
        }

        System.out.print("redBalls: " + Arrays.toString(redBalls.stream().sorted().toArray()));
        System.out.println("  " + " greenBall:" + greenBall1 + " " + greenBall2);
    }
    public static void generateWealthSSQ() {
        List<Integer> redBalls = new ArrayList<>();
        int greenBall1;
        int greenBall2;
        Random random = new SecureRandom();
        for (int i = 0; i < 6; i++) {
            int red = random.nextInt(32) + 1;
            while (redBalls.contains(red)) {
                red = random.nextInt(32) + 1;
            }
            redBalls.add(red);
        }

        greenBall1 = random.nextInt(15) + 1;

        System.out.print("redBalls: " + Arrays.toString(redBalls.stream().sorted().toArray()));
        System.out.println("  " + " greenBall:" + greenBall1);
    }
}