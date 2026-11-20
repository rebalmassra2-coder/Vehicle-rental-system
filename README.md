# Vehicle Rental System

A Java console application that manages a vehicle rental office: vehicles, customers, rental contracts, late-return fines and financial reports.

Built to practice core **Object-Oriented Programming** principles.

## Features

- **Vehicles:** add, remove, search and list cars, motorcycles and trucks (duplicate plate numbers are rejected)
- **Customers:** individuals and companies, with search, edit and availability checks
- **Contracts:** rent a vehicle, return it, and view active or ended contracts
- **Pricing:** a surcharge per vehicle type (+5% electric car, +2% motorcycle with sidecar, +5% refrigerated truck) and a discount for company customers
- **Late fines:** 10% of the daily price for each late day
- **VIP customers:** a customer becomes VIP after 5 contracts
- **Reports:** company revenue, most rented vehicles, VIP customers, rentals within a date range
- **Input validation:** the program re-asks on bad input instead of crashing

## OOP Concepts Used

| Concept | Where |
|---|---|
| Abstraction | abstract classes `Vehicle` and `Customer` |
| Inheritance | `Car`, `Motorcycle`, `Truck` extend `Vehicle`; `Individual`, `Company` extend `Customer` |
| Polymorphism | `extraCost()`, `getDiscountRate()`, `printinfo()` behave differently per subclass |
| Encapsulation | private fields with getters and setters, and a validated input layer (`SafeInput`) |

## UML Class Diagram

![UML Class Diagram](docs/uml_class_diagram.png)

## Project Structure

```
src/Project_java2/
  Vehicle.java      (abstract)
  Car.java
  Motorcycle.java
  Truck.java
  Customer.java     (abstract)
  Individual.java
  Company.java
  Contract.java
  SafeInput.java    (input validation)
  Main.java         (menus and program entry)
docs/
  uml_class_diagram.png
```

## How to Run

Requires **JDK 11 or newer**.

```bash
cd src
javac Project_java2/*.java
java Project_java2.Main
```

## Example: Late Return

Rent a vehicle for 2 days at $50/day, then return it 5 days after the start date:

```
Base Cost: $100.0Late Days: 3
Fines: $15.0
Final Cost: $115.0
```

## Future Improvements

- Save data to a file or database (currently everything is in memory)
- Unit tests for pricing and fines (JUnit)
- A GUI version

## Author

**Rebal Massra**
