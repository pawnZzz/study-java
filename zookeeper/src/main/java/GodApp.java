import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author zyc
 */
public class GodApp {
    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            generateWealth();
            System.out.println("---------------------");
        }
    }

    public static void generateWealth() {
        List<Integer> redBalls = new ArrayList<>();
        int greenBall;
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            int red = random.nextInt(32) + 1;
            while (redBalls.contains(red)) {
                red = random.nextInt(32) + 1;
            }
            redBalls.add(red);
        }

        greenBall = random.nextInt(15) + 1;

        System.out.print("redBalls: " + Arrays.toString(redBalls.stream().sorted().toArray()));
        System.out.println("  " + greenBall + " greenBall");
    }
}
