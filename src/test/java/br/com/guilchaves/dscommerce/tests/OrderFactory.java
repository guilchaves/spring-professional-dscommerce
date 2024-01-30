package br.com.guilchaves.dscommerce.tests;

import br.com.guilchaves.dscommerce.entities.Order;
import br.com.guilchaves.dscommerce.entities.OrderItem;
import br.com.guilchaves.dscommerce.entities.OrderStatus;
import br.com.guilchaves.dscommerce.entities.Payment;
import br.com.guilchaves.dscommerce.entities.Product;
import br.com.guilchaves.dscommerce.entities.User;

import java.time.Instant;

public class OrderFactory {
    public static Order createOrder(User client) {
        Order order = new Order(1L, Instant.now(), OrderStatus.WAITING_PAYMENT, client, new Payment());
        Product product = ProductFactory.createProduct();
        OrderItem orderItem = new OrderItem(order, product, 2, 10.0);
        order.getItems().add(orderItem);

        return order;
    }
}
