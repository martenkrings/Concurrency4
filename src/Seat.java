/**
 * Classe that repesents a seat at a concert
 * The seat can be free, taken of
 */
public class Seat {
    public static final int FREE = 0;
    public static final int RESERVED = 1;
    public static final int TAKEN = 2;

    private int seatNumber;
    private int status;

    public Seat(int seatNumber, int status) {
        this.seatNumber = seatNumber;
        this.status = status;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
