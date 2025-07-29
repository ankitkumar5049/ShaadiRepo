package com.practice.demo.profileMatch

import com.practice.demo.domain.UserResult

class MatchProfileContract{

    data class state(
        val listOfProfile: List<UserResult> = emptyList(),
        val isLoading: Boolean = false,
        val error: Boolean = true,
        val errorMessage: String = "Something went wrong!"
    )

    data class ProfileUiState(
        val profile: state,
        val isAccepted: Boolean = false,
        val isDeclined: Boolean = false,
        val isFavorite: Boolean = false,
        val interactionStatus: InteractionStatus = InteractionStatus.NONE
    )

    enum class InteractionStatus { NONE, ACCEPTED, DECLINED }
}