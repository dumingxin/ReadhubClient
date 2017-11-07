package name.dmx.readhubclient.adapter

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.TextAppearanceSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import name.dmx.readhubclient.R
import name.dmx.readhubclient.model.Topic
import name.dmx.readhubclient.widgets.TimeTextView

/**
 * Created by dmx on 17-10-31.
 */
class TopicListAdapter(private val context: Context, var data: List<Topic>) : RecyclerView.Adapter<TopicListAdapter.MyViewHolder>() {
    var onItemClickListener: OnItemClickListener? = null
    override fun onBindViewHolder(holder: MyViewHolder?, position: Int) {
        val item = data[position]

//        holder?.title?.text = item.title
        holder?.time?.text = item.createdAt
        holder?.summary?.text = item.summary
        holder?.cardView?.tag = position
        val ssb = SpannableStringBuilder(item.title + "  " + holder?.time?.text)
        val textAppearanceSpan = TextAppearanceSpan(context, R.style.time_textAppearance)
        ssb.setSpan(textAppearanceSpan, item.title.length + 2, ssb.length, SpannableString.SPAN_INCLUSIVE_INCLUSIVE)
        holder?.title?.text = ssb
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.toplic_list_item, parent, false)
        val viewHolder = MyViewHolder(view)
        viewHolder.cardView.setOnClickListener {
            onItemClickListener?.onItemClick(viewHolder.cardView, viewHolder.cardView.tag as Int)
        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class MyViewHolder : RecyclerView.ViewHolder {
        var cardView: CardView
        var title: TextView
        var time: TimeTextView
        var summary: TextView

        constructor(view: View) : super(view) {
            this.cardView = view.findViewById(R.id.cardView)
            title = view.findViewById(R.id.title)
            time = view.findViewById(R.id.time)
            summary = view.findViewById(R.id.summary)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }
}