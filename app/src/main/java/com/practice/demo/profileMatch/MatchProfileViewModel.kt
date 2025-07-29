package com.practice.demo.profileMatch

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MatchProfileViewModel @Inject constructor(): ViewModel() {

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