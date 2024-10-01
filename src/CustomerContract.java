import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;

public class CustomerContract {
    int contractNumber;
    Customer customer;
    LocalDate rentalStartDate;
    LocalDate rentalEndDate;
    Car car;
    int maxKm;
    int odometerAtRentalStartDate;


    public CustomerContract(){
    }

    public CustomerContract (int contractNumber, Customer customer, LocalDate rentalStartDate, LocalDate rentalEndDate, Car car, int maxKm, int odometerAtRentalStartDate){
        this.contractNumber=contractNumber;
        this.customer=customer;
        this.rentalStartDate=rentalStartDate;
        this.rentalEndDate=rentalEndDate;
        this.car=car;
        this.maxKm=maxKm;
        this.odometerAtRentalStartDate=odometerAtRentalStartDate;
    }

    public Customer getCustomer(){
        return customer;
    }
    public int getContractNumber(){
        return contractNumber;
    }

    public String toString(){
        return (contractNumber + " ; " + customer + " ; " + rentalStartDate + " ; " + rentalEndDate + " ; " + car +" ; "+ maxKm +" ; " + odometerAtRentalStartDate);
    }

    public String toPrint(){
        return("================= Contract =================\n"+
                "¤ Contract number                 : "+ contractNumber+ "\n" +
                customer.toPrint() +"\n"+
                "¤ Rental start date               : "+ rentalStartDate +"\n" +
                "¤ Rental end date                 : "+ rentalEndDate+ "\n" +
                car.toPrint() + "\n"+
                "¤ Max KM                          : "+ maxKm+ "\n" +
                "¤ Odometer at rental start date   : " +odometerAtRentalStartDate +"\n");

    }

    public String toFile(){
        return ("00"+contractNumber+" ; "+customer.getNameOfDriver()+" ; "+customer.getMobilNr()+" ; " +rentalStartDate+" ; "+rentalEndDate+" ; "+car.getRegistrationNb()+" ; "+maxKm+" ; "+car.getOdometer()+" ;\n");
    }


    /*static Period	between(LocalDate startDateInclusive, LocalDate endDateExclusive)
    Obtains a Period consisting of the number of years, months, and days between two dates.
    public*/

    public LocalDate getRentalStartDate(){
        return rentalStartDate;
    }
    public LocalDate getRentalEndDate(){
        return rentalEndDate;
    }
    public Car getCar(){
        return car;
    }

    public int getOdometerAtRentalStartDate(){
        return odometerAtRentalStartDate;
    }
    public int duration(){
        int between = (int) ChronoUnit.DAYS.between(rentalStartDate, rentalEndDate);
        return(between+1);
    }

    public void setCar(Car car){
        this.car=car;
    }
    public void setRentalStartDate(LocalDate rentalStartDate){
        this.rentalStartDate=rentalStartDate;
    }
    public void setRentalEndDate(LocalDate rentalEndDate){
        this.rentalEndDate=rentalEndDate;
    }
    public void setMaxKm(int maxKm){
        this.maxKm=maxKm;
    }
    public void setOdometerAtRentalStartDate(int odometerAtRentalStartDate){
        this.odometerAtRentalStartDate=odometerAtRentalStartDate;
    }

}
