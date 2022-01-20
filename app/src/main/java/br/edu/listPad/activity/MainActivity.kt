package br.edu.listPad.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.edu.listPad.R
import br.edu.listPad.data.DatabaseHelper
import br.edu.listPad.data.ListaAdapter
import br.edu.listPad.model.Lista
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private val db = DatabaseHelper(this)
    private var listLista = ArrayList<Lista>()
    lateinit var listaAdapter: ListaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(applicationContext, CadastroActivity::class.java)
            startActivity(intent)
        }

        updateUI()

    }

    private fun updateUI() {
        listLista = db.listarLista()
        listaAdapter = ListaAdapter(listLista)

        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = listaAdapter

        val listener = object : ListaAdapter.ListaListener {
            override fun onItemClick(pos: Int) {
                val intent = Intent(applicationContext, DetalheActivity::class.java)
                val c = listaAdapter.listasListaFilterable[pos]
                intent.putExtra("lista", c)
                startActivity(intent)
            }
        }
        listaAdapter.setClickListener(listener)

    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val item = menu?.findItem(R.id.action_search)
        val searchView = item?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                listaAdapter.filter.filter(p0)
                return true
            }

        })

        return super.onCreateOptionsMenu(menu)
    }


}