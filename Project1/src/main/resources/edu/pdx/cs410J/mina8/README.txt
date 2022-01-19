Developer:      Mina Vu
Program:        Project 1: Beginning a Java Application
Instructor:     David Whitlock
Course Name:    Advance Programming in Java
Term:           Winter 2022

This program parses the user inputs from the command line arguments.
See below for argument requirements and usage of program.
The program creates an Airline instance using the name provided by the user.
The program also creates a Flight instance from the information provided by the user.
The Flight is added to the Airline.
Users can select the print option to display the flight information.
Users can also select the readme option to display this message about the program.

usage: java edu.pdx.cs410J.<login-id>.Project1 [options] <args>
args are (in this order):
    airline         The name of the airline
    flightNumber    The flight number
    src             Three-letter code of departure airport
    depart          Departure date and time (24-hour time)
    dest            Three-letter code of arrival airport
    arrive          Arrival date and time (24-hour time)
options are (options may appear in any order):
    -print          Prints a description of the new flight
    -README         Prints a README for this project and exits
Date and time should be in the format: mm/dd/yyyy hh:mm