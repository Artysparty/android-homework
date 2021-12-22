package ru.rut.permissiondemo.inter

import retrofit2.Call
import retrofit2.http.*
import ru.rut.permissiondemo.model.Character

interface RetrofitService {
    @GET("characters")
    fun getCharactersList(@Query("pageNumber") pageNumber: Int, @Query("pageSize") pageSize: Int): Call<MutableList<Character>>
}