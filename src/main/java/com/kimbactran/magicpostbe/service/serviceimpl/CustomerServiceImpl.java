package com.kimbactran.magicpostbe.service.serviceimpl;

import com.kimbactran.magicpostbe.dto.CustomerDto;
import com.kimbactran.magicpostbe.dto.SignUpRequest;
import com.kimbactran.magicpostbe.entity.Customer;
import com.kimbactran.magicpostbe.entity.PostPoint;
import com.kimbactran.magicpostbe.entity.Sender;
import com.kimbactran.magicpostbe.entity.enumtype.UserRole;
import com.kimbactran.magicpostbe.exception.AppException;
import com.kimbactran.magicpostbe.repository.CustomerRepository;
import com.kimbactran.magicpostbe.repository.PostPointRepository;
import com.kimbactran.magicpostbe.repository.SenderRepository;
import com.kimbactran.magicpostbe.service.CustomerService;
import com.kimbactran.magicpostbe.utils.AuthenticationFunction;
import com.kimbactran.magicpostbe.utils.GeneratePassword;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private CustomerRepository customerRepository;
    private PostPointRepository postPointRepository;
    private GeneratePassword generatePassword;
    private AuthenticationFunction authenticationFunction;
    private SenderRepository senderRepository;

    public Customer createAccountCustomer(CustomerDto customerDto) {
        PostPoint postPoint = postPointRepository.findByPointCode(customerDto.getPointCode()).orElseThrow(() -> new AppException("No post point found"));
        if(customerRepository.existsByEmail(customerDto.getEmail())) {
            throw new AppException("Email already exists");
        }

        if(customerRepository.existsByPhone(customerDto.getPhone())) {
            throw new AppException("Phone number already exists");
        }

        Customer customer = new Customer();
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setEmail(customerDto.getEmail());
        customer.setPhone(customerDto.getPhone());
        customer.setCustomerCountry(customerDto.getCustomerCountry());
        customer.setCustomerProvince(customerDto.getCustomerProvince());
        customer.setCustomerDistrict(customerDto.getCustomerDistrict());
        customer.setCustomerCommune(customerDto.getCustomerCommune());
        customer.setCustomerDetailAddress(customerDto.getCustomerDetailAddress());
        customer.setPointCode(postPoint.getPointCode());

        Sender sender = new Sender();
        sender.setFirstName(customerDto.getFirstName());
        sender.setLastName(customerDto.getLastName());
        sender.setPhone(customerDto.getPhone());
        sender.setSenderCountry(customerDto.getCustomerCountry());
        sender.setSenderProvince(customerDto.getCustomerProvince());
        sender.setSenderDistrict(customerDto.getCustomerDistrict());
        sender.setSenderCommune(customerDto.getCustomerCommune());
        sender.setSenderDetailAddress(customerDto.getCustomerDetailAddress());
        sender.setPointCode(postPoint.getPointCode());
        senderRepository.save(sender);

        SignUpRequest signUpRequest = convertCustomerToSignUpRequest(customerDto, postPoint.getId());
        authenticationFunction.createUser(signUpRequest);
        return customerRepository.save(customer);
    }

    private SignUpRequest convertCustomerToSignUpRequest(CustomerDto customerDto, Long postPointId) {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setFirstName(customerDto.getFirstName());
        signUpRequest.setLastName(customerDto.getLastName());
        signUpRequest.setEmail(customerDto.getEmail());
        signUpRequest.setPassword(generatePassword.generatePassword());
        signUpRequest.setPhone(customerDto.getPhone());
        signUpRequest.setRole(UserRole.USER);
        signUpRequest.setPostPointId(postPointId);
        return signUpRequest;
    }

    public Customer getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email).orElseThrow(() -> new AppException("Customer not found"));
    }

    public Customer getCustomerByPhoneNumber(String phone) {
        return customerRepository.findByPhone(phone).orElseThrow(() -> new AppException("Customer not found"));
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new AppException("Customer not found"));
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public List<Customer> getCustomersByPointCode(String pointCode) {
        return customerRepository.findByPointCode(pointCode);
    }

    public Customer updateCustomer(CustomerDto customerDto, Long id) {
        PostPoint postPoint = postPointRepository.findByPointCode(customerDto.getPointCode()).orElseThrow(() -> new AppException("No post point found"));
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new AppException("Customer not found"));
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setEmail(customerDto.getEmail());
        customer.setPhone(customerDto.getPhone());
        customer.setCustomerCountry(customerDto.getCustomerCountry());
        customer.setCustomerProvince(customerDto.getCustomerProvince());
        customer.setCustomerDistrict(customerDto.getCustomerDistrict());
        customer.setCustomerCommune(customerDto.getCustomerCommune());
        customer.setCustomerDetailAddress(customerDto.getCustomerDetailAddress());
        customer.setPointCode(postPoint.getPointCode());
        SignUpRequest signUpRequest = convertCustomerToSignUpRequest(customerDto, postPoint.getId());
        authenticationFunction.updateUserInformation(signUpRequest, customer.getAccountId());
        return customerRepository.save(customer);
    }

    public void deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new AppException("Customer not found"));
        customerRepository.deleteById(id);
    }
}
