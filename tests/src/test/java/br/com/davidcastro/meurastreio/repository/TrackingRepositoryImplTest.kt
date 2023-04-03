package br.com.davidcastro.meurastreio.repository

import TrackingResponseRealMock
import br.com.davidcastro.data.api.TrackingApi
import br.com.davidcastro.data.model.TrackingModel
import br.com.davidcastro.data.repository.trackingrepository.TrackingRepository
import br.com.davidcastro.data.repository.trackingrepository.TrackingRepositoryImpl
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.Assert
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
internal class TrackingRepositoryImplTest {

    private val trackingApi: TrackingApi = mockk()
    private val repository: TrackingRepository = TrackingRepositoryImpl(trackingApi)

    @Test
    fun `when get tracking has success`() = runTest {

        val expectedResult = Response.success(TrackingResponseRealMock.getTrackingModelResponse())

        coEvery {
            trackingApi.getTracking(any(), any(), any())
        } returns expectedResult

        val result = repository.getTracking(anyString())

        Assert.assertEquals(expectedResult, result)
    }

    @Test
    fun `when get tracking has a 500 error with body null`() = runTest {

        val expectedResult = Response.error<TrackingModel>(500, ResponseBody.create(null, ""))

        coEvery {
            trackingApi.getTracking(any(), any(), any())
        } returns expectedResult

        val result = repository.getTracking(anyString())

        Assert.assertEquals(expectedResult, result)
    }

    @Test(expected = RuntimeException::class)
    fun `when get tracking has a exception`() = runTest {

        coEvery {
            trackingApi.getTracking(any(), any(), any())
        }.throws(RuntimeException())

        repository.getTracking(anyString())
    }
}