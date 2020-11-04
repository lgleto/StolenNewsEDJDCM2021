package ipca.example.stolennews

import org.json.JSONObject

//
// Created by lourencogomes on 21/10/2020.
//
class Article {

    var title        : String? = null
    var description  : String? = null
    var url          : String? = null
    var urlToImage   : String? = null

    constructor(title: String?, description: String?, url: String?, urlToImage: String?) {
        this.title = title
        this.description = description
        this.url = url
        this.urlToImage = urlToImage
    }

    constructor(){

    }

    companion object {

        fun fromJson(jsonObject: JSONObject) : Article{
            var article  = Article()
            article.title        = jsonObject.getString("title")
            article.description  = jsonObject.getString("description")
            article.url          = jsonObject.getString("url")
            article.urlToImage   = jsonObject.getString("urlToImage")
            return article
        }
    }

}