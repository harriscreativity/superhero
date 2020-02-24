package co.za.immedia.superhero

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.GridLayoutManager
import co.za.immedia.superhero.Model.SuperHeroModel
import co.za.immedia.superhero.adapters.CardListAdapter
import com.google.gson.Gson
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_search.*

class MainActivity : AppCompatActivity() {

    lateinit var realm: Realm
    val ListHero = mutableListOf<SuperHeroModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getSupportActionBar()?.hide()

        realm = Realm.getDefaultInstance()

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
                MainGridList.adapter = CardListAdapter(ListHero.reversed(),applicationContext,realm)
                MainGridList.refreshDrawableState()
            }
        }


    }

    fun onSearch(view: View){
        var card:CardView = findViewById(R.id.cardView)
        var i = Intent(this,SearchActivity::class.java)
        startActivity(i)
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close() // Remember to close Realm when done.
    }
}
