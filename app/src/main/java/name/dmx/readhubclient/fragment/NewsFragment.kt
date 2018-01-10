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
import kotlinx.android.synthetic.main.news_fragment.*
import name.dmx.readhubclient.R
import name.dmx.readhubclient.activity.WebViewActivity
import name.dmx.readhubclient.adapter.NewsListAdapter
import name.dmx.readhubclient.enum.NewsType
import name.dmx.readhubclient.getNewsType
import name.dmx.readhubclient.model.News
import name.dmx.readhubclient.putNewsType
import name.dmx.readhubclient.viewmodel.NewsViewModel
import name.dmx.readhubclient.viewmodel.NewsViewModelFactory

/**
 * 科技动态、开发者资讯
 * Created by dmx on 17-10-31.
 */
class NewsFragment : Fragment() {
    private val PAGE_SIZE = 10
    private var dataList: List<News> = ArrayList()
    private lateinit var newsViewModel: NewsViewModel
    private lateinit var newsLiveData: LiveData<List<News>>
    private var adapter: NewsListAdapter? = null
    private var newsType: NewsType = NewsType.TechNews

    private fun getObserver() = Observer<List<News>> { newsList ->
        if (newsList != null) {
            dataList = newsList
            if (adapter == null) {
                adapter = NewsListAdapter(context, dataList)
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newsType = arguments?.getNewsType(KEY_NEWS_TYPE)!!
    }

    private val onItemClickListener = object : NewsListAdapter.OnItemClickListener {
        override fun onItemClick(view: View, position: Int) {
            val item = dataList[position]
            val intent = WebViewActivity.makeIntent(context, item.url, item.title, "")
            startActivity(intent)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater?.inflate(R.layout.news_fragment, container, false)
        return view!!
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        newsViewModel = ViewModelProviders.of(this, NewsViewModelFactory(newsType, PAGE_SIZE)).get(NewsViewModel::class.java)
        newsLiveData = newsViewModel.getLiveData()
        newsLiveData.observe(this, getObserver())
        smartRefreshLayout.setOnRefreshListener {
            newsViewModel.refresh()
        }
        smartRefreshLayout.setOnLoadmoreListener {
            newsViewModel.loadMore()
        }
    }

    companion object {
        val KEY_NEWS_TYPE = "KEY_NEWS_TYPE"
        fun newInstance(newsType: NewsType): NewsFragment {
            val fragment = NewsFragment()
            val bundle = Bundle()
            bundle.putNewsType(KEY_NEWS_TYPE, newsType)
            fragment.arguments = bundle
            return fragment
        }
    }
}

