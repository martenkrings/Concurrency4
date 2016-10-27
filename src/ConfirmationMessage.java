import akka.actor.ActorRef;

/**
 * Created by Marten on 10/27/2016.
 */
public class ConfirmationMessage {
    private boolean isBought;
    private ActorRef koper;

    public ConfirmationMessage(boolean isBought, ActorRef koper) {
        this.isBought = isBought;
        this.koper = koper;
    }

    public boolean isBought() {
        return isBought;
    }

    public ActorRef getKoper() {
        return koper;
    }
}
