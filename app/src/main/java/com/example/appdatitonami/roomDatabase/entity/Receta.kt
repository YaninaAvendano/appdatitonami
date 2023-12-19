package com.example.appdatitonami.roomDatabase.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Receta {
@PrimaryKey(autoGenerate = true)
var id:Long=0
    var nomRec:String? = null
    var nomIng:String? = null
    var detRec:String? = null
    var tipoRec:String? = null
    var user:String? = null

    constructor(
        nomRec: String?,
        nomIng: String?,
        detRec: String?,
        tipoRec: String?,
        user: String?
    ) {
        this.nomRec = nomRec
        this.nomIng = nomIng
        this.detRec = detRec
        this.tipoRec = tipoRec
        this.user = user
    }

    override fun toString(): String {
        return "Receta(id=$id, nomRec=$nomRec, nomIng=$nomIng, detRec=$detRec, tipoRec=$tipoRec, user=$user)"
    }

}