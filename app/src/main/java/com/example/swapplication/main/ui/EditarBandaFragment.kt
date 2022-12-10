package com.example.swapplication.main.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.activityViewModels
import com.example.swapplication.databinding.FragmentEditarBandaBinding
import com.example.swapplication.models.Banda
import com.example.swapplication.utils.navup
import kotlinx.android.synthetic.main.fragment_editar_banda.*

class EditarBandaFragment : Fragment() {

    val viewModel: MainViewModel by activityViewModels()

    private var _binding: FragmentEditarBandaBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditarBandaBinding.inflate(inflater, container, false)
        val view = binding.root
        setup()
        return view
    }

    private fun setup() {
        setupObservers()
        setupClickListeners()
        carregarMetalArchives()

    }



    private fun setupClickListeners() {
        binding.btnEditar.setOnClickListener {
            onAtualizarClick()
        }
    }

    private fun onAtualizarClick() {
        val banda = getBandaFromInputs()
        viewModel.atualizaBanda(banda)
        navup()
    }

    private fun getBandaFromInputs(): Banda {
        binding.apply {
            return Banda(
                nomeBanda = binding.inputEditarNomeBanda.text.toString(),
                generorBanda =  binding.inputEditarGenero.text.toString(),
                anoBanda =  binding.inputEditarAno.text.toString()
            )
        }
    }

    private fun setupObservers() {
        viewModel.selectedBandaComId.observe(viewLifecycleOwner){
            binding.apply {
                inputEditarNomeBanda.setText(it.nomeBanda)
                inputEditarGenero.setText(it.generoBanda)
                inputEditarAno.setText(it.formacaoBanda)
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun carregarMetalArchives() {
        val webView = binding.webView
        webView.loadUrl("http://metal-archives.com")
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}