import java.io.*;
import java.util.*;

class Train implements Serializable{
    private String name;
    private int totalSeats;
    private int availableSeats;
    private int bookedSeats;

    public Train(String name, int totalSeats) {
        this.name = name;
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats;
        this.bookedSeats = 0;
    }

    public String getName() {
        return name;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void bookSeats(int numSeats) {
        if (numSeats <= availableSeats) {
            availableSeats -= numSeats;
            System.out.println("\nSeats booked successfully!");
        } else {
            System.out.println("\nNot enough seats available for booking.");
        }
    }

    public void cancelSeats(int numSeats) {
    if (numSeats > 0) {
        bookedSeats -= numSeats;
        availableSeats += numSeats;
    } else {
        System.out.println("\nInvalid number of seats to cancel.");
    }
}

    public int getBookedSeats() {
        return bookedSeats;
    }

    public void updateAvailableSeats() {
        availableSeats = totalSeats - bookedSeats;
    }

}

class CancelledTicket implements Serializable{
    private String bookingId;
    private String trainName;
    private String passengerName;
    private int passengerAge;

    public CancelledTicket(String bookingId, String trainName, String passengerName, int passengerAge) {
        this.bookingId = bookingId;
        this.trainName = trainName;
        this.passengerName = passengerName;
        this.passengerAge = passengerAge;
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getTrainName() {
        return trainName;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public int getPassengerAge() {
        return passengerAge;
    }
}


public class RailwayTicketBooking {
    private static final String BOOKED_TICKETS_FILE = "booked_tickets.txt";
    private static final String CANCELLED_TICKETS_FILE = "cancelled_tickets.txt";
     private static final String AVAILABLE_TRAINS_FILE = "available_trains.ser";

    private static List<String> bookedTickets = new ArrayList<>();
    private static List<Train> availableTrains = new ArrayList<>();
    private static List<CancelledTicket> cancelledTickets = new ArrayList<>();

    public static void main(String[] args) {
        initializeTrains();
        loadBookedTickets();
        loadCancelledTickets();

        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nRailway Ticket Booking System");
            System.out.println("1. Display Available Train");
            System.out.println("2. Book a Ticket");
            System.out.println("3. View Booked Tickets");
            System.out.println("4. Cancel a Ticket");
            System.out.println("5. View Cancelled Tickets");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    displayAvailableTrains();
                    break;
                case 2:
                    bookTicket(scanner);
                    break;
                case 3:
                    viewBookedTickets();
                    break;
                case 4:
                    cancelTicket(scanner);
                    break;
                case 5:
                    viewCancelledTickets();
                    break;
                case 6:
                    saveBookedTickets();
                    saveCancelledTickets(); 
                    System.out.println("Exiting. Thank you!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        } while (choice != 6);

        scanner.close();
    }

    private static void initializeTrains() {
    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(AVAILABLE_TRAINS_FILE))) {
        availableTrains = (List<Train>) ois.readObject();
    } catch (IOException | ClassNotFoundException e) {
        System.out.println("Error loading available trains: " + e.getMessage());
    }

    if (availableTrains.isEmpty()) {
        availableTrains.add(new Train("Express 101", 50));
        availableTrains.add(new Train("Superfast 202", 30));
        availableTrains.add(new Train("Local 303", 100));
    }
    }

        private static void displayAvailableTrains() {
        System.out.println("\nAvailable Trains:");
        for (int i = 0; i < availableTrains.size(); i++) {
            Train train = availableTrains.get(i);
            System.out.println(i+1 + ". " + train.getName() + " - Available Seats: " + train.getAvailableSeats());
        }
    }

    private static void bookTicket(Scanner scanner) {
    displayAvailableTrains();
    System.out.print("Enter the index of the train to book a ticket: ");
    int trainIndex = scanner.nextInt();
    trainIndex = trainIndex - 1;

    if (trainIndex >= 0 && trainIndex < availableTrains.size()) {
        Train selectedTrain = availableTrains.get(trainIndex);

        System.out.print("Enter the number of tickets to book: ");
        int numTickets = scanner.nextInt();

        scanner.nextLine();

        if (numTickets > 0 && numTickets <= selectedTrain.getAvailableSeats()) {
            for (int i = 0; i < numTickets; i++) {
                String passengerName;
                do {
                    System.out.print("Enter passenger name for ticket " + (i + 1) + ": ");
                    passengerName = scanner.nextLine().trim();

                    if (passengerName.isEmpty()) {
                        System.out.println("Passenger name cannot be empty. Please enter a valid name.");
                    }
                } while (passengerName.isEmpty());

                int passengerAge = 0;
                boolean validAge = false;
                while (!validAge) {
                    System.out.print("Enter passenger age for ticket " + (i + 1) + ": ");
                    try {
                        passengerAge = Integer.parseInt(scanner.nextLine());
                        validAge = true;
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid age. Please enter a valid integer.");
                    }
                }

                String bookingId = String.valueOf(System.currentTimeMillis());
                String ticketDetails = bookingId + "," + passengerName + "," + passengerAge + "," + selectedTrain.getName();
                bookedTickets.add(ticketDetails);
            }

            selectedTrain.bookSeats(numTickets);
            saveAvailableTrains();

            System.out.println("Tickets booked successfully!");
        } else {
            System.out.println("Invalid number of tickets. Please enter a valid number within the available seats.");
        }
    } else {
        System.out.println("Invalid train index. Please enter a valid index.");
    }
}


    private static void loadAvailableTrains() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(AVAILABLE_TRAINS_FILE))) {
            availableTrains = (List<Train>) ois.readObject();

            loadBookedTickets();
            for (String ticket : bookedTickets) {
                String[] ticketDetails = ticket.split(",");
                String trainName = ticketDetails[3];

                Train train = null;
                for (Train availableTrain : availableTrains) {
                    if (availableTrain.getName().equals(trainName)) {
                        train = availableTrain;
                        break;
                    }
                }

                if (train != null) {
                    int numBookedSeats = 1; 
                    train.bookSeats(numBookedSeats);
                    train.updateAvailableSeats();
                } else {
                    System.out.println("Invalid train name in booked ticket record.");
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading available trains: " + e.getMessage());
        }

        if (availableTrains.isEmpty()) {
            availableTrains.add(new Train("Express 101", 50));
            availableTrains.add(new Train("Superfast 202", 30));
            availableTrains.add(new Train("Local 303", 100));
        }
    }

    private static void saveAvailableTrains() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(AVAILABLE_TRAINS_FILE))) {
            oos.writeObject(availableTrains);
        } catch (IOException e) {
            System.out.println("Error saving available trains: " + e.getMessage());
         }
    }   

    private static void viewBookedTickets() {
    if (bookedTickets.isEmpty()) {
        System.out.println("\nNo tickets booked yet.");
    } else {
        System.out.println("\nBooked Tickets:");

        for (String ticket : bookedTickets) {
            String[] ticketDetails = ticket.split(",");
            String bookingId = ticketDetails[0];
            String trainName = ticketDetails[3];

            int numTickets = 1; 
            int ticketDetailsLength = ticketDetails.length;             

            System.out.println("Booking ID: " + bookingId + ", Train: " + trainName);

            if (ticketDetailsLength >= 4) {
                String[] passengerNames = Arrays.copyOfRange(ticketDetails, 1, 2);
                String[] passengerAges = Arrays.copyOfRange(ticketDetails, 2, 3);

                for (int i = 0; i < numTickets; i++) {
                    System.out.println("Passenger Name: " + passengerNames[i] + ", Passenger Age: " + passengerAges[i]);
                }
            } else {
                System.out.println("Error: Incomplete ticket information - " + Arrays.toString(ticketDetails));
            }
        }
    }
    }

    private static void cancelTicket(Scanner scanner) {
    if (bookedTickets.isEmpty()) {
        System.out.println("\nNo tickets booked yet.");
        return;
    }

    System.out.print("Enter the Booking ID to cancel a ticket: ");
    String bookingIdToCancel = scanner.next();

    boolean found = false;

    Iterator<String> iterator = bookedTickets.iterator();
    while (iterator.hasNext()) {
        String ticket = iterator.next();
        String[] ticketDetails = ticket.split(",");
        String bookingId = ticketDetails[0];

        if (bookingId.equals(bookingIdToCancel)) {
            iterator.remove();
            System.out.println("Ticket with Booking ID " + bookingIdToCancel + " cancelled successfully!");
            found = true;

            String passengerName = ticketDetails[1];
            int passengerAge = Integer.parseInt(ticketDetails[2]);
            String trainName = ticketDetails[3]; // Correct index for train name

            Train train = null;
            for (Train availableTrain : availableTrains) {
                if (availableTrain.getName().equals(trainName)) {
                    train = availableTrain;
                    break;
                }
            }

            if (train != null) {
                int numCancelledSeats = 1;
                train.cancelSeats(numCancelledSeats);

                CancelledTicket cancelledTicket = new CancelledTicket(bookingId, trainName, passengerName, passengerAge);
                cancelledTickets.add(cancelledTicket);

                saveAvailableTrains();  
            } else {
                System.out.println("Invalid train name in cancellation record.");
            }

            break;
        }
    }

    if (!found) {
        System.out.println("No booked ticket found with Booking ID " + bookingIdToCancel);
    }
}


    private static String getTrainName(int trainIndex) {
        if (trainIndex >= 0 && trainIndex < availableTrains.size()) {
            return availableTrains.get(trainIndex).getName();
        } else {
            return "Unknown Train";
        }
    }   

    private static void viewCancelledTickets() {
    if (cancelledTickets.isEmpty()) {
        System.out.println("No tickets canceled yet.");
    } else {
        System.out.println("\nCancelled Tickets:");
        for (CancelledTicket cancelledTicket : cancelledTickets) {
            System.out.println("Booking ID: " + cancelledTicket.getBookingId() +
                    ", Train: " + cancelledTicket.getTrainName() +
                    ", Passenger Name: " + cancelledTicket.getPassengerName() +
                    ", Passenger Age: " + cancelledTicket.getPassengerAge());
        }
    }
}


    private static void loadBookedTickets() {
        try (BufferedReader reader = new BufferedReader(new FileReader(BOOKED_TICKETS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                bookedTickets.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error loading booked tickets: " + e.getMessage());
        }

        bookedTickets.removeIf(ticket -> {
            String[] ticketDetails = ticket.split(",");
            if (ticketDetails.length < 4) {
                return true; 
            }

            int numTickets = Integer.parseInt(ticketDetails[2]);
            int ticketDetailsLength = ticketDetails.length;

            for (int i = 0; i < numTickets; i++) {
                int passengerIndex = 1 + i;
                int ageIndex = 1 + numTickets + i;

                if (ticketDetailsLength > passengerIndex && ticketDetails[passengerIndex].trim().isEmpty()) {
                    return true; 
                }

                if (ticketDetailsLength > ageIndex && ticketDetails[ageIndex].trim().isEmpty()) {
                    return true; 
                }
            }
            return false;
        });
    }

    private static void saveBookedTickets() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BOOKED_TICKETS_FILE))) {
            for (String ticket : bookedTickets) {
                writer.write(ticket);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving booked tickets: " + e.getMessage());
        }
    }

    private static void loadCancelledTickets() {
    try (BufferedReader reader = new BufferedReader(new FileReader(CANCELLED_TICKETS_FILE))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] details = line.split(",");
            if (details.length == 4) {
                String bookingId = details[0];
                String trainName = details[1];
                String passengerName = details[2];
                int passengerAge = Integer.parseInt(details[3]);

                CancelledTicket cancelledTicket = new CancelledTicket(bookingId, trainName, passengerName, passengerAge);
                cancelledTickets.add(cancelledTicket);
            }
        }
    } catch (IOException | NumberFormatException e) {
        System.out.println("Error loading cancelled tickets: " + e.getMessage());
    }
}

    private static void saveCancelledTickets() {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(CANCELLED_TICKETS_FILE))) {
        for (CancelledTicket cancelledTicket : cancelledTickets) {
            String line = cancelledTicket.getBookingId() + ","
                    + cancelledTicket.getTrainName() + ","
                    + cancelledTicket.getPassengerName() + ","
                    + cancelledTicket.getPassengerAge();
            writer.write(line);
            writer.newLine();
        }
    } catch (IOException e) {
        System.out.println("Error saving cancelled tickets: " + e.getMessage());
    }
}

}

