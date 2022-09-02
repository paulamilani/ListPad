package br.edu.listPad.model

import java.io.Serializable

class ItemLista(
    var id_item: Int? = null,
    var nome_item: String = "",
    var lista_fk: Int? = null
) : Serializable