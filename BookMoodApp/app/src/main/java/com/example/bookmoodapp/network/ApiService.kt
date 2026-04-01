package com.example.bookmoodapp.network
import com.example.bookmoodapp.model.EstadoAnimo
import com.example.bookmoodapp.model.Libro
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService{
    @GET("moods")
    suspend fun getMoods(): Response<List<EstadoAnimo>>
    @GET("books/recommendations")
    suspend fun getRecomendations(@Query("moodId") moodId: Long): Response<List<Libro>>

}