package Model.Parfume;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
}
