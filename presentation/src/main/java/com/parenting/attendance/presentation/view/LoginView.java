package com.parenting.attendance.presentation.view;

import com.parenting.attendance.presentation.model.Login;

/**
 * Created by adwait on 21/03/18.
 */

public interface LoginView extends LoadDataView {
    void onLoginDone(Login login);
}
