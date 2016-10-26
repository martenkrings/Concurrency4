import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

/**
 *
 */
public class VerkoopAgentActor extends UntypedActor {
    private ActorRef[] vakAgentenRefs;

    public VerkoopAgentActor(ActorRef[] actorRefs) {
        vakAgentenRefs = actorRefs;
    }

    public static Props props(ActorRef[] actorRefs) {
        return Props.create(VerkoopAgentActor.class, actorRefs);
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        //if its a reserveMessage redirect it to a vakAgent
        if (message instanceof ReserveMessage) {
            ReserveMessage copyMessage = (ReserveMessage) message;
            vakAgentenRefs[copyMessage.getBlock() - 1].tell(message, getSelf());

            //if its a reserveResultMessage redirect is to a buyer
        }  else if (message instanceof ReserveResultMessage) {
            ReserveResultMessage copyMessage = (ReserveResultMessage) message;
            copyMessage.getKoper().tell(copyMessage, getSelf());

            //if its a buyMessage redirect it to a vakAgent
        } else if (message instanceof BuyMessage) {
            BuyMessage copyMessage = (BuyMessage) message;
            vakAgentenRefs[copyMessage.getBlock() - 1].tell(message, getSelf());

        //if its a cancelMessage redirect it to a vakAgent
        }else if(message instanceof CancelMessage){
            CancelMessage copyMessage = (CancelMessage) message;
            vakAgentenRefs[copyMessage.getBlock() - 1].tell(message, getSelf());

            //else throw it away
        } else {
            unhandled(message);
        }
    }
}
