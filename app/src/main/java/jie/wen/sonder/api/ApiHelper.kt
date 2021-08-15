package jie.wen.sonder.api

import jie.wen.sonder.model.dto.PassengerListResponseDTO
import retrofit2.Response

interface ApiHelper {
    suspend fun fetchPassengerListData(page: Int):Response<PassengerListResponseDTO>
}