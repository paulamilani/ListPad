package br.edu.listPad.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.listPad.R
import br.edu.listPad.data.DatabaseHelper
import br.edu.listPad.model.Lista

class CadastroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_cadastro, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val db = DatabaseHelper(this)

        if (item.itemId == R.id.action_salvar) {
            val nome = findViewById<EditText>(R.id.editTextLista).text.toString()
            val categoria = findViewById<EditText>(R.id.editTextCategoria).text.toString()


            val c = Lista(null, nome, categoria)
            if (db.inserirLista(c) > 0)
                Toast.makeText(this, "Lista inserida.", Toast.LENGTH_LONG).show()
            finish()

        }

        return super.onOptionsItemSelected(item)
    }

}