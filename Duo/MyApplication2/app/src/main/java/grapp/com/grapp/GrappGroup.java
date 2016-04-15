package grapp.com.grapp;

/**
 * Created by Hein on 4/15/2016.
 */
public class GrappGroup {

    private String groupName;
    private String imageResource;

    public GrappGroup(String groupName, String imageResource) {
        this.groupName = groupName;
        this.imageResource = imageResource;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getImageResource() {
        return imageResource;
    }

    public void setImageResource(String imageResource) {
        this.imageResource = imageResource;
    }
}
