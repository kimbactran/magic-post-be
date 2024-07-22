package com.kimbactran.magicpostbe.service.serviceimpl;

import com.kimbactran.magicpostbe.entity.Customer;
import com.kimbactran.magicpostbe.repository.CustomerRepository;
import com.kimbactran.magicpostbe.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private CustomerRepository customerRepository;

    public Customer crateAccount() {
        Customer customer = new Customer();
        return customerRepository.save(customer);

    }
}
