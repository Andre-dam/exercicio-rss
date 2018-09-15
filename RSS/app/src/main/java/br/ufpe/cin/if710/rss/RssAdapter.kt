package br.ufpe.cin.if710.rss

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.itemlista.view.*

class RssAdapter(private val noticias : List<String>, private val c : Context): RecyclerView.Adapter<RssAdapter.ViewHolder> (){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder{
        val view = LayoutInflater.from(c).inflate(R.layout.itemlista,parent,false)
        return ViewHolder(view)
    }
    class ViewHolder(val textView: View) : RecyclerView.ViewHolder(textView){
        val titul = textView.item_titulo
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        holder.titul.text = noticias[position]
    }
    override fun getItemCount(): Int {
        return noticias.size
    }
}