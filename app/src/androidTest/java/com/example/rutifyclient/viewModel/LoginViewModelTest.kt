package com.example.rutifyclient.viewModel

import junit.framework.TestCase.assertFalse
import org.junit.Test

class LoginViewModelTest {

    @Test
    fun comprobarContrasena() {
        val contrasena = "abc1123"
        val resultado = LoginViewModel().comprobarContrasena(contrasena)
        assertFalse("la contraseña es correcta",resultado)
    }

    @Test
    fun comprobarCorreo() {
        val correo = "aq@.c"
        val resultado = LoginViewModel().comprobarCorreo(correo)
        assertFalse("el correo es correcta",resultado)
    }
}