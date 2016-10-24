/**
 * Created by Sander on 24-10-2016.
 */
public class RentMessage {
    public static final int GENERAL_ADMISSION = 1;
    public static final int RESERVED_FLOOR_SEATING = 2;
    public static final int NORTH_GRANDSTAND = 3;
    public static final int SOUT_GRANDSTAND = 4;
    public static final int STANDING_PIT = 5;


    private int block;
    private int numberOfChairs;

    public RentMessage(int block, int numberOfChairs){
        this.block = block;
        this.numberOfChairs = numberOfChairs;
    }

    public int getNumberOfChairs() {
        return numberOfChairs;
    }
}
