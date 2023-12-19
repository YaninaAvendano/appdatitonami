package com.example.appdatitonami.roomDatabase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.appdatitonami.roomDatabase.entity.Receta

@Dao
interface DaoReceta {
    @Query("SELECT * FROM Receta")
    //METODO PARA OBTENER LA INFO
    //suspend son las corrutinas estas son una de las características más impresionantes de Kotlin is simply a function that can be paused and resumed
    fun obtenerReceta(): List<Receta>

    @Query("SELECT * FROM Receta WHERE user=:user")
    fun obtenerRecetaUsuario(user: String): List<Receta>

    @Query("SELECT * FROM Receta WHERE nomRec=:nomRec AND user=:user")
    fun obteneRecetaPorNombre(nomRec: String, user: String): List<Receta>

    @Insert
    fun agregarReceta(Receta: Receta):Long

    @Query("UPDATE  Receta SET nomRec=:nomRec,tipoRec=:tipoRec,detRec=:detRec,nomIng=:nomIng WHERE id=:id")
    fun actualizarReceta(nomRec: String,tipoRec:String,detRec:String,nomIng:String,id:Long): Int

    @Query("DELETE FROM Receta WHERE id=:id")
    fun borrarReceta(id: Long)



}