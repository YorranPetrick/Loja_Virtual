package com.yorran.lojavirtual.dialog

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import com.yorran.lojavirtual.databinding.AtualizarPerfilDialogBinding
import com.yorran.lojavirtual.models.DB

class AtualizarPerfilDialog(private var activity: Activity) {

    lateinit var dialog: AlertDialog //Variavel do tipo dialog
    private lateinit var binding: AtualizarPerfilDialogBinding

    fun iniciarDialog(){

        var builder = AlertDialog.Builder(activity)
        binding = AtualizarPerfilDialogBinding.inflate(activity.layoutInflater)
        builder.setView(binding.root)
        builder.setCancelable(true)

        dialog = builder.create() //Criando a dialog
        dialog.show() //Mostando a dialog

        binding.SalvarPerfil.setOnClickListener {
            atualizarDados(
                binding.NovoNome.text.toString(),
                binding.NovoEmail.text.toString()
            )

            fecharDialog()
        }


    }

    private fun atualizarDados(nome:String, email:String){
        val db = DB() //Instanciando o Banco de dados
        db.atualizarDadosPerffil(
           nome, email
        )
    }

    private fun fecharDialog(){
        val perfilDialog = PerfilDialog(activity)
        perfilDialog.iniciarDialog()
        dialog.dismiss()
    }
}