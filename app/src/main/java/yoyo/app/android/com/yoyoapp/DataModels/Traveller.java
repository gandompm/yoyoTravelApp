package yoyo.app.android.com.yoyoapp.DataModels;

public class Traveller {

    private int travellerId;
    private String email;
    private String firstName;
    private String lastName;
    private String countryCode;
    private String phoneNumber;
    private String ageClass;
    private String dateOfBirth;
    private String iranianNationalCode;
    private String passportNumber;
    private String expiryDateOfPassport;
    private String gender;
    private int nationality;

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

    public String getCountry() {
        return countryCode;
    }

    public void setCountry(String countryCode) {
        this.countryCode = countryCode;
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

    public String getExpiryDateOfPassport() {
        return expiryDateOfPassport;
    }

    public void setExpiryDateOfPassport(String expiryDateOfPassport) {
        this.expiryDateOfPassport = expiryDateOfPassport;
    }

    public int getTravellerId() {
        return travellerId;
    }

    public void setTravellerId(int travellerId) {
        this.travellerId = travellerId;
    }

    public int getNationality() {
        return nationality;
    }

    public void setNationality(int nationality) {
        this.nationality = nationality;
    }
}
