package co.za.immedia.superhero

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import co.za.immedia.superhero.Model.Appearance
import co.za.immedia.superhero.Model.ExpandedItem
import co.za.immedia.superhero.Model.SuperHeroModel
import co.za.immedia.superhero.adapters.ExpandedAdapter
import com.bumptech.glide.Glide
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_search.toolbar
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties

class DetailActivity : AppCompatActivity() {

lateinit var superHeroModel : SuperHeroModel

    var appearanceList = mutableListOf<ExpandedItem>()
    var biographyList = mutableListOf<ExpandedItem>()
    var powerstatsList = mutableListOf<ExpandedItem>()

    internal var adapter: ExpandedAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        var gson = Gson()

        var data = intent.getStringExtra("param")
        superHeroModel = gson.fromJson(data, SuperHeroModel::class.java)

        toolbar.title = superHeroModel.name

        Glide.with(this)
            .load(superHeroModel.image.url)
            .placeholder(R.drawable.ic_insert_photo_24px)
            .placeholder(R.drawable.ic_insert_photo_24px)
            .error(R.drawable.ic_broken_image_24px)
            .optionalCenterCrop()
            .into(detailimage);

        intDetail()
        GroupingExpandedListItem()

    }



    fun GroupingExpandedListItem(){

        var powersts = superHeroModel.powerstats
        var app  = superHeroModel.appearance
        var bio = superHeroModel.biography

        for (prop in powersts::class.memberProperties) {
            val name = powersts.javaClass.kotlin.memberProperties.first { it.name == prop.name }.get(powersts)
            powerstatsList.add(ExpandedItem(key = prop.name,value = name.toString()))
        }

        for (prop in app::class.memberProperties) {
            val name = app.javaClass.kotlin.memberProperties.first { it.name == prop.name }.get(app)
            appearanceList.add(ExpandedItem(key = prop.name,value = name.toString()))
        }

        for (prop in bio::class.memberProperties) {
            val name = bio.javaClass.kotlin.memberProperties.first { it.name == prop.name }.get(bio)
            biographyList.add(ExpandedItem(key = prop.name,value = name.toString()))
        }


        val listData = HashMap<String, List<ExpandedItem>>()
        listData["powerstats"] = powerstatsList
        listData["Appearance"] = appearanceList
        listData["biography"] = biographyList

        var titleList = ArrayList(listData.keys)
        adapter = ExpandedAdapter(this, titleList as ArrayList<String>, listData)
        expandableListView.setAdapter(adapter)
        expandableListView.refreshDrawableState()
    }


    fun intDetail(){

        detailName.text = superHeroModel.name
        detailFullname.text = superHeroModel.biography.fullName
        detailBirthPlace.text = superHeroModel.biography.placeOfBirth
        detailPublisher.text = superHeroModel.biography.publisher
        detailHeight.text = "HEIGHT \n" + superHeroModel.appearance.height[1] + "\n" + superHeroModel.appearance.height[0]
        detailWeight.text = "WEIGHT \n" + superHeroModel.appearance.weight[1] + "\n" + superHeroModel.appearance.weight[0]

        workTitle.text = "OCCUPATION \n" + superHeroModel.work.occupation
        workText.text = "BASE \n" + superHeroModel.work.base

        connTitle.text = "group affiliation \n\n".toUpperCase() + superHeroModel.connections.groupAffiliation
        connText.text = "relatives \n\n".toUpperCase() + superHeroModel.connections.relatives

    }


}
