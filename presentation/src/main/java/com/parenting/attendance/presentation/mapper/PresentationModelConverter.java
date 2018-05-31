package com.parenting.attendance.presentation.mapper;

import com.parenting.attendance.domain.models.CryptoList;
import com.parenting.attendance.domain.models.LTC;
import com.parenting.attendance.domain.models.LoginResponse;
import com.parenting.attendance.presentation.model.Login;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by adwait on 29/11/17.
 */

public class PresentationModelConverter {

    @Inject
    public PresentationModelConverter() {
    }


    public com.parenting.attendance.presentation.model.CryptoList convert(CryptoList res) throws Exception{
        com.parenting.attendance.presentation.model.CryptoList ret = new com.parenting.attendance.presentation.model.CryptoList(convert(res.getCoinList()));
        return ret;
    }

    private List<com.parenting.attendance.presentation.model.LTC> convert(List<LTC> res) throws Exception{
        List<com.parenting.attendance.presentation.model.LTC> ret = new ArrayList<>();
        if(res!=null){
            for(LTC coin : res){
                ret.add(convert(coin));
            }
        }
        return ret;
    }

    private com.parenting.attendance.presentation.model.LTC convert(LTC res) throws Exception{
        com.parenting.attendance.presentation.model.LTC ret = new com.parenting.attendance.presentation.model.LTC();
        ret.setAlgorithm(res.getAlgorithm());
        ret.setCoinName(res.getCoinName());
        ret.setFullName(res.getFullName());
        ret.setId(res.getId());
        ret.setProofType(res.getProofType());
        ret.setImageUrl(res.getImageUrl());
        ret.setSortOrder(res.getSortOrder());
        ret.setName(res.getName());
        ret.setUrl(res.getUrl());
        return ret;
    }

    public Login convert(LoginResponse loginResponse) {
        Login login =  new Login();
        return login;
    }
}
