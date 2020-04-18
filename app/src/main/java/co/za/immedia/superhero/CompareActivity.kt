package co.za.immedia.superhero

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import co.za.immedia.superhero.Model.ExpandedItem
import co.za.immedia.superhero.adapters.PowerSetAdapter
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_compare.*
import kotlin.reflect.full.memberProperties

class CompareActivity : AppCompatActivity() {

    var fragmentManager = supportFragmentManager
    var firstHero: Int = 0
    var secondHero: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compare)
    }

    override fun onResume() {
        super.onResume()

        if (MyApplication.compareItems.size > 0) {
            var row = findViewById<View>(R.id.compareContainerOne)
            updateUI(row, 0)
        }

        if (MyApplication.compareItems.size == 2) {
            var row2 = findViewById<View>(R.id.compareContainerTwo)
            updateUI(row2, 1)
        }
    }

    fun updateUI(row: View, indexParam: Int) {

        var listview = row.findViewById<ListView>(R.id.powersetListView)
        var title = row.findViewById<TextView>(R.id.name)
        var image = row.findViewById<ImageView>(R.id.img)
        var firstScroreText = findViewById<TextView>(R.id.firstScore)
        var secondScoreText = findViewById<TextView>(R.id.secondScore)

        Glide.with(this)
            .load(MyApplication.compareItems[indexParam].image.url)
            .placeholder(R.drawable.ic_insert_photo_24px)
            .error(R.drawable.ic_broken_image_24px)
            .optionalCenterCrop()
            .into(image)

        var items = getItems(indexParam)
        val adapter = PowerSetAdapter(items, applicationContext)
        listview.adapter = adapter
        title.text = MyApplication.compareItems[indexParam].name

        heroContainer.visibility = View.VISIBLE

        if (indexParam == 0)
            firstHero = overrallScore(items)
        else
            secondHero = overrallScore(items)

        firstScroreText.text = firstHero.toString()
        secondScoreText.text = secondHero.toString()
        scoreTitle.text = "OVERRALL SCORE"

        scoreContainer.visibility = View.VISIBLE
    }

    fun addCompareTwo(view: View) {
        PickerHero(1)
    }

    fun addCompareOne(view: View) {
        PickerHero(0)
    }

    fun overrallScore(items: List<ExpandedItem>): Int {
        var value = 0
        for (item in items) {
            if (item.value != "null")
                value = value + item.value.toInt()
        }
        return value
    }

    fun getItems(index: Int): List<ExpandedItem> {
        var powerstatsList = mutableListOf<ExpandedItem>()
        var powersts = MyApplication.compareItems[index].powerstats
        for (prop in powersts::class.memberProperties) {
            val name = powersts.javaClass.kotlin.memberProperties.first { it.name == prop.name }
                .get(powersts)
            powerstatsList.add(ExpandedItem(key = prop.name, value = name.toString()))
        }
        return powerstatsList
    }

    fun PickerHero(index: Int) {
        var i = Intent(this, SearchActivity::class.java)
        i.putExtra("param", "Picker")
        i.putExtra("index", index)
        startActivity(i)
    }
}
