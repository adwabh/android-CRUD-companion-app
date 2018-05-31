package com.parenting.attendance.domain.models;

import com.parenting.attendance.domain.repository.Cacheble;

/**
 * Created by adwait on 21/03/18.
 */

public class LoginResponse implements Cacheble {

    @Override
    public String key() {
        return KEY_TEMP;
    }
}
