package com.yorran.lojavirtual.models

import android.util.Log
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException

class DB {

    fun salvandoDadosUsuario(nome : String){

        //Recuperando o ID do usuário Logado
        val usuarioId = FirebaseAuth.getInstance().currentUser!!.uid

        //Recuperando a Referencia do Banco de Dados
        val db = FirebaseFirestore.getInstance()

        //Criando um HashMap com os dados do usuário que deseja salvar
        val data = hashMapOf(
            "nome" to nome
        )

        //Criando dentro da colação usuários um documento para salvar os dados de cada usuário
        //Cada documento será baseado no ID do usuário
        val documentReference : DocumentReference = db.collection("Usuarios").document(usuarioId)

        //Função para Salvamento
        salvarDados(documentReference, data)
    }

    //Salvando os Dados com o metodo Set
    private fun salvarDados(documentReference: DocumentReference, data: Map<String, Any>) {
        documentReference.set(data)
            .addOnSuccessListener {
                Log.d("DB", "Sucesso ao salvar")
            }
            .addOnFailureListener { erro ->
                val mensagemError = obterMensagemErro(erro)
                Log.d("DB", "Erro ao salvar: $mensagemError")
                erro.printStackTrace()
            }
    }


    fun recuperarDadosUsuario(nome: TextView, email: TextView){
        val usuarioId = FirebaseAuth.getInstance().currentUser!!.uid //Recuperando o ID do usuário autenticado
        val emailUser = FirebaseAuth.getInstance().currentUser!!.email //Recuperando o email
        val db = FirebaseFirestore.getInstance() //Recuperando a instancia do FirebaseFirestore

        val documentReference : DocumentReference = db.collection("Usuarios").document(usuarioId) //Recuperando a coleção com os dados do usuário

        //Metodo para recuperar as informações
        documentReference.addSnapshotListener { document, error ->
            if (document != null){
                nome.text = document.getString("nome") // Vai recuperar a informação baseado na chave nome
                email.text = emailUser
            }
            else{
                Log.d("FirebaseFirestore Documentation", "Erro recuperação dos dados FirebaseFirestore ")
            }
        }
    }

    fun atualizarDadosPerffil(nome: String, email: String){
        val user = FirebaseAuth.getInstance().currentUser
        val db = FirebaseFirestore.getInstance()

        //Inicia a criação de uma solicitação de alteração de perfil.
        val atualizarNome = UserProfileChangeRequest.Builder()
            .setDisplayName(nome)
            .build()

        //Aplica a solicitação de alteração criada ao perfil do usuário.
        user!!.updateProfile(atualizarNome).addOnCompleteListener {task ->
            if (task.isSuccessful){
                //Map com os dados a ser atualizados
                val novoNome = mapOf(
                    "nome" to nome
                )

                //Alteração dentro da coleção
                db.collection("Usuarios").document(user.uid).update(novoNome)
                Log.d("UpdateProfile", "Sucesso ao atualizar o perfil")
            }

            else{
                Log.d("UpdateProfile", "Erro ao atualizar o perfil")
            }

        }

        //Atualizando o email
        user.verifyBeforeUpdateEmail(email).addOnCompleteListener {task ->
            if (task.isSuccessful){
                Log.d("updateEmail", "Sucesso ao atualizar")
            }

        }.addOnFailureListener {Exception->
            Log.d("updateEmail", "Erro ao atualizar $Exception, ${obterMensagemErro(Exception)}")

        }

    }


    // Método para tratar os códigos de erro do FirebaseFirestoreException
    private fun obterMensagemErro(erro: Exception): String {
        return when ((erro as? FirebaseFirestoreException)?.code) {
            FirebaseFirestoreException.Code.ALREADY_EXISTS -> "Já existe um documento com esse ID."
            FirebaseFirestoreException.Code.PERMISSION_DENIED -> "Você não tem permissão para salvar dados nesse documento."
            FirebaseFirestoreException.Code.INVALID_ARGUMENT -> "O argumento fornecido é inválido."
            FirebaseFirestoreException.Code.UNAVAILABLE -> "O banco de dados está temporariamente indisponível."
            FirebaseFirestoreException.Code.FAILED_PRECONDITION -> "A operação não pode ser concluída devido a uma condição prévia não atendida."
            FirebaseFirestoreException.Code.RESOURCE_EXHAUSTED -> "Você atingiu o limite de recursos do Firestore."
            else -> "Erro desconhecido ao salvar dados."
        }
    }
}