package co.za.immedia.superhero

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import co.za.immedia.superhero.Model.SearchSuperHero
import co.za.immedia.superhero.Model.SuperHeroModel
import co.za.immedia.superhero.Model.SuperHeroRealmModel
import co.za.immedia.superhero.Network.ApiClient
import co.za.immedia.superhero.adapters.CardListAdapter
import com.google.gson.Gson
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchActivity : AppCompatActivity(), android.widget.SearchView.OnQueryTextListener {

    val ListHero = mutableListOf<SuperHeroModel>()

    lateinit var realm: Realm
    lateinit var param: String
    var AppService = MyApplication()
    var index: Int = 0

    private val onItemClickListener: (SuperHeroModel) -> Unit = { item ->
        when (param) {
            "Picker" -> updateCompareItems(item)
            "Detail" -> goDetailActivity(item)
            else -> println("Number too high")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        realm = Realm.getDefaultInstance()


        searchbar.setOnQueryTextListener(this)
        searchbar.isIconifiedByDefault = true
        searchbar.isFocusable = true
        searchbar.isIconified = false
        searchbar.requestFocusFromTouch()

        GridListView.layoutManager = GridLayoutManager(this,2)
        GridListView.setHasFixedSize(true)

        var paramData = intent.getStringExtra("param")
        index = intent.getIntExtra("index", 0)

        if (paramData != null) param = paramData


    }

    fun onSubmitSearch(view: View){
        SearchQuery(searchbar.query.toString().trim())
    }


    private fun updateCompareItems(data: SuperHeroModel) {
        MyApplication.compareItems.add(index, data)
        finish()
    }

    fun goDetailActivity(data: SuperHeroModel) {
        val jsonString = Gson().toJson(data)
        AppService.InsertData(
            SuperHeroRealmModel(
                data = jsonString,
                id = data.id.toInt()
            ), realm
        )
        var intent = Intent(applicationContext, DetailActivity::class.java)
        intent.putExtra("param", jsonString)
        startActivity(intent)
    }


    private fun SearchQuery(name:String) {

        LoadingIndicator.visibility = View.VISIBLE
        LoadingIndicator.isIndeterminate = true

        val call: Call<SearchSuperHero> = ApiClient.getClient.searchSuperHero(name.trim())
        call.enqueue(object : Callback<SearchSuperHero> {

            override fun onResponse(call: Call<SearchSuperHero>?, response: Response<SearchSuperHero>?) {

                ListHero.clear()

                LoadingIndicator.isIndeterminate = false
                LoadingIndicator.visibility = View.GONE



                var data = response?.body() as SearchSuperHero
                if(data.results != null){
                    for (hero in data.results){
                        ListHero.add(hero)
                    }
                    GridListView.adapter = CardListAdapter(
                        ListHero.reversed(),
                        applicationContext,
                        realm,
                        param,
                        onItemClickListener
                    )
                    GridListView.refreshDrawableState()
                    searchbar.setQuery("",false)
                    searchbar.clearFocus()
                }else{
                    Toast.makeText(applicationContext,"No Hero found try different name ...",Toast.LENGTH_LONG).show()
                }

            }

            override fun onFailure(call: Call<SearchSuperHero>?, t: Throwable?) {
                LoadingIndicator.isIndeterminate = false
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






