package com.practice.demo.domain

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("results")
    val results: List<UserResult>,
    @SerializedName("info")
    val info: Info
)

data class UserResult(
    @SerializedName("gender")
    val gender: String?,
    @SerializedName("name")
    val name: Name?,
    @SerializedName("location")
    val location: Location?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("login")
    val login: Login?,
    @SerializedName("dob")
    val dob: Dob?,
    @SerializedName("registered")
    val registered: Registered?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("cell")
    val cell: String?,
    @SerializedName("id")
    val id: Id?,
    @SerializedName("picture")
    val picture: Picture?,
    @SerializedName("nat")
    val nat: String?
)

data class Name(
    @SerializedName("title")
    val title: String?,
    @SerializedName("first")
    val first: String?,
    @SerializedName("last")
    val last: String?
)

data class Location(
    @SerializedName("street")
    val street: Street?,
    @SerializedName("city")
    val city: String?,
    @SerializedName("state")
    val state: String?,
    @SerializedName("country")
    val country: String?,
    @SerializedName("postcode")
    val postcode: String?, // Can be String or Int depending on API, String is safer
    @SerializedName("coordinates")
    val coordinates: Coordinates?,
    @SerializedName("timezone")
    val timezone: Timezone?
)

data class Street(
    @SerializedName("number")
    val number: Int?,
    @SerializedName("name")
    val name: String?
)

data class Coordinates(
    @SerializedName("latitude")
    val latitude: String?,
    @SerializedName("longitude")
    val longitude: String?
)

data class Timezone(
    @SerializedName("offset")
    val offset: String?,
    @SerializedName("description")
    val description: String?
)

data class Login(
    @SerializedName("uuid")
    val uuid: String?,
    @SerializedName("username")
    val username: String?,
    // Add other login fields if needed (password, salt, md5, sha1, sha256)
)

data class Dob(
    @SerializedName("date")
    val date: String?, // Consider converting to Date object later
    @SerializedName("age")
    val age: Int?
)

data class Registered(
    @SerializedName("date")
    val date: String?, // Consider converting to Date object later
    @SerializedName("age")
    val age: Int?
)

data class Id(
    @SerializedName("name")
    val name: String?,
    @SerializedName("value")
    val value: String? // Can be null if ID is not available
)

data class Picture(
    @SerializedName("large")
    val large: String?,
    @SerializedName("medium")
    val medium: String?,
    @SerializedName("thumbnail")
    val thumbnail: String?
)

data class Info(
    @SerializedName("seed")
    val seed: String?,
    @SerializedName("results")
    val results: Int?,
    @SerializedName("page")
    val page: Int?,
    @SerializedName("version")
    val version: String?
)
