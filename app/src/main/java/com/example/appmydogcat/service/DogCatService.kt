package com.example.appmydogcat.service

import retrofit2.Call
import retrofit2.http.GET

interface DogCatService {

    @GET("images/search?limit=10&breed_ids=beng&api_key=live_rW02IiF5C76bb7qw16o7Swiq9LfsBSz4FgCnnBgUA0SYCzuIhgkyeRPWeUKhudtM")
    fun keyList(): Call<List<DogCatEntity>>

}