package com.SummerProject.Ecommerce.Customer.Service;

import com.SummerProject.Ecommerce.Customer.Exception.CustomerNotFoundException;
import com.SummerProject.Ecommerce.Customer.Model.Customer;
import com.SummerProject.Ecommerce.Customer.Model.CustomerRequest;
import com.SummerProject.Ecommerce.Customer.Model.CustomerResponse;
import com.SummerProject.Ecommerce.Customer.Repository.CustomerRepository;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper mapper;


    public List<CustomerResponse> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(mapper::fromCustomer)
                .collect(Collectors.toList());
    }

    public String createCustomer(CustomerRequest request) {
        Customer customer =  customerRepository.save(mapper.toCustomer(request));
        return customer.getId();
    }

    public void updateCustomer(CustomerRequest request) {
        Customer customer = customerRepository.findById(request.id())
                .orElseThrow(()-> new CustomerNotFoundException(
                   format("Can't update customer. Customer ID:: %s wasn't found", request.id())
                ));
        mergeCustomer(customer,request);
        customerRepository.save(customer);

    }

    private void mergeCustomer(Customer customer, CustomerRequest request) {
        if(StringUtils.isNotBlank(request.firstname())) {
            customer.setFirstname(request.firstname());
        }
        if(StringUtils.isNotBlank(request.lastname())) {
            customer.setLastname(request.lastname());
        }
        if(request.email() != null) {
            customer.setEmail(request.email());
        }
    }

    public CustomerResponse findById(String id) {
        return customerRepository.findById(id)
                .map(mapper::fromCustomer)
                .orElseThrow(()->new CustomerNotFoundException(
                        format("no Customer with the Id: %s", id)
                ));
    }

    public void deleteCustomer(String id) {
        customerRepository.deleteById(id);
    }
}
