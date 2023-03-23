package br.com.davidcastro.meurastreio.usecase

import br.com.davidcastro.data.model.TrackingModel
import br.com.davidcastro.data.repository.TrackingRepository
import br.com.davidcastro.data.usecase.remote.GetTrackingUseCase
import br.com.davidcastro.data.usecase.remote.GetTrackingUseCaseImpl
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import retrofit2.Response
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
internal class GetTrackingUseCaseImplTest {

    private val repository: TrackingRepository = mockk()
    private val useCase: GetTrackingUseCase = GetTrackingUseCaseImpl(repository)


    @Test
    fun `when get tracking returns a tracking`() = runTest {
        val expectedResult = getResponse().body()

        coEvery {
            repository.getTracking(any())
        } returns Response.success(getResponse().body())

        val result = useCase.getTracking("")

        assertEquals(expectedResult, result)
    }

    @Test
    fun `when get tracking returns a null`() = runTest {
        val expectedResult = null

        coEvery {
            repository.getTracking(any())
        } returns Response.success(null)

        val result = useCase.getTracking("")

        assertEquals(expectedResult, result)
    }

    private fun getResponse(): Response<TrackingModel> {
        return Response.success(TrackingResponseRealMock.getTrackingModelResponse())
    }
}