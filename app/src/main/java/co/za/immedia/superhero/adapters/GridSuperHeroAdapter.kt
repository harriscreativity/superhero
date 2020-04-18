package co.za.immedia.superhero.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.za.immedia.superhero.Model.SuperHeroModel
import co.za.immedia.superhero.MyApplication
import co.za.immedia.superhero.R
import com.bumptech.glide.Glide
import io.realm.Realm
import kotlinx.android.synthetic.main.superhero_grid_item.view.*

class CardListAdapter(
    private val Heros: List<SuperHeroModel>,
    private val context: Context,
    private var realm: Realm,
    var param: String,
    private val itemClickListener: (SuperHeroModel) -> Unit
) :
    RecyclerView.Adapter<CardListAdapter.ViewHolder>() {

    var AppService = MyApplication()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.superhero_grid_item,
                parent, false))
    }

    override fun getItemCount(): Int {
        return Heros.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide
            .with(context)
            .load(this.Heros[position].image.url)
            .placeholder(R.drawable.ic_insert_photo_24px)
            .error(R.drawable.ic_broken_image_24px)
            .optionalCenterCrop()
            .into(holder.itemView.HeroImage)

        holder.itemView.cardTitle.text = this.Heros[position].name
        holder.itemView.cardSubTitle.text = this.Heros[position].biography.fullName

        holder.itemView.setOnClickListener { itemClickListener(Heros[position]) }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}
