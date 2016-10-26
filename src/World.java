import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.SmallestMailboxPool;

/**
 * Created by Sander on 20-10-2016.
 */
public class World {
    public static void main(String[] args){
        new World().run();
    }

    public void run(){
        ActorSystem actorSystem = ActorSystem.create();

        //makes a router that routes 5 VerkoopAgentActors
        ActorRef central = actorSystem.actorOf(new SmallestMailboxPool(1).props(Props.create(Central.class)), "central");

        //make vakAgenten
        for (int i = 1; i < 6; i++){
            ActorRef vakAgent = actorSystem.actorOf(VakAgentActor.prop(i), "vakagent" + i);
        }

        //send messages
        central.tell("something", null);

    }
}
