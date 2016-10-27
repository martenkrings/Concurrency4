import akka.actor.ActorRef;

/**
 * Created by Sander on 24-10-2016.
 */
public class ReserveMessage {
    private int block;
    private int numberOfChairs;
    private ActorRef koper;

    public ReserveMessage(int block, int numberOfChairs, ActorRef koper){
        this.block = block;
        this.numberOfChairs = numberOfChairs;
        this.koper = koper;
    }

    public int getNumberOfChairs() {
        return numberOfChairs;
    }

    public int getBlock() {
        return block;
    }

    public ActorRef getKoper() {
        return koper;
    }
}
