package com.example.appdatitonami.roomDatabase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.appdatitonami.roomDatabase.entity.Usuario

@Dao
interface DaoUsuario {
    @Query("SELECT * FROM Usuario")
    fun obtenerUsuarios():List<Usuario>

    @Query("SELECT * FROM Usuario WHERE nombre=:nombre")
    fun obtenerUsuario(nombre:String):List<Usuario>

    @Query("SELECT * FROM Usuario WHERE nombre=:nombre AND pass=:pass")
    fun login(nombre:String, pass:String):List<Usuario>

    @Insert
    fun agregarUsuario(usuario: Usuario):Long

    @Query("UPDATE Usuario SET nombre=:nombre,correo=:correo,pass=:pass,passConf=:passConf WHERE nombre=:nombre ")
    fun actualizarUsuario(nombre:String,correo:String,pass:String,passConf:String): Int

    @Query("DELETE FROM Usuario WHERE nombre=:nombre")
    fun borrarUsuario(nombre:String)



}