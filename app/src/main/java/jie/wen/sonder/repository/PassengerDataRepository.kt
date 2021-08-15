package jie.wen.sonder.repository

import jie.wen.sonder.api.ApiHelper
import javax.inject.Inject

class PassengerDataRepository @Inject constructor(
    private val apiHelper: ApiHelper
) {
    suspend fun fetchPassengerListData(page: Int) = apiHelper.fetchPassengerListData(page)
}