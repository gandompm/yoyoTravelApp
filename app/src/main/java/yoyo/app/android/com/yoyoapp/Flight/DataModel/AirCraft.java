package yoyo.app.android.com.yoyoapp.Flight.DataModel;

public class AirCraft {

    private String manufacturer;
    private boolean isSelected;
    private int id;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

}
