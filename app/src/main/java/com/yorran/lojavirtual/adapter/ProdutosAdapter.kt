package com.yorran.lojavirtual.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yorran.lojavirtual.databinding.ProdutoItemBinding
import com.yorran.lojavirtual.models.Produtos

class ProdutosAdapter(private val context: Context, private val produtosItem : MutableList<Produtos>):
    RecyclerView.Adapter<ProdutosAdapter.ProdutoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProdutoViewHolder {
        //Responsável por inflar na tela// )
        val itemLista = ProdutoItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ProdutoViewHolder(itemLista)
   }

    override fun onBindViewHolder(holder: ProdutoViewHolder, position: Int) {
        // Vincula os dados ao ViewHolder
        produtosItem.get(position).image?.let { holder.imageProduto.setImageResource(it) }
        holder.nomeProduto.text = produtosItem.get(position).nome
        holder.valorProduto.text = produtosItem.get(position).valor

    }

    override fun getItemCount() = produtosItem.size


    inner class ProdutoViewHolder(binding: ProdutoItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val imageProduto = binding.imageProduto
        val nomeProduto = binding.nomeProduto
        val valorProduto = binding.valorProduto

    }

}