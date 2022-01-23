import java.util.HashMap;
import java.util.Map;

public class BingoTicket implements Ticket {

    private final String playerName;
    private final TicketCell[][] cells;
    private final Map<Integer, Integer> numIndexLocator;

    private final int totalRow;
    private final int totalCol;
    private final int numberPerRow;
    private final int numberRange;

    private int topLineMarked;
    private int totalLineMarked;

    protected BingoTicket(GameConfigData data, int name) {
        this.playerName = Constants.PLAYER_NUMBER + name;
        this.totalRow = data.getTicketRows();
        this.totalCol = data.getTicketCols();
        this.numberPerRow = data.getNumberPerRow();
        this.numberRange = data.getNumberRange();
        this.topLineMarked = 0;
        this.totalLineMarked = 0;
        this.cells = new TicketCell[totalRow][totalCol];
        this.numIndexLocator = new HashMap<>();

        initializeCells();
    }

    public boolean winAnyAward(Awards award) {
        switch(award) {
            case EarlyFive:
                return totalLineMarked == 5;
            case TopLine:
                return topLineMarked == numberPerRow;
            case FullHouse:
                return totalLineMarked == numberPerRow * totalRow;
            default:
                return false;
        }
    }

    private void initializeCells() {
        int[] totalNumToAdd = Utils.getRandomNumsFromRange(numberRange, numberPerRow * totalRow);
        Utils.DEBUG(totalNumToAdd);
        int index = 0;

        for (int row = 0; row < totalRow; row++) {
            int[] cellIdxPerRow = Utils.getRandomNumsFromRange(totalCol, numberPerRow);
            Utils.DEBUG(cellIdxPerRow);
            for (int cellIdx : cellIdxPerRow) {
                cellIdx--;  //need to minus 1 to convert to 0 indexing.

                int currentNumToAdd = totalNumToAdd[index++];
                cells[row][cellIdx] = new TicketCell(currentNumToAdd);
                numIndexLocator.put(currentNumToAdd, row * totalCol + cellIdx);
            }
        }
    }

    private void markNumber(TicketCell cell) {
        cell.setMarked(true);
    }

    @Override
    public void checkNumber(int number) {
        if (numIndexLocator.containsKey(number)) {
            int idx = numIndexLocator.get(number);
            int row = idx / totalCol;
            int col = idx % totalCol;
            markNumber(cells[row][col]);
            if (row == 0) {
                topLineMarked++;
            }
            totalLineMarked++;
        }
    }

    public String getPlayerName() {
        return playerName;
    }
}
