package br.com.raynerweb.pokemon.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import br.com.raynerweb.pokemon.R
import br.com.raynerweb.pokemon.domain.PokemonType
import coil.load
import java.util.*

class PokemonTypeArrayAdapter(
    context: Context,
    @LayoutRes private val resource: Int,
    types: List<PokemonType>,
) : ArrayAdapter<PokemonType>(context, resource, types) {

    class ViewHolder {
        lateinit var tvName: TextView
        lateinit var ivIcon: ImageView
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createView(convertView, parent, position)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createView(convertView, parent, position)
    }

    private fun createView(
        convertView: View?,
        parent: ViewGroup,
        position: Int
    ): View {
        val viewHolder: ViewHolder
        var cView: View? = convertView
        if (cView == null) {
            viewHolder = ViewHolder()
            cView = LayoutInflater.from(context).inflate(resource, parent, false)
            viewHolder.tvName = cView.findViewById(R.id.tv_description)
            viewHolder.ivIcon = cView.findViewById(R.id.iv_type)
            cView.tag = viewHolder
        } else {
            viewHolder = cView.tag as ViewHolder
        }

        getItem(position)?.let { type ->
            viewHolder.tvName.text = type.name.capitalize(Locale.getDefault())
            viewHolder.ivIcon.contentDescription = type.name
            viewHolder.ivIcon.load(uri = type.image)
        }

        return cView!!
    }
}