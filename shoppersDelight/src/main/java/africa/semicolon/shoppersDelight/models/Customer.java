package africa.semicolon.shoppersDelight.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Setter
@Getter
public class Customer {
   @Id
   @GeneratedValue(strategy = IDENTITY) //i want the data base to generate the id for me and the strategy what i want the is shoul lok like
   private Long id;
   private String email;
   private String password;
   private String address;
   private String phoneNumber;
}
