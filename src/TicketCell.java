public class TicketCell {
    private final int number;
    private boolean isMarked;

    public int getNumber() {
        return number;
    }

    public boolean isMarked() {
        return isMarked;
    }

    public void setMarked(boolean marked) {
        this.isMarked = marked;
    }

    public TicketCell(int number) {
        this.number = number;
        this.isMarked = false;
    }
}
