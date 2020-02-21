package co.za.immedia.superhero.Network

import co.za.immedia.superhero.Model.SearchSuperHero
import co.za.immedia.superhero.Model.SuperHeroModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

public interface ApiInterface {



    @GET("/id")
    fun getHeroById(@Query("id") id: String): Call<SuperHeroModel>


    @GET( "2881871825189324/search/{name}")
    fun searchSuperHero(@Path("name") name: String): Call<SearchSuperHero>

}
