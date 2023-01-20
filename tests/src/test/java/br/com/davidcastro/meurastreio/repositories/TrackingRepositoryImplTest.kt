package br.com.davidcastro.meurastreio.repositories

import br.com.davidcastro.data.api.TrackingApi
import br.com.davidcastro.data.model.TrackingModel
import br.com.davidcastro.data.repository.TrackingRepositoryImpl
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import retrofit2.Response
import kotlin.test.assertFailsWith

@OptIn(ExperimentalCoroutinesApi::class)
internal class TrackingRepositoryImplTest {

    private val trackingApi: TrackingApi = mockk()
    private val repository: TrackingRepositoryImpl = TrackingRepositoryImpl(trackingApi)
    private val response = Response.success(mockk<TrackingModel>())

    @Test
    fun `when get tracking has success`() = runTest {
        val expectedResult = response

        coEvery {
            trackingApi.getTracking(any(), any(), any())
        } returns response

        val result = getTracking()

        Assert.assertEquals(expectedResult, result)
    }

    @Test(expected = Throwable::class)
    fun `when get tracking has exception`() = runTest {

        coEvery {
            getTracking()
        } throws Exception()

        assertFailsWith<Throwable> { getTracking() }
    }

    private suspend fun getTracking() =
        repository.getTracking("")
}