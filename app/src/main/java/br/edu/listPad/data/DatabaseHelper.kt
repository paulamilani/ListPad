package br.edu.listPad.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import br.edu.listPad.model.Lista

class DatabaseHelper(context: Context):
    SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {

        companion object{
            private val DATABASE_NAME="lista.db"
            private val DATABASE_VERSION=1
            private  val TABLE_NAME = "lista"
            private val ID = "id"
            private val NOME = "nome"
            private val CATEGORIA = "categoria"

        }

    override fun onCreate(p0: SQLiteDatabase?) {
        val CREATE_TABLE = "CREATE TABLE $TABLE_NAME ($ID INTEGER PRIMARY KEY AUTOINCREMENT, $NOME TEXT, $CATEGORIA TEXT)"
        p0?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        //UPGRADE

    }

    fun inserirLista(lista: Lista): Long
    {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(ID,lista.id)
        values.put(NOME, lista.nome)
        values.put(CATEGORIA, lista.categoria)
        val result = db.insert(TABLE_NAME,null,values)
        db.close()
        return result
    }

    fun atualizarLista (lista: Lista): Int{
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(ID,lista.id)
        values.put(NOME, lista.nome)
        values.put(CATEGORIA, lista.categoria)
        val result = db.update(TABLE_NAME,values,"$ID=?", arrayOf(lista.id.toString()))
        db.close()
        return result
    }

    fun apagarLista(lista: Lista):Int{
        val db = this.writableDatabase
        val result =db.delete(TABLE_NAME,"$ID=?", arrayOf(lista.id.toString()))
        db.close()
        return result
    }

    fun listarLista():ArrayList<Lista>
    {
        val listaListas = ArrayList<Lista>()
        val query = "SELECT * FROM $TABLE_NAME ORDER BY $NOME"
        val db = this.readableDatabase
        val cursor = db.rawQuery(query,null)
        while (cursor.moveToNext())
        {
            val c = Lista (cursor.getInt(0),
                             cursor.getString(1),
                             cursor.getString(2))
            listaListas.add(c)
        }
        cursor.close()
        db.close()
        return listaListas
    }
}