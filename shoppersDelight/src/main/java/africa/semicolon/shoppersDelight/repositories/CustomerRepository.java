package africa.semicolon.shoppersDelight.repositories;

import africa.semicolon.shoppersDelight.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long>{

}
