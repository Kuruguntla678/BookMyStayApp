import java.util.*;

// Booking Request class
class BookingRequest {
    String guestName;
    String roomType;

    BookingRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// Shared Booking Processor
class ConcurrentBookingProcessor {

    Queue<BookingRequest> bookingQueue = new LinkedList<>();
    Map<String, Integer> inventory = new HashMap<>();

    public ConcurrentBookingProcessor() {
        inventory.put("Single", 2);
        inventory.put("Double", 1);
    }

    // Add booking request (synchronized)
    public synchronized void addRequest(BookingRequest request) {
        bookingQueue.add(request);
        System.out.println(request.guestName + " requested " + request.roomType + " room.");
    }

    // Process booking request (critical section)
    public synchronized void processRequest() {

        if (bookingQueue.isEmpty()) {
            return;
        }

        BookingRequest request = bookingQueue.poll();

        if (inventory.containsKey(request.roomType) &&
                inventory.get(request.roomType) > 0) {

            inventory.put(request.roomType,
                    inventory.get(request.roomType) - 1);

            System.out.println("Booking confirmed for " +
                    request.guestName + " (" + request.roomType + ")");
        } else {
            System.out.println("Booking failed for " +
                    request.guestName + " - No rooms available.");
        }
    }
}

// Worker Thread
class BookingThread extends Thread {

    ConcurrentBookingProcessor processor;

    BookingThread(ConcurrentBookingProcessor processor) {
        this.processor = processor;
    }

    public void run() {
        processor.processRequest();
    }
}

// Main class
public class UseCase11ConcurrentBookingSimulation {

    public static void main(String[] args) {

        ConcurrentBookingProcessor processor =
                new ConcurrentBookingProcessor();

        // Multiple guests submit requests
        processor.addRequest(new BookingRequest("Alice", "Single"));
        processor.addRequest(new BookingRequest("Bob", "Single"));
        processor.addRequest(new BookingRequest("Charlie", "Single"));
        processor.addRequest(new BookingRequest("David", "Double"));

        // Simulate concurrent processing
        Thread t1 = new BookingThread(processor);
        Thread t2 = new BookingThread(processor);
        Thread t3 = new BookingThread(processor);
        Thread t4 = new BookingThread(processor);

        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
}