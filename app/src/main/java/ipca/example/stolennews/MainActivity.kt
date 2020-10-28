package ipca.example.stolennews

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.Charset
import java.text.FieldPosition


class MainActivity : AppCompatActivity() {

    var articles : MutableList<Article> = ArrayList()
    var articlesAdapter = ArticlesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listViewArticles  = findViewById<ListView>(R.id.listViewArticles)
        listViewArticles.adapter = articlesAdapter

        object : AsyncTask<Unit, Unit,String>(){
            override fun doInBackground(vararg params: Unit?) : String {
                var result = ""
                try {

                    val urlc: HttpURLConnection = URL("https://newsapi.org/v2/top-headlines?country=pt&apiKey=1765f87e4ebc40229e80fd0f75b6416c").openConnection() as HttpURLConnection
                    urlc.setRequestProperty("User-Agent", "Test")
                    urlc.setRequestProperty("Connection", "close")
                    urlc.setConnectTimeout(1500)
                    urlc.connect()
                    val stream  = urlc.inputStream
                    val isReader = InputStreamReader(stream)
                    val brin = BufferedReader(isReader)
                    var str: String = ""

                    var keepReading = true
                    while (keepReading) {
                        var line = brin.readLine()
                        if (line==null){
                            keepReading = false
                        }else{
                            str += line
                        }
                    }
                    brin.close()
                    result = str

                }catch (e:Exception){
                    e.printStackTrace()
                    result = "Sem internet!"
                }

                return result
            }

            override fun onPostExecute(result: String) {
                super.onPostExecute(result)
                if (result=="Sem internet!"){
                    Toast.makeText(this@MainActivity, "Sem internet!", Toast.LENGTH_LONG).show()
                }else {
                    val jsonObject = JSONObject(result)
                    if (jsonObject.get("status").equals("ok")){
                        var jsonArray : JSONArray = jsonObject.getJSONArray("articles")
                        for (index in 0 until jsonArray.length()){
                            val jsonArticle = jsonArray.getJSONObject(index)
                            val article = Article.fromJson(jsonArticle)
                            articles.add(article)
                        }
                         articlesAdapter.notifyDataSetChanged()
                    }


                }
            }
        }.execute(null,null,null)

    }

    inner class ArticlesAdapter : BaseAdapter() {

        override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {
            val rowView = layoutInflater.inflate(R.layout.row_article, viewGroup, false)

            val textViewTitle = rowView.findViewById<TextView>(R.id.textViewTitle)
            val textViewDescription = rowView.findViewById<TextView>(R.id.textViewDescription)
            val imageViewPhoto = rowView.findViewById<ImageView>(R.id.imageViewPhoto)

            textViewTitle.text       = articles[position].title
            textViewDescription.text = articles[position].description

            if ((articles[position].urlToImage?:"").contains("http")) {
                object : AsyncTask<Unit, Unit, Bitmap>() {
                    override fun doInBackground(vararg p0: Unit?): Bitmap {
                        val input = URL(articles[position].urlToImage).openStream()
                        var bmp = BitmapFactory.decodeStream(input)
                        return bmp
                    }

                    override fun onPostExecute(result: Bitmap?) {
                        super.onPostExecute(result)
                        result?.let {
                            imageViewPhoto.setImageBitmap(result)
                        }

                    }

                }.execute(null, null, null)
            }

            return rowView
        }

        override fun getItem(position: Int): Any {
            return articles[position]
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getCount(): Int {
            return  articles.size
        }

    }
}