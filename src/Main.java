import java.util.*;

class BookingRequest {
    String guestName;
    String roomType;

    BookingRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

public class UseCase6RoomAllocationService {

    // Queue for booking requests (FIFO)
    private static Queue<BookingRequest> requestQueue = new LinkedList<>();

    // Inventory of rooms
    private static Map<String, Integer> inventory = new HashMap<>();

    // Map room type -> allocated room IDs
    private static Map<String, Set<String>> allocatedRooms = new HashMap<>();

    // Set to store all room IDs to ensure uniqueness
    private static Set<String> allRoomIds = new HashSet<>();

    // Room ID counter
    private static int roomCounter = 1;

    public static void main(String[] args) {

        // Initialize inventory
        inventory.put("Single", 2);
        inventory.put("Double", 2);

        // Initialize allocated rooms map
        allocatedRooms.put("Single", new HashSet<>());
        allocatedRooms.put("Double", new HashSet<>());

        // Add booking requests to queue
        requestQueue.add(new BookingRequest("Alice", "Single"));
        requestQueue.add(new BookingRequest("Bob", "Double"));
        requestQueue.add(new BookingRequest("Charlie", "Single"));
        requestQueue.add(new BookingRequest("David", "Single"));

        // Process booking requests
        processBookings();
    }

    public static void processBookings() {

        while (!requestQueue.isEmpty()) {

            BookingRequest request = requestQueue.poll();
            String roomType = request.roomType;

            System.out.println("Processing request for " + request.guestName + " (" + roomType + ")");

            // Check availability
            if (inventory.getOrDefault(roomType, 0) > 0) {

                // Generate unique room ID
                String roomId;
                do {
                    roomId = roomType.substring(0,1).toUpperCase() + roomCounter++;
                } while (allRoomIds.contains(roomId));

                // Record allocated room ID
                allRoomIds.add(roomId);
                allocatedRooms.get(roomType).add(roomId);

                // Decrement inventory
                inventory.put(roomType, inventory.get(roomType) - 1);

                // Confirm reservation
                System.out.println("Reservation confirmed for " + request.guestName +
                        ". Room ID: " + roomId);

            } else {
                System.out.println("Sorry! No " + roomType + " rooms available for " + request.guestName);
            }

            System.out.println();
        }

        // Display final allocation
        System.out.println("Final Room Allocation:");
        for (String type : allocatedRooms.keySet()) {
            System.out.println(type + " Rooms: " + allocatedRooms.get(type));
        }
    }
}