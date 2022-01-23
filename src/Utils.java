import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Utils {
    public static boolean isValidNum(String input) {
        if (input.length() > 2) {
            return false;
        }
        for (char c : input.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    public static int[] getRandomNumsFromRange(int numRange, int numNeeded) {
        List<Integer> list = new ArrayList<>();
        Random random = new Random();
        for (int i = 1; i <= numRange; i++) {
            list.add(i);
        }
        int[] res = new int[numNeeded];
        for (int i = 0; i < numNeeded; i++) {
            int idx = random.nextInt(list.size());
            res[i] = list.get(idx);

            list.set(idx, list.get(list.size() - 1));
            list.remove(list.size() - 1);
        }

        return res;
    }

    public static boolean isValidSizeFormat(String input) {
        if (input.indexOf('X') == -1) {
            return false;
        }
        String[] size = input.split("X");
        return isValidNum(size[0]) && isValidNum(size[1]);
    }

    public static void DEBUG(String log) {
        if (Constants.IS_DEBUGGING) {
            System.out.println("Debugging Log: " + log);
        }
    }

    public static void DEBUG(int[] arr) {
        if (Constants.IS_DEBUGGING) {
            System.out.println("Debugging Log: " + Arrays.toString(arr));
        }
    }
}
