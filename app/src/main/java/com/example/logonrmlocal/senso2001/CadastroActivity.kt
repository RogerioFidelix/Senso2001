package com.example.logonrmlocal.senso2001

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.logonrmlocal.senso2001.api.CepAPI
import com.example.logonrmlocal.senso2001.model.Endereco
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import kotlinx.android.synthetic.main.activity_cadastro.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CadastroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        Stetho.initializeWithDefaults(this)
        btPesquisar.setOnClickListener { pesquisarCep() }
    }

    private fun pesquisarCep(){
        val okHttpClient = OkHttpClient.Builder()
                .addNetworkInterceptor(StethoInterceptor())
                .build()


        val retrofit = Retrofit.Builder()
                .baseUrl("https://viacep.com.br")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()

        val service = retrofit.create(CepAPI::class.java)
        service.pesquisar("01523000")
                .enqueue(object : Callback<Endereco>{
                    override fun onFailure(call: Call<Endereco>?, t: Throwable?) {
                        exibeEro(t)
                    }

                    override fun onResponse(call: Call<Endereco>?, response: Response<Endereco>?) {
                        preencheEndereco(response?.body())
                    }
                })
    }

    private fun preencheEndereco(endereco: Endereco?){
        Toast.makeText(this, endereco?.bairro,
                Toast.LENGTH_LONG).show()
    }

    private fun exibeEro(t:Throwable?){
        Toast.makeText(this, t?.message,
                Toast.LENGTH_LONG).show()
    }
}
