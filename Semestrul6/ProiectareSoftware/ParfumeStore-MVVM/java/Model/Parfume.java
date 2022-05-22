package Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Parfume {
    private String parfumeName;
    private ParfumeInfo parfumeInfo;


    public Parfume(String parfumeName, ParfumeInfo parfumeInfo) {
        this.parfumeName = parfumeName;
        this.parfumeInfo = parfumeInfo;
    }
}
