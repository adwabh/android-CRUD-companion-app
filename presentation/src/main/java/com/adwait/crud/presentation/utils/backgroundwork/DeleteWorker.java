package com.adwait.crud.presentation.utils.backgroundwork;

import android.support.annotation.NonNull;

import com.adwait.crud.data.net.RestApi;
import com.adwait.crud.data.net.RestApiImpl;
import com.adwait.crud.presentation.utils.Constant;

import androidx.work.Worker;

public class DeleteWorker extends Worker {

    private RestApi restApi;

    public DeleteWorker() {
        restApi = new RestApiImpl(getApplicationContext());
    }

    @NonNull
    @Override
    public WorkerResult doWork() {
        try {
            int position = getInputData().getInt(Constant.DELETE_ID,-1);
            if (position != -1) {
                restApi.deleteSampleUserSync(RestApi.ParamsBuilder.buildDeleteWorkerParams(position));
            return WorkerResult.SUCCESS;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return WorkerResult.FAILURE;
        }
        return WorkerResult.FAILURE;
    }

}
