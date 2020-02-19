package co.za.immedia.superhero

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.cardview.widget.CardView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getSupportActionBar()?.hide()
    }

    fun onSearch(view: View){
        var card:CardView = findViewById(R.id.cardView)
        var i = Intent(this,SearchActivity::class.java)
        startActivity(i)
    }
}
