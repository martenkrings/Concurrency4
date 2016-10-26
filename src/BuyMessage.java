import java.util.ArrayList;

/**
 * Created by Sander on 24-10-2016.
 */
public class BuyMessage {
    ArrayList<Integer> chairNumbers;
    int block = 0;

    public BuyMessage(ArrayList<Integer> chairNumbers, int block) {
        this.chairNumbers = chairNumbers;
        this.block = block;
    }

    public ArrayList<Integer> getChairNumbers() {
        return chairNumbers;
    }

    public int getBlock() {
        return block;
    }
}
