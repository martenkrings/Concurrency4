import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.SmallestMailboxPool;

/**
 * Created by Sander on 24-10-2016.
 */
public class Central extends UntypedActor {
    private final int numberOfVerkoopAgents = 100;

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof RentMessage[]) {
            RentMessage[] messagesToGive = (RentMessage[]) message;
            ActorRef router = getContext().actorOf(new SmallestMailboxPool(5).props(Props.create(VerkoopAgentActor.class)), "router1");

            //give everybody work
            for (int i = 0; i < numberOfVerkoopAgents; i++) {
                router.tell(messagesToGive[i], getSelf());
            }
        } else {
            unhandled(message);
        }
    }
}
