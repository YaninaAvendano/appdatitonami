package com.example.appdatitonami

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.appdatitonami.roomDatabase.Db
import com.google.android.material.textfield.TextInputLayout
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
class Activity_editar_rec : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_rec)
        //INICIALIZAMOS LA DB
        val room = Room.databaseBuilder(this, Db::class.java,"database-ciisa").allowMainThreadQueries().build()

        //referencias de los widget o ui control

        val rv_list_edit = findViewById<RecyclerView>(R.id.rv_list_edit)

        //recuperar variable
        val user_name_in = intent.getStringExtra("NameUser").toString()


        //generacion arrayadapter de rv
        rv_list_edit.layoutManager = LinearLayoutManager(this)

        val receta_user = mutableListOf<Item>()
        lifecycleScope.launch {
            var respuesta = room.daoReceta().obtenerRecetaUsuario(user_name_in)
            for (indice in respuesta.indices){
                receta_user.add(
                    Item(respuesta[indice].id.toInt(),
                        respuesta[indice].nomRec.toString(),
                        respuesta[indice].tipoRec.toString(),
                        R.drawable.baseline_favorite_24,
                        respuesta[indice].nomIng.toString(),
                        respuesta[indice].detRec.toString(),
                        respuesta[indice].tipoRec.toString()
                    )



                )
            }

        }


        //adaptador rv
        val adapter = CustomAdapter(receta_user) { receta_user ->
            val intent = Intent(this@Activity_editar_rec, Activity_edit_deta_rec::class.java)
            intent.putExtra("Receta_id", receta_user.id.toString())
            intent.putExtra("Receta_nom", receta_user.title)
            intent.putExtra("Receta_tipo", receta_user.tipoRec)
            intent.putExtra("Receta_ing", receta_user.ing)
            intent.putExtra("Receta_deta", receta_user.detRec)

            intent.putExtra("Receta_img", receta_user.imageResId)
            intent.putExtra("NameUser",user_name_in)

            startActivity(intent)
        }
        rv_list_edit.adapter = adapter
    //importamos gestos
    //IMPLEMENTAMOS GESTOS
    val itemTouchHelperCallback =
        object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {return false}
            //IMPLEMENTAMOS DECORADOR CON LA LIBRERIA RecyclerViewSwipeDecorator
            override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
                RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(this@Activity_editar_rec, R.color.deleteColor))
                    .addSwipeLeftActionIcon(R.drawable.baseline_delete_24)
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(this@Activity_editar_rec, R.color.archiveColor))
                    .addSwipeRightActionIcon(R.drawable.baseline_archive_24)
                    .create()
                    .decorate()

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        adapter.removeItem(position)
                        Toast.makeText(this@Activity_editar_rec, "Elemento eliminado", Toast.LENGTH_SHORT).show()
                    }
                    ItemTouchHelper.RIGHT -> {
                        adapter.removeItem(position)
                        Toast.makeText(this@Activity_editar_rec, "Elemento archivado", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
    itemTouchHelper.attachToRecyclerView(rv_list_edit)
}
    //metodo de ciclo de vida sofware
    override fun onDestroy(){
        super.onDestroy()
        println("onDestroy()")
    }
    override fun onStart() {
        super.onStart()
        println("onStart()")
    }
    override fun onResume() {
        super.onResume()
        println("onResume()")
    }
    override fun onRestart(){
        super.onRestart()
        println("onRestart")
    }

    override fun onPause() {
        println("onPause()")
        super.onPause()
    }
    override fun onStop() {
        println("onStop()")
        super.onStop()
    }
}