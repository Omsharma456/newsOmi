package comi.omideveloper.newsomi

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

//https://newsapi.org/v2/top-headlines?country=us&apiKey=50b7955865ff497a8266cee933e40f3f
//https://newsapi.org/v2/everything?q=bitcoin&apiKey=50b7955865ff497a8266cee933e40f3f

const val BASE_URL = "https://newsapi.org/"
const val API_KEY = "50b7955865ff497a8266cee933e40f3f"

interface NewsInterface {

    @GET("v2/top-headlines?apiKey=$API_KEY")
    fun getHeadlines(@Query("country") country: String, @Query("page") page: Int): Call<News>

}

object NewsService {
    val newsInstance: NewsInterface

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        newsInstance = retrofit.create(NewsInterface::class.java)
    }
}