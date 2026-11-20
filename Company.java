package Project_java2;
  public class Company extends Customer {
    private String CommercialRecord;
    private double discountRate;

    Company( String Name, String Address, String phoneNumber, double discountRate, String CommercialRecord) {
        super( Name, Address, phoneNumber);
        this.discountRate = discountRate;
        this.CommercialRecord = CommercialRecord;
    }

    public String getCommercialRecord() {
        return CommercialRecord;
    }

    public void setCommercialRecord(String commercialRecord) {
        this.CommercialRecord = commercialRecord;
    }

  
    public void setDiscountRate(double discountRate) {
        this.discountRate = discountRate;
    }
  @Override
    public double getDiscountRate() {
        return discountRate / 100;
    }

    @Override
    public void printinfo() {
        System.out.println("Company Customer Info");
        System.out.println(
                "ID:" + ID + "  |  Name:" + Name + "  |  Address:" + Address + "  |  Phone Number:" + phoneNumber);
        System.out.println("CommercialRecord:" + CommercialRecord + "  |  Discount Rate:" + discountRate);
    }

    @Override
    public String getType() {
        return "Company";
    }
}