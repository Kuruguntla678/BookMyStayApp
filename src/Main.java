import java.util.HashMap;
import java.util.Map;

abstract class Room {

    protected String type;
    protected int beds;
    protected double price;

    public Room(String type, int beds, double price) {
        this.type = type;
        this.beds = beds;
        this.price = price;
    }

    public void displayDetails() {
        System.out.println("Room Type : " + type);
        System.out.println("Beds      : " + beds);
        System.out.println("Price     : $" + price);
    }

    public String getType() {
        return type;
    }
}

/* ---------- Concrete Room Types ---------- */

class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 100);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 180);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 350);
    }
}

/* ---------- Centralized Inventory ---------- */

class RoomInventory {

    private Map<String, Integer> inventory;

    public RoomInventory() {

        inventory = new HashMap<>();

        inventory.put("Single Room", 5);
        inventory.put("Double Room", 0);  // unavailable
        inventory.put("Suite Room", 2);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public Map<String, Integer> getInventory() {
        return inventory;
    }
}

/* ---------- Search Service ---------- */

class RoomSearchService {

    public void searchAvailableRooms(RoomInventory inventory, Room[] rooms) {

        System.out.println("------ Available Rooms ------\n");

        for (Room room : rooms) {

            int available = inventory.getAvailability(room.getType());

            // Only display rooms with availability
            if (available > 0) {

                room.displayDetails();
                System.out.println("Available : " + available);
                System.out.println("-----------------------------");
            }
        }
    }
}

/* ---------- Application Entry ---------- */

public class UseCase4RoomSearch {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("Book My Stay - Hotel Booking App");
        System.out.println("Version 4.1");
        System.out.println("=================================\n");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Initialize room domain objects
        Room[] rooms = {
                new SingleRoom(),
                new DoubleRoom(),
                new SuiteRoom()
        };

        // Search service
        RoomSearchService searchService = new RoomSearchService();

        // Perform room search (read-only)
        searchService.searchAvailableRooms(inventory, rooms);
    }
}