package yoyo.app.android.com.yoyoapp.DataModels

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ir.mirrajabi.searchdialog.core.Searchable

@Entity(tableName = "locations")
data class Location(@PrimaryKey
                    @NonNull
                    var code: String = "",
                    @ColumnInfo(name = "title")
                    var name: String? = null,
                    var order: Int = 0,
                    var lat: Double? = null,
                    var lon: Double? = null):Searchable {

    override fun getTitle(): String {
        return name ?: ""
    }
}
