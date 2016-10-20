import akka.actor.UntypedActor;

import java.util.ArrayList;

/**
 * Actor that handles request to a section
 */
public class VakAgentActor extends UntypedActor {
    private ArrayList<Seat> seats = new ArrayList<>();

    /**
     * When I start make all the seats
     */
    @Override
    public void aroundPreStart() {
        super.aroundPreStart();

        //make the seats
        for (int i = 0; i < 100; i++){
            seats.add(new Seat(i, Seat.FREE));
        }
    }

    /**
     * Handles two kinds of requests
     * Reserve: looks if enoug seats are free, if they are reserves them else send message back.
     * Buy: Change reserved seats into bought ones.
     *
     * If an reservation is left untouched to long cancels it.
     * @param o
     * @throws Throwable
     */
    @Override
    public void onReceive(Object o) throws Throwable {

    }
}
