package br.ufpe.cin.if710.rss

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.itemlista.view.*
import android.support.v4.content.ContextCompat.startActivity
import android.content.Intent
import android.net.Uri


class RssAdapter(private val noticias : List<ItemRSS>, private val c : Context): RecyclerView.Adapter<RssAdapter.ViewHolder> (){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder{
        val view = LayoutInflater.from(c).inflate(R.layout.itemlista,parent,false)
        return ViewHolder(view)
    }
    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        val rss_titulo = view.item_titulo
        val rss_data = view.item_data
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        holder.rss_titulo.text = noticias[position].title
        holder.rss_data.text = noticias[position].pubDate
        holder.rss_titulo.setOnClickListener(){

            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(noticias[position].link))
            startActivity(c,browserIntent,null)
        }
    }
    override fun getItemCount(): Int {
        return noticias.size
    }
}