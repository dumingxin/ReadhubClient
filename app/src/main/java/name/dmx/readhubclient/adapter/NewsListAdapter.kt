package name.dmx.readhubclient.adapter

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import name.dmx.readhubclient.R
import name.dmx.readhubclient.model.News
import name.dmx.readhubclient.model.Topic
import name.dmx.readhubclient.widgets.TimeTextView

/**
 * Created by dmx on 17-10-31.
 */
class NewsListAdapter(private val context: Context, var data: List<News>) : RecyclerView.Adapter<NewsListAdapter.MyViewHolder>() {
    var onItemClickListener: OnItemClickListener? = null
    override fun onBindViewHolder(holder: MyViewHolder?, position: Int) {
        val item = data[position]
        holder?.title?.text = item.title
        holder?.time?.text = item.publishDate
        holder?.summary?.text = item.summary
        holder?.siteAndAuthor?.text = item.siteName + if (item.authorName != null) "/ ${item.authorName}" else ""
        holder?.carView?.tag = position
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.news_list_item, parent, false)
        val viewHolder = MyViewHolder(view)
        viewHolder.carView.setOnClickListener {
            onItemClickListener?.onItemClick(viewHolder.carView, viewHolder.carView.tag as Int)
        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class MyViewHolder : RecyclerView.ViewHolder {
        var carView: CardView
        var title: TextView
        var time: TimeTextView
        var summary: TextView
        var siteAndAuthor: TextView

        constructor(view: View) : super(view) {
            this.carView = view.findViewById(R.id.cardView)
            title = view.findViewById(R.id.title)
            time = view.findViewById(R.id.time)
            summary = view.findViewById(R.id.summary)
            siteAndAuthor = view.findViewById(R.id.siteAndAuthor)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }
}