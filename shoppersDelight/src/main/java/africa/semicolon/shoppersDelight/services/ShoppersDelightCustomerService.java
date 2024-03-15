package africa.semicolon.shoppersDelight.services;

import africa.semicolon.shoppersDelight.dtos.request.CustomerRegistrationRequest;
import africa.semicolon.shoppersDelight.dtos.request.UpdateCustomerRequest;
import africa.semicolon.shoppersDelight.dtos.response.ApiResponse;
import africa.semicolon.shoppersDelight.dtos.response.CustomerRegistrationResponse;
import africa.semicolon.shoppersDelight.dtos.response.UpdateCustomerResponse;
import africa.semicolon.shoppersDelight.exception.CustomerNotFoundException;
import africa.semicolon.shoppersDelight.models.Customer;
import africa.semicolon.shoppersDelight.repositories.CustomerRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.TextNode;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchOperation;
import com.github.fge.jsonpatch.ReplaceOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ShoppersDelightCustomerService implements  CustomerService{
    @Autowired
    private CustomerRepository customerRepository;
    @Override
    public CustomerRegistrationResponse register(CustomerRegistrationRequest request) {
        Customer customer = new Customer();

        customer.setEmail(request.getEmail());
        customer.setPassword(request.getPassword());

        Customer savedCustomer = customerRepository.save(customer);

        CustomerRegistrationResponse response =
                new CustomerRegistrationResponse();
        response.setId(savedCustomer.getId());
        return response;
    }

    @Override
    public ApiResponse<UpdateCustomerResponse> updateCustomer(Long id ,
                                                              UpdateCustomerRequest request)
            throws CustomerNotFoundException {
        
        Customer customer = findCustomerBy(id);
        List<JsonPatchOperation> patchOperationList = new ArrayList<>();
        buildPatchOperations(request, patchOperationList);
        customer = applyPatch(patchOperationList,customer);
        customerRepository.save(customer);
        return  new ApiResponse<>(getUpdateCustomerResponseApiResponse());
    }
    private static UpdateCustomerResponse getUpdateCustomerResponseApiResponse() {
        UpdateCustomerResponse response = new UpdateCustomerResponse();
        response.setMessage("Account updated successfully");
        return response;
    }
    private static Customer applyPatch(List<JsonPatchOperation> jsonPatchOperations, Customer customer){
        try{
            ObjectMapper mapper = new ObjectMapper();
            JsonPatch jsonPatch = new JsonPatch(jsonPatchOperations);
            //1. convert Customer to JsonNode
            JsonNode customerNode = mapper.convertValue(customer, JsonNode.class);
            //2. apply JsonPatch to customerNode
            JsonNode updatedNode = jsonPatch.apply(customerNode);
            //3. convert customerNode to Customer
            customer = mapper.convertValue(updatedNode, Customer.class);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return customer;
    }
    private static void buildPatchOperations(UpdateCustomerRequest request, List<JsonPatchOperation> patchOperationList) {
        Arrays.stream(request.getClass().getDeclaredFields())
                .filter(field -> isValidUpdate(field, request))
                .forEach(eachField -> addOperation(request,eachField, patchOperationList));
    }

    private static void addOperation(UpdateCustomerRequest request,
                              Field eachField, List<JsonPatchOperation> patchOperationList) {
        try {
            JsonPointer path = new JsonPointer("/"+eachField.getName());
            JsonNode value = new TextNode(eachField.get(request).toString());
            ReplaceOperation replaceOperation = new ReplaceOperation(path,value);
            patchOperationList.add(replaceOperation);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private static boolean isValidUpdate(Field field , UpdateCustomerRequest request) {
        field.setAccessible(true);
        try{
            return field.get(request) != null;
        }catch (IllegalAccessException exception){
            throw new RuntimeException(exception);
        }
    }
    private Customer findCustomerBy(Long id) throws CustomerNotFoundException {
        return customerRepository.findById(id)
                .orElseThrow(
                        () -> new CustomerNotFoundException(
                                String.format("Customer with id %d does not exist ", id)
                        )
                );
    }
}
