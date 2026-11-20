package Project_java2;
class Truck extends Vehicle {
    private double LoadCapacity;
    private boolean Refrigerated;

    Truck(String PlateNumber, String Brand, String Model, double DailyPrice, double LoadCapacity,  boolean Refrigerated) {
        super(PlateNumber, Brand, Model, DailyPrice, true);
        this.LoadCapacity = LoadCapacity;
        this.Refrigerated = Refrigerated;
    }

    public double getLoadCapacity() {
        return LoadCapacity;
    }

    public void setLoadCapacity(float loadCapacity) {
        LoadCapacity = loadCapacity;
    }

    public boolean getRefrigerated() {
        return Refrigerated;
    }

    public void setRefrigerated(boolean refrigerated) {
        Refrigerated = refrigerated;
    }

    @Override
    public void printinfo() {
        System.out.println("Vehicle Type : Truck");
        System.out.println("VehicleId:"+vehicleId +" | Plate Number:" + PlateNumber + "  |  Brand:" + Brand + "  |  Model:" + Model);
        System.out.println("Daily Price:" + DailyPrice + "  |  Available:" + (Available ? "Yes" : "No"));
        System.out.println("Load Capacity:" + LoadCapacity + "KG  |  Refrigirated:" + (Refrigerated ? "Yes" : "No"));
    }

    @Override
    public String getType() {
        return "Truck";
    }

    @Override
    public double extraCost() {
        if (Refrigerated) {
            return 0.05;
        } else {
            return 0;
        }
    }
}
