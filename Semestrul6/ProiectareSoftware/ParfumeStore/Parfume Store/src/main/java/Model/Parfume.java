package Model;

public class Parfume {
    private String parfumeName;
    private ParfumeInfo parfumeInfo;

    public Parfume() {
    }

    public Parfume(String parfumeName, ParfumeInfo parfumeInfo) {
        this.parfumeName = parfumeName;
        this.parfumeInfo = parfumeInfo;
    }

    public String getParfumeName() {
        return parfumeName;
    }

    public void setParfumeName(String parfumeName) {
        this.parfumeName = parfumeName;
    }

    public ParfumeInfo getParfumeInfo() {
        return parfumeInfo;
    }

    public void setParfumeInfo(ParfumeInfo parfumeInfo) {
        this.parfumeInfo = parfumeInfo;
    }

    @Override
    public String toString() {
        return "Parfume{" +
                "parfumeName='" + parfumeName + '\'' +
                ", parfumeInfo=" + parfumeInfo +
                '}';
    }
}
