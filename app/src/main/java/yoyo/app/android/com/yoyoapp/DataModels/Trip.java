package yoyo.app.android.com.yoyoapp.DataModels;

import android.view.View;

import java.util.ArrayList;

public class Trip {

    private String tripId;
    private String title;
    private String startTime;
    private String endTime;
    private String price;
    private String image;
    private String summary;
    private int remainingCapacity;
    private String previousPrice;
    private String language;
    private String startDate;
    private String endDate;
    private String destination;
    private int dayNum;
    private int nightNum;
    private String startPoint;
    private String endPoint;
    private Tour tour;
    private String agency;
    private int resultsSize;
    private TripLeader tripLeader;
    private ArrayList<String> itineraries;
    private ArrayList<String> transportations;
    private ArrayList<String> attractions;
    private ArrayList<String> meals;
    private ArrayList<String> rules;
    private ArrayList<String> gallery;
    private ArrayList<String> categories;
    private ArrayList<Location> locations;



    public Trip(String price, String previousPrice, String language, String startDate, String endDate, String destination, int dauNum, int nightNum,
                String startPoint, String endPoint, String startTime, String endTime,
                String category, String title, String image, int remainingCapacity) {
        this.price = price;
        this.previousPrice = previousPrice;
        this.language = language;
        this.startDate = startDate;
        this.endDate = endDate;
        this.destination = destination;
        this.dayNum = dauNum;
        this.nightNum = nightNum;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.startTime = startTime;
        this.endTime = endTime;
        this.title = title;
        this.image = image;
        this.remainingCapacity = remainingCapacity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }


    private View.OnClickListener requestBtnClickListener;

    public Trip() {
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getDayNum() {
        return dayNum;
    }

    public void setDayNum(int dayNum) {
        this.dayNum = dayNum;
    }

    public int getNightNum() {
        return nightNum;
    }

    public void setNightNum(int time) {
        this.nightNum = time;
    }

    public View.OnClickListener getRequestBtnClickListener() {
        return requestBtnClickListener;
    }

    public void setRequestBtnClickListener(View.OnClickListener requestBtnClickListener) {
        this.requestBtnClickListener = requestBtnClickListener;
    }

    public String getPreviousPrice() {
        return previousPrice;
    }

    public void setPreviousPrice(String previousPrice) {
        this.previousPrice = previousPrice;
    }

    public int getRemainingCapacity() {
        return remainingCapacity;
    }

    public void setRemainingCapacity(int remainingCapacity) {
        this.remainingCapacity = remainingCapacity;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public ArrayList<String> getItineraries() {
        return itineraries;
    }

    public void setItineraries(ArrayList<String> itineraries) {
        this.itineraries = itineraries;
    }

    public ArrayList<String> getAttractions() {
        return attractions;
    }

    public void setAttractions(ArrayList<String> attractions) {
        this.attractions = attractions;
    }

    public TripLeader getTripLeader() {
        return tripLeader;
    }

    public void setTripLeader(TripLeader tripLeader) {
        this.tripLeader = tripLeader;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public ArrayList<String> getTransportations() {
        return transportations;
    }

    public void setTransportations(ArrayList<String> transportations) {
        this.transportations = transportations;
    }

    public ArrayList<String> getMeals() {
        return meals;
    }

    public void setMeals(ArrayList<String> meals) {
        this.meals = meals;
    }

    public ArrayList<String> getRules() {
        return rules;
    }

    public void setRules(ArrayList<String> rules) {
        this.rules = rules;
    }

    public ArrayList<String> getGallery() {
        return gallery;
    }

    public void setGallery(ArrayList<String> gallery) {
        this.gallery = gallery;
    }

    public ArrayList<Location> getLocations() {
        return locations;
    }

    public void setLocations(ArrayList<Location> locations) {
        this.locations = locations;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getResultsSize() {
        return resultsSize;
    }

    public void setResultsSize(int resultsSize) {
        this.resultsSize = resultsSize;
    }
}
