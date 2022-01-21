package br.edu.listPad.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.listPad.R
import br.edu.listPad.data.DatabaseHelper
import br.edu.listPad.model.ItemLista

class DetalheItemActivity : AppCompatActivity() {
    private var it = ItemLista()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalheitem)

        it = this.intent.getSerializableExtra("it") as ItemLista
        val item = findViewById<EditText>(R.id.editTextItem)

        item.setText(it.nome_item)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_done, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val db = DatabaseHelper(this)

        if (item.itemId == R.id.action_done) {
            if (db.apagarItem(it) > 0)
                Toast.makeText(this, "Done", Toast.LENGTH_LONG).show()
            finish()
        }

        return super.onOptionsItemSelected(item)
    }

}