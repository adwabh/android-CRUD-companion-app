package com.adwait.crud.data.entity.mapper;


import com.adwait.crud.data.entity.SampleUserListEntity;
import com.adwait.crud.domain.models.SampleUserListResponse;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by adwait on 29/11/17.
 */

public class DomainModelConverter {

    @Inject
    public DomainModelConverter() {
    }

    public SampleUserListResponse convert(SampleUserListEntity res) {
        SampleUserListResponse ret = new SampleUserListResponse();
        ret.setResponseCode(res.getResponseCode());
        ret.setMessage(res.getMessage());
        ret.setData(convert(res.getData()));
        return ret;
    }

    private SampleUserListResponse.SampleUserListData convert(SampleUserListEntity.SampleUserListData res) {
        SampleUserListResponse.SampleUserListData ret = new SampleUserListResponse.SampleUserListData();
        ret.setUser_list(convertSampleUserList(res.getUser_list()));
        return ret;
    }

    private ArrayList<SampleUserListResponse.SampleUser> convertSampleUserList(ArrayList<SampleUserListEntity.SampleUser> res) {
        ArrayList<SampleUserListResponse.SampleUser> ret = new ArrayList<>();
        for(SampleUserListEntity.SampleUser user:res){
            ret.add(convert(user));
        }
        return ret;
    }

    private SampleUserListResponse.SampleUser convert(SampleUserListEntity.SampleUser res) {
        SampleUserListResponse.SampleUser ret = new SampleUserListResponse.SampleUser();
        ret.setUsername(res.getUsername());
        ret.setEmail(res.getEmail());
        return ret;
    }
}
