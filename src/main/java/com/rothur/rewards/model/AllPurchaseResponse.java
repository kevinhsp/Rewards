package com.rothur.rewards.model;

import com.rothur.rewards.entity.PurchaseEntity;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Builder
public class AllPurchaseResponse {
    Long allPoints;
    Set<PurchaseEntity> purchaseEntityList;
}
