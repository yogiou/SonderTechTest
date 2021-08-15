package jie.wen.sonder.viewmodel

import io.mockk.*
import jie.wen.sonder.model.dto.PassengerListResponseDTO
import jie.wen.sonder.repository.PassengerDataRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*
import retrofit2.Response

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class PassengerListViewModelTest {
    var testDispatcher = TestCoroutineDispatcher()

    @ExperimentalCoroutinesApi
    @Test
    fun testFetchPassengerListData() {
        val repository : PassengerDataRepository = mockk()
        val response = PassengerListResponseDTO()
        val success = Response.success(response)
        val passengerListViewModel = PassengerListViewModel(repository)
        coEvery { repository.fetchPassengerListData(0) } returns success
        testDispatcher.runBlockingTest {
            passengerListViewModel.fetchPassengerListData(0)
            Assert.assertEquals(passengerListViewModel.currentPage.get(), 1)
        }

        coVerify(exactly = 1) { repository.fetchPassengerListData(0) }

    }
}