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
    fun getHeroById(): Call<SuperHeroModel>

    @Headers("x-rapidapi-host:superhero-search.p.rapidapi.com",
        "x-rapidapi-key:a0aeeefb74mshecf9ba1829b5d05p1b03a3jsnd4e2ffd79e5d")
    @GET( "/")
    fun searchSuperHero(@Query("hero") hero: String): Call<SuperHeroModel>

}
