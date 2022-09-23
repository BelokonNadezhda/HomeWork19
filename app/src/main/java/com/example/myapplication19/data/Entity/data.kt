package com.example.myapplication19

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

/*@Parcelize
data class Film(
    val title: String,
    val poster: String,
    val idfilm: Int,
    val description: String,
    var rating: Double = 0.0,
    var isInFavorites: Boolean = false
) : Parcelable
*/
@Parcelize
@Entity(tableName = "cached_films", indices = [androidx.room.Index(value = ["title"], unique = true)])
data class Film(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "poster_path") val poster: String, //У нас будет приходить ссылка на картинку, так что теперь это String
    @ColumnInfo(name = "overview") val description: String,
    @ColumnInfo(name = "vote_average") var rating: Double = 0.0, //Приходит нецелое число с API
    var isInFavorites: Boolean = false
) : Parcelable
