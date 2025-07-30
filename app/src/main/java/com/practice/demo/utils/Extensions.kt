package com.practice.demo.utils

import com.practice.demo.db.ProfileEntity

import com.practice.demo.domain.*
import com.practice.demo.profileMatch.MatchProfileContract

fun UserResult.toEntity(): ProfileEntity {
    return ProfileEntity(
        uuid = this.login?.uuid ?: "",
        name = "${this.name?.title} ${this.name?.first} ${this.name?.last}",
        age = this.dob?.age,
        city = this.location?.city,
        country = this.location?.country,
        phone = this.phone,
        email = this.email,
        pictureUrl = this.picture?.large,
        interactionStatus = MatchProfileContract.InteractionStatus.NONE
    )
}

fun ProfileEntity.toProfileUiState(): MatchProfileContract.ProfileUiState {
    return MatchProfileContract.ProfileUiState(
        profile = UserResult(
            gender = null,
            name = Name(title = null, first = this.name, last = null),
            location = Location(
                street = null,
                city = this.city,
                state = null,
                country = this.country,
                postcode = null,
                coordinates = null,
                timezone = null
            ),
            email = this.email,
            login = Login(uuid = this.uuid, username = null),
            dob = Dob(date = null, age = this.age),
            registered = null,
            phone = this.phone,
            cell = null,
            id = null,
            picture = Picture(large = this.pictureUrl, medium = null, thumbnail = null),
            nat = null
        ),
        interactionStatus = this.interactionStatus
    )
}

