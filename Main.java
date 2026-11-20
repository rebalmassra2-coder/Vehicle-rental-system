package Project_java2;

import java.util.ArrayList;
import java.time.LocalDate;

public class Main {
    static ArrayList<Vehicle> vehicles = new ArrayList<>();
    static ArrayList<Customer> customers = new ArrayList<>();
    static ArrayList<Contract> contracts = new ArrayList<>();
    static ArrayList<Contract> runningContracts = new ArrayList<>();
    static ArrayList<Contract> endedContracts = new ArrayList<>();
    static double balance = 100000;

    // Reports and Statistics
    public static void reportsAndStatisticsMenu() {

        while (true) {
            System.out.println();
            System.out.println("1- Company revenues" + "\n2- Most rented vehicles" + "\n3- VIP customers"
                    + "\n4- Back to previous menu");
            int choice = SafeInput.readIntRange("Enter the number of the service which you want: ", 1, 4);

            switch (choice) {
                case 1:
                    companyRevenues();
                    break;
                case 2:
                    mostRentedVehicles();
                    break;
                case 3:
                    vipCustomers();
                    break;
                case 4:
                    return;
            }
        }
    }

    public static void companyRevenues() {
        System.out.println("\"Company Revenues Report\"");

        double totalRentalRevenues = 0;

        for (Contract contract : endedContracts) {
            totalRentalRevenues += contract.finalCost();
        }

        System.out.println("Total Revenues from Ended Contracts: $" + totalRentalRevenues);
        System.out.println("Current Treasury Balance: $" + balance);
        System.out.println();
    }

    public static void mostRentedVehicles() {
        System.out.println("\"Most Rented Vehicles\"");

        if (vehicles.isEmpty()) {
            System.out.println("There are no vehicles registered in the system.");
            return;
        }

        int maxRentCount = 0;

        for (Vehicle vehicle : vehicles) {
            int currentVehicleCount = 0;
            for (Contract contract : contracts) {
                if (contract.vehicle.PlateNumber.equals(vehicle.PlateNumber)) {
                    currentVehicleCount++;
                }
            }
            if (currentVehicleCount > maxRentCount) {
                maxRentCount = currentVehicleCount;
            }
        }

        if (maxRentCount == 0) {
            System.out.println("No vehicles have been rented yet.");
            System.out.println();
            return;
        }

        System.out.println("Top rented vehicle(s) with (" + maxRentCount + ") total rentals:\n");
        for (Vehicle vehicle : vehicles) {
            int currentVehicleCount = 0;
            for (Contract contract : contracts) {
                if (contract.vehicle.PlateNumber.equals(vehicle.PlateNumber)) {
                    currentVehicleCount++;
                }
            }
            if (currentVehicleCount == maxRentCount) {

                vehicle.printinfo();
                System.out.println("Rental Frequency: " + currentVehicleCount + " times.");
                System.out.println();
            }
        }
    }

    public static void vipCustomers() {
        System.out.println("\"VIP Customers Report\"");

        boolean isfound = false;
        int i = 1;

        for (Customer customer : customers) {
            if (customer.getNumberOfContracts() >= 5) {
                System.out.println("Customer " + i + ":");
                customer.printinfo();
                isfound = true;
                i++;
            }
        }

        if (!isfound) {
            System.out.println("There are no VIP customers");
        }
        System.out.println();
    }

    // Contracts Management
    public static void contractsManagementMenu() {
        while (true) {
            System.out.println("\n\"Contracts Management Section\"");
            System.out.println("1- Rent a Vehicle (Create Contract)" + "\n2- Return a Vehicle (End Contract)"
                    + "\n3- Show all contracts" + "\n4- Contracts & Vehicles" + "\n5- Contracts & Customers"
                    + "\n6- Back to previous menu");

            int choice = SafeInput.readIntRange("Enter choice: ", 1, 6);

            switch (choice) {
                case 1:
                    rentVehicle();
                    break;
                case 2:
                    returnVehicle();
                    break;
                case 3:
                    showAllContractsMenu();
                    break;
                case 4:
                    contractsAndVehiclesMenu();
                    break;
                case 5:
                    contractsAndCustomersMenu();
                    break;
                case 6:
                    return;
            }
        }
    }

    // Contracts Management - Create, End Contracts
    public static void rentVehicle() {
        if (vehicles.size() == 0) {
            System.out.println("There are no vehicles available for rent");
            return;
        }
        System.out.println("Here are the available vehicles");
        showTheAvailabeVehicles();

        String plateNum = SafeInput
                .readStringButOnlyNumbers("Enter the plate number of the vehicle you want to rent: ");

        Vehicle selectedVehicle = null;
        for (Vehicle vehicle : vehicles) {
            if (vehicle.PlateNumber.equals(plateNum)) {
                selectedVehicle = vehicle;
                break;
            }
        }

        if (selectedVehicle == null) {
            System.out.println("Vehicle not found or invalid plate number");
            return;
        }

        if (!selectedVehicle.Available) {
            System.out.println("This vehicle is not available for rent");
            return;
        }

        System.out.println("This is your vehicle:");
        selectedVehicle.printinfo();
        System.out.println("The daily price of the vehicle is: $" + selectedVehicle.DailyPrice);

        // The surcharge comes from the vehicle itself (polymorphism), so it is only
        // shown when this specific vehicle actually has one.
        if (selectedVehicle.extraCost() > 0) {
            String reason = selectedVehicle.getType().equals("Car") ? "electric car"
                    : selectedVehicle.getType().equals("Motorcycle") ? "sidecar" : "refrigeration";
            System.out.println("The " + reason + " costs extra "
                    + String.format("%.0f", selectedVehicle.extraCost() * 100) + "%");
        }

        System.out.println("Each late day of the return costs extra 10%");
        System.out.println(
                "If you rent a vehicle and return it before the return date, you still have to pay for the full contract days");
        showAvailableCustomers();
        Customer contractCustomer = null;

        while (contractCustomer == null) {
            System.out.println("\nPress 1 to enter your ID");
            System.out.println("Press 2 to add yourself as a new customer");
            System.out.println("Press 3 to cancel rental");

            int choice = SafeInput.readIntRange("Your choice: ", 1, 3);

            if (choice == 1) {
                String ID = SafeInput.readNumericString("Enter your ID: ");
                Customer foundCustomer = null;
                for (Customer customer : customers) {
                    if (customer.ID.equals(ID)) {
                        foundCustomer = customer;
                        break;
                    }
                }
                if (foundCustomer == null) {
                    System.out.println("Customer ID not found. Please try again or register as a new customer.");
                } else if (foundCustomer.hasRented) {
                    System.out.println("This customer already has an active rental. Return the vehicle first.");
                } else {
                    contractCustomer = foundCustomer;
                    foundCustomer.hasRented = true;
                }
            } else if (choice == 2) {
                System.out.println("Please use the 'Add Customer' option from the main menu first.");
                System.out.println("Then come back to rent a vehicle.");
                return;
            } else if (choice == 3) {
                System.out.println("Rental cancelled.");
                return;
            }
        }

        LocalDate rentStartDate = LocalDate.now();
        System.out.println("Current date: " + rentStartDate);
        int numOfRentDays = SafeInput.readIntRange("Enter number of rental days: ", 1, 365);

        double contractBasePrice = selectedVehicle.DailyPrice * numOfRentDays;

        selectedVehicle.Available = false;

        Contract contract = new Contract(selectedVehicle, contractCustomer, rentStartDate, numOfRentDays,
                contractBasePrice);
        contracts.add(contract);
        runningContracts.add(contract);

        System.out.println("Rental successful!");
        System.out.println("Contract ID: " + contract.ID);
        System.out.println("Total days: " + numOfRentDays);
        System.out.println("Base price: $" + contract.basePrice);
        System.out.println("Vehicle has been marked as unavailable.");
    }

    public static void returnVehicle() {
        String contractID = SafeInput.readNumericString("Enter contract ID: ");

        // Only RUNNING contracts can be ended
        Contract target = null;
        for (Contract contract : runningContracts) {
            if (contract.ID.equals(contractID)) {
                target = contract;
                break;
            }
        }

        if (target == null) {
            boolean alreadyEnded = false;
            for (Contract contract : endedContracts) {
                if (contract.ID.equals(contractID)) {
                    alreadyEnded = true;
                    break;
                }
            }
            System.out.println(alreadyEnded ? "This contract has already been ended." : "Couldn't find contract ID.");
            return;
        }

        LocalDate returnDate = SafeInput.readDate("Enter the actual return date: ");
        if (returnDate.isBefore(target.startDate)) {
            System.out.println("Error: Return date cannot be before the contract start date (" + target.startDate + ").");
            return;
        }

        target.endContract(returnDate);

        System.out.println("\n\"Final Bill\"");
        System.out.println("Base Cost: $" + target.basePrice);
        System.out.println("Late Days: " + target.numOfLateDays);
        System.out.println("Fines: $" + target.fines);
        System.out.println("Final Cost: $" + target.finalCost());

        balance += target.finalCost();
        target.vehicle.Available = true;
        target.customer.hasRented = false; // the customer can rent again

        runningContracts.remove(target);
        endedContracts.add(target);
    }

    // Contracts Management - Show all contracts
    public static void showAllContractsMenu() {

        while (true) {
            System.out.println();
            System.out.println("1- Active contracts" + "\n2- Expired contracts" + "\n3- Back to previous menu");

            int choice = SafeInput.readIntRange("Enter the contracts status to display: ", 1, 3);

            switch (choice) {
                case 1:
                    showActiveContracts();
                    break;
                case 2:
                    showExpiredContracts();
                    break;
                case 3:
                    return;
            }

        }
    }

    public static void showActiveContracts() {
        if (runningContracts.size() != 0) {
            for (Contract contract : runningContracts) {
                contract.printRunningContractsInfo();
            }
        } else {
            System.out.println("There are no running contracts");
        }
    }

    public static void showExpiredContracts() {
        if (endedContracts.size() != 0) {
            for (Contract contract : endedContracts) {
                contract.printEndedContractsInfo();
            }
        } else {
            System.out.println("There are no ended contracts");
        }
    }

    // Contracts Management - Contracts and Vehicle
    public static void contractsAndVehiclesMenu() {

        while (true) {
            System.out.println();
            System.out.println("1- Rented vehicles" + "\n2- Vehicles with fines"
                    + "\n3- Rented vehicles within a specific period"
                    + "\n4-Rented motorcycles within a specific period"
                    + "\n5- Back to previous menu");
            int choice = SafeInput.readIntRange("Enter the number of the service which you want: ", 1, 5);

            switch (choice) {
                case 1:
                    rentedVehicles();
                    break;
                case 2:
                    vehiclesWithFines();
                    break;
                case 3:
                    rentedVehiclesWithinAspecificPeriod();
                    break;
                case 4:
                    rentedMotorcyclesWithinAspecificPeriod();
                    break;
                case 5:
                    return;
            }
        }
    }

    public static void rentedVehicles() {
        int i = 1;
        boolean isfound = false;
        for (Vehicle vehicle : vehicles) {
            if (!vehicle.Available) {
                System.out.println("vehicle " + i + ":");
                vehicle.printinfo();
                i++;
                isfound = true;
            }
        }
        if (!isfound) {
            System.out.println("There are no rented vehicles now!");
        }
    }

    public static void vehiclesWithFines() {
        int i = 1;
        boolean found = false;
        for (Contract contract : runningContracts) {
            if (contract.shouldReturnDate.isBefore(LocalDate.now())) {
                System.out.println("vehicle " + i + ":");
                contract.vehicle.printinfo();
                i++;
                found = true;
            }
        }
        if (!found) {
            System.out.println("There are no vehicles with fines now");
        }

    }

    public static void rentedVehiclesWithinAspecificPeriod() {
        LocalDate startDate;
        LocalDate endDate;
        int i = 1;
        while (true) {
            startDate = SafeInput.readDate("Enter start date");
            endDate = SafeInput.readDate("Enter end date");
            if (!startDate.isAfter(endDate)) {
                break;
            }
            System.out.println("Error: The start date must not be after the end date.");
        }
        for (Contract contract : contracts) {
            if (!contract.startDate.isBefore(startDate) && !contract.startDate.isAfter(endDate)) {
                System.out.println("Vehicle " + i + ":");
                contract.vehicle.printinfo();
                i++;
            }
        }
    }

    public static void rentedMotorcyclesWithinAspecificPeriod() {
        LocalDate startDate;
        LocalDate endDate;
        int i = 1;
        while (true) {
            startDate = SafeInput.readDate("Enter start date");
            endDate = SafeInput.readDate("Enter end date");
            if (!startDate.isAfter(endDate)) {
                break;
            }
            System.out.println("Error: The start date must not be after the end date.");
        }
        for (Contract contract : contracts) {
            if (!contract.startDate.isBefore(startDate) && !contract.startDate.isAfter(endDate)) {
                if (contract.vehicle.getType().equalsIgnoreCase("motorcycle")) {
                    System.out.println("Motorcycle " + i + ":");
                    contract.vehicle.printinfo();
                    i++;
                }

            }
        }
    }

    // Contracts Management - Contracts and Customers
    public static void contractsAndCustomersMenu() {
        while (true) {
            System.out.println();
            System.out.println(
                    "1- All contracts for a specific customer"
                            + "\n2- Show the customers who rented a specific car"
                            + "\n3- Show Company Customers"
                            + "\n4- Back to previous menu");

            int choice = SafeInput.readIntRange("Enter the number of the service which you want: ", 1, 4);

            switch (choice) {
                case 1:
                    contractsOfSpecificCustomerMenu();
                    break;
                case 2:
                    customerWhoRentedAspecificCar();
                    break;
                case 3:
                    CompanyCustomers();
                    break;
                case 4:
                    return;
            }
        }
    }

    public static void contractsOfSpecificCustomerMenu() {
        String name = SafeInput.readString("Enter customer name: ");
        int i = 1;
        boolean found = false;
        boolean foundContracts = false;
        for (Customer customer : customers) {
            if (customer.Name.equalsIgnoreCase(name)) {
                found = true;
                for (Contract contract : contracts) {
                    if (contract.customer == customer) {
                        foundContracts = true;
                        System.out.println("Contract " + i + ":");
                        contract.displayContractInfo();
                        i++;
                    }
                }
            }
        }
        if (!found) {
            System.out.println("The name dosen't exist");
        }
        if (!foundContracts) {
            System.out.println("The customer doesn't have any contracts");
        }
    }

    public static void customerWhoRentedAspecificCar() {
        String plateNum = SafeInput.readNumericString("Enter vehicle Plate Number:");
        int i = 1;
        boolean found = false;
        for (Contract contract : contracts) {
            if (contract.vehicle.PlateNumber.equals(plateNum)) {
                System.out.println("Customer " + i + ":");
                contract.customer.printinfo();
                i++;
                found = true;
            }
        }
        if (!found) {
            System.out.println("This vehicle has not been rented");
        }
    }

    public static void CompanyCustomers() {
        // Each company customer is listed once, even if it has several contracts
        int i = 1;
        for (Customer customer : customers) {
            if (customer.getType().equalsIgnoreCase("company") && customer.getNumberOfContracts() > 0) {
                System.out.println("Company " + i + ":");
                customer.printinfo();
                i++;
            }
        }
        if (i == 1) {
            System.out.println("There are no company customers with contracts");
        }
    }

    // Customers Management
    public static void customersManagementMenu() {

        while (true) {
            System.out.println("\"Customers Management Section\"");
            System.out.println("1- Add customer" + "\n2- Search for a customer" + "\n3- Show available customers"
                    + "\n4- Show All Customers" + "\n5- Edit a customer's data" + "\n6- Back to previous menu");

            int choice = SafeInput.readIntRange("Enter the number of the service which you want: ", 1, 6);

            switch (choice) {
                case 1:
                    addCustomer();
                    break;
                case 2:
                    searchForCustomer();
                    break;
                case 3:
                    showAvailableCustomers();
                    break;
                case 4:
                    showAllCustomers();
                    break;
                case 5:
                    editCustomer();
                    break;
                case 6:
                    return;
            }
        }
    }

    public static void addCustomer() {
        int type = SafeInput.readIntRange("Enter customer type: 1-Individual 2-Company\n", 1, 2);

        System.out.println("Fill the following form:");

        String name = SafeInput.readString("Name: ");

        String address = SafeInput.readLabel("Address: ");

        String phoneNumber = SafeInput.readStringButOnlyNumbers("Phone Number: ");

        Customer customer = null;

        switch (type) {
            case 1:
                String license = SafeInput.readNumericString("Driving License Number: ");
                LocalDate birth = SafeInput.readValidBirthDate("Birth Date");
                customer = new Individual(name, address, phoneNumber, license, birth);
                break;

            case 2:
                String commercialRegistrationNumber = SafeInput.readNumericString("Commercial Registration Number: ");
                double discountPercentage = SafeInput.readDoubleRange("Discount Percentage (0-100): ", 0, 100);
                customer = new Company(name, address, phoneNumber, discountPercentage, commercialRegistrationNumber);
                System.out.println();
                break;
            default:
                System.out.println("Invalid Customer type");
                return;
        }
        customers.add(customer);
        System.out.println("Customer added");
    }

    public static void searchForCustomer() {

        int Type = SafeInput.readIntRange("search cutomer : \n" + "1- ID" + "\n2- Name \n", 1, 2);
        switch (Type) {
            case 1:
                String ID = SafeInput.readNumericString("Enter customer ID: ");
                boolean found = false;
                for (Customer customer : customers) {
                    if (customer.ID.equals(ID)) {
                        customer.printinfo();
                        found = true;
                        break;
                    }
                }
                if (found == false) {
                    System.out.println("Customer not found");
                }
                break;
            case 2:
                String name = SafeInput.readString("Enter the name please : ");
                boolean isfound = false;
                for (Customer customer : customers) {
                    if (customer.Name.equalsIgnoreCase(name)) {
                        customer.printinfo();
                        isfound = true;
                        break;

                    }
                }
                if (!isfound) {
                    System.out.println("Customer not found");
                }
                break;
            default:
                System.out.println("Enter 1 or 2 !");
        }

    }

    public static void showAvailableCustomers() {
        if (customers.isEmpty()) {
            System.out.println("There are no customers");
            return;
        }

        boolean found = false;
        for (Customer customer : customers) {
            if (!customer.hasRented) {
                customer.printinfo();
                System.out.println();
                found = true;
            }
        }
        if (!found) {
            System.out.println("There is no available customer");
        }
    }

    public static void showAllCustomers() {
        if (customers.size() != 0) {
            for (int i = 0; i < customers.size(); i++) {
                customers.get(i).printinfo();
                System.out.println();
            }
        }

        else {
            System.out.println("There are no customers");
        }
    }

    public static void editCustomer() {
        String ID = SafeInput.readNumericString("Enter Customer ID to update: ");

        boolean found = false;
        for (Customer customer : customers) {
            if (customer.ID.equals(ID)) {
                found = true;
                System.out.println("Current customer info:");
                customer.printinfo();
                System.out.println("Enter new info, or enter u to keep the current value");
                String newName = SafeInput.readOptionalString("New name", SafeInput.NAME);
                if (newName != null) {
                    customer.Name = newName;
                }
                String newAddress = SafeInput.readOptionalString("New address", SafeInput.LABEL);
                if (newAddress != null) {
                    customer.Address = newAddress;
                }
                String newPhoneNumber = SafeInput.readOptionalString("New phone number", SafeInput.DIGITS);
                if (newPhoneNumber != null) {
                    customer.phoneNumber = newPhoneNumber;
                }
                System.out.println("Customer updated");
                break;
            }
        }
        if (!found) {
            System.out.println("Customer could not be found");
        }
    }

    // Vehicles Management
    public static void vehiclesManagementMenu() {

        while (true) {
            System.out.println("\"Vehicles Management Section\"");
            System.out.println(
                    "1- Add vehicle"
                            + "\n2- Remove vehicle"
                            + "\n3- Show the available vehicles"
                            + "\n4- Search for a vehicle"
                            + "\n5- Show all vehicle"
                            + "\n6- Back to previous menu");

            int choice = SafeInput.readIntRange("Enter the number of the service which you want: ", 1, 6);

            switch (choice) {
                case 1:
                    addVehicle();
                    break;
                case 2:
                    removeVehicle();
                    break;
                case 3:
                    showTheAvailabeVehicles();
                    break;
                case 4:
                    searchForVehicle();
                    break;
                case 5:
                    showAllVehicles();
                    break;
                case 6:
                    return;
            }
        }
    }

    private static boolean plateExists(String plateNumber) {
        for (Vehicle v : vehicles) {
            if (v.PlateNumber.equals(plateNumber)) {
                return true;
            }
        }
        return false;
    }

    public static void addVehicle() {
        System.out.println();
        int type = SafeInput.readIntRange("1-Car \n2-Motorcycle \n3-Truck \nEnter vehicle type: ", 1, 3);

        System.out.println("Fill the following form:");

        // The plate number must be unique across all vehicles
        String plateNumber;
        while (true) {
            plateNumber = SafeInput.readStringButOnlyNumbers("Plate Number: ");
            if (!plateExists(plateNumber)) {
                break;
            }
            System.out.println("This plate number already exists! Please try again.");
        }

        String brand = SafeInput.readLabel("Brand: ");
        String model = SafeInput.readLabel("Model: ");
        double dailyPrice = SafeInput.readPositiveDouble("Daily Price: ");

        Vehicle vehicle;

        switch (type) {
            case 1:
                String fuelType = SafeInput.readFuelType("Fuel Type");
                int numOfSeats = SafeInput.readIntRange("Number of Seats: ", 1, 60);
                boolean ac = SafeInput.readBoolean("Air Condition");
                vehicle = new Car(plateNumber, brand, model, dailyPrice, fuelType, numOfSeats, ac);
                break;

            case 2:
                double engineCapacity = SafeInput.readPositiveDouble("Engine Capacity (CC): ");
                boolean sidecar = SafeInput.readBoolean("Sidecar Availability");
                vehicle = new Motorcycle(plateNumber, brand, model, dailyPrice, engineCapacity, sidecar);
                break;

            default:
                double load = SafeInput.readPositiveDouble("Load Capacity (KG): ");
                boolean fridge = SafeInput.readBoolean("Refrigeration Support");
                vehicle = new Truck(plateNumber, brand, model, dailyPrice, load, fridge);
                break;
        }

        vehicles.add(vehicle);
        System.out.println("The " + vehicle.getType() + " has been added ");
        System.out.println();
    }

    public static void removeVehicle() {
        String plateNum = SafeInput.readNumericString("Enter vehicle plate number to remove: ");

        for (int i = 0; i < vehicles.size(); i++) {
            Vehicle v = vehicles.get(i);
            if (v.PlateNumber.equals(plateNum)) {
                if (v.Available) {
                    vehicles.remove(i);
                    System.out.println("Vehicle removed");
                } else {
                    System.out.println("Can't remove vehicle, vehicle is rented");
                }
                return;
            }
        }
        System.out.println("Vehicle could not be found");
    }

    public static void showTheAvailabeVehicles() {
        boolean found = false;
        for (int i = 0; i < vehicles.size(); i++) {
            if (vehicles.get(i).Available) {
                vehicles.get(i).printinfo();
                found = true;
                System.out.println();
            }
        }
        if (!found) {
            System.out.println("There are no available vehicles");
        }
    }

    public static void searchForVehicle() {
        String plateNum = SafeInput.readNumericString("Enter plate number: ");

        boolean found = false;
        for (Vehicle vehicle : vehicles) {
            if (vehicle.PlateNumber.equals(plateNum)) {
                vehicle.printinfo();
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Vehicle not found");
        }
    }

    public static void showAllVehicles() {
        if (vehicles.size() != 0) {
            for (int i = 0; i < vehicles.size(); i++) {
                vehicles.get(i).printinfo();
                System.out.println();
            }
        } else {
            System.out.println("There are no vehicles");
        }
    }

    // MAIN MENU
    public static void mainMenu() {
        while (true) {
            System.out.println("\nWhich service do you need?");
            System.out.println(
                    "1- Vehicles Management"
                            + "\n2- Customers Management"
                            + "\n3- Contracts Management"
                            + "\n4- Reports and Statistics"
                            + "\n5- Exit the Program");

            int choice = SafeInput.readIntRange("\nEnter the number of the service which you want: ", 1, 5);

            switch (choice) {
                case 1:
                    vehiclesManagementMenu();
                    break;
                case 2:
                    customersManagementMenu();
                    break;
                case 3:
                    contractsManagementMenu();
                    break;
                case 4:
                    reportsAndStatisticsMenu();
                    break;
                case 5:
                    System.out.println("Exit Successful.");
                    return;
            }

        }
    }

    public static void main(String[] args) {

        vehicles.add(new Car("4128542", "Toyota", "Camry", 50.0, "petrol", 5, true));
        vehicles.add(new Car("789456", "Kia", "Rio", 42.0, "electric", 5, true));
        vehicles.add(new Motorcycle("654987", "Harley", "Davidson", 35.0, 1200, true));
        vehicles.add(new Motorcycle("4584", "Kawasaki", "Ninja H2R", 190.0, 998, false));
        vehicles.add(new Truck("455412", "Volvo", "FH16", 90.0, 8000, true));

        customers.add(
                new Individual("Yessayie", "Damascus", "0994 592 915", "06001237", LocalDate.of(2007, 7, 13)));
        customers.add(
                new Company("Cham City Center", "Damascus", "011 562 8889", 23, "CCDU7782"));

        System.out.println("\nWelcome to our System!");
        mainMenu();

    }
}