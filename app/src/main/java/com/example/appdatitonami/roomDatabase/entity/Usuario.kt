package com.example.appdatitonami.roomDatabase.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Usuario {
    @PrimaryKey(autoGenerate = true)
    var id:Long=0
 var correo:String? = null
 var nombre:String? = null
 var fecha:String? = null
 var pass:String? = null
 var passConf:String? = null

    constructor(
        correo: String?,
        nombre: String?,
        fecha: String?,
        pass: String?,
        passConf: String?
    ) {
        this.correo = correo
        this.nombre = nombre
        this.fecha = fecha
        this.pass = pass
        this.passConf = passConf
    }

    override fun toString(): String {
        return "Usuario(id=$id, correo=$correo, nombre=$nombre, fecha=$fecha, pass=$pass, passConf=$passConf)"
    }

}