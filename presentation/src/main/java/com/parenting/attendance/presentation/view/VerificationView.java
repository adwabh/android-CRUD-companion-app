package com.parenting.attendance.presentation.view;

import com.parenting.attendance.domain.models.VerificationResponse;

/**
 * Created by adwait on 18/03/18.
 */

public interface VerificationView extends LoadDataView{
    void onVerificationSuccess(VerificationResponse response);
}
