import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.japi.Procedure;
import akka.routing.SmallestMailboxPool;

import java.util.ArrayList;

/**
 * Created by Sander on 24-10-2016.
 */
public class Central extends UntypedActor {
    private final int numberOfVerkoopAgents = 3;
    ArrayList<ActorRef> vakAgenten = null;
    ActorRef router = null;
    ActorSystem system = null;

    public Central(ArrayList<ActorRef> vakAgenten) {
        this.vakAgenten = vakAgenten;
        this.system = ActorSystem.create("Routing");
    }

    public static Props props(ArrayList<ActorRef> vakAgenten){
        return Props.create(Central.class, vakAgenten);
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof ReserveMessage) {
            ReserveMessage copyMessage = (ReserveMessage) message;

            //delegate work
            router.tell(copyMessage, getSelf());


            //else throw it away
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
        router = system.actorOf(new SmallestMailboxPool(numberOfVerkoopAgents).props(Props.create(VerkoopAgentActor.class, vakAgenten)), "router");
    }

    Procedure<Object> scaleUp = new Procedure<Object>() {
        @Override
        public void apply(Object message) throws Exception {

        }
    };

}
