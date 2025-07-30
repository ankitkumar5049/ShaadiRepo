package com.practice.demo.profileMatch

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practice.demo.domain.ApiService
import com.practice.demo.domain.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

@HiltViewModel
class MatchProfileViewModel @Inject constructor(
    private val repository: MainRepository
): ViewModel() {

    var state by mutableStateOf(MatchProfileContract.state())

    fun getProfileList() {
        viewModelScope.launch {
            try {
                state = state.copy(
                    isLoading = true
                )
                val response = repository.getUsers(count = 10)

                if (response.isSuccessful) {
                    val userResponse = response.body()

                    state = state.copy(
                        listOfProfile = userResponse?.results ?: emptyList(),
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
                state = state.copy(
                    error = true,
                    errorMessage = "Network Error: ${e.message}",
                    isLoading = false
                )
            }
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

    fun updateInteraction(userId: String, status: MatchProfileContract.InteractionStatus) {
        val updatedInteractions = state.userInteractions.toMutableMap()
        updatedInteractions[userId] = status

        state = state.copy(userInteractions = updatedInteractions)
    }

}