package com.kimbactran.magicpostbe.service.serviceimpl;

import com.kimbactran.magicpostbe.dto.OderRequest;
import com.kimbactran.magicpostbe.entity.*;
import com.kimbactran.magicpostbe.exception.AppException;
import com.kimbactran.magicpostbe.repository.*;
import com.kimbactran.magicpostbe.service.OrderService;
import com.kimbactran.magicpostbe.utils.FindPostPointByAddress;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final ReceiverRepository receiverRepository;
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final SenderRepository senderRepository;
    private final UserRepository userRepository;
    private final PostPointRepository postPointRepository;
    private final FindPostPointByAddress findPostPointByAddress;

    public OrderInfo createOrder(OderRequest orderRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User currentUser = userRepository.findByEmail(email).orElse(null);
        OrderInfo orderInfo = new OrderInfo();
        Customer customer = customerRepository.findById(orderRequest.getOrderCustomerId()).orElseThrow(() -> new AppException("Customer not found"));
        assert currentUser != null;
        orderInfo.setCreateUser(currentUser.getId());
        orderInfo.setOrderCustomerId(customer.getId());
        PostPoint currentPostPoint = postPointRepository.findById(currentUser.getPostPointId()).orElseThrow(() -> new AppException("PostPoint not found"));
        // Get Sender with Customer Id
        Sender sender = senderRepository.findByPhone(customer.getPhone()).orElseThrow(() -> new AppException("Sender not found"));
        orderInfo.setUserSendId(sender.getSenderId());

        if(receiverRepository.existsByPhone(orderRequest.getPhoneReceiver())){
            Receiver receiver = receiverRepository.findByPhone(orderRequest.getPhoneReceiver());
            orderInfo.setUserReceiverId(receiver.getReceiverId());
        } else {
            Receiver receiver = new Receiver();
            receiver.setPhone(orderRequest.getPhoneReceiver());
            receiver.setFirstName(orderRequest.getFirstNameReceiver());
            receiver.setLastName(orderRequest.getLastNameReceiver());
            receiver.setReceiverCountry(orderRequest.getCountryReceiver());
            receiver.setReceiverProvince(orderRequest.getProvinceReceiver());
            receiver.setReceiverDistrict(orderRequest.getDistrictReceiver());
            receiver.setReceiverCommune(orderRequest.getCommuneReceiver());
            receiver.setReceiverDetailAddress(orderRequest.getDetailAddressReceiver());
            receiver.setPointCode(orderRequest.getPointCodeReceiver());
            Receiver savedReceiver = receiverRepository.save(receiver);
            orderInfo.setUserReceiverId(savedReceiver.getReceiverId());
        }

        orderInfo.setOrderType(orderRequest.getOrderType());
        orderInfo.setOrderValue(orderRequest.getOrderValue());
        orderInfo.setAdditionalService(orderRequest.getAdditionalService());
        orderInfo.setOrderStatus(OrderStatus.ORDER_00);
        orderInfo.setOrderWeight(orderRequest.getOrderWeight());
        orderInfo.setUserPayment(orderRequest.getUserPayment());
        orderInfo.setOrderDescription(orderRequest.getOrderDescription());
        orderInfo.setMainCharge(orderRequest.getMainCharge());
        orderInfo.setSurCharge(orderRequest.getSurCharge());
        orderInfo.setTotalCharge(orderRequest.getTotalCharge());
        orderInfo.setCurrentPoint(currentPostPoint.getId());
        orderInfo.setTransactionPointSender(currentPostPoint.getId());
        orderInfo.setGatherPointSender(currentPostPoint.getPointGatherId());
        PostPoint senderPostPoint = findPostPointByAddress.getPostPointByAddress(orderRequest.getProvinceReceiver(), orderRequest.getDistrictReceiver(), orderRequest.getCommuneReceiver());
        if(senderPostPoint != null){
            orderInfo.setTransactionPointSender(senderPostPoint.getId());
            orderInfo.setGatherPointSender(senderPostPoint.getPointGatherId());
        }
        orderInfo.setStatusPointOrder(StatusPointOrder.S1);
        return orderRepository.save(orderInfo);
    }

    public int getTotalOrder(){
        return orderRepository.findAll().size();
    }

    public int getTotalOrderByOrderStatus(OrderStatus orderStatus){
        return orderRepository.countByOrderStatus(orderStatus);
    }

    public List<OrderInfo> getTotalOrderByOrderPointId(Long postPointId){
        return orderRepository.findByCurrentPoint(postPointId);
    }

    public List<OrderInfo> getAllOrder() {
        return orderRepository.findAll();
    }
}
