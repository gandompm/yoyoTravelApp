package yoyo.app.android.com.yoyoapp.DataModels;

import android.view.View;

import yoyo.app.android.com.yoyoapp.Enum.Capacity;

import java.util.ArrayList;

public class Tour {

    private String price;
    private String previousPrice;
    private String language;
    private String startDate;
    private String endDate;
    private String destination;
    private int dayNum;
    private int nightNum;
    private Capacity capacity;
    private String tourLeaderName;
    private String tourLeaderImg;
    private String startPoint;
    private String endPoint;
    private String moveTime;
    private String endTime;
    private String tourGroup;
    private String tourTitle;
    private String tourImg;

    public Tour(String price, String previousPrice, String language, String startDate, String endDate, String destination, int dauNum, int nightNum,
                        String tourLeaderName,String tourLeaderImg, String startPoint,String endPoint, String moveTime, String endTime,
                                 String tourGroup, String tourTitle ,String tourImg, Capacity capacity) {
        this.price = price;
        this.previousPrice = previousPrice;
        this.language = language;
        this.startDate = startDate;
        this.endDate = endDate;
        this.destination = destination;
        this.dayNum = dauNum;
        this.nightNum = nightNum;
        this.tourLeaderName = tourLeaderName;
        this.tourLeaderImg = tourLeaderImg;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.moveTime = moveTime;
        this.endTime = endTime;
        this.tourGroup = tourGroup;
        this.tourTitle = tourTitle;
        this.tourImg = tourImg;
        this.capacity = capacity;
    }

    public String getTourImg() {
        return tourImg;
    }

    public void setTourImg(String tourImg) {
        this.tourImg = tourImg;
    }


    public String getTourTitle() {
        return tourTitle;
    }

    public void setTourTitle(String tourTitle) {
        this.tourTitle = tourTitle;
    }

    public String getTourLeaderName() {
        return tourLeaderName;
    }

    public void setTourLeaderName(String tourLeaderName) {
        this.tourLeaderName = tourLeaderName;
    }

    public String getTourLeaderImg() {
        return tourLeaderImg;
    }

    public void setTourLeaderImg(String tourLeaderImg) {
        this.tourLeaderImg = tourLeaderImg;
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

    public String getMoveTime() {
        return moveTime;
    }

    public void setMoveTime(String moveTime) {
        this.moveTime = moveTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTourGroup() {
        return tourGroup;
    }

    public void setTourGroup(String tourGroup) {
        this.tourGroup = tourGroup;
    }


    private View.OnClickListener requestBtnClickListener;

    public Tour() {
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
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        Tour item = (Tour) o;
//
//        if (destination != item.destination) return false;
//        if (price != null ? !price.equals(item.price) : item.price != null) return false;
//        if (language != null ? !language.equals(item.language) : item.language != null)
//            return false;
//        if (startDate != null ? !startDate.equals(item.startDate) : item.startDate != null)
//            return false;
//        if (endDate != null ? !endDate.equals(item.endDate) : item.endDate != null)
//            return false;
//        if (dayNum != null ? !dayNum.equals(item.dayNum) : item.dayNum != null) return false;
//        return !(nightNum != null ? !nightNum.equals(item.nightNum) : item.nightNum != null);
//
//    }

//    @Override
//    public int hashCode() {
//        int result = price != null ? price.hashCode() : 0;
//        result = 31 * result + (language != null ? language.hashCode() : 0);
//        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
//        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
//        result = 31 * result + destination;
//        result = 31 * result + (dayNum != null ? dayNum.hashCode() : 0);
//        result = 31 * result + (nightNum != null ? nightNum.hashCode() : 0);
//        return result;
//    }

    /**
     * @return List of elements prepared for tests
     */
    public static ArrayList<Tour> getTestingList() {
        ArrayList<Tour> items = new ArrayList<>();
        items.add(new Tour("$43","$35", "Ar", "12 Nov 2018", "14 nov 2018", "Tehran", 4, 5,"Behrooz Shahi" , "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQg2Gp3NSj57yE91wSdHxOWSS-U2clqz2Bu-m-Uc5T7d2ffRrn3QA","Tehran, Ferdosi Sq","Kashan, Emam Sq","Tomorrow, 08:00 am","wednesday, 05:00 pm","National Parks Tour","Bus, vip , 35","https://images.unsplash.com/photo-1521348586094-bf54fdaa3004?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=750&q=80",Capacity.Available));
        items.add(new Tour("$44","$38", "En", "17 nov 2018", "20 nov 2018", "Tehran", 3, 3, "Ali Rezai" , "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRxxqHTgbSjO2gpvNyjC-JkkR2zQL1kvYUyXcBUBn7wTjHat7q6","Tehran, Ferdosi Sq","Kashan, Emam Sq","Tomorrow, 08:00 am","wednesday, 05:00 pm","National Parks Tour","Bus, vip , 35","https://images.unsplash.com/photo-1527217346210-58e169b438f7?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=750&q=80",Capacity.Available));
        items.add(new Tour("$63","$55", "En", "20 nov 2018", "21 nov 2018", "Qom", 5, 4,"Shiva Asadi","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQiODLegOX5UdN9YiVwjifJHb0yrH_l33L2rdGDvVmyaRhrwDbm","Tehran, Ferdosi Sq","Kashan, Emam Sq","Tomorrow, 08:00 am","wednesday, 05:00 pm","National Parks Tour","Bus, vip , 35","https://images.unsplash.com/photo-1524945054674-362051610bd0?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=607&q=80",Capacity.Limited));
        items.add(new Tour("$30","$24", "Fa, En", "22 nov 2018", "26 nov 2018", "Qazvin", 1, 2,"Reza alizadeh","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSv6HDYmH1kfLPS6-IucSwTeepLi9Z_2YLalAwjOFSkvUOPtT12","Tehran, Ferdosi Sq","Kashan, Emam Sq","Tomorrow, 08:00 am","wednesday, 05:00 pm","National Parks Tour","Bus, vip , 35","https://images.unsplash.com/photo-1529154179990-c490c5701e52?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=750&q=80",Capacity.Available));
        items.add(new Tour("$50" ,"$40", "En, Ar", "23 nov 2018", "28 nov 2018", "Esfahan", 3, 3,"Elnaz nasimi","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQaZgl7KiYWVSWNbFSQWTrJWxTVk51DymVkDVepxLERzzhehkERGg","Tehran, Ferdosi Sq","Kashan, Emam Sq","Tomorrow, 08:00 am","wednesday, 05:00 pm","National Parks Tour","Bus, vip , 35","https://images.unsplash.com/photo-1502135750159-8efc89fe563b?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=667&q=80",Capacity.SoldOut));
        return items;
    }



    public String getPreviousPrice() {
        return previousPrice;
    }

    public void setPreviousPrice(String previousPrice) {
        this.previousPrice = previousPrice;
    }

    public Capacity getCapacity() {
        return capacity;
    }

    public void setCapacity(Capacity capacity) {
        this.capacity = capacity;
    }
}
