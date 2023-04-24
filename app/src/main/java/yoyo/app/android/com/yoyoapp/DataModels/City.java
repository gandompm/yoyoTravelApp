package yoyo.app.android.com.yoyoapp.DataModels;

import java.util.ArrayList;

public class City {

    private String name;
    private String image;
    private String about;
    private String naturalAttraction;
    private String historicalAttraction;
    private String ManMadeAttraction;
    private String foodAndSouvenir;
    private String climate;
    private String topExperince;

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getNaturalAttraction() {
        return naturalAttraction;
    }

    public void setNaturalAttraction(String naturalAttraction) {
        this.naturalAttraction = naturalAttraction;
    }

    public String getHistoricalAttraction() {
        return historicalAttraction;
    }

    public void setHistoricalAttraction(String historicalAttraction) {
        this.historicalAttraction = historicalAttraction;
    }

    public String getManMadeAttraction() {
        return ManMadeAttraction;
    }

    public void setManMadeAttraction(String manMadeAttraction) {
        ManMadeAttraction = manMadeAttraction;
    }

    public String getFoodAndSouvenir() {
        return foodAndSouvenir;
    }

    public void setFoodAndSouvenir(String foodAndSouvenir) {
        this.foodAndSouvenir = foodAndSouvenir;
    }

    public String getClimate() {
        return climate;
    }

    public void setClimate(String climate) {
        this.climate = climate;
    }

    public String getTopExperince() {
        return topExperince;
    }

    public void setTopExperince(String topExperince) {
        this.topExperince = topExperince;
    }

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
                    city.setAbout("Tehran is the capital and the most populous city in Iran and has been known as the capital of Iran for more than 200 years.\n" +
                            "The city, which is now the political and administrative pole of the country, has been further developed since the Qajar period.\n" +
                            "Tehran is one of the five top tourist destinations in Iran, and its most important and first symbol is the Liberation Tower\n" +
                            "But what does Tehran mean?\n" +
                            "Tehran in the word means tropical region against Shemiran (regional name in Tehran) is the cold region.\n");
                    city.setNaturalAttraction("Tochal Mountain\n" +
                            "Mount Damavand\n");
                    city.setHistoricalAttraction("Golestan Palace\n" +
                            "Sa'dabad Complex\n" +
                            "Niavaran Complex\n" +
                            "Tehran Bazaar\n" +
                            "Saint Sarkis Cathedral\n" +
                            "National Museum of Iran\n" +
                            "National Jewels Museum\n" +
                            "Azadi Tower\n");
                    city.setManMadeAttraction("Milad Tower\n" +
                            "Tabiat Bridge\n");
                    city.setFoodAndSouvenir("The most famous foods in Tehran are Ghorme sabzi and Kabab koobideh that have a lot of fans.\n" +
                            "Traditional ice cream is also one of the desserts that was first produced in Tehran and very tasty.\n");
                    city.setClimate("Spring: Tehran's weather is very pleasant in the first months of the year.\n" +
                            "Summer: The weather is very hot and the days are very long.\n" +
                            "Autumn: Tehran's weather is very desirable and the rain gives a special freshness to the environment.\n" +
                            "Winter: Mount Damavand is covered with snow, but air pollution is higher this season.\n");
                    city.setTopExperince("Skiing on the Dizin International resort\n" +
                            "The first international ski resort in Iran is one of the most popular ski resort in the Middle East. The mountainous and snow-covered landscape of this resort draws many athletes into the area.\n");
                    break;
                case 1:
                    city.setName("Kashan");
                    city.setImage("https://images.unsplash.com/photo-1530311583484-ea8bf4c407fb?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=750&q=80");
                    city.setAbout("The city of Kashan is the cradle of civilization and culture of Iran, which is 7000 years old. It has a 3-hour distance from Tehran, and according to many tourists, one of the most beautiful cities in Iran.\n" +
                            "In this city one of the most famous Iranian ceremonies called Golabgiri (that Extract oil from Damask rose) performed at this ceremony in accordance with the special rules .\n" +
                            "This city is one of the most popular tourist destinations in Iran over the past decades.\n");
                    city.setNaturalAttraction("Abouzeidabad desert\n" +
                            "Maranjab desert\n");
                    city.setHistoricalAttraction("Abyaneh village\n" +
                            "Noushabad Underground City\n" +
                            "Borujerdi House\n" +
                            "Sialk Hills\n" +
                            "Agha Bozorg Mosque\n");
                    city.setManMadeAttraction("");
                    city.setFoodAndSouvenir("Kashan handicrafts are very rich and diverse, including them Traditional Iranian painting and Ghalamzani of this city\n" +
                            "(Insertion on metal bodies of copper, rice, gold, and silver is called the so-called ghalamzani)\n" +
                            " Kashan weaving carpet is so interesting that its name has been placed among the intangible heritage of UNESCO.\n");
                    city.setClimate("Spring: Many tourists visit Kashan this season due to the beginning of the Golab giry ceremony and the appropriate weather.\n" +
                            "Summer: Throughout the summer the weather is warm and dry\n" +
                            "Autumn: In this season, the weather is very good during the day and very cold at night.\n" +
                            "Winter: The weather is cold.\n");
                    city.setTopExperince("he desert and the sunrise and sunset view in the desert of Kashan is unique. Also, the experience of motorcycling and safari in the desert of Kashan is very enjoyable.");
                    break;
                case 2:
                    city.setName("Neyshabour");
                    city.setImage("https://www.eghamat24.com/blog/wp-content/uploads/2017/04/khayyam-Nishapur.jpg");
                    city.setAbout("About Neyshabur city | Neyshabur is one of beautiful and attractive cities of Khorasan. Also, Neyshabur is a place where Kamal-Ol-Molk (one of the greatest Iranian painter), Khayyam-e Neyshaburi (a great Persian poet and mystic) and Attar-e Neyshaburi (a great Persian mathematician, astronomer, and poet) are buried. In following sections, we will familiarize you with this city briefly.");
                    city.setNaturalAttraction("Neyshabur Turquoise\n");
                    city.setHistoricalAttraction("Omar Khayyam Mausoleum\n" +
                            "Khayam Planetarium\n" +
                            "Mausoleum of Attar Neyshaburi\n" +
                            "Tomb of Kamal-ol-Molk\n" +
                            "Wooden Mosque of Neyshabur\n");
                    city.setManMadeAttraction("");
                    city.setFoodAndSouvenir("Neyshabur has diverse and delicious local foods which are cooked in special way. Some of these foods are: Qurut/ Qurutu, Halim, Ash-e Balqur, Ash-e Zireh, Eshkeneh with tomato, Yatimcheh, rhubarb stew, Ash-e Bi Bi Seshanbeh, , Adas Banjo, Eshkeneh Kashk, Ash-e Amaj, Komay, Bilmaq, Abgusht, Zirak Jush, Kaleh Jush, Kuku with edible mushroom, Hoobleh Berenj, Siki Yarma, Dolma.\n"+
                           "\n Neyshabur has many handicrafts which are made in urban, rural and nomadic areas. Theses handicrafts have long history, dating back to many years ago. They include glaze, pottery, cutting and grinding turquoise, Palas weaving ( Palas is a type of cotton and woolen ground cloth) with various patterns, metalworking, copperworking dying, felted products, Forat weaving (Forat is a type of traditional fabric made of cotton or wool), different types of colorful carpets made of wool or silk, miniature, toreutics, mosaics, wood carving, Giveh sewing ( Giveh is a type of traditional Iranian footwear), mat weaving, rug weaving, woodturning, silver making, musical instruments, Khorjin (It is a type of traditional bag which is put on pack animals in order to carry loads), Sermeh embroidery and fabric weaving.");
                    city.setClimate("Neyshabur has different climate in different regions. Due to presence of mountain in north part of this city, it has a cold climate. It has a temperate climate in central part.");
                    city.setTopExperince("Neyshabur Turquoise is the finest in the world.  For at least 2,000 years, Iran and Neyshabur has remained an important source of turquoise which was named by Iranians initially “pirouzeh” (Arabic Firouzeh) meaning “victory”. This deposit, which is blue naturally, and turns green when heated due to dehydration, is restricted to a mine-riddled region in Neyshabur, the 2,012-metre (6,601 ft) mountain peak of Ali-mersai, which is tens of kilometers from Mashhad, the capital of Khorasan province.");
                    break;
                case 3:
                    city.setName("Isfahan");
                    city.setImage("http://img6.irna.ir/1396/13960901/82739650/n82739650-72008394.jpg");
                    city.setAbout("Isfahan is the third largest city in Iran and is one of the top destinations of tourism in Iran, which has always been a tourist destination for its many attractions and fine handicraft\n" +
                            "Esfahan, after becoming the capital of Iran during the reign of Shah Abbas I, greatly expanded, and most of the historical monuments in this city belong to the Islamic period.\n" +
                            "But why are they telling this city Nesfe- jahan (half of the world)?\n" +
                            "Due to thousands of historical attractions in the city of Isfahan, this city is said to be half the world.\n");
                    city.setNaturalAttraction("Varzaneh Desert\n" +
                            "AB malakh waterfall\n" +
                            "Mount Soffeh\n" +
                            "Zayanderud\n");
                    city.setHistoricalAttraction("Vank Cathedral\n" +
                            "Khaju Bridge\n" +
                            "Si-o-se-pol-\n" +
                            "Menar Jonban\n" +
                            "Jameh Mosque of Isfahan\n" +
                            "Qeysarieh bazar\n" +
                            "Chehel Sotoun palace\n" +
                            "Hasht Behesht palace\n" +
                            "Sheikh Lotfollah Mosque\n" +
                            "Shah Mosque\n" +
                            "Ālī Qāpū\n" +
                            "Naqsh-e Jahan Square\n");
                    city.setManMadeAttraction("Esfahan Birds Garden\n" +
                            "Isfahan Aquarium\n");
                    city.setFoodAndSouvenir("Undoubtedly, one of the most famous and delicious traditional foods in Isfahan is Bryani; the food looks like a hamburger, and is made up of a mixture of meat, white liver, spices, onions.\n" +
                            "One of the most famous handicrafts in Isfahan is the Khatam, but what is Khatam?\n" +
                            "It is a version of marquetry where art forms are made by decorating the surface of wooden articles with delicate pieces of wood, bone and metal precisely-cut intricate geometric patterns.\n" +
                            "But from other handicrafts of Isfahan to Tarem this city can be mentioned\n");
                    city.setClimate("Spring: In the first two months of the spring, the air is warm, but then it gets warmer.\n" +
                            "Summer: Due to the warm and dry climate of Isfahan, the summer is hot.\n" +
                            "Autumn: In the early fall, the weather is very suitable.\n" +
                            "Winter: The weather is cold and dry in winter\n");
                    city.setTopExperince("The most important pleasures that can be experienced in Isfahan are Desert Recreation in cities like Varzaneh.\n" +
                            "Another experience is to explore the city's historic market and experience a talk with a native people.\n");
                    break;
                case 4:
                    city.setName("Kerman");
                    city.setImage("https://theiranproject.com/wp-content/uploads/2014/03/kerman-3.jpg");
                    city.setAbout("Kerman is the second highest city in Iran, where the history of human settlements and settlements reaches four thousand BC.\n" +
                            "This city has passed behind so many ups and downs through history and so many important events have taken place here from pre-Islamic times until contemporary times.\n" +
                            "But what does Kerman mean in the word?\n" +
                            "Kerman in the word means the place of bravery and battle.\n" +
                            "This city is full of historic and natural tourist attractions, so it likens the city to the jewel of the desert.\n");
                    city.setNaturalAttraction("Kalut Shahdad Desert");
                    city.setHistoricalAttraction("Vakil Bazaar\n" +
                            "Fath-Abad Garden\n" +
                            "Shazdeh Garden\n" +
                            "Rayen Castle\n" +
                            "Arg e Bam\n" +
                            "Jabalieh\n" +
                            "Vakil Bath\n" +
                            "Guohariz Jupar Qanat\n");
                    city.setManMadeAttraction("");
                    city.setFoodAndSouvenir("Kerman has a variety of traditional and delicious cuisine, which is the most famous of Halim bademjan.\n" +
                            " Different kinds of traditional Kerman sweets include Kolompeh, ,  Hajbadam, Pistachio, ,Qottab and Bereshtok\n" +
                            "Kerman's handicrafts include Scarf, Pateh and Terme\n");
                    city.setClimate("Summer: In Kerman is hot and burning.\n" +
                            "Winters: There is snowfall and blizzard in Kerman.\n" +
                            "Spring and Autumn: The best time to travel to Kerman from late autumn to the end of spring\n");
                    city.setTopExperince("Safari in the Kerman Desert is one of the best recreations in the Kerman province");
                    break;
                case 5:
                    city.setName("Shiraz");
                    city.setImage("https://images.unsplash.com/photo-1527126887308-6cdf83c7d844?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=750&q=80");
                    city.setAbout("Shiraz is a beautiful city in western Iran. Shiraz is one of the biggest cities in Iran and most significant city at the center of Fars province at the height of 1486 m above the sea level, located in the Zagros mountainous area, a highly important protection for the strategic location of Shiraz\n" +
                            "Tourism in Shiraz is not just about visiting various historical attractions and architecture of this city and it has different aspects like tourism health (Shiraz decades ago as one of the major cities of health tourism in the region of Iran and the Middle East was known. )\n" +
                            "This city has been known as the most significant tourism center,. The tombs of several poets such as Hafez and Sa’adi placed in Shiraz where embraces a major part of Iran’s ancient history, historical, cultural, religious and natural attractions.\n");
                    city.setNaturalAttraction("Margoon Waterfall\n" +
                            "Maharloo Lake\n" +
                            "Reghez canyon\n");
                    city.setHistoricalAttraction("Margoon Waterfall\n" +
                            "Maharloo Lake\n" +
                            "Reghez canyon\n" +
                            "HISTORICAL AND CULTURAL ATTRACTION\n" +
                            "Nasir-ol-molk Mosque\n" +
                            "Vakil Bazaar and Vakil Bath\n" +
                            "Arg of Karim Khan\n" +
                            "Eram Garden\n" +
                            "Qavam House\n" +
                            "Qur'an Gate\n" +
                            "Shah Cheragh\n" +
                            "Tomb of Saadi\n" +
                            "Tomb of Hafez\n" +
                            "Perspolis\n" +
                            "Pasargad\n");
                    city.setManMadeAttraction("");
                    city.setFoodAndSouvenir("Food in Shiraz is very diverse and Delicious\n" +
                            " One of the most famous foods in Shiraz is the Kalam of Polo Shirazi (a kind of food made with rice and cabbage, meat and spice) and various kinds of Aush\n" +
                            "Desserts are also popular in Shiraz, including Shirazi Salad, Falodeh Shirazi, and Muscati.\n" +
                            "Among the famous Shiraz handicrafts, we can mention the Kilim and the gobah (a kind of carpet weaving with silk and Wool)\n");
                    city.setClimate("Spring: The weather is very cool and the weather is good. The spring creates a very beautiful season in Shiraz.\n" +
                            "Summer: Shiraz air is very hot and dry.\n" +
                            "Autumn: The climate of the city of Shiraz is very cool.\n" +
                            "Winter: The weather is very mild and rainy\n");
                    city.setTopExperince("The Raghz Canyon in Fars province is one of the most beautiful natural phenomena, which Incites\n" +
                            "excitement in unique nature.\n" +
                            "This is an unforgettable adventure journey that includes: Canyoning. Rock climbing and swimming and excitement.\n");

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
