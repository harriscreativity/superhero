package co.za.immedia.superhero.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.za.immedia.superhero.Model.SuperHeroModel
import co.za.immedia.superhero.R
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

        holder.itemView.cardTitle.text = this.Heros[position].name
        holder.itemView.cardSubTitle.text = this.Heros[position].biography.fullName
    }
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}