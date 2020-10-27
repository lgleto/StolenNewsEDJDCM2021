package ipca.example.stolennews

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
}