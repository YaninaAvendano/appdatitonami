package com.example.appdatitonami

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.textfield.TextInputLayout

class Activity_rec_det : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rec_det)
        //referencias de los widget o ui control
        val btn_volver_menu = findViewById<Button>(R.id.btn_volver_menu)

        //accion del metodo clic
        //boton que direcciona a vista de busqueda
        btn_volver_menu.setOnClickListener {
            val intent = Intent(this@Activity_rec_det,Activity_menu::class.java)
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