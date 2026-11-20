package Project_java2;
class Motorcycle extends Vehicle {
    private double EngineCapacity;
    private boolean hasSideCar;

    Motorcycle(String PlateNumber, String Brand, String Model, double DailyPrice, double EngineCapacity, boolean hasSideCar) {
        super(PlateNumber, Brand, Model, DailyPrice, true);
        this.EngineCapacity = EngineCapacity;
        this.hasSideCar = hasSideCar;
    }

    public double getEngineCapacity() {
        return EngineCapacity;
    }

    public void setEngineCapacity(float engineCapacity) {
        EngineCapacity = engineCapacity;
    }

    public boolean gethasSideCar() {
        return hasSideCar;
    }

    public void sethasSideCar(boolean sidecar) {
        hasSideCar = sidecar;
    }

    @Override
    public void printinfo() {
        System.out.println("Vehicle Type : Motorcycle");
        System.out.println("VehicleId:"+vehicleId+" | Plate Number:" + PlateNumber + "  |  Brand:" + Brand + "  |  Model:" + Model);
        System.out.println("Daily Price:" + DailyPrice + "  |  Available:" + (Available ? "Yes" : "No"));
        System.out.println("Engine Capacity:" + EngineCapacity + "CC  |  Sidecar:" + (hasSideCar ? "Yes" : "No"));
    }

    @Override
    public String getType() {
        return "Motorcycle";
    }

    @Override
    public double extraCost() {
        if (hasSideCar) {
            return 0.02;    // 2% extra cost for motorcycles with a sidecar
        } else {
            return 0;
        }
    }
}