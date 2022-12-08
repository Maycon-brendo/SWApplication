package com.example.swapplication.login.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.swapplication.databinding.ActivityRegistrarBinding
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.firebase.auth.FirebaseAuth

class RegistrarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrarBinding
    private lateinit var auth: FirebaseAuth

    lateinit var mAdView: AdView
    private  var anuncioInter: InterstitialAd? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrarBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        auth = FirebaseAuth.getInstance()

        MobileAds.initialize(this){}
        val mAdView = binding.adView
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        setup()
    }

    private fun setup() {
        requestNewInterstitial()
        setupClickListeners()
//        repository = BandasRepository.get()
    }

    private fun requestNewInterstitial() {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(this, "ca-app-pub-3940256099942544/1033173712", adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d("Intertitial", adError.message)
                    anuncioInter = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    Log.d("Interstitial", "Add foi carregado")
                    anuncioInter = interstitialAd
                    configureFullScreen()
                }
            })
    }

    private fun configureFullScreen() {
        anuncioInter?.fullScreenContentCallback = createFullScreenCalback("REWARDED") {
            anuncioInter = null
        }
    }

    private fun createFullScreenCalback(TAG: String, funcao: () -> Unit): FullScreenContentCallback?  = object : FullScreenContentCallback() {
        override fun onAdDismissedFullScreenContent() {
            Log.d(TAG, "Ad was dismissed.")
        }

        override fun onAdFailedToShowFullScreenContent(p0: AdError) {
            Log.d(TAG, "Ad failed to show.")
        }

        override fun onAdShowedFullScreenContent() {
            Log.d(TAG, "Ad showed fullscreen content.")
            funcao()
        }
    }

    private fun setupClickListeners() {
        binding.btnRegistrar.setOnClickListener {
            noClickCadastrar()
        }
    }

    private fun noClickCadastrar() {
        val email = binding.inputEmailRegistrar.text.toString()
        val senha = binding.inputPasswordRegistrar.text.toString()
        if (email.isBlank() || senha.isBlank()) {
            Toast.makeText(this, "Email e senha vazios", Toast.LENGTH_SHORT).show()
            return
        } else {
            auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Sucesso", Toast.LENGTH_SHORT).show()
                    if(anuncioInter != null){
                        anuncioInter?.show(this)
                    }else{
                        Log.d("Interstitial", "o ad interstitial não estava pronto")
                    }
                    iniciarLogin()
                } else {
                    Toast.makeText(this, "Falha ao criar contao", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun iniciarLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
