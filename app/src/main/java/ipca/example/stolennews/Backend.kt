package ipca.example.stolennews

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.widget.Toast
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

//
// Created by lourencogomes on 04/11/2020.
//
class Backend {

    companion object {

        fun getLatestNews( onFetchedArticles : (String)->Unit )  {
            object : AsyncTask<Unit, Unit, String>(){
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

                    onFetchedArticles.invoke(result)



                }
            }.execute(null,null,null)
        }

        fun getBitmapFromUrl(urlString : String, onBitmapResult : (Bitmap)->Unit ){
            object : AsyncTask<Unit, Unit, Bitmap>() {
                override fun doInBackground(vararg p0: Unit?): Bitmap {
                    val input = URL(urlString).openStream()
                    var bmp = BitmapFactory.decodeStream(input)
                    return bmp
                }

                override fun onPostExecute(result: Bitmap?) {
                    super.onPostExecute(result)
                    result?.let {
                        onBitmapResult.invoke(result)
                    }
                }
            }.execute(null, null, null)
        }
    }

}