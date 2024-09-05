## Railway Ticket Booking System
The Railway Ticket Booking System is a simple Java application that allows users to book and cancel train tickets, view available trains, and see booked and canceled tickets. It uses basic file handling to store data about bookings and cancellations.

## Features
- Display Available Trains: View a list of available trains, including the number of seats available for booking.
- Book Tickets: Easily book tickets by selecting a train and entering passenger details. The system ensures that seat availability is checked before booking.
- View Booked Tickets: See a detailed list of all tickets that have been booked, along with the passenger details.
- Cancel Tickets: Cancel a previously booked ticket using the Booking ID. The system updates the seat availability accordingly.
- View Cancelled Tickets: Check the list of tickets that have been canceled.
- Persistence: Data is stored using file handling, allowing the system to keep track of bookings and cancellations between sessions.

## Technologies Used
- Java: Core programming language used to develop the system.
- File Handling: Used to store and retrieve booking and cancellation data.
- Command-Line Interface (CLI): The system is operated through a text-based user interface.

## How to Run the Program
- Clone the repository to your local machine:
git clone https://github.com/yourusername/Railway-Ticket-Booking-System.git

- Compile the Java source code:
javac RailwayTicketBooking.java

- Run the program:
java RailwayTicketBooking

## Follow the Instructions
- Main Menu: Upon running the program, you'll be presented with a main menu that offers various options like viewing available trains, booking tickets, viewing booked tickets, canceling tickets, and viewing canceled tickets.
- Booking a Ticket: Select the option to book a ticket, then choose a train, specify the number of seats, and provide passenger details. The system will confirm the booking and update the seat availability.
- Canceling a Ticket: If you need to cancel a ticket, select the cancellation option and enter the Booking ID. The system will mark the ticket as canceled and adjust the seat count.
- Viewing Tickets: You can view all booked and canceled tickets at any time by selecting the appropriate menu options.

## Output
Below are example screenshots of the program in action:

- Image 1:

 ![Run Configuration](https://i.imgur.com/wFpSA94.png) 
 
- Image 2:

  ![Run Configuration](https://i.imgur.com/bkK3fFN.png)
  
- Image 3:

 ![Run Configuration](https://i.imgur.com/AOdBCcw.png) 
 
- Image 4:

 ![Run Configuration](https://i.imgur.com/BdiFT3k.png) 
 
## Example Usage
- Start the program and choose the option to display available trains.
- Book a ticket for a train by selecting the train, specifying the number of seats, and entering passenger details.
- View your booked ticket to confirm the booking details.
- Cancel a ticket if needed by entering the Booking ID.
- View the canceled tickets to see the status of the cancellation.

## Future Enhancements
- Graphical User Interface (GUI): Transition from a CLI-based system to a more user-friendly GUI.
- Database Integration: Replace file handling with a database for more efficient data storage and retrieval.
- Advanced Booking Features: Implement features such as seat selection, special discounts, and notifications.
- User Authentication: Add a user login system to manage individual bookings and cancellations.
