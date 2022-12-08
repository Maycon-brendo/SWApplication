package com.example.swapplication.repositorios


import com.example.swapplication.models.Banda
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.lang.IllegalStateException


const val TAG = "BandasFirebase"

class BandasRepository private constructor() {


// ...
// Initialize Firebase Auth


    companion object {


        private lateinit var auth: FirebaseAuth

        private lateinit var db: FirebaseFirestore


        lateinit var colecaoBandas : CollectionReference


        private var INSTACE: BandasRepository? = null
        fun initializer() {
            if (INSTACE == null) {
                INSTACE = BandasRepository()
            }
            auth = Firebase.auth
            db = Firebase.firestore
            colecaoBandas = db.collection("bandas")
        }

        fun get(): BandasRepository {
            return INSTACE
                ?: throw IllegalStateException("BandasRepository Deve ser inicializado")
        }
    }

    fun pegarUsuarioAtual() = auth.currentUser

    fun cadastrarUsuarioComSenha(email: String, senha: String): Task<AuthResult> {
        return auth.createUserWithEmailAndPassword(email, senha)

    }

    fun login(
        email: String,
        password: String
    ): Task<AuthResult> {
        return auth.signInWithEmailAndPassword(email, password)
    }

    fun estaLogado(): Boolean {
        if (pegarUsuarioAtual() != null) {
            return true
        }
        return false
    }

    fun logout() {
        Firebase.auth.signOut()
    }

    fun cadastrarBanda(banda: Banda): Task<DocumentReference>{
        return colecaoBandas.add(banda)
    }

    fun deleteBanda(id: String) {
        colecaoBandas.document(id).delete()
    }

    fun atualizaBanda(id: String?, banda: Banda) {
        if (id != null) {
            colecaoBandas.document(id).set(banda)
        }
    }
    fun getBandasColecao(): CollectionReference {
        return colecaoBandas
    }
}