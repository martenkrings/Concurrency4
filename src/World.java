import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.Routee;
import akka.routing.Router;
import akka.routing.SmallestMailboxPool;

import java.util.ArrayList;
import java.util.List;

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
        ActorRef central = actorSystem.actorOf(new SmallestMailboxPool(5).props(Props.create(VerkoopAgentActor.class)), "central");

        //recieve messages and route them
        central.tell("something", null);

    }
}
