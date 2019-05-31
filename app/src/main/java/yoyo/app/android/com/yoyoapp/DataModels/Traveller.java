package yoyo.app.android.com.yoyoapp.DataModels;

public class Traveller {

    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String ageClass;
    private String dateOfBirth;
    private String iranianNationalCode;
    private String passportNumber;
    private String gender;
    private boolean isIranian;
    private String nationality;
    private String nationalityCode;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public String getNationalityCode() {
        return nationalityCode;
    }

    public void setNationalityCode(String nationalityCode) {
        this.nationalityCode = nationalityCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
