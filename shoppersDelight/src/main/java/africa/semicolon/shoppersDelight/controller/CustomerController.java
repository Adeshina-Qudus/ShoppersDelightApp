package africa.semicolon.shoppersDelight.controller;

import africa.semicolon.shoppersDelight.dtos.request.CustomerRegistrationRequest;
import africa.semicolon.shoppersDelight.dtos.request.UpdateCustomerRequest;
import africa.semicolon.shoppersDelight.dtos.response.ApiResponse;
import africa.semicolon.shoppersDelight.dtos.response.CustomerRegistrationResponse;
import africa.semicolon.shoppersDelight.dtos.response.UpdateCustomerResponse;
import africa.semicolon.shoppersDelight.exception.CustomerNotFoundException;
import africa.semicolon.shoppersDelight.models.Customer;
import africa.semicolon.shoppersDelight.services.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerRegistrationResponse> registerCustomer(@RequestBody CustomerRegistrationRequest registrationRequest){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(customerService.register(registrationRequest));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@RequestBody UpdateCustomerRequest request, @PathVariable Long id) {
        try {
            return ResponseEntity.ok(customerService.updateCustomer(id, request));

        }catch (Exception exception){
            return ResponseEntity.badRequest().body(new ApiResponse<>("Failed"));
        }
    }
}
