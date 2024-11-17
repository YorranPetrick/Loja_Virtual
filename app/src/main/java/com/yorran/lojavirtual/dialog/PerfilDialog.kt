package com.yorran.lojavirtual.dialog

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.widget.TextView
import com.yorran.lojavirtual.databinding.PerfilDialogBinding
import com.yorran.lojavirtual.models.DB

class PerfilDialog(private val activity: Activity) {

    lateinit var dialog: AlertDialog //Variavel do tipo dialog
    lateinit var binding: PerfilDialogBinding //Variavel binding

    fun iniciarDialog(){
        val builder = AlertDialog.Builder(activity)//Usando o metodo Builder para criar a dialog na atividade
        binding = PerfilDialogBinding.inflate(activity.layoutInflater)
        builder.setView(binding.root)
        builder.setCancelable(true) //Parametro para o cancelamento da dialog
        dialog = builder.create() //Criando a dialog
        dialog.show() //Mostando a dialog

        dadosUsuarios(binding.nomeUsuario, binding.emailUsuario)

        binding.editarPerfil.setOnClickListener {
            editarPerfil()
        }

    }

    private fun dadosUsuarios(nome: TextView, email: TextView){
        val db = DB()
        db.recuperarDadosUsuario(nome, email)
    }

    //Inicializar nova dialog
    private fun editarPerfil(){
        val atualizarPerfil = AtualizarPerfilDialog(activity)
        atualizarPerfil.iniciarDialog()
        dialog.dismiss() //Finaliza a dialog atual
    }

}