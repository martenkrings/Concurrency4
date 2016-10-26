import akka.actor.UntypedActor;

import java.util.Random;

/**
 * Created by Sander on 24-10-2016.
 */
public class KoperActor extends UntypedActor {
    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof ReserveResultMessage){
            ReserveResultMessage copyMessage = (ReserveResultMessage) message;

            if (copyMessage.isSucces()) {
                //kies of je de kaartje wil kopen, 5% kans van cancel
                Random random = new Random();
                if (random.nextInt(20) == 1) {
                    getSender().tell(new BuyMessage(null, copyMessage.getBlock()), getSelf());

                } else {
                    getSender().tell(new BuyMessage(copyMessage.getSeatNumbers(), copyMessage.getBlock()), getSelf());
                }
            } else {
                //kill urself
            }

        } else if (message instanceof BuyMessage){

        }
    }
}
