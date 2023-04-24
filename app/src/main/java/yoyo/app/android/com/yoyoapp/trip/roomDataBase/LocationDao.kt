package yoyo.app.android.com.yoyoapp.trip.roomDataBase

import androidx.room.*
import yoyo.app.android.com.yoyoapp.DataModels.Location

@Dao
interface LocationDao {
    @Query("SELECT * FROM locations")
    fun getAll(): List<Location>

    @Insert
    fun insertAll(vararg locations: Location)


    @Delete
    fun delete(location: Location)

}