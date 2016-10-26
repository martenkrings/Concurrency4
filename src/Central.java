import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.SmallestMailboxPool;

import java.util.ArrayList;

/**
 * Created by Sander on 24-10-2016.
 */
public class Central extends UntypedActor {
    private final int numberOfVerkoopAgents = 3;
    ArrayList<ActorRef> vakAgenten = null;
    ActorRef router = null;

    public Central(ArrayList<ActorRef> vakAgenten) {
        this.vakAgenten = vakAgenten;
    }

    public static Props props(ArrayList<ActorRef> vakAgenten){
        return Props.create(Central.class, vakAgenten);
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof ReserveMessage) {
            ReserveMessage copyMessage = (ReserveMessage) message;

            //give everybody work
            for (int i = 0; i < numberOfVerkoopAgents; i++) {
                router.tell(copyMessage, getSelf());
            }

        } else {
            unhandled(message);
        }
    }

    /**
     * When started create a router
     *
     * @throws Exception
     */
    @Override
    public void preStart() throws Exception {
        router = getContext().actorOf(new SmallestMailboxPool(numberOfVerkoopAgents).props(Props.create(VerkoopAgentActor.class, vakAgenten)), "router");
    }
}
