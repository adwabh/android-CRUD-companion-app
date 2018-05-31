package com.parenting.attendance.domain.models;

import com.parenting.attendance.domain.repository.Cacheble;

/**
 * Created by adwait on 17/03/18.
 */

public class VerificationResponse implements Cacheble {


    @Override
    public String key() {
        return KEY_TEMP;
    }
}
