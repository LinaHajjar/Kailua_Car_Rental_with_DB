import java.time.LocalDate;

public class Car {
        String brand;
        String model;
        String fuelType;
        String registrationNb;
        LocalDate firstRegistrationDate;
        int odometer;
        String Description;
        boolean automaticTransmission; //true if automatic
        boolean AC;
        boolean borrowed;
        int seats;


        public Car(){
        }

        public Car(String brand, String model, String fuelType, String registrationNb, LocalDate firstRegistrationDate, int odometer, String Description, boolean automaticTransmission, boolean AC, boolean borrowed, int seats ){
            this.brand=brand;
            this.model=model;
            this.fuelType=fuelType;
            this.registrationNb=registrationNb;
            this.firstRegistrationDate=firstRegistrationDate;
            this.odometer=odometer;
            this.Description=Description;
            this.automaticTransmission=automaticTransmission;
            this.AC=AC;
            this.borrowed=borrowed;
            this.seats=seats;
        }
        public String getBrand(){
            return brand;
        }
        public String getModel(){
            return model;
        }
        public String getFuelType(){
            return fuelType;
        }
        public String getRegistrationNb(){
            return registrationNb;
        }

        public LocalDate getFirstRegistrationDate() {
            return firstRegistrationDate;
        }

        public int getOdometer(){
            return odometer;
        }
        public String getDescription(){
            return Description;
        }
        public boolean getAutomaticTransmission() {
            return automaticTransmission;
        }
        public boolean getAC() {
            return AC;
        }
        public boolean getBorrowed() {
            return borrowed;
        }
        public int getSeats() {
            return seats;
        }

        public void setBrand(String brand){
            this.brand=brand;
        }
        public void setModel(String model){
            this.model=model;
        }
        public void setFuelType(String fuelType){
            this.fuelType=fuelType;
        }
        public void setRegistrationNb(String registrationNb){
            this.registrationNb=registrationNb;
        }
        public void setFirstRegistrationDate(LocalDate firstRegistrationDate){
            this.firstRegistrationDate=firstRegistrationDate;
        }
        public void setOdometer(int odometer){
            this.odometer=odometer;
        }
        public void setDescription(String Descritpion){
            this.Description=Description;
        }
        public void setAutomaticTransmission(boolean automaticTransmission){
            this.automaticTransmission=automaticTransmission;
        }
        public void setAC(boolean AC) {
            this.AC = AC;
        }
        public void setBorrowed(boolean borrowed) {
            this.borrowed = borrowed;
        }
        public void setSeats(int seats) {
            this.seats = seats;
        }

        public String toString() {
            return (brand + " ; " + model + " ; " + fuelType + " ; " + registrationNb + " ; " + firstRegistrationDate + " ; " + odometer + " ; " +  Description + " ; " +  automaticTransmission + " ; " + AC + " ; " + borrowed+ " ; " + seats + " ; "+"\n");
        }

        public String toPrint() {
            String acText ="";
            if (AC==true){
                acText= "yes";
            } else {
                acText="No";
            }

            String Aut="";
            if (automaticTransmission==true){
                Aut= "yes";
            }else {
                Aut="No";
            }

            return ("brand                             : " + brand+  "\nmodel                             : " + model +"\nfueltype                          : " + fuelType + "\nRegistration number               : " +registrationNb + "\nFirst registration date           : " + firstRegistrationDate+ "\nodometer                          : " + odometer+ "\nDescription                       : " +Description+  "\nAutomatic transmission            : " + Aut + "\nAC                                : " + acText+ "\nSeats                             : " + seats);
        }

        public String shortPrint() {
            String acText ="";
            if (AC==true){
                acText= "yes";
            } else {
                acText="No";
            }

            String Aut="";
            if (automaticTransmission==true){
                Aut= "yes";
            }else {
                Aut="No";
            }
            return ("Brand                             : " + brand+  "\nModel                             : " + model +"\nFueltype                          : " + fuelType + "\nDescription                       : " +Description+  "\nAutomatic transmission            : " + Aut + "\nAC                                : " + acText + "\nSeats                             : " + seats);
        }

    }
