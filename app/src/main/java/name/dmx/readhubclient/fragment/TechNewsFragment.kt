package name.dmx.readhubclient.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hzzh.baselibrary.net.transformer.SchedulerTransformer
import kotlinx.android.synthetic.main.tech_news_fragment.*
import name.dmx.readhubclient.R
import name.dmx.readhubclient.WebViewActivity
import name.dmx.readhubclient.adapter.NewsListAdapter
import name.dmx.readhubclient.http.DataRepository
import name.dmx.readhubclient.model.News
import name.dmx.readhubclient.toDate

/**
 * Created by dmx on 17-10-31.
 */
class TechNewsFragment : Fragment() {
    private var lastCursor: Long = 0L
    private val PAGE_SIZE = 10
    private var isFirstPage = true
    private val dataList: MutableList<News> = MutableList(0, { _ ->
        News()
    })
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
        getData()
        smartRefreshLayout.setOnRefreshListener {
            isFirstPage = true
            getData()
        }
        smartRefreshLayout.setOnLoadmoreListener {
            isFirstPage = false
            getData()
        }
    }

    private fun getData() {
        if (isFirstPage) {
            lastCursor = System.currentTimeMillis()
        }
        val observable = DataRepository.getService(context).getTechNews(lastCursor, PAGE_SIZE).compose(SchedulerTransformer())
        observable.subscribe({ data ->
            if (isFirstPage) {
                dataList.clear()
            }
            dataList.addAll(dataList.size, data.data?.toList()!!)
            val adapter = NewsListAdapter(context, dataList)
            adapter.onItemClickListener = onItemClickListener
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = adapter
            lastCursor = data.data?.last()?.publishDate!!.toDate("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")?.time!!
            smartRefreshLayout.finishLoadmore()
            smartRefreshLayout.finishRefresh()
            adapter.notifyDataSetChanged()
            recyclerView.scrollToPosition(dataList.size - PAGE_SIZE)
        }, { error ->
            error.printStackTrace()
            smartRefreshLayout.finishLoadmore()
            smartRefreshLayout.finishRefresh()
        })
    }

    companion object {
        fun newInstance(): TechNewsFragment {
            return TechNewsFragment()
        }
    }
}