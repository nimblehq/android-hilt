package com.example.android.hilt.util

import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test

@Suppress("IllegalIdentifier")
class DateFormatterTest {

    @Test
    fun formatDate() {
        val formatter = DateFormatter()
        formatter.formatDate(1592460867421) shouldBeEqualTo "18 Jun 2020 13:14:27"
    }
}
