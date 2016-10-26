import java.util.ArrayList;

/**
 * Created by Sander on 26-10-2016.
 */
public class CancelMessage {
    ArrayList<Integer> chairNumbers;
    int block = 0;

    public CancelMessage(ArrayList<Integer> chairNumbers, int block) {
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
