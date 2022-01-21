package br.edu.listPad.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import br.edu.listPad.model.ItemLista
import br.edu.listPad.model.Lista

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private val DATABASE_NAME = "lista.db"
        private val DATABASE_VERSION = 1

        private val TABLE_NAME1 = "lista"
        private val ID = "id"
        private val NOME = "nome"
        private val CATEGORIA = "categoria"

        private val TABLE_NAME2 = "item"
        private val ID_ITEM = "id"
        private val NOME_ITEM = "nome"
        private val LISTA_FK = "lista_fk"
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val CREATE_TABLE1 =
            "CREATE TABLE $TABLE_NAME1 ($ID INTEGER PRIMARY KEY AUTOINCREMENT, $NOME TEXT, $CATEGORIA TEXT )"
        val CREATE_TABLE2 =
            "CREATE TABLE $TABLE_NAME2 ($ID_ITEM INTEGER, $NOME_ITEM TEXT, $LISTA_FK INTEGER, FOREIGN KEY ($LISTA_FK) REFERENCES $TABLE_NAME1 ($ID) )"
        p0?.execSQL(CREATE_TABLE1)
        p0?.execSQL(CREATE_TABLE2)
    }


    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        //UPGRADE

    }

    fun inserirLista(lista: Lista): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(ID, lista.id)
        values.put(NOME, lista.nome)
        values.put(CATEGORIA, lista.categoria)
        val result = db.insert(TABLE_NAME1, null, values)
        db.close()
        return result
    }

    fun inserirItem(item: ItemLista): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(ID_ITEM, item.id_item)
        values.put(NOME_ITEM, item.nome_item)
        values.put(LISTA_FK, item.lista_fk)
        val result = db.insert(TABLE_NAME2, null, values)
        db.close()
        return result
    }


    fun atualizarLista(lista: Lista): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(ID, lista.id)
        values.put(NOME, lista.nome)
        values.put(CATEGORIA, lista.categoria)
        val result = db.update(TABLE_NAME1, values, "$ID=?", arrayOf(lista.id.toString()))
        db.close()
        return result
    }

    fun apagarLista(lista: Lista): Int {
        val db = this.writableDatabase
        val result = db.delete(TABLE_NAME1, "$ID=?", arrayOf(lista.id.toString()))
        db.close()
        return result
    }

    fun apagarItem(item: ItemLista): Int {
        val db = this.writableDatabase
        val result = db.delete(TABLE_NAME2, "$ID_ITEM=?", arrayOf(item.id_item.toString()))
        db.close()
        return result
    }

    fun listarLista(): ArrayList<Lista> {
        val listaListas = ArrayList<Lista>()
        val query = "SELECT * FROM $TABLE_NAME1 ORDER BY $NOME"
        val db = this.readableDatabase
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()) {
            val c = Lista(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2)
            )
            listaListas.add(c)
        }
        cursor.close()
        db.close()
        return listaListas
    }

    fun listarItem(): ArrayList<ItemLista> {
        val itemListas = ArrayList<ItemLista>()
        val query = "SELECT * FROM $TABLE_NAME2 ORDER BY $NOME"
        val db = this.readableDatabase
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()) {
            val c = ItemLista(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getInt(2)
            )
            itemListas.add(c)
        }
        cursor.close()
        db.close()
        return itemListas
    }
}