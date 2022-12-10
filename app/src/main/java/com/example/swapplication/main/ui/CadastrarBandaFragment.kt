package com.example.swapplication.main.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.swapplication.databinding.FragmentCadastrarBandaBinding
import com.example.swapplication.models.Banda
import com.example.swapplication.repositorios.BandasRepository
import com.example.swapplication.utils.navup
import com.example.swapplication.utils.toast

class CadastrarBandaFragment : Fragment() {

    private var _binding: FragmentCadastrarBandaBinding? = null

    private lateinit var repository: BandasRepository

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCadastrarBandaBinding.inflate(inflater, container, false)
        val view = binding.root
        setup()
        return view
    }

    private fun setup() {
        repository = BandasRepository.get()
        setupViews()
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.btnCadastrar.setOnClickListener {
            noClickCadastrar()
        }
    }

    private fun noClickCadastrar() {
        val banda = pegarBandaDoInput()

        repository.cadastrarBanda(banda)
            .addOnSuccessListener { documentReference ->
                toast("Sucesso no cadatro com id : ${documentReference.id}")
                navup()
            }
            .addOnFailureListener { e ->
                toast("Falha cadastro")
            }
    }

    private fun pegarBandaDoInput(): Banda {
        return Banda(
            nomeBanda = binding.inputNomeBanda.text.toString(),
            generorBanda = binding.inputGenero.text.toString(),
            anoBanda = binding.inputAno.text.toString()
        )
    }

    private fun setupViews() {
        activity?.setTitle("Cadastrar bandas")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}