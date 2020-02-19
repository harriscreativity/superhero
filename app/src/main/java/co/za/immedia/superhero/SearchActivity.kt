package co.za.immedia.superhero

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.za.immedia.superhero.Model.SearchSuperHero
import co.za.immedia.superhero.Model.SuperHeroModel
import co.za.immedia.superhero.Network.ApiClient
import co.za.immedia.superhero.adapters.CardListAdapter
import kotlinx.android.synthetic.main.activity_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchActivity : AppCompatActivity() {

    val ListHero = mutableListOf<SuperHeroModel>()
    lateinit var adapter: CardListAdapter

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)


        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        searchbar.setIconifiedByDefault(true);
        searchbar.setFocusable(true);
        searchbar.setIconified(false);
        searchbar.requestFocusFromTouch();




        //GridListView.layoutManager = GridLayoutManager(this,2)


    }

    fun onSubmitSearch(view: View){
        SearchQuery(searchbar.query.toString())
    }



    private fun SearchQuery(name:String) {
        print(name)
        val call: Call<SearchSuperHero> = ApiClient.getClient.searchSuperHero(name)
        call.enqueue(object : Callback<SearchSuperHero> {

            override fun onResponse(call: Call<SearchSuperHero>?, response: Response<SearchSuperHero>?) {

                var data = response?.body() as SearchSuperHero

                print(data)

            }

            override fun onFailure(call: Call<SearchSuperHero>?, t: Throwable?) {
                print(t?.message)
            }

        })
    }


}




