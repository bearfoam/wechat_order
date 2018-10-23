package com.ly.service.impl;

import com.ly.dataproject.SellerInfo;
import com.ly.repository.SellerInfoRepository;
import com.ly.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ly
 * @ 2018-10-23
 */
@Service
public class SellerServiceImpl implements SellerService {
    @Autowired
    SellerInfoRepository sellerInfoRepository;
    @Override
    public SellerInfo findSellerInfoByOpenid(String openid) {
        SellerInfo sellerInfo = sellerInfoRepository.findByOpenid(openid);
        return null;
    }
}
