package co.za.immedia.superhero

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import co.za.immedia.superhero.Model.SuperHeroModel
import co.za.immedia.superhero.adapters.CardListAdapter
import com.google.gson.Gson
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    lateinit var realm: Realm
    val ListHero = mutableListOf<SuperHeroModel>()
    var AppService = MyApplication()

    private val onItemClickListener: (SuperHeroModel) -> Unit = { item ->
        val jsonString = Gson().toJson(item)
        var intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("param", jsonString)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        realm = Realm.getDefaultInstance()
    }

    override fun onResume() {
        super.onResume()

        ListHero.clear()

        var AppService = MyApplication()
        var gson = Gson()
        var items = AppService.GetSuperHero(realm)


        if (items != null) {
            for (item in items){
                var superHeroModel = gson.fromJson(item.data, SuperHeroModel::class.java)
                ListHero.add(superHeroModel)
            }

            if(ListHero.size > 0){
                MainGridList.visibility = View.VISIBLE
                main_ills.visibility = View.GONE
                wellcometxt.visibility = View.GONE
                txtwelcomemessage.visibility = View.GONE

                MainGridList.layoutManager = GridLayoutManager(this,2)
                MainGridList.setHasFixedSize(true)
                MainGridList.adapter = CardListAdapter(
                    ListHero.reversed(),
                    applicationContext,
                    realm,
                    "Detail",
                    onItemClickListener
                )
                MainGridList.refreshDrawableState()
            }
        }
    }

    fun onSearch(view: View){
        var i = Intent(this,SearchActivity::class.java)
        i.putExtra("param", "Detail")
        startActivity(i)
    }

    fun onCompare(view: View) {
        var i = Intent(this, CompareActivity::class.java)
        startActivity(i)
    }


    override fun onDestroy() {
        super.onDestroy()
        realm.close() // Remember to close Realm when done.
    }


}
