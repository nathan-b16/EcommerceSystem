package com.Order.Service;

import com.Order.Customer.CustomerClient;
import com.Order.DTO.OrderRequest;
import com.Order.Exception.OrderException;
import com.Order.Orderline.OrderLineRequest;
import com.Order.Orderline.OrderLineService;
import com.Order.Payment.PaymentClient;
import com.Order.Payment.PaymentRequest;
import com.Order.Product.PurchaseRequest;
import com.Order.Repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CustomerClient customerClient;
    private final OrderRepository repository;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;
    private final PaymentClient paymentClient;

    public Integer createOrder(OrderRequest request) {
        var customer = customerClient.findById(request.customerId())
                .orElseThrow(() -> new OrderException("can't create an order, id is wrong"));

        var order = repository.save(mapper.toOrder(request));

        for (PurchaseRequest purchaseRequest : request.products()) { /// לכל מוצר בהזמנה ניצור שורה בDB עם הכמות שהלקוח קנה
            orderLineService.saveOrderLineInDB(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }
        var paymentRequest =  new PaymentRequest(
                request.totalAmount(),
                request.paymentMethod(),
                order.getId(),
                order.getReference(),
                customer
        );
        paymentClient.createPayment(paymentRequest);

        return order.getId();
    }
}
