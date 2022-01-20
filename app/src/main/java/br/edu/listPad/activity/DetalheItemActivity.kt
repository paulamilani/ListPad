package br.edu.listPad.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.edu.listPad.R
import br.edu.listPad.data.DatabaseHelper
import br.edu.listPad.data.ItemAdapter
import br.edu.listPad.model.ItemLista

class DetalheItemActivity : AppCompatActivity() {

    private val db = DatabaseHelper(this)
    private var listaItem = ArrayList<ItemLista>()
    lateinit var itemAdapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalheitem)

        updateItem()

    }

    //exibir item
    private fun updateItem() {
        listaItem = db.listarItem()
        itemAdapter = ItemAdapter(listaItem)

        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = itemAdapter

        val listener = object : ItemAdapter.ListaListener {
            override fun onItemClick(pos: Int) {
                val intent = Intent(applicationContext, DetalheitemItemActivity::class.java)
                val c = itemAdapter.listasListaFilterable[pos]
                intent.putExtra("it", c)
                startActivity(intent)
            }
        }
        itemAdapter.setClickListener(listener)

    }


    override fun onResume() {
        super.onResume()
        updateItem()
    }

}