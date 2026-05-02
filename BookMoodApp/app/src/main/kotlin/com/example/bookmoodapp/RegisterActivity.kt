package com.example.bookmoodapp
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.bookmoodapp.LoginActivity
import com.example.bookmoodapp.R
import com.example.bookmoodapp.model.RegisterRequest
import com.example.bookmoodapp.network.RetrofitClient
import kotlinx.coroutines.launch
import java.lang.Exception


class RegisterActivity : AppCompatActivity() {
    private lateinit var editNombre: EditText
    private lateinit var editEmail: EditText
    private lateinit var editPassword: EditText
    private lateinit var btnRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        editNombre = findViewById(R.id.editNombre)
        editEmail = findViewById(R.id.editEmail)
        editPassword = findViewById(R.id.editPassword)
        btnRegister = findViewById(R.id.btnRegister)

        btnRegister.setOnClickListener {
            register()
        }
    }

    private fun register() {
        val nombre = editNombre.text.toString()
        val email = editEmail.text.toString()
        val password = editPassword.text.toString()

        if (nombre.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            try{
                val request = RegisterRequest(nombre, email, password)
                val response = RetrofitClient.api.register(request)
                if (response.isSuccessful) {
                    Toast .makeText(this@RegisterActivity,
                        "Usuarios registrado correctamente", Toast.LENGTH_SHORT).show()

                    startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                    finish()

                }else{
                    Toast.makeText(this@RegisterActivity,
                        "Error al registrar el usuario", Toast.LENGTH_SHORT).show()

                }

            }catch (e: Exception){
                Toast.makeText(this@RegisterActivity, "Fallo: ${e.message}",
                    Toast.LENGTH_SHORT).show()
            }
        }

    }
}