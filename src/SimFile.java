import java.io.Serializable;

public class SimFile implements Serializable {
    private String name;

    public SimFile(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
