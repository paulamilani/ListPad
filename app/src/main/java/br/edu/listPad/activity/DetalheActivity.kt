package br.edu.listPad.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.listPad.R
import br.edu.listPad.data.DatabaseHelper
import br.edu.listPad.model.Lista
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DetalheActivity : AppCompatActivity() {
    private var lista = Lista()

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

        //get itens
        val fab_item1 = findViewById<FloatingActionButton>(R.id.fab_item1)
        fab_item1.setOnClickListener{
            val intent = Intent(applicationContext, DetalheItemActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detalhe,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val db = DatabaseHelper(this)

        if (item.itemId==R.id.action_alterar) {
            val nome = findViewById<EditText>(R.id.editTextLista).text.toString()
            val categoria = findViewById<EditText>(R.id.editTextCategoria).text.toString()

            lista.nome = nome
            lista.categoria = categoria

            if(db.atualizarLista(lista)>0)
                Toast.makeText(this,"Lista alterada.", Toast.LENGTH_LONG).show()
            finish()
        }

        if (item.itemId==R.id.action_excluir) {
            if (db.apagarLista(lista)>0)
                Toast.makeText(this,"Lista excluída.", Toast.LENGTH_LONG).show()
            finish()
        }

        return super.onOptionsItemSelected(item)
    }


}