package com.example.swapplication.main.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.swapplication.models.Banda
import com.example.swapplication.models.BandaComId
import com.example.swapplication.repositorios.BandasRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.toObject

class MainViewModel: ViewModel() {

    val TAG = "ViewModel"


    val repository = BandasRepository.get()



    fun cadastrarBanda(banda: Banda): Task<DocumentReference> {
        return repository.cadastrarBanda(banda)
    }

    // Ouvir vários documentos em uma coleção
    // https://firebase.google.com/docs/firestore/query-data/listen?hl=pt&authuser=0#listen_to_multiple_documents_in_a_collection
    fun observeColecaoBandas() {

        repository.getBandasColecao()
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    Log.w(TAG, "listen:error", e)
                    return@addSnapshotListener
                }

                val listaInput = mutableListOf<BandaComId>()

                val listaRemocao = mutableListOf<String>()

                val listaModificacao = mutableListOf<BandaComId>()

                // Ver alterações entre instantâneos
                // https://firebase.google.com/docs/firestore/query-data/listen?hl=pt&authuser=0#view_changes_between_snapshots
                for (dc in snapshots!!.documentChanges) {
                    when (dc.type) {

                        // Documento adicionado
                        DocumentChange.Type.ADDED -> {

                            val banda = dc.document.toObject<Banda>()
                            val id = dc.document.id
                            val bandaComId = bandaToBandaComId(banda, id)

                            Log.i(TAG, "bandaComId: ${bandaComId}")
                            listaInput.add(bandaComId)

                        }

                        // Documento modificado
                        DocumentChange.Type.MODIFIED -> {
                            val banda = dc.document.toObject<Banda>()
                            val id = dc.document.id
                            val bandaComId = bandaToBandaComId(banda, id)

                            Log.i(TAG, "Modificacao - bandaComId: ${bandaComId}")
                            listaModificacao.add(bandaComId)
                        }

                        // Documento removido
                        DocumentChange.Type.REMOVED -> {
                            val id = dc.document.id
                            Log.i(TAG, "id removido: ${id}")
                            listaRemocao.add(dc.document.id)

                        }
                    }
                }

                addListaToBandasComId(listaInput)
                removeFromBandasComId(listaRemocao)
                modifyInBandasComId(listaModificacao)
            }
    }

    fun modifyItemInListaBandasComId(itemModificado: BandaComId) {
        val listaAntiga = bandasComId.value
        val listaNova = mutableListOf<BandaComId>()

        listaAntiga?.forEach { itemDaLista ->
            if (itemModificado.id == itemDaLista.id) {
                listaNova.add(itemModificado)
            } else {
                listaNova.add(itemDaLista)
            }
        }
        setBandasComId(listaNova)
    }

    private fun modifyInBandasComId(listaModificacao: List<BandaComId>) {
        Log.i(TAG, "listaModificacao: ${listaModificacao}")
        if (listaModificacao.isNotEmpty()) {
            for (itemModificado in listaModificacao) {
                modifyItemInListaBandasComId(itemModificado)
            }
        }
    }

    private fun removeFromBandasComId(listaRemocao: List<String>) {

        val listaAntiga = bandasComId.value

        val listaNova = mutableListOf<BandaComId>()

        Log.i(TAG, "listaRemocao: ${listaRemocao}")

        if (listaRemocao.isNotEmpty()) {
            listaAntiga?.forEach {
                Log.i(TAG, "item da lista Antiga: ${it.id}")
                if (it.id in listaRemocao) {
                    Log.i(TAG, "item ${it.id} está dentro da listaRemocao")

                    //listaNova.add(it)
                } else {
                    Log.i(TAG, "item ${it.id} _NÃO_ está dentro da listaRemocao")

                    listaNova.add(it)
                }
            }
            setBandasComId(listaNova)
        }


    }

    fun addListaToBandasComId(listaInput: List<BandaComId>) {
        val listaAntiga = bandasComId.value

        val listaNova = mutableListOf<BandaComId>()

        listaAntiga?.forEach {
            listaNova.add(it)
        }

        listaInput.forEach {
            listaNova.add(it)
        }

        setBandasComId(listaNova)


    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Bandas //////////////////////////////////////////////////////////////////////////////////////
    private val _bandas = MutableLiveData<List<Banda>>()
    val bandas: LiveData<List<Banda>> = _bandas
    fun setTurmas(value: List<Banda>) {
        _bandas.postValue(value)
    }

    private val _bandasComId = MutableLiveData<List<BandaComId>>()
    val bandasComId: LiveData<List<BandaComId>> = _bandasComId
    fun setBandasComId(value: List<BandaComId>) {
        _bandasComId.postValue(value)
    }

    fun bandaToBandaComId(banda: Banda, id: String): BandaComId {
        return BandaComId(
            nomeBanda = banda.nomeBanda,
            generoBanda = banda.generorBanda,
            formacaoBanda = banda.anoBanda,
            id = id
        )
    }

    fun deleteBanda(id: String) {
        repository.deleteBanda(id)
    }

    private val _selectedBandaComId = MutableLiveData<BandaComId>()
    val selectedBandaComId: LiveData<BandaComId> = _selectedBandaComId
    fun setSelectedBandaComId(value: BandaComId) {
        _selectedBandaComId.postValue(value)
    }

    fun atualizaBanda(banda: Banda) {
        repository.atualizaBanda(selectedBandaComId.value?.id, banda)
    }

    init {
        observeColecaoBandas()
    }


}