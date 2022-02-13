Developer:      Mina Vu
Program:        Project 4 Storing Airlines as XML
Instructor:     David Whitlock
Course Name:    Advance Programming in Java
School Term:    Winter 2022

This program parses the user inputs from the command line arguments.
If the argument requirements are satisfied, the program creates an Airline from the
user-provided name.  The program then creates a new Flight from the user-provided
data and adds the new flight to the airline.

Users can provide a text file with airline and flight information delimited by "|"
and this program will parse the file to create an airline with the associated flights
and add the new flight from the command line arguments to the airline.  The airline
will be saved once again the text file.  All flights will be ordered.

Users can elect to pretty print the airline to a file.  This simply gives a nice
representation of the airline and flights.  Pretty printing can be outputted to stdout.

Users can elect to parse and dump airline to an xml file conforming to the airline.dtd
format instructions.  A text file containing airline information delimited by "|" can
also be converted into xml format using a converter.

Users can select the README option to display this message about the program.
Alternate options include various ways to read in data and write out to different formats.

usage: java -jar target/airline-2022.0.0.jar [options] <args>
    args are (in this order):
        airline         The name of the airline
        flightNumber    The flight number
        src             Three-letter code of departure airport
        depart          Departure date and time (24-hour time)
        dest            Three-letter code of arrival airport
        arrive          Arrival date and time (24-hour time)
    options are (options may appear in any order):
        -xmlFile file   Where to read/write the airline info (cannot be called with -textFile option)
        -textFile file  Where to read/write the airline info (cannot be called with -xmlFile option)
        -pretty file    Pretty print the airline's flights to a text file or stdout (file indicated as -)
        -print          Prints a description of the new flight
        -README         Prints a README for this project and exits
    Date and time should be in the format: mm/dd/yyyy hh:mm a