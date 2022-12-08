package com.example.swapplication.main.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.swapplication.MainActivity
import com.example.swapplication.databinding.ActivityMain2Binding
import com.example.swapplication.login.ui.LoginActivity
import com.example.swapplication.repositorios.BandasRepository

class MainActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityMain2Binding

    private lateinit var repository: BandasRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setup()
    }

    private fun setup() {
        setupclickListener()
        repository = BandasRepository.get()
    }

    private fun setupclickListener() {
        binding.btnSair.setOnClickListener{
            repository.logout()
            iniciarLoginActivity()
        }
        binding.btnMusicos.setOnClickListener{
            iniciarregistroMusicos()
        }
    }

    private fun iniciarLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun iniciarregistroMusicos() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun setupView(){
        binding.aaa.text = "seja bem vindo ${repository.pegarUsuarioAtual()?.email}"
    }
}