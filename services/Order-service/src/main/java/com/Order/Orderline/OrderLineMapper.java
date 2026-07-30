package com.Order.Orderline;

import com.Order.DTO.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Service;

//@Service
//public class OrderLineMapper {
//
//    public OrderLine toOrderLine(OrderLineRequest request) {
//        return OrderLine.builder()
//                .id(request.id())
//                .quantity(request.quantity())
//                .orderId(
//                        Order.builder()
//                                .Id(request.orderId())
//                                .build()
//                )
//                .productId(request.productId())
//                .build();
//    }
//}

@Mapper(componentModel = "spring")
public interface OrderLineMapper {
    @Mapping(target = "order.Id", source = "orderId")
    OrderLine toOrderLine(OrderLineRequest request);
}