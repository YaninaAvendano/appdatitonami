package com.example.appdatitonami


import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.appdatitonami.roomDatabase.Db
import com.example.appdatitonami.roomDatabase.entity.Usuario
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch
import java.time.Year

class Activity_registro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
        //INICIALIZAMOS LA DB
        val room = Room.databaseBuilder(this, Db::class.java,"database-ciisa").allowMainThreadQueries().build()
        //referencias de los widget o ui control
        val til_correo = findViewById<TextInputLayout>(R.id.til_correo)
        val til_name_user = findViewById<TextInputLayout>(R.id.til_name_user)
        val til_user_pass = findViewById<TextInputLayout>(R.id.til_user_pass)
        val til_confirm_pass = findViewById<TextInputLayout>(R.id.til_confirm_pass)
        val til_date =findViewById<TextInputLayout>(R.id.til_date)
        val fab_direccion = findViewById<FloatingActionButton>(R.id.fab_direccion)
        val btn_regis = findViewById<Button>(R.id.btn_regis)

        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog = DatePickerDialog(
            this@Activity_registro,
            DatePickerDialog.OnDateSetListener{
                view,selectedYear,selectedMonth, selectedDay ->

                til_date.editText?.setText("$selectedYear/$selectedMonth/$selectedDay")
            },
            year,
            month,
            day
        )
        //establecec la fecha minima
        val minCalendar = Calendar.getInstance()
        minCalendar.add(Calendar.YEAR,-70)
        datePickerDialog.datePicker.minDate = minCalendar.timeInMillis

        //establece la fecha maxima
        val maxCalendar = Calendar.getInstance()
        maxCalendar.add(Calendar.YEAR,0)
        datePickerDialog.datePicker.maxDate = maxCalendar.timeInMillis


        //generacion del calendario al presionar el campo
        til_date.editText?.setOnClickListener {
            //mostramos el picker
              datePickerDialog.show()
        }

    //accion metodo clik
        btn_regis.setOnClickListener {
            if(validarCampos() == 0) {
                val correo = findViewById<TextInputLayout>(R.id.til_correo).editText?.text.toString()
                val name_user = findViewById<TextInputLayout>(R.id.til_name_user).editText?.text.toString()
                val user_pass = findViewById<TextInputLayout>(R.id.til_user_pass).editText?.text.toString()
                val confirm_pass = findViewById<TextInputLayout>(R.id.til_confirm_pass).editText?.text.toString()
                val date =findViewById<TextInputLayout>(R.id.til_date).editText?.text.toString()

                //creamos el objeto
                val usuario = Usuario(correo,name_user,date,user_pass,confirm_pass)
                lifecycleScope.launch{
                    val id = room.daoUsuario().agregarUsuario(usuario)
                    if (id>0) {
                        Toast.makeText(
                            this@Activity_registro,
                            "Usuario registrado exitosamente",
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent = Intent(this@Activity_registro,Activity_main_ingreso::class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(this@Activity_registro,"Error al registrar usuario",Toast.LENGTH_SHORT).show()
                    }
                    }


            }
        }
        fab_direccion.setOnClickListener{
            val intent = Intent(this@Activity_registro,MapsActivity::class.java)
            startActivity(intent)
        }

    }
    //funcion para validar campos de esta activity
    fun validarCampos():Int {
        val validador = Validador()
        var contador: Int = 0
        val til_correo = findViewById<TextInputLayout>(R.id.til_correo)
        val til_name_user = findViewById<TextInputLayout>(R.id.til_name_user)
        val til_user_pass = findViewById<TextInputLayout>(R.id.til_user_pass)
        val til_confirm_pass = findViewById<TextInputLayout>(R.id.til_confirm_pass)
        val til_date = findViewById<TextInputLayout>(R.id.til_date)
        var user_correo = til_correo.editText?.text.toString()
        var user_name = til_name_user.editText?.text.toString()
        var user_pass_regis = til_user_pass.editText?.text.toString()
        var user_pass_conf = til_confirm_pass.editText?.text.toString()
        var user_date = til_date.editText?.text.toString()
        //hacer validaciones necesarias
        //validacion correo nulo
        if (validador.validarCampoNulo(user_correo)) {
            til_correo.error = "* Error, este campo es requerido"
            contador++
        } else {
            //validar el formato del correo
            if (validador.validarcorreo(user_correo)) {
                til_correo.error = "Error de formato, ej: ejemplo@gmail.cl"
                contador++
            } else {
                til_correo.error = ""
            }
            //validar que ambos campos tenga el mismo texto
            if (validador.validarCamposIguales(user_pass_regis, user_pass_conf)) {
                til_user_pass.error = "* Contraseña y Confirmación de esta no son iguales"
                til_confirm_pass.error = "* Contraseña y Confirmación de esta no son iguales"
                contador++
            } else {
                til_user_pass.error = ""
                til_confirm_pass.error = ""
            }
        }
        //validacion de nombre de usuario no sea nulo
        if (validador.validarCampoNulo(user_name)) {
            til_name_user.error = "* Error, este campo es requerido"
            contador++
        } else {
            til_name_user.error = ""
        }
        //validacion de pass no sea nulo
        if (validador.validarCampoNulo(user_pass_regis)) {
            til_user_pass.error = "* Error, este campo es requerido"
            contador++
        } else {
            til_user_pass.error = ""
        }
        //validacion de pass confirmado no nulo
        if (validador.validarCampoNulo(user_pass_conf)) {
            til_confirm_pass.error = "* Error, este campo es requerido"
            contador++
        } else {
            til_confirm_pass.error = ""
        }
        //validacion de fecha cumple no nulo
        if (validador.validarCampoNulo(user_date)) {
            til_date.error = "* Error, este campo es requerido"
            contador++
        } else {
            til_date.error = ""
        }
            println(validador.validarCampoNulo(user_correo))
            println(validador.validarCampoNulo(user_name))
            println(validador.validarCampoNulo(user_pass_regis))
            println(validador.validarCampoNulo(user_pass_conf))
            println(validador.validarCampoNulo(user_date))

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