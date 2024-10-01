import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    public static final String DATABASE_URL = "jdbc:mysql://localhost:3306/KailuaCarRental";
    public static Connection con;

    public static void main(String[] args) throws IOException, SQLException {

        try {
            con = DriverManager.getConnection(DATABASE_URL,"root","Ma2404ro@@@@");

            //*** now that the connection is established we do the query
            Statement s = con.createStatement();

            /*ResultSet rs = s.executeQuery("SELECT vendor_name,  vendor_city  from vendors where default_account_number > '500'");

            // if the resultset is not empty, we position to first row and display first field
            if (rs != null)
                while (rs.next()) {
                    System.out.println("Data for name: " + rs.getString("vendor_name"));
                    System.out.printf("\n%s  %s\n ","Data for City: ",rs.getString("vendor_city"));
                }*/
            s.close();
            con.close();
        }catch (SQLException sqlex) {
            System.out.println(sqlex.getMessage());
            System.exit(1);  // terminate program
        }


        Scanner scan = new Scanner(new File("src/Cars List"));
        Scanner input = new Scanner(System.in);
        Scanner readFileContract = new Scanner(new File("src/Contracts"));
        ArrayList<Car> listOfCars = readFromFile(scan);
        Collections.sort(listOfCars, new ComparableBrand());
        ArrayList<Customer> customers = readFromFileCustomers();
        ArrayList<CustomerContract> contracts = readFromFileContracts(readFileContract, customers, listOfCars);

        UI.hovedMenu(input, listOfCars, contracts,customers); //readFromFileContracts(scan) returns contracts that are read from the Contracts txt file

    }//end of main

    //readFromFile: Cars List to arrayList<Car>
    public static ArrayList<Car> readFromFile(Scanner scan) {
        ArrayList<Car> listOfCar = new ArrayList<>();

        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            if (line.equals("End of file")) {
                break;
            }
            Scanner linescan = new Scanner(line);

            String brand = "";
            while (linescan.hasNext() && !linescan.hasNext(";")) {
                brand += linescan.next();
            }
            linescan.next();
            //System.out.println(brand);

            String model = " ";
            while (linescan.hasNext() && !(linescan.hasNext(";"))) {
                model += linescan.next();
            }
            linescan.next();
            //System.out.println(model);

            String fueltype = " ";
            while (linescan.hasNext() && !(linescan.hasNext(";"))) {
                fueltype += linescan.next();
            }
            linescan.next();
            //System.out.println(fueltype);

            String registrationNb = " ";
            while (linescan.hasNext() && !(linescan.hasNext(";"))) {
                registrationNb += linescan.next();
            }
            linescan.next();
            //System.out.println(registrationNb);

            LocalDate firstRegistrationDate = LocalDate.now();
            while (linescan.hasNext() && !(linescan.hasNext(";"))) {
                firstRegistrationDate = LocalDate.parse(linescan.next());
            }
            linescan.next();
            // System.out.println(firstRegistrationDate);

            int odometer = 0;
            while (linescan.hasNext() && !(linescan.hasNext(";"))) {
                odometer = linescan.nextInt();
            }
            linescan.next();
            //System.out.println(odometer);

            String description = " ";
            while (linescan.hasNext() && !(linescan.hasNext(";"))) {
                description += linescan.next() + " ";
            }
            linescan.next();
            //System.out.println(description);

            boolean automaticTransmission = true;
            while (linescan.hasNext() && !(linescan.hasNext(";"))) {
                automaticTransmission = linescan.nextBoolean();
            }
            linescan.next();
            //System.out.println(automaticTransmission);

            boolean AC = true;
            while (linescan.hasNext() && !(linescan.hasNext(";"))) {
                AC = linescan.nextBoolean();
            }
            linescan.next();
            //System.out.println(AC);

            boolean borrowed = true;
            while (linescan.hasNext() && !(linescan.hasNext(";"))) {
                borrowed = linescan.nextBoolean();
            }
            linescan.next();
            //System.out.println(borrowed);

            int seats = 0;
            while (linescan.hasNext() && !(linescan.hasNext(";"))) {
                seats = linescan.nextInt();
            }
            //linescan.next();
            //System.out.println(seats);

            Car newcar = new Car(brand.trim(), model.trim(), fueltype.trim(), registrationNb.trim(), firstRegistrationDate, odometer, description.trim(), automaticTransmission, AC, borrowed, seats);
            //.trim() til at fjerne de mellemrum der kommer i slutningen af string.
            listOfCar.add(newcar);

        }//end while hasNextLine

        return (listOfCar);

    }//end readFromFile

    public static Car addCar(Scanner input) {
        Car newCar = new Car();
        try {
            System.out.println("To add a new car, first we need some information");
            System.out.println("Enter the brand of the car:");
            String brand = input.nextLine();
            System.out.println("Now enter the model:");
            String model = input.nextLine();
            System.out.println("Enter the fueltype:");
            String fueltype = input.nextLine();
            System.out.println("Enter the registration number:");
            String registrationNb = input.nextLine();
            System.out.println("Enter the date of the first registration, in the format year-month-date::");
            LocalDate firstRegDate = LocalDate.parse(input.nextLine());
            System.out.println("Enter the kilometers on the odometer");
            int odometer = input.nextInt();
            input.nextLine();
            System.out.println("Add a description");
            String description = input.nextLine();
            System.out.println("Does the car have automatic transmission? true for yes / false for no");
            boolean automaticTransmission = input.nextBoolean();
            System.out.println("Does the car have air conditioning? true for yes / false for no");
            boolean AC = input.nextBoolean();
            System.out.println("Is the car currently rented out? true for yes / false for no");
            boolean borrowed = input.nextBoolean();
            System.out.println("Please enter the number of seats in the car:");
            int seats = input.nextInt();
            newCar = new Car(brand, model, fueltype, registrationNb, firstRegDate, odometer, description, automaticTransmission, AC, borrowed, seats);
        }catch(InputMismatchException e){
            System.out.println("Follow the instructions on entering data");
            addCar(input);
        }catch(DateTimeParseException h){
            System.out.println("Follow the given format for entering dates, year-month-day");
            addCar(input);
        }
        return newCar;
    }

    //method that write a new car in the txt-file
    public static void writeToFile(ArrayList<Car> carsList) throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter("src/Cars List"));
        for (Car car : carsList) {
            out.write(car.toString());
        }
        out.write("End of file");
        out.close();
    }//end of writetofile

    /*public static void makeCustomer(Scanner input, ArrayList<Customer>allcustomers){

    }*/

    public static CustomerContract makeContract(Scanner input, ArrayList<Car> listOfCars, ArrayList<CustomerContract> contracts,ArrayList<Customer> allCustomers) throws IOException {
        CustomerContract newContract = new CustomerContract();
        try {

            System.out.println("Enter the start date of the period you want to rent a car\nUse the format year-month-date");
            LocalDate startDate = LocalDate.parse(input.nextLine());

            System.out.println("And for how many days would you like to rent the car?");
            int daysOfRental = input.nextInt();

            LocalDate endDate = startDate.plusDays(daysOfRental);
            System.out.println("These are the available cars for the period: " + startDate + " to " + endDate + ":");
            int i = 1;
            ArrayList<Car> availableCars = availableCars(listOfCars, startDate, endDate, contracts);
            for (Car c : availableCars) {
                System.out.println("          Car number: " + i);
                System.out.println(c.toPrint());
                System.out.println();
                i++;
            }
            System.out.println("Which car would you like to rent? please enter the corresponding number ");
            int carIndex = input.nextInt();
            if((carIndex-1)>availableCars.size() || (carIndex-1)<0){
                System.out.println("There is no corresponding car to the entered number, try again");
                makeContract(input, listOfCars,contracts,allCustomers);
            }

            System.out.println("How many km's do you expect you will be driving during the rental period?");
            int maxKm = input.nextInt();

            Customer newCustomer = newCustomer(input, allCustomers);

            newContract = new CustomerContract(makeContractNumber(contracts), newCustomer, startDate, endDate, availableCars.get(carIndex - 1), maxKm, availableCars.get(carIndex - 1).getOdometer());
            contracts.add(newContract);
            System.out.println(newContract.toPrint());
            System.out.println("Your booking has been registered, thank you for chosing us for your rental needs");
            writeToFileContract(contracts);

        }catch(DateTimeParseException h){
            System.out.println("Follow the given format for entering dates, year-month-day");
            input.nextLine();
            System.out.println();
            makeContract(input, listOfCars, contracts,allCustomers);
        }
        catch(InputMismatchException e){
            System.out.println("Follow the instructions on entering data");
            input.nextLine();
            System.out.println();
            makeContract(input, listOfCars, contracts,allCustomers);
        }
        return newContract;
    }//end of makeContract

    public static void writeToFileContract(ArrayList<CustomerContract>contracts) throws IOException {
        FileWriter fw = new FileWriter(new File("src/contracts"), false);
        for (CustomerContract c : contracts) {
            if (c.getCustomer() instanceof PrivateCustomer) {
                fw.write(c.toFile());
            }
        }
        fw.write("Company Contract\n");
        for (CustomerContract c : contracts) {
            if (c.getCustomer() instanceof CompanyCustomer) {
                fw.write(c.toFile());
            }
        }
        fw.write("End of list");
        fw.close();

    }//end of WriteToFileContract

    public static ArrayList<Car> availableCars(ArrayList<Car> allCars, LocalDate startDate, LocalDate endDate, ArrayList<CustomerContract> contracts) throws IOException {
        ArrayList<Car> availableCars = new ArrayList<>();
        availableCars.addAll(allCars);
        ArrayList<Car> unavailableCars = new ArrayList<>();
        for (CustomerContract c : contracts) {
            if (startDate.isAfter(c.getRentalStartDate()) && startDate.isBefore(c.getRentalEndDate())) {
                unavailableCars.add(c.getCar());
            }
        }
        availableCars.removeAll(unavailableCars);
        return availableCars;
    }//end of method: availableCars

    public static Customer newCustomer(Scanner input,ArrayList<Customer>allCustomers) throws IOException {
        Customer newCustomer = new Customer();
        try {
            System.out.println("Choose whether it's a private customer, or a company customer:");
            System.out.println("Enter 1 for private and 2 for company");
            int customerType = input.nextInt();
            input.nextLine();
            System.out.println("To add a new customer, first we need some information");
            System.out.println("Enter the name of the driver: ");
            String nameOfDriver = input.nextLine();
            System.out.println("Enter the address of the driver: ");
            String addressOfDriver = input.nextLine();
            System.out.println("Please enter the zipcode: ");
            int zipCode = input.nextInt();
            input.nextLine();
            System.out.println("Please enter the name of the city: ");
            String city = input.nextLine();
            System.out.println("Please enter the name of the country: ");
            String country = input.nextLine();
            System.out.println("Please enter the phonenumber of the driver: ");
            String mobilNr = input.nextLine();
            int mobilNrTest = Integer.parseInt(mobilNr);
            //input.nextLine();
            System.out.println("Please enter the E-mail of the driver: ");
            String email = input.nextLine();
            if (customerType == 1) {
                System.out.println("Please enter the drivers license number: ");
                int driversLicenseNumber = input.nextInt();
                System.out.println("Please enter how many years the driver has had his license: ");
                int yearsWithLicense = input.nextInt();
                //input.nextLine();
                newCustomer = new PrivateCustomer(nameOfDriver, addressOfDriver, zipCode, city, country, mobilNr, email, driversLicenseNumber, yearsWithLicense);
            } else if (customerType == 2) {
                System.out.println("Please enter the company name: ");
                String companyName = input.nextLine();
                System.out.println("Please enter the company address: ");
                String companyAddress = input.nextLine();
                System.out.println("Please enter the company phonenumber: ");
                String companyPhoneNr = input.nextLine();
                int companyPhoneNrTest = Integer.parseInt(companyPhoneNr);
                System.out.println("Please enter the company CRN number: ");
                String companyCRN = input.nextLine();
                newCustomer = new CompanyCustomer(nameOfDriver, addressOfDriver, zipCode, city, country, mobilNr, email, companyName, companyAddress, companyPhoneNr, companyCRN);
            } else {
                System.out.println("Wrong input");
            }
        }catch(InputMismatchException e){
            System.out.println("Follow the instructions on entering data");
            input.nextLine();
            System.out.println();
            newCustomer(input,allCustomers);
        }
        catch(NumberFormatException e){
            System.out.println("Follow the instructions on entering data");
            System.out.println();
            newCustomer(input,allCustomers);
        }
        allCustomers.add(newCustomer);
        writeToFileCustomer(allCustomers);
        return newCustomer;
    }//end of newCustomer

    public static void writeToFileCustomer(ArrayList<Customer>Allcustomers)throws IOException{

        FileWriter filewr=new FileWriter(new File("src/Customer"),false);

        for(Customer c: Allcustomers){
            if (c instanceof PrivateCustomer){
                filewr.write(c.toString());
            }
        }

        filewr.write("Company Customer\n");
        for(Customer c: Allcustomers){
            if (c instanceof CompanyCustomer){
                filewr.write(c.toString());
            }
        }
        filewr.write("End Of File");
        filewr.close();

/*
        for (CustomerContract c : contracts) {
            if (c.getCustomer() instanceof PrivateCustomer) {
                fw.write(c.toFile());
            }
        }
        fw.write("Company Contract\n");
        for (CustomerContract c : contracts) {
            if (c.getCustomer() instanceof CompanyCustomer) {
                fw.write(c.toFile());
            }
        }
        fw.write("End of list");
        fw.close();

 */
    }//end of method WriteToFileCustomer


    public static ArrayList<Car>unavailableCars(ArrayList<Car>allCars, LocalDate startDate, LocalDate endDate,ArrayList<CustomerContract>contracts){
        ArrayList<Car>unavailableCars = new ArrayList<>();
        for (CustomerContract c:contracts){
            if(overlaps(startDate,endDate,c.getRentalStartDate(),c.getRentalEndDate())){
                unavailableCars.add(c.getCar());
            }
        }
        return unavailableCars;
    }//end of method: availableCars

    public static boolean overlaps(LocalDate s1,LocalDate e1,LocalDate s2,LocalDate e2){
        if(s1.isBefore(e2) && s2.isBefore(e1)){
            return true;
        }
        return false;
    }

    //method that reads the contracts from the file and return an arrayList
    public static ArrayList<CustomerContract> readFromFileContracts (Scanner readFile,ArrayList<Customer> customer, ArrayList<Car> car)throws FileNotFoundException {
        ArrayList<CustomerContract> contracts = new ArrayList<>();
        //reading the first part of the contracts which is: (private) CustomerContracts
        while (readFile.hasNextLine()){
            String line=readFile.nextLine();
            if (line.equals("Company Contract")){
                break;
            }

            Scanner linescan=new Scanner(line);
            int contractNb=0;
            while ((linescan.hasNext()) && !linescan.hasNext(";")){
                contractNb=linescan.nextInt();
            }
            linescan.next();
            //System.out.println(contractNb);

            String name= "";
            while (linescan.hasNext() && !linescan.hasNext(";")){
                name+=linescan.next()+" ";
            }
            linescan.next();
            //System.out.println(name);

            String tlfNb= "";
            while (linescan.hasNext() && !linescan.hasNext(";")){
                tlfNb+=linescan.next()+" ";
            }
            linescan.next();

            //System.out.println(tlfNb);
            Customer customer1=new Customer();
            for (Customer c: customer){
                if((c.getNameOfDriver().trim().equals(name.trim())) && (c.getMobilNr().trim().equals(tlfNb.trim()))){
                    customer1=c;
                }
            }

            LocalDate rentalStartDate = LocalDate.now();
            while (linescan.hasNext() && !linescan.hasNext(";")){
                rentalStartDate=LocalDate.parse(linescan.next());
            }
            linescan.next();
            //System.out.println(rentalStartDate);

            LocalDate rentalEndDate = LocalDate.now();
            while (linescan.hasNext() && !linescan.hasNext(";")){
                rentalEndDate=LocalDate.parse(linescan.next());
            }
            linescan.next();
            //System.out.println(rentalEndDate);

            String registrationNb = "";
            while (linescan.hasNext() && !linescan.hasNext(";")){
                registrationNb+=linescan.next();
            }
            linescan.next();
            //System.out.println(name);

            Car car1=new Car();
            for (Car c: car){
                if((c.getRegistrationNb().equals(registrationNb))){
                    car1=c;
                    break;
                }
            }

            int maxKm= 0;
            while (linescan.hasNext() && !linescan.hasNext(";")){
                maxKm=linescan.nextInt();
            }
            linescan.next();
            //System.out.println(maxKm);

            int odometer= 0;
            while (linescan.hasNext() && !linescan.hasNext(";")){
                odometer=linescan.nextInt();
            }
            linescan.next();
            //System.out.println(odometer);

            CustomerContract newcontract=new CustomerContract(contractNb, customer1, rentalStartDate, rentalEndDate, car1, maxKm, odometer);
            contracts.add(newcontract);
        }//end while reading private customer contracts

        //Now reading the second part of the contracts which is: (company) CustomerContracts
        while (readFile.hasNextLine()){
            String line=readFile.nextLine();
            if (line.equals("End of list")){
                break;
            }

            Scanner linescan=new Scanner(line);
            int contractNb=0;
            while ((linescan.hasNext()) && !linescan.hasNext(";")){
                contractNb=linescan.nextInt();
            }
            linescan.next();
            //System.out.println(contractNb);

            String name= "";
            while (linescan.hasNext() && !linescan.hasNext(";")){
                name+=linescan.next()+" ";
            }
            linescan.next();
            //System.out.println(name);

            String tlfNb= "";
            while (linescan.hasNext() && !linescan.hasNext(";")){
                tlfNb+=linescan.next();
            }
            linescan.next();
            //System.out.println(tlfNb);
            Customer customer2=new Customer();
            for (Customer c: customer){
                if((c.getNameOfDriver().trim().equals(name.trim())) && (c.getMobilNr().trim().equals(tlfNb.trim()))){
                    customer2=c;
                    break;
                }
            }

            LocalDate rentalStartDate = LocalDate.now();
            while (linescan.hasNext() && !linescan.hasNext(";")){
                rentalStartDate=LocalDate.parse(linescan.next());
            }
            linescan.next();
            //System.out.println(rentalStartDate);

            LocalDate rentalEndDate = LocalDate.now();
            while (linescan.hasNext() && !linescan.hasNext(";")){
                rentalEndDate=LocalDate.parse(linescan.next());
            }
            linescan.next();
            //System.out.println(rentalEndDate);

            String registrationNb = "";
            while (linescan.hasNext() && !linescan.hasNext(";")){
                registrationNb+=linescan.next();
            }
            linescan.next();
            //System.out.println(name);

            Car car2=new Car();
            for (Car c: car){
                if((c.getRegistrationNb().equals(registrationNb))){
                    car2=c;
                }

            }

            int maxKm= 0;
            while (linescan.hasNext() && !linescan.hasNext(";")){
                maxKm=linescan.nextInt();
            }
            linescan.next();
            //System.out.println(maxKm);

            int odometer= 0;
            while (linescan.hasNext() && !linescan.hasNext(";")){
                odometer=linescan.nextInt();
            }
            //linescan.next();
            //System.out.println(odometer);

            CustomerContract newcontract=new CustomerContract(contractNb, customer2, rentalStartDate, rentalEndDate, car2, maxKm, odometer);
            contracts.add(newcontract);
        }//end while reading company customer contracts

        return contracts;
    }//end of method readFromFileContracts


    public static ArrayList <Customer> readFromFileCustomers() throws FileNotFoundException{

        ArrayList<Customer> customers = new ArrayList<>();
        Scanner readFile = new Scanner(new File("src/Customer"));

        //reading the first part of the Customers which is: (private) Customers
        while (readFile.hasNextLine()){
            String line=readFile.nextLine();
            if (line.equals("Company Customer")){
                break;
            }

            Scanner linescan=new Scanner(line);
            String name = "";
            while ((linescan.hasNext()) && !linescan.hasNext(";")){
                name +=linescan.next()+" ";
            }
            linescan.next();

            String address= "";
            while ((linescan.hasNext()) && !linescan.hasNext(";")){
                address +=linescan.next()+" ";
            }
            linescan.next();

            //linescan.next();
            //System.out.println(address);

            int zipCode= 0;
            while (linescan.hasNext() && !linescan.hasNext(";")){
                zipCode=linescan.nextInt();
            }
            linescan.next();
            //System.out.println(zipCode);

            String city= "";
            while (linescan.hasNext() && !linescan.hasNext(";")){
                city +=linescan.next()+" ";
            }
            linescan.next();
            //System.out.println(city);

            String country= "";
            while (linescan.hasNext() && !linescan.hasNext(";")){
                country +=linescan.next()+" ";
            }
            linescan.next();
            //System.out.println(country);

            String tlfNb= "";
            while (linescan.hasNext() && !linescan.hasNext(";")){
                tlfNb=linescan.next();
            }
            linescan.next();
            //System.out.println(tlfNb);

            String email= "";
            while (linescan.hasNext() && !linescan.hasNext(";")){
                email +=linescan.next();
            }
            linescan.next();
            //System.out.println(email);

            int driverslicenceNumber = 0;
            while (linescan.hasNext() && !linescan.hasNext(";")){
                driverslicenceNumber=linescan.nextInt();
            }
            linescan.next();
            //System.out.println(driverslicenceNumber);

            int yearsWithLicence=0;
            while (linescan.hasNext() && !linescan.hasNext(";")){
                yearsWithLicence=linescan.nextInt();
            }
            linescan.next();
            //System.out.println(yearsWithLicence);

            PrivateCustomer customer=new PrivateCustomer(name.trim(), address.trim(), zipCode, city.trim(), country.trim(), tlfNb.trim(), email, driverslicenceNumber, yearsWithLicence);
            customers.add(customer);
        }//end while reading private customer contracts

        //reading the second part of the Customers which is: Company Customers
        while (readFile.hasNextLine()){
            String line=readFile.nextLine();
            if (line.equals("End Of File")){
                break;
            }

            Scanner linescan=new Scanner(line);
            String name = "";
            while ((linescan.hasNext()) && !linescan.hasNext(";")){
                name +=linescan.next()+" ";
            }
            linescan.next();
            //System.out.println(name);

            String address= "";
            while (linescan.hasNext() && !linescan.hasNext(";")){
                address+=linescan.next()+ " ";
            }
            linescan.next();
            //System.out.println(address);

            int zipCode= 0;
            while (linescan.hasNext() && !linescan.hasNext(";")){
                zipCode=linescan.nextInt();
            }
            linescan.next();
            //System.out.println(zipCode);

            String city= "";
            while (linescan.hasNext() && !linescan.hasNext(";")){
                city +=linescan.next()+" ";
            }
            linescan.next();
            //System.out.println(city);

            String country= "";
            while (linescan.hasNext() && !linescan.hasNext(";")){
                country +=linescan.next()+" ";
            }
            linescan.next();
            //System.out.println(country);

            String tlfNb= "";
            while (linescan.hasNext() && !linescan.hasNext(";")){
                tlfNb=linescan.next();
            }
            linescan.next();
            //System.out.println(tlfNb);

            String email= "";
            while (linescan.hasNext() && !linescan.hasNext(";")){
                email +=linescan.next();
            }
            linescan.next();
            //System.out.println(email);

            String companyName = "";
            while (linescan.hasNext() && !linescan.hasNext(";")){
                companyName +=linescan.next();
            }
            linescan.next();
            //System.out.println(companyName);

            String companyAddress= "";
            while (linescan.hasNext() && !linescan.hasNext(";")){
                companyAddress+=linescan.next();
            }
            linescan.next();
            //System.out.println(companyAddress);

            String companyPhoneNb="";
            while (linescan.hasNext() && !linescan.hasNext(";")){
                companyPhoneNb=linescan.next();
            }
            linescan.next();
            //System.out.println(companyPhoneNb);

            String companyCRN = "";
            while (linescan.hasNext() && !linescan.hasNext(";")){
                companyCRN+=linescan.next();
            }
            linescan.next();
            //System.out.println(companyCRN);

            CompanyCustomer customer=new CompanyCustomer(name.trim(), address.trim(), zipCode, city.trim(), country.trim(), tlfNb.trim(), email, companyName, companyAddress, companyPhoneNb, companyCRN);
            customers.add(customer);
        }//end while reading private customer contracts

        return customers;
    }//end of readFromFileCustomers

    public static void searchCar(ArrayList<Car>listOfCars,ArrayList<CustomerContract> contracts,Scanner scan,ArrayList<Customer> allCustomers)throws IOException{
        try {
            ArrayList<Car> matches = new ArrayList<>();
            System.out.println("What would you like to search for? ");
            String searchString = scan.nextLine();
            String[] keywords = searchString.split(",\\s+|\\.");
            for (Car c : listOfCars) {
                for (int i = 0; i < keywords.length; i++) {
                    if (containsIgnoreCase(c.getBrand(), keywords[i]) || containsIgnoreCase(c.getModel(), keywords[i]) || containsIgnoreCase(c.getDescription(), keywords[i])) {
                        matches.add(c);
                        break;
                    }
                }
            }
            int matchIndex = 1;
            if (matches.isEmpty()) {
                System.out.println("No matches");
                return;
            } else if (matches.size() == 1) {
                System.out.println("        1 Match found");
                System.out.println(matches.getFirst().shortPrint());
                System.out.println();
            } else {
                int i = 1;
                System.out.println("          Matches found: " + matches.size());
                for (Car c : matches) {
                    System.out.println("Match number: " + i);
                    System.out.println(c.shortPrint());
                    System.out.println();
                    i++;
                }
                System.out.println("Enter the match number of the car you're interested in, and we will check the availability");
                matchIndex = scan.nextInt();
            }
            Car chosenCar = matches.get(matchIndex - 1);
            if (!contractForCarExists(chosenCar, contracts)) {
                System.out.println("No current bookings for the car");
            } else {
                System.out.println("The requested car is unavailable in the following periods:");
                for (CustomerContract c : contracts) {
                    if (c.getCar().equals(chosenCar)) {
                        System.out.println("Fra: " + c.getRentalStartDate() + " til " + c.getRentalEndDate());
                        //System.out.println(c.getRentalStartDate().datesUntil(c.getRentalEndDate()).collect(Collectors.toList()));
                    }
                }
            }
            System.out.println("Would you like to book the car?\n1 for yes, 2 for no");
            int bookOrNot = scan.nextInt();
            scan.nextLine();
            if (bookOrNot == 1) {
                Customer newCustomer = newCustomer(scan, allCustomers);
                System.out.println("How many Kms do you think you will drive during your rental period?");
                int maxKm = scan.nextInt();
                scan.nextLine();
                System.out.println("Write the first rental day in the form year-month-date: ");
                LocalDate fRentalDate = LocalDate.parse(scan.nextLine());
                System.out.println("Write the last day of you rental period in the form year-month-date: ");
                LocalDate eRentalDate = LocalDate.parse(scan.nextLine());
                CustomerContract newContract = new CustomerContract(makeContractNumber(contracts), newCustomer, fRentalDate, eRentalDate, chosenCar, maxKm, chosenCar.getOdometer());
                contracts.add(newContract);
                writeToFileContract(contracts);
                //contracts.add(makeContract(scan,listOfCars,contracts,allCustomers));
            } else {
                return;
            }
        }catch(InputMismatchException e){
            System.out.println("Follow the instructions on entering data");
            scan.nextLine();
            searchCar(listOfCars,contracts,scan,allCustomers);
        }catch(DateTimeParseException h){
            System.out.println("Follow the instructions on entering dates, year-month-day");
            searchCar(listOfCars,contracts,scan,allCustomers);
        }
    }
    public static boolean containsIgnoreCase(String s1, String s2) {
        return s1.toLowerCase().contains(s2.toLowerCase());
    }

    public static boolean contractForCarExists(Car car,ArrayList<CustomerContract>contracts){
        for(CustomerContract c: contracts){
            if(c.getCar().equals(car)){
                return true;
            }
        }
        return false;
    }

    public static void editCar(Scanner scan,Car car){
        int j;
        try {
            do {
                j = 0;
                System.out.println("====================================================");
                System.out.println("            What do you want to change?             ");
                System.out.println("====================================================");
                System.out.println("  Press 1  for: Brand                               ");
                System.out.println("  Press 2  for: Model                               ");
                System.out.println("  Press 3  for: Fuel type                           ");
                System.out.println("  Press 4  for: Registration number                 ");
                System.out.println("  Press 5  for: First registration date             ");
                System.out.println("  Press 6  for: Odometer                            ");
                System.out.println("  Press 7  for: Description                         ");
                System.out.println("  Press 8  for: Automatic transmission              ");
                System.out.println("  Press 9  for: AC                                  ");
                System.out.println("  Press 10 for: Borrowed                            ");
                System.out.println("  Press 11 for: Number of seats                     ");
                int choice = scan.nextInt();
                scan.nextLine();

                switch (choice) {
                    case 1:
                        System.out.println("Write the new brand:");
                        String brand = scan.nextLine();
                        car.setBrand(brand);
                        break;
                    case 2:
                        System.out.println("Write the new model:");
                        String model = scan.nextLine();
                        car.setModel(model);
                        break;
                    case 3:
                        System.out.println("Write the new fuel type:");
                        String fueltype = scan.nextLine();
                        car.setFuelType(fueltype);
                        break;
                    case 4:
                        System.out.println("Write the new registration number:");
                        String RegNb = scan.nextLine();
                        car.setRegistrationNb(RegNb);
                        break;
                    case 5:
                        System.out.println("Write the new registration's date using the form year-month-day:");
                        LocalDate regDate = LocalDate.parse(scan.next());
                        car.setFirstRegistrationDate(regDate);
                        break;
                    case 6:
                        System.out.println("Write the new number of Kms shown on the odometer:");
                        int odometer = scan.nextInt();
                        car.setOdometer(odometer);
                        scan.nextLine();
                        break;
                    case 7:
                        System.out.println("Write a new description of the car:");
                        String description = scan.nextLine();
                        car.setDescription(description);
                        break;
                    case 8:
                        System.out.println("Does the car have now automatic transmission? true for yes, false for no.");
                        boolean aut = scan.nextBoolean();
                        car.setAutomaticTransmission(aut);
                        scan.nextLine();
                        break;
                    case 9:
                        System.out.println("Does the car have now AC? true for yes, false for no.");
                        boolean ac = scan.nextBoolean();
                        car.setAC(ac);
                        scan.nextLine();
                        break;
                    case 10:
                        System.out.println("Is the car now borrowed? true for yes, false for no.");
                        boolean borrowed = scan.nextBoolean();
                        car.setBorrowed(borrowed);
                        scan.nextLine();
                        break;
                    case 11:
                        System.out.println("How many seats does the car have now?");
                        int seats = scan.nextInt();
                        car.setSeats(seats);
                        scan.nextLine();
                        break;
                    default:
                        System.out.println("Invalid option, returning you the menu");
                        break;
                }//end switch
                System.out.println("would you like to edit something else? yes / no ");
                String answer = scan.nextLine();
                if (containsIgnoreCase("yes", answer)) {
                    j = 1;
                }
            } while (j == 1);
        }catch(InputMismatchException e){
            System.out.println("Follow the given instructions when entering data");
            editCar(scan,car);
        }
        catch(DateTimeParseException h){
            System.out.println("Follow the given instructions when entering dates, year-month-day");
            editCar(scan,car);
        }
    }// end of editCar
    public static void editCustomer(Customer customer,Scanner scan){
        System.out.println(customer.toPrint());
        int j=1;
        try {
            do {
                System.out.println("What would you like to edit?");
                String dataToChange = scan.nextLine();

                String newData;
                int newIntData;

                if (containsIgnoreCase("Name", dataToChange)) {
                    System.out.println("WRITE the new name:");
                    newData = scan.nextLine();
                    customer.setName(newData);
                } else if (containsIgnoreCase("Address", dataToChange)) {
                    System.out.println("WRITE the new address:");
                    newData = scan.nextLine();
                    customer.setAddress(newData);
                } else if (containsIgnoreCase("Zipcode", dataToChange)) {
                    System.out.println("WRITE the new Zip code:");
                    newIntData = scan.nextInt();
                    customer.setZipcode(newIntData);
                    scan.nextLine();
                } else if (containsIgnoreCase("City", dataToChange)) {
                    System.out.println("WRITE the new City:");
                    newData = scan.nextLine();
                    customer.setCity(newData);
                } else if (containsIgnoreCase("Country", dataToChange)) {
                    System.out.println("WRITE the new Country:");
                    newData = scan.nextLine();
                    customer.setCountry(newData);
                } else if (containsIgnoreCase("Mobile Number, Phone, Cell", dataToChange)) {
                    System.out.println("WRITE the new mobile number:");
                    newData = scan.nextLine();
                    customer.setMobilNr(newData);
                } else if (containsIgnoreCase("Email,E-mail", dataToChange)) {
                    System.out.println("WRITE the new email:");
                    newData = scan.nextLine();
                    customer.setEmail(newData);
                } else if (customer instanceof PrivateCustomer && containsIgnoreCase("Driver's License, licensenumber, drivers", dataToChange)) {
                    System.out.println("WRITE the new driver License number:");
                    newIntData = scan.nextInt();
                    ((PrivateCustomer) customer).setDriversLicenseNumber(newIntData);
                    scan.nextLine();
                } else if (customer instanceof PrivateCustomer && containsIgnoreCase("Years with license", dataToChange)) {
                    System.out.println("WRITE the new years with license:");
                    newIntData = scan.nextInt();
                    ((PrivateCustomer) customer).setYearWithLicense(newIntData);
                    scan.nextLine();
                } else if (customer instanceof CompanyCustomer && containsIgnoreCase("Company Name, Company's Name, companys name", dataToChange)) {
                    System.out.println("WRITE the new company name:");
                    newData = scan.nextLine();
                    ((CompanyCustomer) customer).setCompanyName(newData);
                } else if (customer instanceof CompanyCustomer && containsIgnoreCase("Company Address, companys address, company's address, companys adress, company addres", dataToChange)) {
                    System.out.println("WRITE the new company address:");
                    newData = scan.nextLine();
                    ((CompanyCustomer) customer).setCompanyAddress(newData);
                } else if (customer instanceof CompanyCustomer && containsIgnoreCase("Company phone, company fone, company's phonenumber", dataToChange)) {
                    System.out.println("WRITE the new company phone:");
                    newData = scan.nextLine();
                    ((CompanyCustomer) customer).setCompanyPhoneNb(newData);
                } else if (customer instanceof CompanyCustomer && containsIgnoreCase("Company CRN, company's CRN, CRN number", dataToChange)) {
                    System.out.println("WRITE the new company CRN:");
                    newData = scan.nextLine();
                    ((CompanyCustomer) customer).setCompanyCRN(newData);
                } else {
                    System.out.println("Found no matching information to edit");
                }

                System.out.println("Do you still want to change some info? yes / no");
                String yesNo = scan.next();
                scan.nextLine();
                if (containsIgnoreCase(yesNo, "n")) {
                    j = 0;
                }

            } while (j == 1);

        }catch(InputMismatchException e){
            System.out.println("Follow the given instructions when entering data");
            editCustomer(customer,scan);
        }
        catch(DateTimeParseException h){
            System.out.println("Follow the given instructions when entering dates, year-month-day");
            editCustomer(customer,scan);
        }
    }// end of editCustomer

    public  static void editContract(Scanner scan, ArrayList<Car> allCars,CustomerContract contract, ArrayList<CustomerContract>allcontracts, ArrayList<Customer> customers) throws IOException{
        int editMore=1;
        try{
            do {
                System.out.println("====================================================");
                System.out.println("            What do you want to change?             ");
                System.out.println("====================================================");
                System.out.println("  Press 1  for: Car                                 ");
                System.out.println("  Press 2  for: Customer                            ");
                System.out.println("  Press 3  for: Rental period                       ");
                System.out.println("  Press 4  for: max kilometers                      ");

                int choice = scan.nextInt();
                scan.nextLine();

                switch (choice) {
                    case 1:
                        System.out.println("Are you still interested in the same period?\ntrue for yes, false for no");
                        boolean AnsPeriod = scan.nextBoolean();
                        if (AnsPeriod == true) {
                            System.out.println("Here is the list of available cars during this period:");
                            ArrayList<Car> availableCars = availableCars(allCars, contract.rentalStartDate, contract.rentalEndDate, allcontracts);
                            int k = 1;
                            for (Car c : availableCars) {
                                System.out.println("Car number: " + k);
                                System.out.println(c.shortPrint());
                                System.out.println();
                                k++;
                            }
                            System.out.println("Write the number of the car you want to choose: ");
                            int choiceCar = scan.nextInt();
                            contract.setCar(availableCars.get(choiceCar - 1));
                            System.out.println("The contract has been updated");
                        } else {
                            System.out.println("Enter the new rental start date in the form year-month-day. NO OTHER CHOICES.");
                            LocalDate newRentalStart = LocalDate.parse(scan.next());
                            System.out.println("Enter the new rental end date in the form year-month-day. I SAID: NO OTHER CHOICES.");
                            LocalDate newRentalEnd = LocalDate.parse(scan.next());
                            ArrayList<Car> availableCars = availableCars(allCars, newRentalStart, newRentalEnd, allcontracts);

                            System.out.println("Here is the list of available cars during this period:");
                            int k = 1;
                            for (Car c : availableCars) {
                                System.out.println("Car number: " + k);
                                System.out.println(c.shortPrint());
                                System.out.println();
                                k++;
                            }
                            System.out.println("WRITE the number of the car you want to choose: ");
                            int choiceCar = scan.nextInt();
                            contract.setCar(availableCars.get(choiceCar - 1));
                            contract.setRentalStartDate(newRentalStart);
                            contract.setRentalEndDate(newRentalEnd);
                            //System.out.println("the contract has been updated");
                        }//end else
                        break;

                    case 2:
                        editCustomer(contract.getCustomer(), scan);

                        break;
                    case 3:
                        System.out.println("Write the new rental start date in the form year-month-day:");
                        LocalDate rentalStartDate = LocalDate.parse(scan.next());
                        System.out.println("Write the new rental end date in the form year-month-day:");
                        LocalDate rentalEndDate = LocalDate.parse(scan.next());
                        ArrayList<Car> availableCars = availableCars(allCars, rentalStartDate, rentalEndDate, allcontracts);
                        contract.setRentalStartDate(rentalStartDate);
                        contract.setRentalEndDate(rentalEndDate);
                        if (availableCars.contains(contract.getCar())) {
                            System.out.println("Great, the car you previously picked is also available for those days.\nContract has been updated");
                        } else {
                            System.out.println("Unfortunately the car you previously chose, is not available for the new period you've entered");
                            System.out.println("Here is the list of available cars in the new rental period");
                            int k = 1;
                            for (Car c : availableCars) {
                                System.out.println("Car number: " + k);
                                System.out.println(c.shortPrint());
                                System.out.println();
                                k++;
                            }
                            System.out.println("Enter the corresponding number of the car you would like as a replacement");
                            int newCarIndex = scan.nextInt();
                            contract.setCar(availableCars.get(newCarIndex - 1));
                            System.out.println("Contract has been updated with the new car and dates");
                        }
                        break;
                    case 4:
                        System.out.println("How much Kms do you expect to drive maximum on a day?");
                        contract.setMaxKm(scan.nextInt());
                        break;

                    default:
                        break;
                }//end switch
                System.out.println("Do you want to edit anything else on the contract?\nYes/No");
                String checkForFurtherChanges=scan.next();
                if(containsIgnoreCase(checkForFurtherChanges,"n")){
                    editMore=0;

                }
            }while(editMore==1);

            writeToFileCustomer(customers);
            writeToFileContract(allcontracts);
            System.out.println("The contract has been updated.");
        }catch(InputMismatchException e){
            System.out.println("Follow the given instructions when entering data");
            editContract(scan, allCars,contract, allcontracts, customers);
        }
        catch(DateTimeParseException h){
            System.out.println("Follow the given instructions when entering dates, year-month-day");
            editContract(scan, allCars,contract, allcontracts, customers);
        }
    }//end editContract


    public  static void pickUpCar(Scanner scan, ArrayList<CustomerContract>contracts, ArrayList<Car>listOfCars, ArrayList<Customer>allCustomers) throws IOException{
        System.out.println("What is the name of the customer?");
        try {
            String customerName = scan.nextLine();
            CustomerContract contractExist = new CustomerContract();
            ArrayList<CustomerContract>matches = new ArrayList<>();
            int k = 0;
            for (CustomerContract c : contracts) {
                if (Main.containsIgnoreCase(c.getCustomer().getNameOfDriver(), customerName)) {
                    matches.add(c);
                    //contractExist = c;
                    k++;
                }
            }
            if(k==1){
                System.out.println("Match found:");
                System.out.println(matches.getFirst().toPrint());
                contractExist=matches.getFirst();
            }
            else if(k>1){
                System.out.println("Matches found:");
                for(CustomerContract c:matches){
                    System.out.println(c.toPrint());
                }
                System.out.println("Enter the contract number of the corresponding contract");
                int contractNumber = scan.nextInt();
                if(contractNumber<1 || contractNumber>(makeContractNumber(matches)-1)){
                    System.out.println("No such contractnumber exists");
                }
                for(CustomerContract c:matches){
                    if(c.getContractNumber()==contractNumber){
                        contractExist=c;
                        break;
                    }
                }

            }

            if (k == 0) {
                makeContract(scan, listOfCars, contracts, allCustomers);
            } else {
                System.out.println("The odometer on the contract has been updated based on the car's info.");
                contractExist.setOdometerAtRentalStartDate(contractExist.getCar().getOdometer());
            }
        }catch(InputMismatchException e){
            System.out.println("Follow the given instructions when entering data");
            pickUpCar(scan, contracts,listOfCars, allCustomers);
        }
        catch(NullPointerException n){
            System.out.println("Error with entered data.");
            pickUpCar(scan, contracts,listOfCars, allCustomers);
        }

    }//end pick up car

    public  static void returnCar(Scanner scan, ArrayList<Car>listOfCars,ArrayList<CustomerContract>contracts,ArrayList<Customer>allCustomer) throws IOException{
        System.out.println("WRITE the contract number:");
        try {
            int nbContract = scan.nextInt();
            scan.nextLine();

            int startOdometer = 0;
            CustomerContract contract = new CustomerContract();

            for (CustomerContract cc : contracts) {
                if (cc.getContractNumber() == nbContract) {
                    contract = cc;
                    startOdometer = cc.getOdometerAtRentalStartDate();
                    break;
                }
            }
            System.out.println("Enter the odometer value as shown on the car's odometer");
            int NewOd = scan.nextInt();
            contract.getCar().setOdometer(NewOd);

            int antaldag = contract.duration();
            System.out.println("you are going to pay for " + antaldag + " days.");
            double kmdag = (((double) NewOd) - startOdometer) / antaldag;
            System.out.printf("you drove approximately: %.2f km per day.", kmdag);
            System.out.println();
            double defaultPricePerDay = 200;
            double sum = 0.0;

            if (kmdag - 100 >= 0) {
                sum += (kmdag - 100) * 1.8;
                kmdag = 100;
            }
            if (kmdag - 50 >= 0) {
                sum += (kmdag - 50) * 1.6;
                kmdag = 50;
            }
            if (kmdag - 30 >= 0) {
                sum += (kmdag - 30) * 1.4;
                kmdag = 30;
            }
            if (kmdag - 15 >= 0) {
                sum += (kmdag - 15) * 1.2;
            }
            sum += kmdag * 1;
            sum = (sum + defaultPricePerDay) * antaldag;
            System.out.printf("The total sum is: %.2f Kr.", sum);
            System.out.println();

            deleteContract(contracts, nbContract, allCustomer);
        }catch(InputMismatchException e){
            System.out.println("Follow the given instructions when entering data");
            returnCar(scan, listOfCars,contracts,allCustomer);
        }
    }//end return car

    public static void deleteContract(ArrayList <CustomerContract>contracts, int nbContractDelete, ArrayList<Customer>allCustomer) throws IOException{
        CustomerContract contract=contracts.get(nbContractDelete-1);
        contracts.remove(nbContractDelete-1);
        writeToFileContract(contracts);

        allCustomer.removeIf(customer1 -> customer1== contract.getCustomer());
        writeToFileCustomer(allCustomer);
    }//end deleteContract

    public static void deleteCar(ArrayList<Car>listOfCars, Scanner scan)throws IOException{
        System.out.println("This is the list of all the cars:");

        int i=1;
        try {
            for (Car c : listOfCars) {
                System.out.println("Car number " + i + ": \n" + c.toPrint());
                System.out.println();
                i++;
            }
            System.out.println("Write the brand of the car you want to remove from the list: ");
            String CarBrandDelete = scan.nextLine();
            System.out.println("Write the model of the car you want to remove from the list: ");
            String CarModelDelete = scan.nextLine();

            ArrayList<Car> deleteCars = new ArrayList<>();
            if (!CarBrandDelete.isEmpty() && !CarModelDelete.isEmpty()) {
                for (Car c : listOfCars) {
                    if ((containsIgnoreCase(c.getBrand(), CarBrandDelete)) && containsIgnoreCase(c.getModel(), CarModelDelete)) {
                        deleteCars.add(c);
                    }
                }
                if (deleteCars.size() == 1) {
                    System.out.println("this car matches your criterias: \n" + deleteCars.getFirst().toPrint());
                    listOfCars.removeIf(car1 -> (deleteCars.getFirst() == car1));
                } else {
                    System.out.println("these cars match your criterias: \n");
                    int count = 1;
                    for (Car c : deleteCars) {
                        System.out.println("Car number: " + count + " :");
                        System.out.println(c.toPrint());
                        System.out.println();
                        count++;
                    }
                    System.out.println("WRITE the number of the car you want to remove:");
                    int numberDelete = scan.nextInt();
                    scan.nextLine();
                    Car deletedCar = deleteCars.get(numberDelete - 1);
                    listOfCars.removeIf(car1 -> (deletedCar == car1));
                }
                System.out.println("list of the cars after you deleted " + CarBrandDelete + " : " + CarModelDelete + " :\n");
                int j = 1;
                for (Car c : listOfCars) {
                    System.out.println("Car number " + j + ": \n" + c.toPrint());
                    System.out.println();
                    j++;
                }
                Main.writeToFile(listOfCars);
            } else {
                System.out.println("You didn't enter any search words");
            }
        }catch(InputMismatchException e){
            System.out.println("Follow the given instructions when entering data");
            scan.nextLine();
            deleteCar(listOfCars, scan);
        }
    }//end deleteCar

    public static int makeContractNumber(ArrayList<CustomerContract>contracts){
        int contractNumber = 0;
        for(CustomerContract c: contracts){
            if(c.getContractNumber()>contractNumber){
                contractNumber=c.getContractNumber();
            }
        }
        return contractNumber+1;
    }

}//end of class
