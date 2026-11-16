
import java.util.*;

class Bus {
    int busNo;
    String busName, source, destination;
    int totalSeats, availableSeats;
    double fare;

    Bus(int busNo, String busName, String source,
        String destination, int totalSeats, double fare) {

        this.busNo = busNo;
        this.busName = busName;
        this.source = source;
        this.destination = destination;
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats;
        this.fare = fare;
    }

    void display() {
        System.out.println(busNo + "\t" + busName + "\t" +
                source + "\t" + destination + "\t" +
                availableSeats + "\t" + fare);
    }
}

class Reservation {
    int bookingId;
    String passengerName;
    int age;
    Bus bus;
    int seats;
    double totalFare;

    Reservation(int bookingId, String passengerName,
                int age, Bus bus, int seats) {

        this.bookingId = bookingId;
        this.passengerName = passengerName;
        this.age = age;
        this.bus = bus;
        this.seats = seats;
        this.totalFare = bus.fare * seats;
    }

    void display() {
        System.out.println("Booking ID: " + bookingId);
        System.out.println("Passenger Name: " + passengerName);
        System.out.println("Age: " + age);
        System.out.println("Bus Name: " + bus.busName);
        System.out.println("Route: " + bus.source +
                " to " + bus.destination);
        System.out.println("Seats Booked: " + seats);
        System.out.println("Total Fare: Rs." + totalFare);
        System.out.println("--------------------------------");
    }
}

public class BusTicketReservation {

    static Scanner sc = new Scanner(System.in);

    static Bus[] buses = {
        new Bus(101, "Express", "Chennai", "Madurai", 40, 450),
        new Bus(102, "Super Fast", "Madurai", "Coimbatore", 35, 350),
        new Bus(103, "Deluxe", "Chennai", "Coimbatore", 30, 600),
        new Bus(104, "City Express", "Trichy", "Madurai", 25, 200)
    };

    static Reservation[] reservations = new Reservation[100];

    static int reservationCount = 0;
    static int nextBookingId = 1001;

    static void displayBuses() {

        System.out.println("\nAVAILABLE BUSES");
        System.out.println("------------------------------------------------");

        System.out.println("Bus No\tName\tSource\tDestination\tSeats\tFare");

        for (Bus b : buses) {
            b.display();
        }
    }

    static void bookTicket() {

        if (reservationCount == 100) {
            System.out.println("Reservation limit reached!");
            return;
        }

        displayBuses();

        System.out.print("\nEnter Bus Number: ");
        int busNo = Integer.parseInt(sc.nextLine());

        Bus selectedBus = null;

        for (Bus b : buses) {
            if (b.busNo == busNo) {
                selectedBus = b;
                break;
            }
        }

        if (selectedBus == null) {
            System.out.println("Invalid Bus Number!");
            return;
        }

        System.out.print("Enter Passenger Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Age: ");
        int age = Integer.parseInt(sc.nextLine());

        if (age < 1 || age > 120) {
            System.out.println("Invalid Age!");
            return;
        }

        System.out.print("Enter Number of Seats: ");
        int seats = Integer.parseInt(sc.nextLine());

        if (seats <= 0 || seats > selectedBus.availableSeats) {
            System.out.println("Seats Not Available!");
            return;
        }

        Reservation r = new Reservation(
                nextBookingId, name, age, selectedBus, seats);

        reservations[reservationCount++] = r;

        selectedBus.availableSeats -= seats;

        System.out.println("\nTicket Booked Successfully!");
        System.out.println("Booking ID: " + nextBookingId);

        System.out.println("Total Fare: Rs." + r.totalFare);

        nextBookingId++;
    }

    static void viewReservations() {

        if (reservationCount == 0) {
            System.out.println("No Reservations Found!");
            return;
        }

        System.out.println("\nRESERVATION DETAILS");

        for (int i = 0; i < reservationCount; i++) {
            reservations[i].display();
        }
    }

    static void cancelTicket() {

        System.out.print("Enter Booking ID: ");
        int id = Integer.parseInt(sc.nextLine());

        for (int i = 0; i < reservationCount; i++) {

            if (reservations[i].bookingId == id) {

                reservations[i].bus.availableSeats +=
                        reservations[i].seats;

                for (int j = i; j < reservationCount - 1; j++) {
                    reservations[j] = reservations[j + 1];
                }

                reservations[--reservationCount] = null;

                System.out.println("Ticket Cancelled Successfully!");
                return;
            }
        }

        System.out.println("Booking ID Not Found!");
    }

    public static void main(String[] args) {

        int choice;

        do {
            System.out.println("\n===== BUS TICKET RESERVATION SYSTEM =====");
            System.out.println("1. Display Available Buses");
            System.out.println("2. Book Ticket");
            System.out.println("3. View Reservations");
            System.out.println("4. Cancel Ticket");
            System.out.println("5. Exit");

            System.out.print("Enter Your Choice: ");

            choice = Integer.parseInt(sc.nextLine());

            switch (choice) {

                case 1:
                    displayBuses();
                    break;

                case 2:
                    bookTicket();
                    break;

                case 3:
                    viewReservations();
                    break;

                case 4:
                    cancelTicket();
                    break;

                case 5:
                    System.out.println("Thank You!");
                    break;

                default:
                    System.out.println("Invalid Choice!");
            }

        } while (choice != 5);

        sc.close();
    }
}