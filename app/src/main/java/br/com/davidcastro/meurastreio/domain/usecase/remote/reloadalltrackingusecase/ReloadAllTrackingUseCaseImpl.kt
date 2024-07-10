package br.com.davidcastro.meurastreio.domain.usecase.remote.reloadalltrackingusecase

import br.com.davidcastro.meurastreio.commons.utils.extensions.getAllTrackingInProgress
import br.com.davidcastro.meurastreio.commons.utils.extensions.orZero
import br.com.davidcastro.meurastreio.domain.usecase.db.getalltrackingsindbusecase.GetAllTrackingsInDbUseCase
import br.com.davidcastro.meurastreio.domain.usecase.db.inserttrackingindbusecase.InsertTrackingInDbUseCase
import br.com.davidcastro.meurastreio.domain.usecase.remote.gettrackingusecase.GetTrackingUseCase

class ReloadAllTrackingUseCaseImpl(
    private val getTrackingUseCase: GetTrackingUseCase,
    private val getAllTrackingsInDbUseCase: GetAllTrackingsInDbUseCase,
    private val insertTrackingInDbUseCase: InsertTrackingInDbUseCase
): ReloadAllTrackingUseCase {

    override suspend fun invoke(): Boolean {
        var hasUpdate = false

        getAllTrackingsInDbUseCase().collect { list ->
            list.getAllTrackingInProgress().forEach { trackingModel ->

                getTrackingUseCase(trackingModel.code).collect { tracking ->
                    tracking.let { result ->
                        if (result.events?.count().orZero() > trackingModel.events?.count().orZero()) {
                            trackingModel.apply {
                                hasUpdated = true
                                events = result.events
                            }

                            hasUpdate = true
                            insertTrackingInDbUseCase(trackingModel)
                        }
                    }
                }
            }
        }

        return hasUpdate
    }
}