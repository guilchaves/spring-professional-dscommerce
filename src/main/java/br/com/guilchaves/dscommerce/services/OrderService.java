package br.com.guilchaves.dscommerce.services;

import br.com.guilchaves.dscommerce.dto.OrderDTO;
import br.com.guilchaves.dscommerce.entities.Order;
import br.com.guilchaves.dscommerce.repository.OrderRepository;
import br.com.guilchaves.dscommerce.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Transactional(readOnly = true)
    public OrderDTO findById(Long id) {
        Order order = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Resource not found")
        );
        return new OrderDTO(order);
    }

}
