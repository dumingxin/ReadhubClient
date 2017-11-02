package name.dmx.readhubclient.widgets

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import name.dmx.readhubclient.toDate
import java.text.SimpleDateFormat

/**
 * Created by dmx on 17-10-31.
 */
class TimeTextView : TextView {
    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet)
    constructor(context: Context) : this(context, null)

    override fun setText(text: CharSequence?, type: BufferType?) {
        val formatText = formatTime(text.toString())
        super.setText(formatText, type)
    }

    private fun formatTime(text: String): String {
        if (text.isEmpty()) {
            return text
        }
        try {
            val date = text.toDate("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            val timestamp = date?.time!!
            val current = System.currentTimeMillis()
            val timeSpan = (current - timestamp) / 1000
            return TimeDescription(timeSpan).toString()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return text
    }

    inner class TimeDescription(private val timeSpan: Long) {
        private val day: Int
        private val hour: Int
        private val minute: Int

        init {
            day = (timeSpan / (60 * 60 * 24)).toInt()
            hour = (timeSpan % (60 * 60 * 24) / (60 * 60)).toInt()
            minute = (timeSpan % (60 * 60) / 60).toInt()
        }

        override fun toString(): String {
            if (day > 0) {
                return "${day}天前"
            }
            if (hour > 0) {
                return "${hour}小时前"
            }
            if (minute > 0) {
                return "${minute}分钟前"
            }
            return "刚刚"
        }
    }
}