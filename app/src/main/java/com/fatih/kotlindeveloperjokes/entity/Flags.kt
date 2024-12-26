package com.fatih.kotlindeveloperjokes.entity

import android.os.Parcel
import android.os.Parcelable


data class Flags(
    val explicit: Boolean,
    val nsfw: Boolean,
    val political: Boolean,
    val racist: Boolean,
    val religious: Boolean,
    val sexist: Boolean
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (explicit) 1 else 0)
        parcel.writeByte(if (nsfw) 1 else 0)
        parcel.writeByte(if (political) 1 else 0)
        parcel.writeByte(if (racist) 1 else 0)
        parcel.writeByte(if (religious) 1 else 0)
        parcel.writeByte(if (sexist) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Flags> {
        override fun createFromParcel(parcel: Parcel): Flags {
            return Flags(parcel)
        }

        override fun newArray(size: Int): Array<Flags?> {
            return arrayOfNulls(size)
        }
    }
}