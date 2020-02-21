package co.za.immedia.superhero

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import co.za.immedia.superhero.Model.SuperHeroModel
import com.bumptech.glide.Glide
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.activity_search.toolbar
import kotlinx.android.synthetic.main.biography_item.*
import kotlinx.android.synthetic.main.detail_powerset.*

class DetailActivity : AppCompatActivity() {

lateinit var superHeroModel : SuperHeroModel

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

        SetPowerset();
        SetBiography();

    }

    private fun SetPowerset(){

        detailname.text = superHeroModel.name
        fullname.text = superHeroModel.biography.fullName

        intetlligence.text = superHeroModel.powerstats.intelligence
        seekBarIntelligence.progress = superHeroModel.powerstats.intelligence.toInt()

        combat.text = superHeroModel.powerstats.combat
        seekBarCombat.progress = superHeroModel.powerstats.combat.toInt()

        durability.text = superHeroModel.powerstats.durability
        seekBarDurability.progress = superHeroModel.powerstats.durability.toInt()

        power.text = superHeroModel.powerstats.power
        seekBarPower.progress = superHeroModel.powerstats.power.toInt()

        strengthValue.text = superHeroModel.powerstats.strength
        seekBarStrength.progress = superHeroModel.powerstats.strength.toInt()

        speed.text = superHeroModel.powerstats.speed
        speedSeekBar.progress = superHeroModel.powerstats.speed.toInt()
    }

    private fun SetBiography(){
        bioFullname.text = superHeroModel.biography.fullName
        BioAfterEgos.text = superHeroModel.biography.alterEgos
        BioAliasses.text = superHeroModel.biography.aliases.toString()
        BioPlaceOfBirth.text = superHeroModel.biography.placeOfBirth
        BiofirstAppearance.text = superHeroModel.biography.firstAppearance
        BioPublisher.text = superHeroModel.biography.publisher
        Bioalignment.text = superHeroModel.biography.alignment
    }
}
