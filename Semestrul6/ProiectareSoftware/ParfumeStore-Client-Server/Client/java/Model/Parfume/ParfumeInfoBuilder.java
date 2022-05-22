package Model.Parfume;

public class ParfumeInfoBuilder {
    private String manufacturerName;
    private Integer numberOfCopies;
    private Integer barCode;
    private Double price;
    private Integer parfumeAmount;
    private Integer numberOfSoldCopies;

    public ParfumeInfoBuilder() {
        this.manufacturerName = "";
        this.numberOfCopies = 0;
        this.barCode = 0;
        this.price = 0.0;
        this.parfumeAmount = 0;
        this.numberOfSoldCopies = 0;
    }

    public ParfumeInfoBuilder setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
        return this;
    }

    public ParfumeInfoBuilder setNumberOfCopies(Integer numberOfCopies) {
        this.numberOfCopies = numberOfCopies;
        return this;
    }

    public ParfumeInfoBuilder setBarCode(Integer barCode) {
        this.barCode = barCode;
        return this;
    }

    public ParfumeInfoBuilder setPrice(Double price) {
        this.price = price;
        return this;
    }

    public ParfumeInfoBuilder setParfumeAmount(Integer parfumeAmount) {
        this.parfumeAmount = parfumeAmount;
        return this;
    }

    public ParfumeInfoBuilder setNumberOfSoldCopies(Integer numberOfSoldCopies) {
        this.numberOfSoldCopies = numberOfSoldCopies;
        return this;
    }
    public ParfumeInfo build(){
        return new ParfumeInfo(manufacturerName,numberOfCopies,barCode,price,parfumeAmount,numberOfSoldCopies);
    }
}
