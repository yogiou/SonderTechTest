package jie.wen.sonder.api

import jie.wen.sonder.model.dto.PassengerListResponseDTO
import jie.wen.sonder.other.Constants.Companion.API_PAGINATION_SIZE
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val apiService: ApiService
): ApiHelper{
    override suspend fun fetchPassengerListData(page: Int): Response<PassengerListResponseDTO> = apiService.fetchPassengerListData(page, API_PAGINATION_SIZE)
}