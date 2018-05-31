package com.parenting.attendance.data.entity.mapper;

import com.parenting.attendance.data.entity.CryptoList;
import com.parenting.attendance.data.entity.LoginEntity;
import com.parenting.attendance.data.entity.VerificationEntity;
import com.parenting.attendance.domain.models.LTC;
import com.parenting.attendance.domain.models.LoginResponse;
import com.parenting.attendance.domain.models.VerificationResponse;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by adwait on 29/11/17.
 */

public class DomainModelConverter {

    @Inject
    public DomainModelConverter() {
    }

    public VerificationResponse convert(VerificationEntity res) throws Exception{
        VerificationResponse ret = new VerificationResponse();
        return ret;
    }

    public LoginResponse convert(LoginEntity res) throws Exception{
        LoginResponse ret = new LoginResponse();
        return ret;
    }

    public com.parenting.attendance.domain.models.CryptoList convert(CryptoList res) throws Exception{
        com.parenting.attendance.domain.models.CryptoList ret = new com.parenting.attendance.domain.models.CryptoList(convert(res.getCoinList()));
        return ret;
    }

    private List<LTC> convert(List<com.parenting.attendance.data.entity.LTC> res) {
        List<LTC> ret = new ArrayList<>();
        try {
            if(res!=null){
                for (com.parenting.attendance.data.entity.LTC coin:res) {
                    ret.add(convert(coin));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    private LTC convert(com.parenting.attendance.data.entity.LTC res) {
        LTC ret = new LTC();
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
}
