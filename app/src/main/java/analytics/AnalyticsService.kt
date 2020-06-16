package analytics

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.android.hilt.R
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

interface AnalyticsService {
    fun track(name: String)
}

class AnalyticsServiceImpl @Inject constructor(
    @ActivityContext val context: Context
) : AnalyticsService {


    override fun track(name: String) {
        Toast.makeText(
            context,
            context.getString(R.string.analytics_notification, name),
            Toast.LENGTH_SHORT
        ).show()
        Log.d("AnalyticsHilt", "Screen name: $name, service instance: $this")
    }
}
