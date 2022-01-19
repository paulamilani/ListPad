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


class ListaAdapter(val listasLista:ArrayList<Lista>):RecyclerView.Adapter<ListaAdapter.ListaViewHolder>(), Filterable {

    var listener:ListaListener?=null

    var listasListaFilterable = ArrayList<Lista>()

    init {
        this.listasListaFilterable = listasLista
    }

    fun setClickListener(listener:ListaListener)
    {
        this.listener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListaAdapter.ListaViewHolder {
      val view = LayoutInflater.from(parent.context).inflate(R.layout.lista, parent, false)
      return  ListaViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListaAdapter.ListaViewHolder, position: Int) {
        holder.nomeVH.text = listasListaFilterable[position].nome
        holder.categoriaVH.text = listasListaFilterable[position].categoria
    }

    override fun getItemCount(): Int {
        return listasListaFilterable.size
    }

    inner class ListaViewHolder(view:View):RecyclerView.ViewHolder(view)
    {
        val nomeVH = view.findViewById<TextView>(R.id.nome)
        val categoriaVH = view.findViewById<TextView>(R.id.categoria)

        init {
            view.setOnClickListener {
               listener?.onItemClick(adapterPosition)
            }
        }

    }

    interface ListaListener
    {
        fun onItemClick(pos: Int)
    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(p0: CharSequence?): FilterResults {
                if (p0.toString().isEmpty())
                    listasListaFilterable = listasLista
                else
                {
                    val resultList = ArrayList<Lista>()
                    for (row in listasLista)
                        if (row.nome.lowercase().contains(p0.toString().lowercase()))
                            resultList.add(row)
                    listasListaFilterable = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = listasListaFilterable
                return filterResults
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                listasListaFilterable = p1?.values as ArrayList<Lista>
                notifyDataSetChanged()
            }

        }
    }

}