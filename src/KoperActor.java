import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

import java.util.Random;

/**
 * Created by Sander on 24-10-2016.
 */
public class KoperActor extends UntypedActor {
    ActorRef centralRef;

    public KoperActor(ActorRef centralRef) {
        this.centralRef = centralRef;
    }

    public static Props props(ActorRef centralRef){
        return Props.create(KoperActor.class, centralRef);
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof ReserveResultMessage){
            System.out.println("received reservation result");
            ReserveResultMessage copyMessage = (ReserveResultMessage) message;

            if (copyMessage.isSucces()) {
                //kies of je de kaartje wil kopen, 5% kans van cancel
                Random random = new Random();
                if (random.nextInt(20) == 1) {
                    getSender().tell(new CancelMessage(copyMessage.getSeatNumbers(), copyMessage.getBlock()), getSelf());

                } else {
                    getSender().tell(new BuyMessage(getSelf(), copyMessage.getSeatNumbers(), copyMessage.getBlock()), getSelf());
                }
            } else {
                System.out.println(getSelf() + "I didn't want them anyway, BAKA!");
                getContext().stop(getSelf());
            }

        } else if (message instanceof ConfirmationMessage){
            System.out.println(getSelf() + "yay, I got tickets!!!");
            getContext().stop(getSelf());
        } else {
            unhandled(message);
        }
    }

    /**
     * When started sends a message to central
     *
     * @throws Exception
     */
    @Override
    public void preStart() throws Exception {
        super.preStart();
        Random random = new Random();
        //wait a random amount before trying to buy a ticket
        Thread.sleep(random.nextInt(500));

        //try to reserve seats
        ReserveMessage reserveMessage = new ReserveMessage(random.nextInt(5) + 1, random.nextInt(4) + 1, getSelf());
        centralRef.tell(reserveMessage, getSelf());
    }


}
