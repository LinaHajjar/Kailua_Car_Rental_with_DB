import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

import static java.util.Objects.isNull;

public class UI {

    Scanner scan =new Scanner(System.in);

    public static void hovedMenu(Scanner scan, ArrayList<Car> listOfCars, ArrayList<CustomerContract>contracts,ArrayList<Customer> allCustomers) throws IOException {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now();
        int choice=0;

        System.out.println("====================================================");
        System.out.println("                     Main  menu                     ");
        System.out.println("====================================================");
        System.out.println("         Enter a number to select an option         ");
        System.out.println("====================================================");
        System.out.println("  Press 1 for : See the list of available cars      "); //sorted by Name, kræver ordrene
        System.out.println("  Press 2 for : See the list of borrowed cars       "); //sorted by rentalEndingDate,  kræver ordrene
        System.out.println("  Press 3 for : See the list of contracts           "); //sorted by contractNb
        System.out.println("  Press 4 for : Add a new car                       ");
        System.out.println("  Press 5 for : Delete a car                        ");
        System.out.println("  Press 6 for : Make a new contract                 ");//make a new order
        System.out.println("  Press 7 for : Edit a car                          ");
        System.out.println("  Press 8 for : Edit a contract                     ");
        System.out.println("  Press 9 for : Search and book                     ");
        System.out.println("  Press 10 for: Pick-up a car                       ");
        System.out.println("  Press 11 for: Return a car                        ");
        System.out.println("  Press 12 for: Delete a contract                   ");
        System.out.println("  Press 13 for: End the program                     ");
        try{
            choice = scan.nextInt();
            scan.nextLine();


            switch (choice){
                case 1: //done
                    System.out.println("You chose option 1: See the list of available cars. ");
                    try {
                        System.out.println("Please enter the start date of the period you want to rent a car\nUse the format year-month-date");
                        startDate = LocalDate.parse(scan.nextLine());
                        System.out.println("Please enter end date for the rental period");
                        endDate = LocalDate.parse(scan.nextLine());
                    }catch (DateTimeParseException e){
                        System.out.println("Wrong input, returning to Main menu");
                        hovedMenu(scan, listOfCars, contracts,allCustomers);
                        return;
                    }
                    ArrayList<Car> availableCars = (Main.availableCars(listOfCars, startDate, endDate, contracts));
                    for (Car c : availableCars) {
                        System.out.println(c.shortPrint());
                        System.out.println();
                    }

                    hovedMenu(scan, listOfCars, contracts,allCustomers);
                    break;

                case 2: //done
                    System.out.println("You chose option 2: See the list of borrowed cars. ");
                    try{
                        System.out.println("Enter the start date of the period you want to see unavailable cars for\nUse the format year-month-date");
                        startDate=LocalDate.parse(scan.nextLine());
                        System.out.println("Enter the end date for the rental period");
                        endDate=LocalDate.parse(scan.nextLine());
                    }catch (DateTimeParseException e){
                        System.out.println("Wrong input, returning to Main menu");
                        hovedMenu(scan, listOfCars, contracts,allCustomers);
                        return;
                    }
                    ArrayList<Car>unavailableCars = (Main.unavailableCars(listOfCars,startDate,endDate,contracts));
                    for(Car c: unavailableCars){
                        System.out.println(c.shortPrint());
                        System.out.println();
                    }

                    hovedMenu(scan, listOfCars, contracts,allCustomers);
                    break;

                case 3: //done
                    System.out.println("You chose option 3: See the list of contracts. ");
                    for(CustomerContract c: contracts){
                        System.out.println(c.toPrint());
                        System.out.println();
                    }

                    hovedMenu(scan, listOfCars, contracts,allCustomers);
                    break;

                case 4: //done
                    System.out.println("You chose option 4: Add a new car. ");
                    listOfCars.add(Main.addCar(scan));
                    Main.writeToFile(listOfCars);

                    hovedMenu(scan, listOfCars, contracts,allCustomers);
                    break;

                case 5: //done
                    System.out.println("You chose option 5: delete a car. ");
                    Main.deleteCar(listOfCars, scan);

                    hovedMenu(scan, listOfCars, contracts,allCustomers);
                    break;

                case 6: //done
                    System.out.println("You chose option 6: Make a new contract. ");
                    CustomerContract newContract = Main.makeContract(scan, listOfCars, contracts,allCustomers);
                /*for (CustomerContract c:contracts){
                    System.out.println(c.toPrint());
                }*/

                    hovedMenu(scan, listOfCars, contracts,allCustomers);
                    break;

                case 7: //edit a car
                    System.out.println("You chose option 7: edit a car. ");
                    System.out.println("This is the list of all the cars:");
                    int count=1;
                    for (Car c: listOfCars){
                        System.out.println("Car number " + count+ ": \n" + c.toPrint());
                        System.out.println();
                        count++;
                    }
                    System.out.println("Which car do you want to edit? Enter the number of the car : ");
                    int NbEdit=scan.nextInt();
                    scan.nextLine();

                    //Car carToEdit=listOfCars.get(NbEdit-1); = listOfCars.get(NbEdit-1);
                    System.out.println("You chose to edit the following car:\n" + listOfCars.get(NbEdit-1).toPrint());

                    Main.editCar(scan, listOfCars.get(NbEdit-1));

                    System.out.println("Here is the car after you changed some information:");
                    System.out.println(listOfCars.get(NbEdit-1).toPrint());

                    hovedMenu(scan, listOfCars, contracts,allCustomers);
                    break;

                case 8:
                    System.out.println("You chose option 8: edit a contract. ");
                    System.out.println("This is the list of all the contracts:");
                    //int count2=1;
                    for (CustomerContract c: contracts){
                        System.out.println(c.toPrint());
                        System.out.println();

                        //count2++;
                    }
                    System.out.println("Which contract do you want to edit? Enter the number of the contract : ");
                    int contractEdit=scan.nextInt();
                    CustomerContract contractInQuestion = new CustomerContract();
                    for(CustomerContract c:contracts){
                        if(c.getContractNumber()==contractEdit){
                            contractInQuestion = c;
                            break;
                        }
                    }

                    //CustomerContract customerContractEdit=contracts.get(count2-1);
                    Main.editContract(scan, listOfCars ,contractInQuestion, contracts, allCustomers);

                    hovedMenu(scan, listOfCars, contracts,allCustomers);
                    break;

                case 9: //done
                    System.out.println("You chose option 9: search and book. ");
                    Main.searchCar(listOfCars,contracts,scan,allCustomers);

                    hovedMenu(scan, listOfCars,contracts,allCustomers);
                    break;

                case 10: //pick up done
                    System.out.println("You chose option 10: pick up a car. ");
                    Main.pickUpCar(scan, contracts, listOfCars, allCustomers);
                    hovedMenu(scan, listOfCars,contracts,allCustomers);
                    break;

                case 11: //return done
                    System.out.println("You chose option 11: return a car. ");
                    Main.returnCar(scan,listOfCars,contracts, allCustomers);
                    hovedMenu(scan, listOfCars,contracts,allCustomers);
                    break;

                case 12: //delete contract
                    System.out.println("Here is the list of all your contracts, which one do you want to delete?");
                    for(CustomerContract c: contracts){
                        System.out.println(c.toPrint());
                        System.out.println();
                    }
                    System.out.println("WRITE the number of the contract you want to remove:");
                    int nbContractRemove=scan.nextInt();
                    scan.nextLine();
                    Main.deleteContract(contracts, nbContractRemove,allCustomers);
                    System.out.println("Your list of contracts has been updated.");

                    break;

                case 13:
                    System.exit(0);
                    break;

                default:
                    System.out.println("You have entered a wrong number, please try again");
                    hovedMenu(scan, listOfCars, contracts,allCustomers);

            }//end switch

        }catch (InputMismatchException e){
            System.out.println("Wrong input, choose a number between 1 and 13");
            scan.nextLine();
            hovedMenu(scan, listOfCars, contracts,allCustomers);
            return;
        }
    }//end of HovedMenu

}
