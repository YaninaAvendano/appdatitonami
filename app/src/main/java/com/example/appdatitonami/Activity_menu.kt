package com.example.appdatitonami

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView

class Activity_menu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        //referencias los wifget o ui control
        val btn_buscar = findViewById<Button>(R.id.btn_buscar)
        val btn_editar = findViewById<Button>(R.id.btn_editar)
        val btn_registrar = findViewById<Button>(R.id.btn_registrar)
        val tv_bienvenido = findViewById<TextView>(R.id.tv_bienvenido)

        //recuperar variable
        val user_name_in = intent.getStringExtra("NameUser").toString()
       tv_bienvenido.setText("Bienvenido ${user_name_in}")

        //accion del metodo clic
        //boton que direcciona a vista de busqueda
        btn_buscar.setOnClickListener {
            val intent = Intent(this@Activity_menu,Activity_buscador::class.java)
            startActivity(intent)
        }
        //boton que direcciona a vista de registrar receta
        btn_registrar.setOnClickListener {
            val intent = Intent(this@Activity_menu,Activity_registrar_rec::class.java)
            intent.putExtra("NameUser",user_name_in)
            startActivity(intent)
        }
        //boton que direcciona a vista con el listado de recetas para modificar
        btn_editar.setOnClickListener {
            val intent = Intent(this@Activity_menu,Activity_editar_rec::class.java)
            intent.putExtra("NameUser",user_name_in)
            startActivity(intent)
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