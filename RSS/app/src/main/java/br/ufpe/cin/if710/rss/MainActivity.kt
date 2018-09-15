package br.ufpe.cin.if710.rss

import android.app.Activity
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import br.ufpe.cin.if710.rss.ParserRSS.parse

import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : Activity() {

    //ao fazer envio da resolucao, use este link no seu codigo!
    //private val RSS_FEED = "http://leopoldomt.com/if1001/g1brasil.xml"
    //OUTROS LINKS PARA TESTAR...
    //http://rss.cnn.com/rss/edition.rss
    //http://pox.globo.com/rss/g1/brasil/
    //http://pox.globo.com/rss/g1/ciencia-e-saude/
    //http://pox.globo.com/rss/g1/tecnologia/

    //use ListView ao invés de TextView - deixe o atributo com o mesmo nome
    //private var conteudoRSS: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onStart() {
        super.onStart()
        val download = DownloadRSS()
        download.execute()
    }

    private inner class DownloadRSS:AsyncTask<String,Void,String> () {
        override fun doInBackground(vararg p0: String?): String {
            try {
                val RSS_FEED = getString(R.string.rssfeed)
                val feedXML = getRssFeed(RSS_FEED)
                return feedXML
            } catch (e: IOException) {
                e.printStackTrace()

                val text = e.message
                val duration = Toast.LENGTH_LONG
                val toast = Toast.makeText(this@MainActivity, text, duration)
                toast.show()

                return "Error"
            }
        }

        override fun onPostExecute(result: String) {
            super.onPostExecute(result)
            val RSS_list = parse(result)
            val conteudoRSS = findViewById<RecyclerView>(R.id.conteudoRSS)
            conteudoRSS.layoutManager = LinearLayoutManager(this@MainActivity)
            conteudoRSS.adapter = RssAdapter(RSS_list,this@MainActivity)
        }
    }

    //Opcional - pesquise outros meios de obter arquivos da internet - bibliotecas, etc.
    @Throws(IOException::class)
    private fun getRssFeed(feed: String): String {
        var `in`: InputStream? = null
        var rssFeed = ""
        try {
            val url = URL(feed)
            val conn = url.openConnection() as HttpURLConnection
            `in` = conn.inputStream
            val out = ByteArrayOutputStream()
            val buffer = ByteArray(1024)
            var count: Int
            count = `in`!!.read(buffer)
            while (count != -1) {
                out.write(buffer, 0, count)
                count = `in`!!.read(buffer)
            }
            val response = out.toByteArray()
            rssFeed = String(response, Charsets.UTF_8)
        } finally {
            `in`?.close()
        }
        return rssFeed
    }

}
