package br.com.davidcastro.meurastreio.usecase

import br.com.davidcastro.data.model.TrackingModel
import br.com.davidcastro.data.repository.TrackingRepository
import br.com.davidcastro.data.usecase.GetTrackingUseCase
import br.com.davidcastro.data.usecase.GetTrackingUseCaseImpl
import com.google.gson.Gson
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import retrofit2.Response
import trackingRealResponseMock
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
internal class GetTrackingUseCaseImplTest {

    private val repository: TrackingRepository = mockk()
    private val useCase: GetTrackingUseCase = GetTrackingUseCaseImpl(repository)
    private val trackingModel = Gson().fromJson(trackingRealResponseMock, TrackingModel::class.java)

    @Test
    fun `when get tracking returns a tracking`() = runTest{
        val expectedResult = trackingModel

        coEvery {
            repository.getTracking(any())
        } returns Response.success(trackingModel)

        val result = useCase.getTracking("")

        assertEquals(expectedResult, result)
    }

}