Developer:      Mina Vu
Program:        Project 2: TextFile
Instructor:     David Whitlock
Course Name:    Advance Programming in Java
School Term:    Winter 2022

This program parses the user inputs from the command line arguments.
If the argument requirements are satisfied, the program creates an Airline from the
user-provided name.  The program then creates a Flight from the user-provided
data and adds the flight to the airline.

Users can select the README option to display this message about the program.
Alternate options include print which will display a description of the new flight
and textFile which takes a file name where airline data can be read and written.

usage: java edu.pdx.cs410J.<login-id>.Project1 [options] <args>
    args are (in this order):
        airline         The name of the airline
        flightNumber    The flight number
        src             Three-letter code of departure airport
        depart          Departure date and time (24-hour time)
        dest            Three-letter code of arrival airport
        arrive          Arrival date and time (24-hour time)
    options are (options may appear in any order):
        -textFile file  Where to read/write the airline info
        -print          Prints a description of the new flight
        -README         Prints a README for this project and exits
    Date and time should be in the format: mm/dd/yyyy hh:mm