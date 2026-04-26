package com.example.bookmoodapp.network
import com.example.bookmoodapp.model.EstadoAnimo
import com.example.bookmoodapp.model.Libro
import com.example.bookmoodapp.model.UsuarioLibroRequest
import com.example.bookmoodapp.model.RegisterRequest
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Body
import retrofit2.http.POST
import com.example.bookmoodapp.model.LoginRequest
import com.example.bookmoodapp.model.UsuarioResponse
import retrofit2.http.Path





interface ApiService{
    @GET("moods")
    suspend fun getMoods(): Response<List<EstadoAnimo>>
    @GET("books/recommendations")
    suspend fun getRecommendations(@Query("moodId") moodId: Long): Response<List<Libro>>

    @POST("usuarioLibro")
    suspend fun saveUserBook(@Body request: UsuarioLibroRequest): Response<UsuarioLibroRequest>
    @POST("usuario/login")
    suspend fun login(@Body request: LoginRequest): Response<UsuarioResponse>

    @POST("usuario/register")
    suspend fun register(@Body request: RegisterRequest): Response<UsuarioResponse>
    @GET("usuario/{id}/favoritos")
    suspend fun getFavoritos(@Path("id") userId: Long): Response<List<Libro>>

    @GET("usuario/{id}/leidos")
    suspend fun getLeidos(@Path("id") userId: Long): Response<List<Libro>>

    @GET("usuario/{id}/quieroLeer")
    suspend fun getQuieroLeer(@Path("id") userId: Long): Response<List<Libro>>
}





