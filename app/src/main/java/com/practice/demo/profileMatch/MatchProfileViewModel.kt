package com.practice.demo.profileMatch

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practice.demo.db.MatchProfileRepository
import com.practice.demo.domain.MainRepository
import com.practice.demo.utils.toEntity
import com.practice.demo.utils.toProfileUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatchProfileViewModel @Inject constructor(
    private val repository: MainRepository,
    private val repo: MatchProfileRepository
): ViewModel() {

    var state by mutableStateOf(MatchProfileContract.state())

    fun getProfileList() {
        viewModelScope.launch {
            try {
                state = state.copy(
                    isLoading = true
                )

                val cached = repo.getProfilesFromDb()
                Log.d("TAG", "getProfileList: $cached")
                if (cached.isNotEmpty()) {
                    state = state.copy(
                        listOfProfile = cached.map { it.toProfileUiState() },
                        isLoading = false
                    )
                }
                val response = repository.getUsers(count = 10)

                if (response.isSuccessful) {
                    val apiProfiles = response.body()?.results ?: emptyList()

                    val entities = apiProfiles.map { it.toEntity() }

                    repo.insertProfiles(entities)

                    val uiProfiles = apiProfiles.map { user ->
                        MatchProfileContract.ProfileUiState(
                            profile = user,
                            interactionStatus = MatchProfileContract.InteractionStatus.NONE
                        )
                    }

                    state = state.copy(
                        listOfProfile = uiProfiles,
                        isLoading = false
                    )
                } else {
                    state = state.copy(
                        error = true,
                        errorMessage = "Error fetching data: ${response.message()}",
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                val cached = repo.getProfilesFromDb()
                if (cached.isNotEmpty()) {
                    state = state.copy(
                        listOfProfile = cached.map { it.toProfileUiState() },
                        isLoading = false,
                        error = false
                    )
                } else {
                    state = state.copy(
                        error = true,
                        errorMessage = "Network Error: ${e.message}",
                        isLoading = false
                    )
                }
            }
        }
    }

    fun updateInteraction(uuid: String, status: MatchProfileContract.InteractionStatus) {
        viewModelScope.launch {
            repo.updateInteraction(uuid, status)

            state = state.copy(
                listOfProfile = state.listOfProfile.map {
                    if (it.profile.login?.uuid == uuid) it.copy(interactionStatus = status)
                    else it
                }
            )
        }
    }


    fun smartTruncate(text: String?, desiredPrefixLength: Int, maxLengthWithEllipsis: Int): String {
        if (text == null) return ""
        if (desiredPrefixLength < 0 || maxLengthWithEllipsis < 0) throw IllegalArgumentException("Lengths cannot be negative")
        if (maxLengthWithEllipsis < 3 && text.length > maxLengthWithEllipsis) return "...".take(maxLengthWithEllipsis) // Edge case: max length too small for ellipsis
        if (maxLengthWithEllipsis < 3) return text.take(maxLengthWithEllipsis)


        if (text.length <= maxLengthWithEllipsis) {
            return text // Fits entirely
        }

        // Try to keep the desired prefix
        val prefix = text.take(desiredPrefixLength)

        if (prefix.length + 3 <= maxLengthWithEllipsis) { // Prefix + "..." fits
            // Check if the original string was actually longer than the prefix
            if (text.length > prefix.length) {
                return prefix + "..."
            } else {
                return prefix // Original string was just the prefix
            }
        } else {
            // Prefix + "..." doesn't fit, so truncate to maxLengthWithEllipsis - 3 and add "..."
            if (maxLengthWithEllipsis <= 3) return "...".take(maxLengthWithEllipsis) // Not enough space even for "..."
            return text.take(maxLengthWithEllipsis - 3) + "..."
        }
    }


}