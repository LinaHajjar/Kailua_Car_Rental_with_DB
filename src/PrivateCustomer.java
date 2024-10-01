public class PrivateCustomer extends Customer {
    int driverslicenceNumber;
    int yearsWithLicence;

    public  PrivateCustomer(){

    }

    public int getDriverslicenceNumber(){
        return driverslicenceNumber;
    }
    public int getYearsWithLicence(){
        return yearsWithLicence;
    }

    public PrivateCustomer(String nameOfDriver, String addressOfDriver, int zipCode, String city, String country, String mobilNr, String email, int driverslicenceNumber, int yearsWithLicence){
        super(nameOfDriver, addressOfDriver, zipCode, city, country, mobilNr, email);
        this.driverslicenceNumber=driverslicenceNumber;
        this.yearsWithLicence=yearsWithLicence;
    }

    public String toString(){
        return (nameOfDriver + " ; " + addressOfDriver + " ; " + zipCode + " ; " + city + " ; " + country +" ; "+ mobilNr +" ; " + email +" ; "+ driverslicenceNumber+" ; "+ yearsWithLicence +" ;\n");
    }

    public void setDriversLicenseNumber(int driverslicenceNumber){
        this.driverslicenceNumber=driverslicenceNumber;
    }
    public void setYearWithLicense(int yearsWithLicence){
        this.yearsWithLicence=yearsWithLicence;
    }



    public String toPrint(){
        return ("---------Private Customer:---------\n" +
                "Name of the driver                : "+ nameOfDriver+ "\n" +
                "Address of the driver             : "+ addressOfDriver + "\n" +
                "Zip Code                          : "+ zipCode + "\n" +
                "City                              : " + city + "\n" +
                "Country                           : "+ country+ "\n" +
                "Mobile number                     : "+ mobilNr+ "\n" +
                "E-mail                            : "+ email + "\n" +
                "Driver's licence number           : "+ driverslicenceNumber+ "\n" +
                "Years with licence                : " +yearsWithLicence+ "\n");
    }
}
