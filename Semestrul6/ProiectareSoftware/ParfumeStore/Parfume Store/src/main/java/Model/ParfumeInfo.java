package Model;

public class ParfumeInfo {
    private String manufacturerName;
    private Integer numberOfCopies;
    private Integer barCode;
    private Double price;
    private Integer parfumeAmount;
    private Integer numberOfSoldCopies;



    public ParfumeInfo(String manufacturerName, Integer numberOfCopies, Integer barCode, Double price, Integer parfumeAmount, Integer numberOfSoldCopies) {
        this.manufacturerName = manufacturerName;
        this.numberOfCopies = numberOfCopies;
        this.barCode = barCode;
        this.price = price;
        this.parfumeAmount = parfumeAmount;
        this.numberOfSoldCopies = numberOfSoldCopies;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public Integer getNumberOfCopies() {
        return numberOfCopies;
    }


    public Integer getBarCode() {
        return barCode;
    }



    public Double getPrice() {
        return price;
    }



    public Integer getParfumeAmount() {
        return parfumeAmount;
    }



    public Integer getNumberOfSoldCopies() {
        return numberOfSoldCopies;
    }


    @Override
    public String toString() {
        return "ParfumeInfo{" +
                "manufacturerName='" + manufacturerName + '\'' +
                ", numberOfCopies=" + numberOfCopies +
                ", barCode=" + barCode +
                ", price=" + price +
                ", parfumeAmount=" + parfumeAmount +
                ", numberOfSoldCopies=" + numberOfSoldCopies +
                '}';
    }
}
