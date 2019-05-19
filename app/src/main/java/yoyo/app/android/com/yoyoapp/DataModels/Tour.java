package yoyo.app.android.com.yoyoapp.DataModels;

public class Tour {
    private String id;
    private String name;
    private int passengerCount;
    private float createdAt;

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

    public int getPassengerCount() {
        return passengerCount;
    }

    public void setPassengerCount(int passengerCount) {
        this.passengerCount = passengerCount;
    }

    public float getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(float createdAt) {
        this.createdAt = createdAt;
    }
}
