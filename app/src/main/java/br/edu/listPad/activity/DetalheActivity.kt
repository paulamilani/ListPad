package br.edu.listPad.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.edu.listPad.data.DatabaseHelper
import br.edu.listPad.model.Lista
import br.edu.listPad.R
import br.edu.listPad.data.ListaAdapter
import br.edu.listPad.model.ItemLista
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DetalheActivity : AppCompatActivity() {
    private var lista = Lista()

    private val db = DatabaseHelper(this)
    private var listLista = ArrayList<Lista>()
    lateinit var listaAdapter: ListaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhe)

        lista = this.intent.getSerializableExtra("lista") as Lista
        val nome = findViewById<EditText>(R.id.editTextLista)
        val categoria = findViewById<EditText>(R.id.editTextCategoria)

        nome.setText(lista.nome)
        categoria.setText(lista.categoria)

        //add item na lista
        val fab_item = findViewById<FloatingActionButton>(R.id.fab_item)
        fab_item.setOnClickListener{
            val intent = Intent(applicationContext, CadastroItemActivity::class.java)
            startActivity(intent)
        }

    }

//    //mostrar item
//    private fun updateItem()
//    {
//        listLista = db.listarLista()
//        listaAdapter = ListaAdapter(listLista)
//
//        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)
//        recyclerview.layoutManager = LinearLayoutManager(this)
//        recyclerview.adapter = listaAdapter
//
//        val listener = object : ListaAdapter.ListaListener{
//            override fun onItemClick(pos: Int) {
//                val intent = Intent(applicationContext, DetalheActivity::class.java)
//                val c = listaAdapter.listasListaFilterable[pos]
//                intent.putExtra("lista", c)
//                startActivity(intent)
//            }
//        }
//        listaAdapter.setClickListener(listener)
//
//    }
//
//    override fun onResume() {
//        super.onResume()
//        updateItem()
//    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detalhe,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val db = DatabaseHelper(this)

        if (item.itemId==R.id.action_alterarContato) {
            val nome = findViewById<EditText>(R.id.editTextLista).text.toString()
            val categoria = findViewById<EditText>(R.id.editTextCategoria).text.toString()

            lista.nome = nome
            lista.categoria = categoria

            if(db.atualizarLista(lista)>0)
                Toast.makeText(this,"Lista alterada.", Toast.LENGTH_LONG).show()
            finish()
        }

        if (item.itemId==R.id.action_excluirContato) {
            if (db.apagarLista(lista)>0)
                Toast.makeText(this,"Lista excluída.", Toast.LENGTH_LONG).show()
            finish()
        }

        return super.onOptionsItemSelected(item)
    }


}