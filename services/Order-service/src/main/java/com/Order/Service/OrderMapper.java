package com.Order.Service;

import org.mapstruct.Mapper;
import com.Order.DTO.Order;
import com.Order.DTO.OrderRequest;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Service;
//@Service
//
//public class OrderMapper {
//
//    public Order toOrder(OrderRequest request) {
//        return Order.builder()
//                .Id(request.Id())
//                .customerId(request.customerId())
//                .reference(request.reference())
//                .totalAmount(request.totalAmount())
//                .paymentMethod(request.paymentMethod())
//                .build();
//    }
//}
@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "orderLines", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Order toOrder(OrderRequest request);
}
