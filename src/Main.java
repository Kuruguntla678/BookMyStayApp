import java.util.*;

// Reservation class
class Reservation {
    String reservationId;
    String guestName;
    String roomType;
    String roomId;

    Reservation(String reservationId, String guestName, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
    }
}

// Cancellation Service
class CancellationService {

    Map<String, Reservation> reservations = new HashMap<>();
    Map<String, Integer> inventory = new HashMap<>();
    Stack<String> releasedRooms = new Stack<>();

    public CancellationService() {
        inventory.put("Single", 1);
        inventory.put("Double", 1);
    }

    // Add confirmed reservation
    public void addReservation(Reservation r) {
        reservations.put(r.reservationId, r);
    }

    // Cancel reservation
    public void cancelReservation(String reservationId) {

        if (!reservations.containsKey(reservationId)) {
            System.out.println("Cancellation Failed: Reservation not found.");
            return;
        }

        Reservation r = reservations.get(reservationId);

        // Push released room ID to stack (rollback)
        releasedRooms.push(r.roomId);

        // Restore inventory
        inventory.put(r.roomType, inventory.get(r.roomType) + 1);

        // Remove reservation
        reservations.remove(reservationId);

        System.out.println("Reservation " + reservationId + " cancelled successfully.");
        System.out.println("Room released: " + r.roomId);
    }

    // Display inventory
    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " Rooms Available: " + inventory.get(type));
        }
    }
}

// Main class
public class UseCase10BookingCancellation {

    public static void main(String[] args) {

        CancellationService service = new CancellationService();

        // Add confirmed bookings
        service.addReservation(new Reservation("RES101", "Alice", "Single", "S1"));
        service.addReservation(new Reservation("RES102", "Bob", "Double", "D1"));

        // Cancel a reservation
        service.cancelReservation("RES101");

        // Try cancelling invalid reservation
        service.cancelReservation("RES200");

        // Show updated inventory
        service.displayInventory();
    }
}