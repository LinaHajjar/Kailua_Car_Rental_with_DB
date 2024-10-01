public class Customer {
    String nameOfDriver;
    String addressOfDriver;
    int zipCode;
    String city;
    String country;
    String mobilNr;
    String email;

    public Customer(){

    }
    public Customer(String nameOfDriver, String addressOfDriver, int zipCode, String city, String country, String mobilNr, String email ){
        this.nameOfDriver=nameOfDriver;
        this.addressOfDriver=addressOfDriver;
        this.zipCode=zipCode;
        this.city=city;
        this.country=country;
        this.mobilNr=mobilNr;
        this.email=email;
    }

    public  String getMobilNr (){
        return mobilNr;
    }

    public String getNameOfDriver (){
        return nameOfDriver;
    }



    public String toPrint(){
        return ("Name of the driver                : "+ nameOfDriver + "\n" +
                "Address of the driver             : "+ addressOfDriver + "\n" +
                "Zip Code                          : "+zipCode + "\n" +
                "City                              : "+city + "\n" +
                "Country                           : "+country + "\n" +
                "Mobile number                     : "+mobilNr + "\n" +
                "E-mail                            : "+email + "\n");
    }

    public void setName(String nameOfDriver) {
        this.nameOfDriver=nameOfDriver;
    }

    public void setAddress(String addressOfDriver) {
        this.addressOfDriver=addressOfDriver;
    }

    public void setZipcode(int zipCode){
        this.zipCode=zipCode;
    }

    public void setCity(String city){
        this.city=city;
    }

    public void setCountry(String country){
        this.country=country;
    }

    public void setMobilNr(String mobilNr){
        this.mobilNr=mobilNr;
    }

    public void setEmail(String Email){
        this.email=email;
    }

}
