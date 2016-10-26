import akka.actor.Props;
import akka.actor.UntypedActor;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Actor that handles request to a section
 */
public class VakAgentActor extends UntypedActor {
    private ArrayList<Seat> seats = new ArrayList<>();
    private static int block;

    /**
     * When I start make all the seats
     */
    @Override
    public void aroundPreStart() {
        super.aroundPreStart();

        //make the seats
        for (int i = 0; i < 20; i++){
            seats.add(new Seat(i, Seat.FREE));
        }
    }

    public VakAgentActor(int block){
        this.block = block;
    }

    public static Props prop(int block){
        return Props.create(VakAgentActor.class, block);
    }

    /**
     * Handles two kinds of requests
     * Reserve: looks if enoug seats are free, if they are reserves them(for a limited time) else send message back.
     * Buy: Change reserved seats into bought ones.
     *
     * If an reservation is left untouched to long cancels it.
     * @param message
     * @throws Throwable
     */
    @Override
    public void onReceive(Object message) throws Throwable {
        //try to reserve seats
        if (message instanceof ReserveMessage){
            System.out.println(block + " vakAgent krijgt reservering");
            ReserveMessage copyMessage = (ReserveMessage) message;

            //find avaible seats
            ArrayList<Seat> avaibleSeats = new ArrayList<>();
            for (Seat seat: seats){
                if (seat.getStatus() == Seat.FREE){
                    avaibleSeats.add(seat);
                }
                if (copyMessage.getNumberOfChairs() == avaibleSeats.size()){
                    break;
                }
            }

            //if enough seats found send Arraylist of the seat numbers
            if (avaibleSeats.size() > copyMessage.getNumberOfChairs()){
                System.out.println(block + " vakAgent heeft genoeg stoelen voor reservering, stuurt stoelnummers terug.");
                ArrayList<Integer> seatNumbers = new ArrayList<>();
                for (Seat seat:avaibleSeats){

                    //reserve seats
                    seatNumbers.add(seat.getSeatNumber());
                    seat.setStatus(Seat.RESERVED);
                }
                getSender().tell(new ReserveResultMessage(seatNumbers, true, block, copyMessage.getKoper()), getSelf());

                //else false
            } else {
                System.out.println(block + " vakAgent heeft niet genoeg stoelen voor reservering, stuurt bericht terug");
                getSender().tell(new ReserveResultMessage(null, false, block, copyMessage.getKoper()), getSelf());
            }

            //change the seats to bought
        } else if (message instanceof BuyMessage){
            BuyMessage copyMessage = (BuyMessage) message;
            for (Seat seat:seats){
                for (int chairNumber : copyMessage.getChairNumbers())
                if (seat.getSeatNumber() == chairNumber){
                    seat.setStatus(Seat.TAKEN);
                }
            }
            //todo send succes??

            //change the seats to free
        } else if(message instanceof CancelMessage){
            CancelMessage copyMessage = (CancelMessage) message;
            for (Seat seat:seats) {
                for (int chairNumber : copyMessage.getChairNumbers())
                    if (seat.getSeatNumber() == chairNumber) {
                        seat.setStatus(Seat.FREE);
                    }
            }

            //else throw it away
        } else {
            unhandled(message);
        }
    }

    /**
     * Inner class that represents a seat at a concert
     * The seat can be free, taken of
     */
    private class Seat {
        public static final int FREE = 0;
        public static final int RESERVED = 1;
        public static final int TAKEN = 2;

        private int seatNumber;
        private int status;

        public Seat(int seatNumber, int status) {
            this.seatNumber = seatNumber;
            this.status = status;
        }

        public int getSeatNumber() {
            return seatNumber;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
