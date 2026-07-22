package com.SummerProject.Ecommerce.Customer.Repository;

import com.SummerProject.Ecommerce.Customer.Model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {
}
