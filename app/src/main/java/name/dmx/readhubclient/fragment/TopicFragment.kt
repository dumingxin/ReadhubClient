package name.dmx.readhubclient.fragment

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.topic_fragment.*
import name.dmx.readhubclient.R
import name.dmx.readhubclient.activity.WebViewActivity
import name.dmx.readhubclient.adapter.TopicListAdapter
import name.dmx.readhubclient.model.Topic
import name.dmx.readhubclient.viewmodel.TopicViewModel

/**
 * 热门话题
 * Created by dmx on 17-10-31.
 */
class TopicFragment : Fragment() {
    private val PAGE_SIZE = 10
    private var dataList: List<Topic> = ArrayList()
    private lateinit var topicViewModel: TopicViewModel
    private lateinit var topicLiveData: LiveData<List<Topic>>
    private var adapter: TopicListAdapter? = null

    private fun getObserver() = Observer<List<Topic>> { newsList ->
        if (newsList != null) {
            dataList = newsList
            if (adapter == null) {
                adapter = TopicListAdapter(context, dataList)
                adapter!!.onItemClickListener = onItemClickListener
                recyclerView.layoutManager = LinearLayoutManager(context)
                recyclerView.adapter = adapter
            } else {
                adapter?.data = dataList
            }
            smartRefreshLayout.finishLoadmore()
            smartRefreshLayout.finishRefresh()
            adapter!!.notifyDataSetChanged()
            recyclerView.scrollToPosition(dataList.size - PAGE_SIZE)
        }
    }

    private val onItemClickListener = object : TopicListAdapter.OnItemClickListener {
        override fun onItemClick(view: View, position: Int) {
            val item = dataList[position]
            val intent = WebViewActivity.makeIntent(context, "https://readhub.me/topic/${item.id}", item.title, "")
            startActivity(intent)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater?.inflate(R.layout.topic_fragment, container, false)
        return view!!
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        topicViewModel = ViewModelProviders.of(this).get(TopicViewModel::class.java)
        topicLiveData = topicViewModel.getLiveData(PAGE_SIZE)
        topicLiveData.observe(this, getObserver())
        smartRefreshLayout.setOnRefreshListener {
            topicViewModel.refresh()
        }
        smartRefreshLayout.setOnLoadmoreListener {
            topicViewModel.loadMore()
        }
    }

    companion object {
        fun newInstance(): TopicFragment {
            val topicFragment = TopicFragment()
            return topicFragment
        }
    }
}