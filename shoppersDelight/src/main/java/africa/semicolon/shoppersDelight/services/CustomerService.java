package africa.semicolon.shoppersDelight.services;

import africa.semicolon.shoppersDelight.dtos.request.CustomerRegistrationRequest;
import africa.semicolon.shoppersDelight.dtos.request.UpdateCustomerRequest;
import africa.semicolon.shoppersDelight.dtos.response.ApiResponse;
import africa.semicolon.shoppersDelight.dtos.response.CustomerRegistrationResponse;
import africa.semicolon.shoppersDelight.dtos.response.UpdateCustomerResponse;
import africa.semicolon.shoppersDelight.exception.CustomerNotFoundException;
import org.springframework.stereotype.Service;

public interface CustomerService {

    CustomerRegistrationResponse register(CustomerRegistrationRequest request);

    ApiResponse<UpdateCustomerResponse> updateCustomer(Long id, UpdateCustomerRequest request) throws CustomerNotFoundException;
}
