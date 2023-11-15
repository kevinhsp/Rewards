package com.rothur.rewards.service;

import com.rothur.rewards.entity.PurchaseEntity;
import com.rothur.rewards.entity.UserEntity;
import com.rothur.rewards.model.Purchase;

import java.util.List;
import java.util.Set;

public interface CombineService {

    PurchaseEntity savePurchase(Purchase purchase, Long userId);
    Set<PurchaseEntity> findPurchasesByUserIdAndMonth(String month, Long userId);

    Set<PurchaseEntity> findPurchasesByUserId(Long userId);

    Long getAllPointsByUserId(Long userId);

    UserEntity saveUser(UserEntity user);

}
