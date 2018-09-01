package com.example.logonrmlocal.senso2001.api

import com.example.logonrmlocal.senso2001.model.Endereco
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface  CepAPI{
    @GET("/ws/{cep}/json")
    fun pesquisar(@Path("cep")cep: String) : Call<Endereco>
}