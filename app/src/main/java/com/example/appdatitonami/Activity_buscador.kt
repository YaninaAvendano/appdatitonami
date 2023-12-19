package com.example.appdatitonami

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.SearchView
import android.widget.Spinner
import com.google.android.material.textfield.TextInputLayout

class Activity_buscador : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buscador)
        //referencias de los widget o ui control
        val list_rec = findViewById<ListView>(R.id.list_rec)
        val sp_menu_tip_rec = findViewById<Spinner>(R.id.sp_menu_tip_rec)

        //generacion array adapter del sp
        val arrayAdapterSpinner : ArrayAdapter<*>
        var tipoReceta = arrayOf("Todo tipo de receta","Dulce","Salado")
        arrayAdapterSpinner = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,tipoReceta)
        sp_menu_tip_rec.adapter= arrayAdapterSpinner

        //generacion array adapter del listview
        var arrayAdapterlistView : ArrayAdapter<*>

        //Definir un arreglo vacio
        var receta_all = arrayOf("Queque de zanahoria-Nina1410","Pure con pollo al jugo-Mimi1708","Crema de zapallo-grillito08","Lentejas comen las viejas-lulu50","Panitas para los pulentos-Victor02","Guatitas a la jardinera-Nina1410")


        //adaptador
        arrayAdapterlistView = ArrayAdapter(this@Activity_buscador,android.R.layout.simple_expandable_list_item_1,receta_all)
        list_rec.adapter = arrayAdapterlistView

        //ir a detalle segun seleccion en listado de receta
        list_rec.onItemClickListener = object : AdapterView.OnItemClickListener{
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val intent = Intent( this@Activity_buscador,Activity_rec_det::class.java)
                startActivity(intent)
            }

        }
        //spinner dinamico, para cambiar las opciones segun lo seleccionado en el sp(todos,dulce,salado)
        sp_menu_tip_rec.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                var input = sp_menu_tip_rec.selectedItem.toString()
                println(input)
                when(input){
                    "Todo tipo de receta" -> receta_all = arrayOf("Queque de zanahoria-Nina1410","Pure con pollo al jugo-Mimi1708","Crema de zapallo-grillito08","Lentejas comen las viejas-lulu50","Panitas para los pulentos-Victor02","Guatitas a la jardinera-Nina1410")
                    "Dulce" -> receta_all = arrayOf("Queque de zanahoria-Nina1410")
                    "Salado" -> receta_all = arrayOf("Pure con pollo al jugo-Mimi1708","Crema de zapallo-grillito08","Lentejas comen las viejas-lulu50","Panitas para los pulentos-Victor02","Guatitas a la jardinera-Nina1410")
                }
                arrayAdapterlistView = ArrayAdapter(this@Activity_buscador,android.R.layout.simple_expandable_list_item_1,receta_all)
                list_rec.adapter = arrayAdapterlistView
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
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