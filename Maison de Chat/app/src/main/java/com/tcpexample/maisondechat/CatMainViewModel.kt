package com.tcpexample.maisondechat

import android.app.Application
import androidx.lifecycle.*
import com.example.ecommerceapplication.HttpService
import org.json.JSONArray
import org.json.JSONObject



class CatMainViewModel(application: Application) : AndroidViewModel(application) {

    val liveData = MutableLiveData<MutableList<CatModel>>()
    /*
    * demander des données
    * */
    fun fetch() {

        Thread {
            val myService = HttpService()
            val result = myService.getCatBreedsList()
            if (result != null) {
                try {
                    // Parse result string JSON to data class
                    //categories = Klaxon().parse(result)
                    val results = JSONArray(result)

                    val products = mutableListOf<CatModel>()
                    for (i in 0 until results.length()) {
                        val productObject: JSONObject = results.getJSONObject(i)
                        val name = productObject.getString("name")
                        val id = productObject.getString("id")
                        val description = productObject.getString("description")

                        var vetstreet_url="";
                        try {
                            vetstreet_url =  productObject.getString("vetstreet_url")
                        }
                        catch(err:Exception) {
                            print("Error when parsing JSON: "+err.localizedMessage)
                        }
                        var imageLink="";
                        try {
                         imageLink = productObject.getJSONObject("image").getString("url")
                        }
                        catch(err:Exception) {
                            print("Error when parsing JSON: "+err.localizedMessage)
                        }

                        val product = CatModel(id.toString(), name, imageLink, description, vetstreet_url)
                        products.add(product)
                    }
                    //childrenList.get (0..10)
                    liveData.postValue(products)

                }
                catch(err:Exception) {
                    print("Error when parsing JSON: "+err.localizedMessage)
                }
            }
        }.start()


    }


}