import java.util.*;

// Add-On Service class
class Service {
    String serviceName;
    double cost;

    Service(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }
}

// Manager class to handle add-on services
class AddOnServiceManager {

    // Map reservationID -> list of services
    private Map<String, List<Service>> reservationServices = new HashMap<>();

    // Add service to reservation
    public void addService(String reservationId, Service service) {

        reservationServices.putIfAbsent(reservationId, new ArrayList<>());
        reservationServices.get(reservationId).add(service);

        System.out.println(service.serviceName + " added to Reservation " + reservationId);
    }

    // Calculate total cost
    public double calculateServiceCost(String reservationId) {

        double total = 0;

        List<Service> services = reservationServices.get(reservationId);

        if (services != null) {
            for (Service s : services) {
                total += s.cost;
            }
        }

        return total;
    }

    // Display services for reservation
    public void displayServices(String reservationId) {

        List<Service> services = reservationServices.get(reservationId);

        System.out.println("\nServices for Reservation " + reservationId + ":");

        if (services == null || services.isEmpty()) {
            System.out.println("No services selected.");
            return;
        }

        for (Service s : services) {
            System.out.println("- " + s.serviceName + " : $" + s.cost);
        }

        System.out.println("Total Add-On Cost: $" + calculateServiceCost(reservationId));
    }
}

// Main class
public class UseCase7AddOnServiceSelection {

    public static void main(String[] args) {

        AddOnServiceManager manager = new AddOnServiceManager();

        // Example reservation IDs
        String reservation1 = "RES101";
        String reservation2 = "RES102";

        // Guest selects services
        manager.addService(reservation1, new Service("Breakfast", 15));
        manager.addService(reservation1, new Service("Airport Pickup", 30));
        manager.addService(reservation1, new Service("Extra Bed", 20));

        manager.addService(reservation2, new Service("Spa Access", 40));

        // Display selected services
        manager.displayServices(reservation1);
        manager.displayServices(reservation2);
    }
}