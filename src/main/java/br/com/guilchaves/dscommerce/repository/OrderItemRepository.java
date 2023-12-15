package br.com.guilchaves.dscommerce.repository;

import br.com.guilchaves.dscommerce.entities.OrderItem;
import br.com.guilchaves.dscommerce.entities.OrderItemPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK> {
}
