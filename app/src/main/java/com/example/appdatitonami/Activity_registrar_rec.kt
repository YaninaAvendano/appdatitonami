package com.example.appdatitonami


import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import android.Manifest
import android.provider.MediaStore
import android.widget.ArrayAdapter
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.appdatitonami.roomDatabase.Db
import com.example.appdatitonami.roomDatabase.entity.Receta
import com.google.android.material.textfield.TextInputLayout



class Activity_registrar_rec : AppCompatActivity() {
    companion object {
        const val REQUEST_IMAGE_CAPTURE = 1
        private val CAMERA_PERMISSION_REQUEST_CODE = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_rec)
        //INICIALIZAMOS LA DB
        val room = Room.databaseBuilder(this, Db::class.java,"database-ciisa").allowMainThreadQueries().build()
        //referencias de los widget o ui control
        val til_nombre_rec = findViewById<TextInputLayout>(R.id.til_nombre_rec)
        val til_ing = findViewById<TextInputLayout>(R.id.til_ing)
        val til_paso = findViewById<TextInputLayout>(R.id.til_paso)
        val sp_tiporec = findViewById<Spinner>(R.id.sp_tiporec)
        val btn_img = findViewById<Button>(R.id.btn_img)
        val btn_regis_rec = findViewById<Button>(R.id.btn_regis_rec)

        //recuperar variable
        val user_name_in = intent.getStringExtra("NameUser").toString()

        //generacion spinner tipo de receta
        val arrayAdapterSpinner : ArrayAdapter<*>
        var tipo_rec = ArrayList<String>()
        tipo_rec.add("Seleccione una opción")
        tipo_rec.add("Todas las opciones")
        tipo_rec.add("Dulce")
        tipo_rec.add("Salada")

        arrayAdapterSpinner = ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1,tipo_rec)

        sp_tiporec.adapter=arrayAdapterSpinner

        //accion del metodo clic
            //boton registrar receta
        btn_regis_rec.setOnClickListener {
            if(validarCampos() == 0) {
                var name_receta = til_nombre_rec.editText?.text.toString()
                var name_ing = til_ing.editText?.text.toString()
                var name_paso = til_paso.editText?.text.toString()
                var tipoRec = sp_tiporec.selectedItem.toString()
                val receta = Receta(name_receta,name_ing,name_paso,tipoRec,user_name_in)
                lifecycleScope.launchWhenCreated {
                    //insertamos
                    val id = room.daoReceta().agregarReceta(receta)
                    //opcional ver informacion en el log de todas las recetas
                    var respuesta = room.daoReceta().obtenerReceta()
                    for (elemento in respuesta){
                        println(elemento.toString())
                    }
                    if(id>0){
                        Toast.makeText(this@Activity_registrar_rec,"Receta registrada exitosamente", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@Activity_registrar_rec,Activity_menu::class.java)
                        intent.putExtra("NameUser",user_name_in)
                        startActivity(intent)

                    }

                }


                val intent = Intent(this@Activity_registrar_rec,Activity_menu::class.java)
                intent.putExtra("NameUser",user_name_in)
                startActivity(intent)
            }
        }
           // boton adj imagen
        btn_img.setOnClickListener {
            checkCameraPermission()
        }

    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            CAMERA_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permiso otorgado, inicia la cámara
                    dispatchTakePictureIntent()
                } else {
                    // Permiso denegado, muestra un mensaje o maneja según tus necesidades
                    Toast.makeText(this, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show()
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            val iv_receta = findViewById<ImageView>(R.id.iv_receta)
            iv_receta.setImageBitmap(imageBitmap)
        }
    }
    fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // Solicitar permiso
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_REQUEST_CODE)
        } else {
            // Si ya tienes el permiso, inicia la cámara
            dispatchTakePictureIntent()
        }
    }
    // METODO QUE GATILLARA LA CAPTURA DE LA IMAGEN
    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }



    fun validarCampos():Int {
        val validador = Validador()
        var contador: Int = 0
        val til_nombre_rec = findViewById<TextInputLayout>(R.id.til_nombre_rec)
        val til_paso = findViewById<TextInputLayout>(R.id.til_paso)
        val til_ing = findViewById<TextInputLayout>(R.id.til_ing)
        var name_rec = til_nombre_rec.editText?.text.toString()
        var name_paso = til_paso.editText?.text.toString()
        var name_ing = til_ing.editText?.text.toString()

        //validaciones necesarias
        //validacion el nombre de receta que no sea nulo
        if (validador.validarCampoNulo(name_rec)) {
            til_nombre_rec.error = "* Error, este campo es requerido"
            contador++
        } else {
            til_nombre_rec.error = ""
        }
        //validacion el ing de receta que no sea nulo
        if (validador.validarCampoNulo(name_ing)) {
            til_ing.error = "* Error, este campo es requerido"
            contador++
        } else {
            til_ing.error = ""
        }

        //validacion el valor detalle receta que no sea nulo
        if (validador.validarCampoNulo(name_paso)) {
            til_paso.error = "* Error, este campo es requerido"
            contador++
        } else {
            til_paso.error = ""
        }
        println(validador.validarCampoNulo(name_rec))
        println(validador.validarCampoNulo(name_paso))
        println(validador.validarCampoNulo(name_ing))


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