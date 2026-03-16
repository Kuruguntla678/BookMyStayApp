import java.util.*;

// Custom Exception
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Booking Validator
class InvalidBookingValidator {

    Map<String, Integer> inventory = new HashMap<>();

    public InvalidBookingValidator() {
        inventory.put("Single", 2);
        inventory.put("Double", 2);
    }

    // Validate booking
    public void validateBooking(String guestName, String roomType) throws InvalidBookingException {

        if (guestName == null || guestName.isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty.");
        }

        if (!inventory.containsKey(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }

        if (inventory.get(roomType) <= 0) {
            throw new InvalidBookingException("No rooms available for type: " + roomType);
        }

        // If valid, update inventory
        inventory.put(roomType, inventory.get(roomType) - 1);

        System.out.println("Booking confirmed for " + guestName + " (" + roomType + ")");
    }
}

// Main Class
public class UseCase9ErrorHandlingValidation {

    public static void main(String[] args) {

        InvalidBookingValidator validator = new InvalidBookingValidator();

        String[][] bookingRequests = {
                {"Alice", "Single"},
                {"Bob", "Suite"},     // invalid room type
                {"", "Double"},       // invalid guest name
                {"Charlie", "Double"}
        };

        for (String[] request : bookingRequests) {
            try {
                validator.validateBooking(request[0], request[1]);
            } catch (InvalidBookingException e) {
                System.out.println("Booking Failed: " + e.getMessage());
            }
        }
    }
}