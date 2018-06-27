package com.adwait.crud.presentation.mapper;

import com.adwait.crud.domain.models.SampleUserListResponse;
import com.adwait.crud.presentation.model.SampleUserList;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by adwait on 29/11/17.
 */

public class PresentationModelConverter {

    @Inject
    public PresentationModelConverter() {
    }

    public SampleUserList convert(SampleUserListResponse res) {
        SampleUserList ret =  new SampleUserList();
        ret.setData(convert(res.getData()));
        ret.setMessage(res.getMessage());
        ret.setResponseCode(res.getResponseCode());
        return ret;
    }

    private SampleUserList.SampleUserListData convert(SampleUserListResponse.SampleUserListData res) {
        SampleUserList.SampleUserListData ret = new SampleUserList.SampleUserListData();
        ret.setUser_list(convertSampleUserList(res.getUser_list()));
        return ret;
    }

    private ArrayList<SampleUserList.SampleUser> convertSampleUserList(ArrayList<SampleUserListResponse.SampleUser> res) {
        ArrayList<SampleUserList.SampleUser> ret = new ArrayList<>();
        for(SampleUserListResponse.SampleUser user:res){
            ret.add(convert(user));
        }
        return ret;
    }

    private SampleUserList.SampleUser convert(SampleUserListResponse.SampleUser res) {
        SampleUserList.SampleUser ret = new SampleUserList.SampleUser();
        ret.setUsername(res.getUsername());
        ret.setEmail(res.getEmail());
        return ret;
    }

    public SampleUserListResponse.SampleUser reverseConvert(SampleUserList.SampleUser res) {
        SampleUserListResponse.SampleUser ret = new SampleUserListResponse.SampleUser();
        ret.setUsername(res.getUsername());
        ret.setEmail(res.getEmail());
        return ret;
    }
}
