package co.za.immedia.superhero.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import co.za.immedia.superhero.DetailActivity
import co.za.immedia.superhero.Model.SuperHeroModel
import co.za.immedia.superhero.R
import com.bumptech.glide.Glide
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.superhero_grid_item.view.*

class CardListAdapter(private val Heros: List<SuperHeroModel>, private val context: Context) :
    RecyclerView.Adapter<CardListAdapter.ViewHolder>() {

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
            .into(holder.itemView.HeroImage);

        holder.itemView.cardTitle.text = this.Heros[position].name
        holder.itemView.cardSubTitle.text = this.Heros[position].biography.fullName

        holder.itemView.GridItem.setOnClickListener {

            var data = Heros[position]
            val jsonString = Gson().toJson(data)
            var intent = Intent(holder.itemView.GridItem.context, DetailActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("param", jsonString)
            holder.itemView.GridItem.context.startActivity(intent)
        }


    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}