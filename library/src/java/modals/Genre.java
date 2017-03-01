package modals;
import java.io.Serializable;

public class Genre implements Serializable {
       private String name;
   private int Id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public Genre() {
    }
}
