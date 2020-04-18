package co.za.immedia.superhero.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import co.za.immedia.superhero.Model.ExpandedItem
import co.za.immedia.superhero.R


class PowerSetAdapter(
    private val Items: List<ExpandedItem>,
    private val context: Context
) : BaseAdapter() {

    private val mInflator: LayoutInflater = LayoutInflater.from(context)

    override fun getCount(): Int {
        return Items.size
    }

    override fun getItem(position: Int): Any {
        return Items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        val view: View?
        if (convertView == null) {
            view = this.mInflator.inflate(R.layout.compare_item_powerset, parent, false)
            var row = view.findViewById<View>(R.id.powerset)
            var title = row.findViewById<TextView>(R.id.title)
            var value = row.findViewById<TextView>(R.id.value)
            title.text = Items[position].key.toUpperCase()
            value.text = Items[position].value

        } else {
            view = convertView
        }
        return view
    }
}

/**
 * View holder
 */
private class ViewHolder(row: View?)