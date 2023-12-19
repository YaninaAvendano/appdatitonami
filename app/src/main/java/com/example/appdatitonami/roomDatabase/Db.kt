package com.example.appdatitonami.roomDatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.appdatitonami.roomDatabase.dao.DaoReceta
import com.example.appdatitonami.roomDatabase.dao.DaoUsuario
import com.example.appdatitonami.roomDatabase.entity.Receta
import com.example.appdatitonami.roomDatabase.entity.Usuario

@Database(
    entities = [Receta::class,Usuario::class],
    version = 1
)
abstract class Db:RoomDatabase() {
    abstract fun daoUsuario():DaoUsuario
    abstract fun daoReceta():DaoReceta

}