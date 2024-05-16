package com.example.mjosevl20240512


import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

//interface ApiServicio {
  //  @GET("bitcoin")
  //  suspend fun obtenerPrecioBitcoin(): Response<PrecioBitcoin>
//}//

interface ApiServicio {
    @GET("bitcoin")
    fun obtenerPrecioBitcoin(): Call<PrecioBitcoin>
}
