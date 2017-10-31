package name.dmx.readhubclient.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hzzh.baselibrary.net.transformer.SchedulerTransformer
import kotlinx.android.synthetic.main.topic_fragment.*
import name.dmx.readhubclient.R
import name.dmx.readhubclient.adapter.TopicListAdapter
import name.dmx.readhubclient.http.DataRepository

/**
 * Created by dmx on 17-10-31.
 */
class TopicFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater?.inflate(R.layout.topic_fragment, container, false)
        return view!!
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val observable = DataRepository.getService(context).getTopics(null, 10).compose(SchedulerTransformer())
        observable.subscribe({ data ->
            val adapter = TopicListAdapter(context, data.data?.toList()!!)
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = adapter
        }, { error ->
            error.printStackTrace()
        })


    }

    companion object {
        fun newInstance(): TopicFragment {
            val topicFragment = TopicFragment()
            return topicFragment
        }
    }
}