package br.com.davidcastro.meurastreio.usecase

import TrackingResponseRealMock
import br.com.davidcastro.data.model.TrackingModel
import br.com.davidcastro.data.repository.TrackingRepository
import br.com.davidcastro.data.usecase.remote.GetTrackingUseCase
import br.com.davidcastro.data.usecase.remote.GetTrackingUseCaseImpl
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import retrofit2.Response
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@OptIn(ExperimentalCoroutinesApi::class)
internal class GetTrackingUseCaseImplTest {

    private val repository: TrackingRepository = mockk()
    private val useCase: GetTrackingUseCase = GetTrackingUseCaseImpl(repository)


    @Test
    fun `getTracking returns a tracking`() = runTest {
        val expectedResult = Response.success(TrackingResponseRealMock.getTrackingModelResponse())

        coEvery {
            repository.getTracking(any())
        } returns expectedResult

        val result = useCase.getTracking(anyString())

        assertNotNull(result)
    }

    @Test
    fun `getTracking in usecase returns a null when repository returns a response error`() = runTest {
        val expectedResult = Response.error<TrackingModel>(500, ResponseBody.create(null, ""))

        coEvery {
            repository.getTracking(any())
        } returns expectedResult

        val result = useCase.getTracking(anyString())

        assertNull(result)
    }
}