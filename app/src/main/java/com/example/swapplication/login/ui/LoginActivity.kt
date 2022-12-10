package com.example.swapplication.login.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.swapplication.databinding.ActivityLoginBinding
import com.example.swapplication.main.ui.MainActivity2
import com.example.swapplication.repositorios.BandasRepository
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {


    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var repository: BandasRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        auth = FirebaseAuth.getInstance()

        setup()
    }

    override fun onStart() {
        super.onStart()

        if (repository.estaLogado()) {
            iniciarMainActivity2()
        }
    }

    private fun iniciarMainActivity2() {
        startActivity(Intent(this, MainActivity2::class.java))
        finish()
    }

    private fun setup() {
        setupClickListener()

        repository = BandasRepository.get()
    }

    private fun setupClickListener() {
        binding.tvRegister.setOnClickListener {
            val intent = Intent(this, RegistrarActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {
            noClickLogar()
        }
    }

    private fun noClickLogar() {
        val email = binding.inputEmail.text.toString()
        val senha = binding.inputPassword.text.toString()
        if (email.isBlank() || senha.isBlank()) {
            Toast.makeText(this, "Email e senha vazios", Toast.LENGTH_SHORT).show()
            return
        } else {
            auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Sucesso", Toast.LENGTH_SHORT).show()
                    iniciarMainActivity2()
                } else {
                    Toast.makeText(this, "Falha na autenticação", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}