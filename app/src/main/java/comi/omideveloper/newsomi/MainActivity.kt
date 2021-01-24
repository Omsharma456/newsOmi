package comi.omideveloper.newsomi

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.littlemango.stacklayoutmanager.StackLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var adapter: NewsAdapter
    private var articles = mutableListOf<Article>()
    var page = 1
    var totalResults = -1
    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = NewsAdapter(this@MainActivity, articles)
        findViewById<RecyclerView>(R.id.NewsRecycleView).adapter = adapter
        val layoutManager = StackLayoutManager(StackLayoutManager.ScrollOrientation.BOTTOM_TO_TOP)
        layoutManager.setPagerMode(true)
        layoutManager.setPagerFlingVelocity(100)
        findViewById<RecyclerView>(R.id.NewsRecycleView).layoutManager = layoutManager
        layoutManager.setItemChangedListener(object : StackLayoutManager.ItemChangedListener {
            override fun onItemChanged(position: Int) {
                Log.d(TAG, "First Visible item - ${layoutManager.getFirstVisibleItemPosition()}")
                Log.d(TAG, "Total count item - ${layoutManager.itemCount}")
                if (totalResults > layoutManager.itemCount && layoutManager.getFirstVisibleItemPosition() >= layoutManager.itemCount - 5) {
                    page++;
                    getNews()
                }
            }

        })
        getNews()
    }

    private fun getNews() {
        val news = NewsService.newsInstance.getHeadlines("in", page)
        news.enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                val news = response.body()
                if (news != null) {
                    totalResults = news.totalResults
                    Log.d("OmiNews", news.toString())
                    articles.addAll(news.articles)
                    adapter.notifyDataSetChanged()

                }
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d("OmiNews", "Error in fetching News")
            }

        })
    }
}