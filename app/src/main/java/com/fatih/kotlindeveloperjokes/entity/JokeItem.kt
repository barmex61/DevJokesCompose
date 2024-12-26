package com.fatih.kotlindeveloperjokes.entity

import android.os.Parcel
import android.os.Parcelable

data class JokeItem(
    val category: String? = "",
    val delivery: String? = "",
    val error: Boolean? = false,
    val flags: Flags? = null,
    val id: Int? = 0,
    val joke: String = "",
    val lang: String? = "",
    val safe: Boolean? = false,
    val setup: String = "",
    val type: String? = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readParcelable(Flags::class.java.classLoader) as? Flags,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString()?:"",
        parcel.readString(),
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readString()?:"",
        parcel.readString()
    )


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(category)
        parcel.writeString(delivery)
        parcel.writeValue(error)
        parcel.writeParcelable(this.flags, 0)
        parcel.writeValue(id)
        parcel.writeString(joke)
        parcel.writeString(lang)
        parcel.writeValue(safe)
        parcel.writeString(setup)
        parcel.writeString(type)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<JokeItem> {
        override fun createFromParcel(parcel: Parcel): JokeItem {
            return JokeItem(parcel)
        }

        override fun newArray(size: Int): Array<JokeItem?> {
            return arrayOfNulls(size)
        }
    }
}