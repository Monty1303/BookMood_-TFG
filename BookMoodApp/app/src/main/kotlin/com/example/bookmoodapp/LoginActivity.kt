package com.example.bookmoodapp
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope

import com.example.bookmoodapp.network.RetrofitClient
import com.example.bookmoodapp.model.LoginRequest
import kotlinx.coroutines.launch
import android.content.Intent
import android.widget.Toast
import java.lang.Exception






class LoginActivity : AppCompatActivity(){
    private lateinit var editEmail : EditText
    private lateinit var editPassword : EditText
    private lateinit var btnLogin : Button
    private lateinit var btnGoRegister : Button


     override fun onCreate (savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        editEmail = findViewById(R.id.editEmail)
        editPassword = findViewById(R.id.editPassword)
        btnLogin = findViewById(R.id.btnLogin)
         btnGoRegister= findViewById(R.id.btnGoRegister)

        btnGoRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        btnLogin.setOnClickListener {
            login()
        }
    }
    private fun login(){
        val email = editEmail.text.toString()
        val password = editPassword.text.toString()
        if (email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }
        lifecycleScope.launch {
            try {
                val request = LoginRequest(email, password)
                val response = RetrofitClient.api.login(request)

                if (response.isSuccessful){
                    val user = response.body()
                    if(user != null){
                        val sharedPref = getSharedPreferences("bookmood", MODE_PRIVATE)
                        sharedPref.edit().putLong("userId", response.body()!!.idUsuario)
                            .putString("userName",response.body()!!.nombre)
                            .apply()

                        Toast.makeText(this@LoginActivity, "Login Correcto", Toast.LENGTH_SHORT).show()

                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                    }
                }else {
                    Toast.makeText(this@LoginActivity, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
                }

            }catch (e: Exception){
                Toast.makeText(this@LoginActivity, "Fallo: ${e.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}