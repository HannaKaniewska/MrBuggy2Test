package businessLayer;

public class EnvironmentBL {
    public String projectPrefix;
    public String name;
    public String description;

    @Override
    public String toString() {
        return "TestData{" +
                "projectPrefix='" + projectPrefix + '\'' +
                "name='" + name + '\'' +
                ", description='" + description +
                '}';
    }

}
