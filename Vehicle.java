package Project_java2;
public abstract class Vehicle {
    static protected int nextId=1;
     String vehicleId;
    protected String PlateNumber;
    protected String Brand;
    protected String Model;
    protected double DailyPrice;
    protected boolean Available;

    Vehicle(String PlateNumber, String Brand, String Model, double DailyPrice, boolean Available) {
        this.PlateNumber = PlateNumber;
        this.vehicleId="VEH_"+String.format("%04d", nextId++);
        this.Brand = Brand;
        this.Model = Model;
        this.DailyPrice = DailyPrice;
        this.Available = Available;
    }

    public abstract void printinfo();

    public abstract String getType();

    public abstract double extraCost();
}