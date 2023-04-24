package yoyo.app.android.com.yoyoapp.trip.result

import android.content.Context
import android.os.Handler
import android.util.Log
import androidx.core.util.Consumer
import yoyo.app.android.com.yoyoapp.DataModels.*
import yoyo.app.android.com.yoyoapp.trip.ApiService
import java.util.HashMap

class TourResultRepository(val context: Context) {

    private val apiService: ApiService = ApiService(context)

    fun getTripList(page: Int, tripQuery: TripQuery, f: (ArrayList<Trip>?) -> Unit){
        //apiService.getTripListRequest(page, tripQuery) { f(it) }
        Handler().postDelayed(
            {
                f(getFakeTourData())
            },
            2000 // value in milliseconds
        )

    }

    private fun getFakeTourData(): java.util.ArrayList<Trip> {
        val trips = java.util.ArrayList<Trip>()
            for (i: Int in 0..7) {
                val trip = Trip()
                trip.tripId = "djkfjdkfj"
                trip.title = "Tehran Family Trip"
                trip.nightNum = 5
                trip.dayNum = 6
                trip.summary =
                    "Pack your bags right away to explore the cosmopolitan metropolis of Iran full of fun-filled interactive activities by a customized outing with your children. This tour takes you through the royal palaces and old architectures of the streets, bazaar, park and cafes of Tehran to enjoy the quality time together. Tehran family trip is designed considering age-appropriate experiences so kids and adults can take part alike. Having a professional team tailor amenities and gateways, you are not alone; we are there to connect you with the best of Tehran."

                // leader
                val tripLeader = TripLeader()
                tripLeader.name = "Parham"
                tripLeader.picture = "https://hairstyleonpoint.com/wp-content/uploads/2016/12/Short-Mens-Hairstyles-Simple-Short-Cut-300x300.jpg"
                tripLeader.language = "English"
                trip.tripLeader = tripLeader

                // tour
                val tour = Tour()
                tour.name = "Trip in Tehran with your family and kids"
                tour.passengerCount = 25
                trip.tour = tour

                // agency
                trip.agency = "Pardisan Agency"

                // itinerary
                val itineraries = java.util.ArrayList<String>()
                itineraries.add("Walking and watching the city nightlife in a famous street Tehrani people come to enjoy street food and night chat")
                itineraries.add("Right after delicious lunch in bazaar we set forth toward the first modernized park of Iran and Tehran once used to be a private lush garden of Nasir al-Din Shah of Qajar located in Sangelaj neighbourhood as one of the oldest districts Tehran began to expand from. ")
                itineraries.add("The birding activity with all gears and a birdwatching guide will open up your child's world to the wonder. Being to watch the migrating or residing bird spices and listening to their singing in the 24th populous metropolis in the world is of a kind of experience that actual Tehranis barely know of its existence. ")
                trip.itineraries = itineraries

                // attraction
                val attractios = java.util.ArrayList<String>()
                for (j in 0 until 2) {
                    attractios.add("Tehran grand bazaar ")
                }
                trip.attractions = attractios

                // transportation
                val transportations = java.util.ArrayList<String>()
                for (j in 0 until 1) {
                    transportations.add("Bus")
                }
                trip.transportations = transportations

                // meals
                val meals = java.util.ArrayList<String>()
                meals.add("Kebab")
                meals.add("Iranian Soup")
                trip.meals = meals

                // rules
                val rules = java.util.ArrayList<String>()
                for (j in 0 until 1) {
                    rules.add(" Visa and travel insurance, international and domestic flights ,Tips (Optional), extra services not indicated in the itinerary, meals and drinks not mentioned in the itinerary, entry fee of not stipulated monuments, cost of medical immunizations, phone calls")
                }
                trip.rules = rules

                // selectedCategories
                val categories = java.util.ArrayList<String>()
                categories.add("Family Trip")
                categories.add("Advanture")
                trip.categories = categories

                // gallery
                val galleries = java.util.ArrayList<String>()
                galleries.add("https://media.mehrnews.com/d/2016/10/01/4/2227876.jpg?ts=1486462047399")
                galleries.add("https://persiadigest.com/uploads/video/Azadi-Tower-7.jpg")
                galleries.add("https://1stquest.com/blog/wp-content/uploads/2019/01/ssdfjk.jpg")

                trip.gallery = galleries

                // location

                val locations = HashMap<Int, Location>()

                    val location = Location()
                    location.name = "Grand Bazar"
                    location.lon = 35.6892
                    location.lat = 51.3890
                    location.order = 0

                    locations[0] =location
                location.name = "Ferdosi Hotel"
                location.lon = 35.7092
                location.lat = 51.5090
                location.order = 1

                locations[1] = location
                trip.locations = ArrayList()
                trip.locations.add(locations[0])
                trip.locations.add(locations[1])


                val tripCount = 7
                if (trips.size == 0) {
                    trip.resultsSize = tripCount
                }

                trips.add(trip)

            }
        return trips
    }
}
