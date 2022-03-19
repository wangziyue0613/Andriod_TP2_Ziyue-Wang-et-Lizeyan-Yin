package com.example.ecommerceapplication

import okhttp3.*
import java.io.IOException


class HttpService {

    var myResponse : String = ""


    /*
    * Obtenir la race de chat
    * */
    fun getCatBreedsList(): String {



        val client = OkHttpClient()


        try {
            val request = Request.Builder()
                .url("https://api.thecatapi.com/v1/breeds")
                .get()
                .build()
            val call  = client.newCall(request)

            val response = call.execute()
            myResponse = response.body()!!.string().toString()
        } catch(err:Exception) {
            print("Error when parsing JSON: "+err.localizedMessage)
        }


        return myResponse;
    }

    /*
    * Obtenir la catégorie de chat
    * */
    fun getCateCategories(categoryName: String) : String {
        val client = OkHttpClient()

        val request = Request.Builder()
            .url("https://api.bestbuy.com/v1/categories(name=$categoryName)?format=json&apiKey=28ff007a-e947-4657-b51b-a3a154a253d9")
            .get()
            .build()


        val response = client.newCall(request).execute()
        myResponse = response.body()!!.string().toString()

        return myResponse
    }

}