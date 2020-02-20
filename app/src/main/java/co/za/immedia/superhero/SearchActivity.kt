package co.za.immedia.superhero

import android.graphics.Color
import android.graphics.ColorFilter
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.za.immedia.superhero.Model.SuperHeroModel
import co.za.immedia.superhero.Network.ApiClient
import co.za.immedia.superhero.adapters.CardListAdapter
import kotlinx.android.synthetic.main.activity_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchActivity : AppCompatActivity(), android.widget.SearchView.OnQueryTextListener {

    val ListHero = mutableListOf<SuperHeroModel>()
    lateinit var adapter: CardListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        searchbar.setOnQueryTextListener(this)
        searchbar.setIconifiedByDefault(true);
        searchbar.setFocusable(true);
        searchbar.setIconified(false);
        searchbar.requestFocusFromTouch();

        SearchQuery("batman")


        GridListView.layoutManager = GridLayoutManager(this,2)
        GridListView.setHasFixedSize(true)

        GridListView.addOnItemTouchListener()
    }

    fun onSubmitSearch(view: View){

        SearchQuery(searchbar.query.toString())
    }



    private fun SearchQuery(name:String) {

        LoadingIndicator.visibility = View.VISIBLE
        LoadingIndicator.isIndeterminate = true

        val call: Call<SuperHeroModel> = ApiClient.getClient.searchSuperHero(name)
        call.enqueue(object : Callback<SuperHeroModel> {

            override fun onResponse(call: Call<SuperHeroModel>?, response: Response<SuperHeroModel>?) {

                if(response?.body() == null){
                    searchbar.clearFocus()
                    Toast.makeText(applicationContext,"No Hero found try different name ...",Toast.LENGTH_LONG).show()
                    return;
                }
                LoadingIndicator.isIndeterminate = false;
                LoadingIndicator.visibility = View.GONE

                var data = response?.body() as SuperHeroModel

                ListHero.add(data)

                GridListView.adapter = CardListAdapter(ListHero.reversed(),applicationContext)
                GridListView.refreshDrawableState()
                searchbar.setQuery("",false)
                searchbar.clearFocus()

            }

            override fun onFailure(call: Call<SuperHeroModel>?, t: Throwable?) {
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


}

private fun RecyclerView.addOnItemTouchListener() {

}





