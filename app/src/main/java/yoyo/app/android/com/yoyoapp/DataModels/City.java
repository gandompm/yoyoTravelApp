package yoyo.app.android.com.yoyoapp.DataModels;

import java.util.ArrayList;

public class City {

    private String name;
    private String image;
    private String naturalAttraction;
    private String about;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static ArrayList<City> getFakeCities()
    {
        ArrayList<City> cities = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            City city = new City();

            switch (i)
            {
                case 0:
                    city.setName("Tehran");
                    city.setImage("https://images.unsplash.com/photo-1524567492592-cee28084482e?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=375&q=80");
                    break;
                case 1:
                    city.setName("Kashan");
                    city.setImage("https://images.unsplash.com/photo-1530311583484-ea8bf4c407fb?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=750&q=80");
                    break;
                case 2:
                    city.setName("Neyshabour");
                    city.setImage("https://www.eghamat24.com/blog/wp-content/uploads/2017/04/khayyam-Nishapur.jpg");
                    break;
                case 3:
                    city.setName("Isfahan");
                    city.setImage("http://img6.irna.ir/1396/13960901/82739650/n82739650-72008394.jpg");
                    break;
                case 4:
                    city.setName("Kerman");
                    city.setImage("https://theiranproject.com/wp-content/uploads/2014/03/kerman-3.jpg");
                    break;
                case 5:
                    city.setName("Shiraz");
                    city.setImage("https://images.unsplash.com/photo-1527126887308-6cdf83c7d844?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=750&q=80");
                    break;
            }
            cities.add(city);
        }
        return cities;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
