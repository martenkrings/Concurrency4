import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 */
public class VerkoopAgentActor extends UntypedActor {
    private ArrayList<ActorRef> vakAgentenRefs;
    private static final int PROGRESS = 200; //in miliseconds

    public VerkoopAgentActor(ArrayList<ActorRef> actorRefs) {
        vakAgentenRefs = actorRefs;
    }

    public static Props props(ArrayList<ActorRef> actorRefs) {
        return Props.create(VerkoopAgentActor.class, actorRefs);
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        Random random = new Random();
        //wait to simulate progressing erquest
        Thread.sleep(random.nextInt(PROGRESS));


        //if its a reserveMessage redirect it to a vakAgent
        if (message instanceof ReserveMessage) {
            ReserveMessage copyMessage = (ReserveMessage) message;
            vakAgentenRefs.get(copyMessage.getBlock() - 1).tell(message, getSelf());

            //if its a reserveResultMessage redirect is to a buyer
        }  else if (message instanceof ReserveResultMessage) {
            ReserveResultMessage copyMessage = (ReserveResultMessage) message;
            copyMessage.getKoper().tell(copyMessage, getSelf());

            //if its a buyMessage redirect it to a vakAgent
        } else if (message instanceof BuyMessage) {
            BuyMessage copyMessage = (BuyMessage) message;
            vakAgentenRefs.get(copyMessage.getBlock() - 1).tell(message, getSelf());

        //if its a cancelMessage redirect it to a vakAgent
        }else if(message instanceof CancelMessage) {
            CancelMessage copyMessage = (CancelMessage) message;
            vakAgentenRefs.get(copyMessage.getBlock() - 1).tell(message, getSelf());

        //send confirmation of purchase to KoperActor
        } else if(message instanceof ConfirmationMessage) {
            ConfirmationMessage copyMessage = (ConfirmationMessage) message;
            copyMessage.getKoper().tell(copyMessage, getSelf());

        //else throw it away
        } else {
            unhandled(message);
        }
    }
}
