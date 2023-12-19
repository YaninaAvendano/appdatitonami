package com.example.appdatitonami

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.appdatitonami.roomDatabase.Db
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch

class Activity_main_ingreso : AppCompatActivity() {

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_ingreso)
        //INICIALIZAMOS LA DB
        val room = Room.databaseBuilder(this, Db::class.java,"database-ciisa").allowMainThreadQueries().build()
    //referencias o inicializar de los widget o ui control
        val til_ingreso_user = findViewById<TextInputLayout>(R.id.til_ingreso_user)
        val til_ingreso_pass = findViewById<TextInputLayout>(R.id.til_ingreso_pass)
        val sw_menu_rm = findViewById<Switch>(R.id.sw_menu_rm)
        val btn_in = findViewById<Button>(R.id.btn_in)
        val btn_regis_user = findViewById<Button>(R.id.btn_regis_user)
        val preferencia = getSharedPreferences("datos",Context.MODE_PRIVATE)

        til_ingreso_user.editText?.setText(preferencia.getString("user_name",""))

    //accion del metodo clic
        //boton que direcciona a menu de acciones cuando no hay errores

    btn_in.setOnClickListener {
        var user_name = til_ingreso_user.editText?.text.toString()
        var user_pass = til_ingreso_pass.editText?.text.toString()
        var user_sw_rm = sw_menu_rm.isChecked
        if(validarCampos() == 0) {
            val editor = preferencia.edit()
            if (sw_menu_rm.isChecked){
                editor.putString("user_name",til_ingreso_user.editText?.text.toString())
                editor.commit()
            }
            else{
                editor.putString("user_name","")
                editor.commit()
            }
            //Toast.makeText(this,"$user_name,$user_pass,$user_sw_rm",Toast.LENGTH_SHORT).show()
            //verificar el login
            lifecycleScope.launch{
            val response = room.daoUsuario().login(user_name,user_pass)
                if(response.size == 1){
                    til_ingreso_user.error = ""
                    til_ingreso_pass.error = ""
                    Toast.makeText(this@Activity_main_ingreso,"Login exitoso",Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@Activity_main_ingreso,Activity_menu::class.java)
                    intent.putExtra("NameUser",user_name)
                    startActivity(intent)
                }else{
                    til_ingreso_user.error = "Usuario y/o contraseña invalidos"
                    til_ingreso_pass.error = "Usuario y/o contraseña invalidos"
                }
            }

        }
    }
        //boton que direcciona a formulario de registro de usuario
    btn_regis_user.setOnClickListener {
        val intent = Intent(this@Activity_main_ingreso,Activity_registro::class.java)
        startActivity(intent)
    }
    }

    //funcion para validar campos de esta activity
    fun validarCampos():Int{
        val validador = Validador()
        var contador:Int = 0
        val til_ingreso_user = findViewById<TextInputLayout>(R.id.til_ingreso_user)
        val til_ingreso_pass = findViewById<TextInputLayout>(R.id.til_ingreso_pass)
        var user_name = til_ingreso_user.editText?.text.toString()
        var user_pass = til_ingreso_pass.editText?.text.toString()
        //hacer validaciones necesarias
          //validacion el nombre de usuario que no sea nulo
        if (validador.validarCampoNulo(user_name)){
            til_ingreso_user.error = "* Error, este campo es requerido"
            contador++
        }
        else {
            til_ingreso_user.error = ""
        }
        //validacion de contraseña de usuario que no sea nulo
        if (validador.validarCampoNulo(user_pass)){
            til_ingreso_pass.error = "* Error, este campo es requerido"
            contador++
        }
        else{
            til_ingreso_pass.error = ""
        }

        println(validador.validarCampoNulo(user_name))
        println(validador.validarCampoNulo(user_pass))
        return contador
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


