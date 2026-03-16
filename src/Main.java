import java.util.*;

// Reservation class
class Reservation {
    String reservationId;
    String guestName;
    String roomType;

    Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// Booking History class
class BookingHistory {

    // List to store reservations in insertion order
    private List<Reservation> reservations = new ArrayList<>();

    // Add confirmed reservation
    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    // Get all reservations
    public List<Reservation> getReservations() {
        return reservations;
    }
}

// Report Service class
class BookingReportService {

    public void generateReport(List<Reservation> reservations) {

        System.out.println("\nBooking History Report");
        System.out.println("----------------------");

        for (Reservation r : reservations) {
            System.out.println("Reservation ID: " + r.reservationId +
                    ", Guest: " + r.guestName +
                    ", Room Type: " + r.roomType);
        }

        System.out.println("\nTotal Bookings: " + reservations.size());
    }
}

// Main class
public class UseCase8BookingHistoryReport {

    public static void main(String[] args) {

        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        // Confirmed bookings added to history
        history.addReservation(new Reservation("RES101", "Alice", "Single"));
        history.addReservation(new Reservation("RES102", "Bob", "Double"));
        history.addReservation(new Reservation("RES103", "Charlie", "Suite"));

        // Admin requests booking report
        reportService.generateReport(history.getReservations());
    }
}