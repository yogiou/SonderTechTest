package jie.wen.sonder.api

import jie.wen.sonder.model.dto.PassengerListResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("passenger")
    suspend fun fetchPassengerListData(
        @Query("page") page: Int,
        @Query("size") size: Int
    ) : Response<PassengerListResponseDTO>
}