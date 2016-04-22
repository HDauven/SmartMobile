package grapp.com.grapp.model;

/**
 * Created by Hein on 4/22/2016.
 */
public class GrappModule {

    private String name;
    private String imageResource;

    public GrappModule(String name, String imageResource) {
        this.name = name;
        this.imageResource = imageResource;
    }

    public String getImageResource() {
        return imageResource;
    }

    public void setImageResource(String imageResource) {
        this.imageResource = imageResource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
