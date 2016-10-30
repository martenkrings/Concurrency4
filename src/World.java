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
            ActorRef vakAgent = actorSystem.actorOf(VakAgentActor.prop(i), "vakagent" + i);
            vakagenten.add(vakAgent);
        }
        System.out.println("Vak agenten aangemaakt");

        //makes a router that routes 5 VerkoopAgentActors
        ActorRef central = actorSystem.actorOf(Central.props(vakagenten), "central");
        System.out.println("Central aangemaakt");

        for (int i = 0; i < 50; i++){
            ActorRef koper = actorSystem.actorOf(KoperActor.props(central), "koper" + i);
        }
        System.out.println("Kopers aangemaakt");

        //wait long enough
        try {
            Thread.sleep(LONG_ENOUGH);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.exit(0);
    }
}
