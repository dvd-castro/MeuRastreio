package br.com.davidcastro.meurastreio.helpers.utils

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.lang.Exception

class UpdateWorker(appContext: Context, workerParams: WorkerParameters):
    Worker(appContext, workerParams) {

    override fun doWork(): Result {
        return try{
            Log.d("###", "dasdas")
            Result.success()
        } catch (ex: Exception){
            ex.message?.let { Log.d("###", it) }
            Result.retry()
        }
    }
}