import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Dealer {
    private final List<Integer> numberHolder;
    private final Random random;

    public Dealer(int numRange) {
        this.random = new Random();
        this.numberHolder = new ArrayList<>();
        initNumberHolder(numRange);
    }

    public boolean noMoreNumbers() {
        return numberHolder.isEmpty();
    }

    public int nextNum() {
        if (!numberHolder.isEmpty()) {
            int len = numberHolder.size();
            int idx = random.nextInt(len);
            int nextNum = numberHolder.get(idx);

            updateNumHolder(len, idx);

            return nextNum;
        } else {
            throw new RuntimeException("No more numbers!");
        }
    }

    private void updateNumHolder(int len, int idx) {
        numberHolder.set(idx, numberHolder.get(len - 1));
        numberHolder.remove(len - 1);
    }

    private void initNumberHolder(int numRange) {
        for (int i = 1; i <= numRange; i++) {
            numberHolder.add(i);
        }
    }
}
