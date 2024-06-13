package com.example.tap_sample.database

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigInteger

@Entity(tableName = "inspector")
data class Inspector(
    @PrimaryKey
    @ColumnInfo(name = "_id") val id: Int,
    @ColumnInfo(name = "card_type") val cardType: String?,
    @ColumnInfo(name = "comments") val comments: String?,
    @ColumnInfo(name = "employee_id") val employeeId: Int?,
    @ColumnInfo(name = "expire_date") val expireDate: String?,
    @ColumnInfo(name = "first_name") val firstName: String?,
    @ColumnInfo(name = "gender") val gender: String?,
    @ColumnInfo(name = "group_name") val groupName: String?,
    @ColumnInfo(name = "last_name") val lastName: String?,
    @ColumnInfo(name = "mifare") val mifare: String?,
    @ColumnInfo(name = "mifare_16") val mifare16: String?,
    @ColumnInfo(name = "operator") val operator: String?,
    @ColumnInfo(name = "password") val password: String?,
    @ColumnInfo(name = "prox_card_id") val proxCardId: String?,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "username") val username: String?,
    @ColumnInfo(name = "bureau") val bureau: String?,
    @ColumnInfo(name = "division") val division: String?,
    @ColumnInfo(name = "vendorid") val vendorId: Int,
    ):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(cardType)
        parcel.writeString(comments)
        parcel.writeValue(employeeId)
        parcel.writeString(expireDate)
        parcel.writeString(firstName)
        parcel.writeString(gender)
        parcel.writeString(groupName)
        parcel.writeString(lastName)
        parcel.writeString(mifare)
        parcel.writeString(mifare16)
        parcel.writeString(operator)
        parcel.writeString(password)
        parcel.writeString(proxCardId)
        parcel.writeString(title)
        parcel.writeString(username)
        parcel.writeString(bureau)
        parcel.writeString(division)
        parcel.writeInt(vendorId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Inspector> {
        override fun createFromParcel(parcel: Parcel): Inspector {
            return Inspector(parcel)
        }

        override fun newArray(size: Int): Array<Inspector?> {
            return arrayOfNulls(size)
        }
    }
}



