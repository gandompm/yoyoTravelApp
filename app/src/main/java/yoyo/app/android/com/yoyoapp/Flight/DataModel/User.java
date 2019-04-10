package yoyo.app.android.com.yoyoapp.Flight.DataModel;

public class User {

//    @SerializedName("username")
    private String userName;
//    @SerializedName("first_name")
    private String firstName;
//    @SerializedName("last_name")
    private String lastName;
//    @SerializedName("email")
    private String email;
//    @SerializedName("expires")
    private String expireDate;
//    @SerializedName("token")
    private String token;

    private String language;




    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
