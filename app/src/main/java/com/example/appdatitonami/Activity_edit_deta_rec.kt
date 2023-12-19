package com.example.appdatitonami

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.appdatitonami.roomDatabase.Db
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch

class Activity_edit_deta_rec : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_deta_rec)
        //INICIALIZAMOS LA DB
        val room = Room.databaseBuilder(this, Db::class.java,"database-ciisa").allowMainThreadQueries().build()

        //referencias de los widget o ui control
        val btn_save_modi = findViewById<Button>(R.id.btn_save_modi)
        val btn_del = findViewById<Button>(R.id.btn_del)
        val til_titulo_edit = findViewById<TextInputLayout>(R.id.til_titulo_edit)
        val til_ing_edit = findViewById<TextInputLayout>(R.id.til_ing_edit)
        val til_pasos_edit = findViewById<TextInputLayout>(R.id.til_pasos_edit)
        val tv_id = findViewById<TextView>(R.id.tv_id)

        val sp_tipo_edit = findViewById<Spinner>(R.id.sp_tipo_edit)

        //recuperar variable
        val user_name_in = intent.getStringExtra("NameUser").toString()

        //generacion spinner tipo de receta
        val arrayAdapterSpinner : ArrayAdapter<*>
        var tipo_rec_edit = ArrayList<String>()
        tipo_rec_edit.add("Seleccione una opción")
        tipo_rec_edit.add("Todas las opciones")
        tipo_rec_edit.add("Dulce")
        tipo_rec_edit.add("Salada")

        arrayAdapterSpinner = ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1,tipo_rec_edit)

        sp_tipo_edit.adapter=arrayAdapterSpinner

        //recuperar variables del intent
        val receta_id = intent.getStringExtra("Receta_id").toString()
        val receta_nombre = intent.getStringExtra("Receta_nom").toString()
        val receta_tipo = intent.getStringExtra("Receta_tipo").toString()
        val receta_ing = intent.getStringExtra("Receta_ing").toString()
        val receta_deta = intent.getStringExtra("Receta_deta").toString()
        val user_name = intent.getStringExtra("NameUser").toString()

        //seteamos los valores en la interfaz
        til_titulo_edit.editText?.setText(receta_nombre)
        til_ing_edit.editText?.setText(receta_ing)
        til_pasos_edit.editText?.setText(receta_deta)
        sp_tipo_edit.setSelection(tipo_rec_edit.indexOf(receta_tipo))
        tv_id.setText(receta_id)


        //accion del metodo clic
        //boton modicar receta a la vista de editar listado recetas
        btn_save_modi.setOnClickListener {
            var name_receta = til_titulo_edit.editText?.text.toString()
            var name_ing = til_ing_edit.editText?.text.toString()
            var name_paso = til_pasos_edit.editText?.text.toString()
            var tipoRec = sp_tipo_edit.selectedItem.toString()
            var id = tv_id.text.toString()
            lifecycleScope.launch {

                val respuesta = room.daoReceta().actualizarReceta(name_receta,tipoRec,name_paso,name_ing,id.toLong())
                Toast.makeText(this@Activity_edit_deta_rec, "Receta Actualizada", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@Activity_edit_deta_rec, Activity_editar_rec::class.java)
                intent.putExtra("NameUser",user_name)
                startActivity(intent)
            }

        }

        //boton editar texto de paso aviso
        btn_del.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Confirmacion de eliminar")
            builder.setMessage("¿Estas seguro de eliminar la receta?")
            builder.setPositiveButton(android.R.string.ok) { dialog, wich ->
                var id = tv_id.text.toString()
                lifecycleScope.launch {
                    val respuesta = room.daoReceta().borrarReceta(id.toLong())
                    //ACCION A REALIZAR SI PRESIONA OK
                    Toast.makeText(this@Activity_edit_deta_rec, "Receta Eliminada", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@Activity_edit_deta_rec, Activity_editar_rec::class.java)
                    intent.putExtra("NameUser",user_name)
                    startActivity(intent)
                }
            }
            builder.setNegativeButton("Cancelar", null)
            builder.show()


        }
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