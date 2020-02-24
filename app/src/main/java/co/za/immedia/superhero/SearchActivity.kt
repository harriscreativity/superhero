package co.za.immedia.superhero

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import co.za.immedia.superhero.Model.SearchSuperHero
import co.za.immedia.superhero.Model.SuperHeroModel
import co.za.immedia.superhero.Network.ApiClient
import co.za.immedia.superhero.adapters.CardListAdapter
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchActivity : AppCompatActivity(), android.widget.SearchView.OnQueryTextListener {

    val ListHero = mutableListOf<SuperHeroModel>()

    lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        realm = Realm.getDefaultInstance()


        searchbar.setOnQueryTextListener(this)
        searchbar.setIconifiedByDefault(true);
        searchbar.setFocusable(true);
        searchbar.setIconified(false);
        searchbar.requestFocusFromTouch();

        GridListView.layoutManager = GridLayoutManager(this,2)
        GridListView.setHasFixedSize(true)

    }

    fun onSubmitSearch(view: View){

        SearchQuery(searchbar.query.toString())
    }



    private fun SearchQuery(name:String) {

        LoadingIndicator.visibility = View.VISIBLE
        LoadingIndicator.isIndeterminate = true

        val call: Call<SearchSuperHero> = ApiClient.getClient.searchSuperHero(name)
        call.enqueue(object : Callback<SearchSuperHero> {

            override fun onResponse(call: Call<SearchSuperHero>?, response: Response<SearchSuperHero>?) {

                ListHero.clear()

                LoadingIndicator.isIndeterminate = false;
                LoadingIndicator.visibility = View.GONE

                var data = response?.body() as SearchSuperHero

                for (hero in data.results){
                    ListHero.add(hero)
                }


                GridListView.adapter = CardListAdapter(ListHero.reversed(),applicationContext,realm)
                GridListView.refreshDrawableState()
                searchbar.setQuery("",false)
                searchbar.clearFocus()

            }

            override fun onFailure(call: Call<SearchSuperHero>?, t: Throwable?) {
                LoadingIndicator.isIndeterminate = false;
                LoadingIndicator.visibility = View.GONE
                Toast.makeText(applicationContext,"No Hero found try different name ...",Toast.LENGTH_LONG).show()
                print(t?.message)
            }

        })
    }

    override fun onQueryTextSubmit(p0: String?): Boolean {
        SearchQuery(searchbar.query.toString())
        return false
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        return false
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close() // Remember to close Realm when done.
    }

}






