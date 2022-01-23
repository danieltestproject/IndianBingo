import java.util.*;
import java.util.stream.Collectors;

public class GameOrganizer {
    private List<BingoTicket> players;
    private Set<Awards> awardRedeemed;
    private Dealer dealer;
    private static GameOrganizer instance;
    private Map<String, List<String>> finalResult;

    private GameOrganizer(GameConfigData data) {
        this.dealer = new Dealer(data.getNumberRange());

        initializeGame(data);
    }

    private void initializeGame(GameConfigData data) {
        this.awardRedeemed = new HashSet<>();

        initializePlayers(data);
        initializeFinalResult();
    }

    private void initializeFinalResult() {
        this.finalResult = new HashMap<>();

        for (BingoTicket ticket : players) {
            finalResult.put(ticket.getPlayerName(), new ArrayList<>());
        }
    }

    public static GameOrganizer getInstance(GameConfigData data) {
        if (instance == null) {
            instance = new GameOrganizer(data);
        }

        return instance;
    }

    private void initializePlayers(GameConfigData data) {
        this.players = new ArrayList<>();
        int playerNum = data.getPlayerNum();

        for (int i = 1; i <= playerNum; i++) {
            players.add(new BingoTicket(data, i));
        }
    }

    public boolean checkGameOver() {
        return awardRedeemed.size() == Awards.values().length || dealer.noMoreNumbers();
    }

    public int callNumber() {
        int num = dealer.nextNum();
        checkEachPlayer(num);

        return num;
    }

    private void checkEachPlayer(int num) {
        for (BingoTicket ticket : players) {
            ticket.checkNumber(num);
        }
    }

    public List<String> getCurrentWinners() {
        List<String> currentWinnerStatement = new ArrayList<>();
        for (Awards award : Awards.values()) {
            if (!awardRedeemed.contains(award)) {
                List<String> curWinnerList = new ArrayList<>();
                for (BingoTicket ticket : players) {
                    if (ticket.winAnyAward(award)) {
                        curWinnerList.add(ticket.getPlayerName());
                        awardRedeemed.add(award);
                        finalResult.get(ticket.getPlayerName()).add(award.name());
                    }
                }
                if (!curWinnerList.isEmpty()) {
                    currentWinnerStatement.add(buildWinnerStatement(award, curWinnerList));
                }
            }
        }

        return currentWinnerStatement;
    }

    public List<String> getFinalResult() {
        return finalResult.entrySet().stream().map(this::buildFinalResultStatement).collect(Collectors.toList());
    }

    private String buildFinalResultStatement(Map.Entry<String, List<String>> entry) {
        String result = entry.getValue().isEmpty() ? Constants.NO_AWARD : String.join(" and ", entry.getValue());
        return entry.getKey().concat(" : ").concat(result);
    }

    private String buildWinnerStatement(Awards award, List<String> curWinnerList) {
        String players = String.join(",", curWinnerList);

        return Constants.CURRENT_WINNER_STATEMENT.replaceFirst("Player", players).replaceFirst("Award", award.name());
    }
}
