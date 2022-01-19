package br.edu.listPad.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import br.edu.listPad.data.DatabaseHelper
import br.edu.listPad.R
import br.edu.listPad.model.ItemLista

class CadastroItemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_itemcadastro)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_cadastro, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val db  =DatabaseHelper(this)

        if (item.itemId==R.id.action_salvar)
        {
            val item_lista = findViewById<EditText>(R.id.editTextItem).text.toString()

            val c = ItemLista(null, item_lista)
            if (db.inserirItem(c)>0)
                Toast.makeText(this,"Item inserido.", Toast.LENGTH_LONG).show()
            finish()

        }

        return super.onOptionsItemSelected(item)
    }

}