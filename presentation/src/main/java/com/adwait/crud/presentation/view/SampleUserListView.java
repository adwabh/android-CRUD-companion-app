package com.adwait.crud.presentation.view;

import com.adwait.crud.presentation.model.SampleUserList;

/**
 * Created by adwait on 21/03/18.
 */

public interface SampleUserListView extends LoadDataView {
    void onSampleUserListFetched(SampleUserList sampleUserList);
}
