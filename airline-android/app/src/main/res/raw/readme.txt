Developer:      Mina Vu
Program:        Project 6 Small Android Devices
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