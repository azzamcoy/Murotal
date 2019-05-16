package supriyanto.ownmodal;

import java.io.Serializable;

/**
 * Created by SUPRIYANTO on 12/05/2019.
 */

public class Category extends com.example.murotal.ownmodal.Category implements Serializable {

    private String id, name, image;

    public Category(String id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
