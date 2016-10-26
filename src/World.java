import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.SmallestMailboxPool;

import java.util.ArrayList;

/**
 * Created by Sander on 20-10-2016.
 */
public class World {
    private final int LONG_ENOUGH = 10000;

    public static void main(String[] args){
        new World().run();
    }

    public void run(){
        ActorSystem actorSystem = ActorSystem.create();

        ArrayList<ActorRef> vakagenten = new ArrayList<>();

        //make vakAgenten
        for (int i = 1; i < 6; i++){
            System.out.println("Vakagent" + i + " aangemaakt.");
            ActorRef vakAgent = actorSystem.actorOf(VakAgentActor.prop(i), "vakagent" + i);
            vakagenten.add(vakAgent);
        }

        //makes a router that routes 5 VerkoopAgentActors
        ActorRef central = actorSystem.actorOf(Central.props(vakagenten), "central");

        for (int i = 0; i < 50; i++){
            System.out.println("Koper" + i + " aangemaakt.");
            ActorRef koper = actorSystem.actorOf(KoperActor.props(central), "koper" + i);
        }

        //wait long enough
        try {
            Thread.sleep(LONG_ENOUGH);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
