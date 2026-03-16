import java.io.*;
import java.util.*;

// Reservation class (Serializable)
class Reservation implements Serializable {
    String reservationId;
    String guestName;
    String roomType;

    Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// System State class (Serializable)
class SystemState implements Serializable {
    List<Reservation> bookingHistory;
    Map<String, Integer> inventory;

    SystemState(List<Reservation> bookingHistory, Map<String, Integer> inventory) {
        this.bookingHistory = bookingHistory;
        this.inventory = inventory;
    }
}

// Persistence Service
class PersistenceService {

    private static final String FILE_NAME = "system_state.dat";

    // Save system state
    public void saveState(SystemState state) {
        try {
            ObjectOutputStream out =
                    new ObjectOutputStream(new FileOutputStream(FILE_NAME));
            out.writeObject(state);
            out.close();
            System.out.println("System state saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving system state.");
        }
    }

    // Load system state
    public SystemState loadState() {
        try {
            ObjectInputStream in =
                    new ObjectInputStream(new FileInputStream(FILE_NAME));
            SystemState state = (SystemState) in.readObject();
            in.close();
            System.out.println("System state loaded successfully.");
            return state;
        } catch (Exception e) {
            System.out.println("No previous data found. Starting fresh.");
            return null;
        }
    }
}

// Main class
public class UseCase12DataPersistenceRecovery {

    public static void main(String[] args) {

        PersistenceService persistence = new PersistenceService();

        // Try to load saved state
        SystemState state = persistence.loadState();

        List<Reservation> bookingHistory;
        Map<String, Integer> inventory;

        if (state == null) {
            // Initialize new system state
            bookingHistory = new ArrayList<>();
            inventory = new HashMap<>();

            inventory.put("Single", 2);
            inventory.put("Double", 1);

            bookingHistory.add(new Reservation("RES101", "Alice", "Single"));
            bookingHistory.add(new Reservation("RES102", "Bob", "Double"));

        } else {
            bookingHistory = state.bookingHistory;
            inventory = state.inventory;
        }

        // Display recovered state
        System.out.println("\nBooking History:");
        for (Reservation r : bookingHistory) {
            System.out.println(r.reservationId + " - " +
                    r.guestName + " - " + r.roomType);
        }

        System.out.println("\nInventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " rooms available: " + inventory.get(type));
        }

        // Save state before shutdown
        persistence.saveState(new SystemState(bookingHistory, inventory));
    }
}