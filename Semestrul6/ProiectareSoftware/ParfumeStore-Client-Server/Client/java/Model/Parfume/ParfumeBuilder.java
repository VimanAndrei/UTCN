package Model.Parfume;

public class ParfumeBuilder {
    private String parfumeName;
    private ParfumeInfo parfumeInfo;

    public ParfumeBuilder() {
        this.parfumeName = "";
        this.parfumeInfo = null;
    }

    public ParfumeBuilder setParfumeName(String parfumeName) {
        this.parfumeName = parfumeName;
        return this;
    }

    public ParfumeBuilder setParfumeInfo(ParfumeInfo parfumeInfo) {
        this.parfumeInfo = parfumeInfo;
        return this;
    }

    public Parfume build(){
        return new Parfume(parfumeName,parfumeInfo);
    }

}