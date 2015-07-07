package ge.edu.freeuni.emis.emisapp.ui;

/**
 * Created by giorgi on 7/7/15.
 */
public class DrawerItem {
    private int imgResource;
    private String itemText;

    public DrawerItem(int imgResource, String itemText) {
        this.imgResource = imgResource;
        this.itemText = itemText;
    }

    public int getImgResource() {
        return imgResource;
    }
    public String getItemText() {
        return itemText;
    }
}
