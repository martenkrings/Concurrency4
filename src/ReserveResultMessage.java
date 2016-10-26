import akka.actor.ActorRef;

import java.util.ArrayList;

/**
 * Created by Sander on 24-10-2016.
 */
public class ReserveResultMessage {
    private ArrayList<Integer> seatNumbers = new ArrayList<>();
    private boolean succes;
    private ActorRef koper;
    private int block;

    public ReserveResultMessage(ArrayList<Integer> seatNumbers, boolean succes,int block, ActorRef koper) {
        this.seatNumbers = seatNumbers;
        this.succes = succes;
        this.block = block;
        this.koper = koper;
    }

    public ArrayList<Integer> getSeatNumbers() {
        return seatNumbers;
    }

    public boolean isSucces() {
        return succes;
    }

    public ActorRef getKoper() {
        return koper;
    }

    public int getBlock() {
        return block;
    }
}
