package name.dmx.readhubclient

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import name.dmx.readhubclient.adapter.TabFragmentAdapter
import name.dmx.readhubclient.fragment.NewsFragment
import name.dmx.readhubclient.fragment.NotYetImplementedFragment
import name.dmx.readhubclient.fragment.TechNewsFragment
import name.dmx.readhubclient.fragment.TopicFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        toolbar.setTitle(R.string.app_name)
        init()
    }

    private fun init() {
        val titleList = arrayListOf("热门话题", "科技动态", "开发者资讯")
        val fragmentList = arrayListOf(TopicFragment.newInstance(), NewsFragment.newInstance(), TechNewsFragment.newInstance())
        val tabFragmentAdapter = TabFragmentAdapter(supportFragmentManager, fragmentList, titleList)
        viewPager.adapter = tabFragmentAdapter
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun onStart() {
        super.onStart()

    }
}
