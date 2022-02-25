Developer:      Mina Vu
Program:        Project 5 A REST-ful Airline Web Service
Instructor:     David Whitlock
Course Name:    Advance Programming in Java
School Term:    Winter 2022

This program is a two-in-one client-and-servlet application for an airline web service.
The servlet must be running for the client program to work properly.  The servlet
allows posting of a new flight to an existing or new airline, getting of all flights in
an existing airline, and getting of a subset of flights in an airline when the source
and destination are provided.  The servlet is listening for connection on port 8080.

The client program parses the user inputs from the command line arguments.
If users want to create a new flight and has provided all required arguments, the program
will have the client communicate with the servlet to add the flight with the arguments.
If a flight cannot be created due to error in the arguments, the servlet will tell that
to the client and the client will handle it gracefully.

Users can select the README option to display this message about the program.

usage: java -jar target/airline-client.jar [options] <args>
    args are (in this order):
        airline         The name of the airline
        flightNumber    The flight number
        src             Three-letter code of departure airport
        depart          Departure date and time (AM/PM time)
        dest            Three-letter code of arrival airport
        arrive          Arrival date and time (AM/PM time)
    options are (options may appear in any order):
        -host hostname	Host computer on which the server runs
        -port port		Port on which the server is listening
        -search			Search for flights
        -print          Prints a description of the new flight
        -README         Prints a README for this project and exits
    Date and time should be in the format: mm/dd/yyyy hh:mm a