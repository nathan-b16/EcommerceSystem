package com.Order.Service;

import org.mapstruct.Mapper;
import com.Order.DTO.Order;
import com.Order.DTO.OrderRequest;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "orderLines", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Order toOrder(OrderRequest request);
}
