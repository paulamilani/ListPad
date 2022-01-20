package br.edu.listPad.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.edu.listPad.model.Lista
import br.edu.listPad.R
import br.edu.listPad.model.ItemLista


class ItemAdapter(internal val itemlistasLista: ArrayList<ItemLista>) :
    RecyclerView.Adapter<ItemAdapter.ListaViewHolder>(), Filterable {

    var listener: ListaListener? = null

    var listasListaFilterable = ArrayList<ItemLista>()

    init {
        this.listasListaFilterable = itemlistasLista
    }

    fun setClickListener(listener: ListaListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemAdapter.ListaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lista, parent, false)
        return ListaViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemAdapter.ListaViewHolder, position: Int) {
        holder.nomeVH.text = listasListaFilterable[position].nome_item

    }

    override fun getItemCount(): Int {
        return listasListaFilterable.size
    }

    inner class ListaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nomeVH = view.findViewById<TextView>(R.id.item)

        init {
            view.setOnClickListener {
                listener?.onItemClick(adapterPosition)
            }
        }

    }

    interface ListaListener {
        fun onItemClick(pos: Int)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                if (p0.toString().isEmpty())
                    listasListaFilterable = itemlistasLista
                else {
                    val resultList = ArrayList<ItemLista>()
                    for (row in itemlistasLista)
                        if (row.nome_item.lowercase().contains(p0.toString().lowercase()))
                            resultList.add(row)
                    listasListaFilterable = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = listasListaFilterable
                return filterResults
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                listasListaFilterable = p1?.values as ArrayList<ItemLista>
                notifyDataSetChanged()
            }

        }
    }

}