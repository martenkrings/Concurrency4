import akka.actor.ActorRef;

import java.util.ArrayList;

/**
 * Created by Sander on 24-10-2016.
 */
public class BuyMessage {
    ActorRef koper;
    ArrayList<Integer> chairNumbers;
    int block = 0;

    public BuyMessage(ActorRef koper, ArrayList<Integer> chairNumbers, int block) {
        this.koper = koper;
        this.chairNumbers = chairNumbers;
        this.block = block;
    }

    public ActorRef getKoper() {
        return koper;
    }

    public ArrayList<Integer> getChairNumbers() {
        return chairNumbers;
    }

    public int getBlock() {
        return block;
    }
}
