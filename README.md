# Hospital Management System

## Overview

This project is a Hospital Management System built in Java with PostgreSQL integration using JDBC. It provides functionalities for managing hospital operations, including patient records, doctor appointments, pharmacy details, and more. The system demonstrates Object-Oriented Programming (OOP) principles and features a built-in SQL query tool using a query tree within the Java console.

## Features

PostgreSQL Database Integration with secure database connection handling.

SQL Query Tool implemented via a query tree structure.

Object-Oriented Design separating concerns into different classes.

Hospital Entity Management, including doctors, patients, appointments, and prescriptions.

Console-based User Interface for interacting with the system.

## Technologies Used

Java (Core Logic, JDBC integration)

PostgreSQL (Database Management)

JDBC (Database Connectivity)

## Installation

### Prerequisites

Ensure you have the following installed:

Java (JDK 11 or higher)

PostgreSQL (latest version)

### Setup

Clone the repository:

git clone https://github.com/your-username/hospital-management-system.git
cd hospital-management-system

Configure the PostgreSQL database:

Create a database and tables as required.

Update the database connection settings in DatabaseConnection.java.

Compile and run the project:

javac -d bin src/*.java
java -cp bin Main

## Usage

Run the application from the console.

Navigate through options to manage hospital records.

Use the built-in SQL query tool for database interactions.

## Project Structure

/src
  |-- DatabaseConnection.java   # Manages database connection
  |-- QueryTreeBuilder.java     # Builds SQL query tree
  |-- Hospital.java             # Main hospital entity
  |-- Appointment.java          # Handles appointments
  |-- Doctor.java               # Doctor entity
  |-- PatientApp.java           # Manages patient records
  |-- ConsoleUI.java            # Command-line interface
  |-- Main.java                 # Entry point


## Author

Developed by Lenny Medina & Amit Bar Kama as part of a college project for Databases course.

## Contributing

Feel free to fork this repository and submit pull requests!

