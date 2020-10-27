package ipca.example.stolennews

import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        object : AsyncTask<Unit, Unit,Unit>(){
            override fun doInBackground(vararg params: Unit?) {
                val url = URL("https://newsapi.org/v2/top-headlines?country=pt&apiKey=1765f87e4ebc40229e80fd0f75b6416c")
                val brin = BufferedReader(InputStreamReader(url.openStream()))
                var str : String = ""
                while(( brin.readLine().also{ str = it })!=null){
                    println(str)
                }
                brin.close()
            }
        }.execute(null,null,null)





    }
}