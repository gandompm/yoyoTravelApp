package yoyo.app.android.com.yoyoapp.DataModels;

public class Traveller {

    private String id;
    private String firstName;
    private String lastName;
    private String gender;
    private String ageClass;
    private String dateOfBirth;
    private String passportNumber;
    private String iranianNationalCode;
    private boolean isIranian;
    private String nationality;


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

    public String getAgeClass() {
        return ageClass;
    }

    public void setAgeClass(String ageClass) {
        this.ageClass = ageClass;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getIranianNationalCode() {
        return iranianNationalCode;
    }

    public void setIranianNationalCode(String iranianNationalCode) {
        this.iranianNationalCode = iranianNationalCode;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public boolean isIranian() {
        return isIranian;
    }

    public void setIranian(boolean iranian) {
        isIranian = iranian;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
