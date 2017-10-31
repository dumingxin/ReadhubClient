package name.dmx.readhubclient.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import name.dmx.readhubclient.R
import name.dmx.readhubclient.model.Topic

/**
 * Created by dmx on 17-10-31.
 */
class TopicListAdapter(private val context: Context, val data: List<Topic>) : RecyclerView.Adapter<TopicListAdapter.MyViewHolder>() {
    override fun onBindViewHolder(holder: MyViewHolder?, position: Int) {
        val item = data[position]
        holder?.title?.text = item.title
//        holder?.time?.text = item.createdAt.toString()
        holder?.summary?.text = item.summary
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.toplic_list_item, null, false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class MyViewHolder : RecyclerView.ViewHolder {
        var title: TextView
        var time: TextView
        var summary: TextView

        constructor(view: View) : super(view) {
            title = view.findViewById(R.id.title)
            time = view.findViewById(R.id.time)
            summary = view.findViewById(R.id.summary)
        }
    }
}