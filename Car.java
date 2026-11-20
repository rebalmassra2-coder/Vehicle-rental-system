package Project_java2;
public class Car extends Vehicle {
    private String FuelType;
    private boolean AirConditioner;
    private int NumberOfSeats;

    Car(String PlateNumber, String Brand, String Model, double DailyPrice,  String FuelType,int NumberOfSeats, boolean AirConditioner) {
        super(PlateNumber, Brand, Model, DailyPrice, true);
        this.FuelType = FuelType;
        this.NumberOfSeats = NumberOfSeats;
        this.AirConditioner = AirConditioner;

    }

    public String getFuelType() {
        return FuelType;
    }

    public void setFuelType(String fuelType) {
        FuelType = fuelType;
    }

    public boolean getAirConditioner() {
        return AirConditioner;
    }

    public void setAirConditioner(boolean airConditioner) {
        AirConditioner = airConditioner;
    }

    public int getNumberOfSeats() {
        return NumberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        NumberOfSeats = numberOfSeats;
    }

    @Override
    public void printinfo() {
        System.out.println("Vehicle Type : Car");
        System.out.println( "VehicleId:"+vehicleId+ " | Plate Number:" + PlateNumber + "  |  Brand:" + Brand + "  |  Model:" + Model);
        System.out.println("Daily Price:" + DailyPrice + "  |  Available:" + (Available ? "Yes" : "No"));
        System.out.println("Fuel Type:" + FuelType + "  |  Number of Seats:" + NumberOfSeats + "  |  Air Condition:"
                + (AirConditioner ? "Yes" : "No"));
    }

    @Override
    public String getType() {
        return "Car";
    }

    @Override
    public double extraCost() {
        if (FuelType.equalsIgnoreCase("Electric")) {
            return 0.05;  // 5% extra cost for electric cars
        } else {
            return 0;
        }
    }
}