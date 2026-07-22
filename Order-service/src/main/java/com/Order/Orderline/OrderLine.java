package com.Order.Orderline;

import com.Order.DTO.Order;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrderLine {
        @Id
        @GeneratedValue
        private Integer id;
        @ManyToOne
        @JoinColumn(name = "order_id")
        private Order order;
        private Integer productId;
        private double quantity;

}
