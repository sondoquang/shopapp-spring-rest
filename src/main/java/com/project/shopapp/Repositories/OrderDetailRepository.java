package com.project.shopapp.Repositories;

import com.project.shopapp.models.OrderDetail;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderDetailRepository extends CrudRepository<OrderDetail, Long> {

    List<OrderDetail> findByOrderId(Long orderId);

}
