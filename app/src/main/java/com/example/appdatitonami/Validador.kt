package com.example.appdatitonami

import android.util.Patterns
import java.util.regex.Pattern

class Validador {
    fun validarCampoNulo(texto:String):Boolean{
        return texto.trim().equals("") || texto.trim().length==0
    }
    fun validarCamposIguales(texto:String,texto2: String):Boolean{
        return !texto.trim().equals(texto2.trim())
    }
    fun validarnombre(nombre:String):Boolean{
        val pattern = Pattern.compile("^[a-zA-Z]+\$")
        return !pattern.matcher(nombre).matches()
    }
    fun validarcorreo(correo:String):Boolean{
        return !Patterns.EMAIL_ADDRESS.matcher(correo).matches()
    }

}