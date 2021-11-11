package com.example.java;

import com.example.java.Request.CreatePhoneRequest;
import com.example.java.Request.CreateUserRequest;
import com.example.java.exception.ApiException;
import com.example.java.repositories.UserRepository;
import com.example.java.services.UsersService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        JavaApplication.class,
        TestProfileJPAConfig.class})
@ActiveProfiles("test")
public class UserUnitTests {

    @Autowired
    UsersService usersService;

    @Autowired
    UserRepository userRepository;




    @Test
    public void when_createUser() {
//-----------Caso de email con formato incorrecto------------------------------------------------
        CreatePhoneRequest phoneRequest1=createPhoneRequest(1,53,54430227L);
        CreatePhoneRequest phoneRequest2=createPhoneRequest(1,53,55889966L);

        Collection<CreatePhoneRequest> phoneRequestCollection = new ArrayList<>();
        phoneRequestCollection.add(phoneRequest1);
        phoneRequestCollection.add(phoneRequest2);

        CreateUserRequest userRequest = createUserRequest("Roli","roli@gmail.cl.com","Roli12",phoneRequestCollection);

        Assertions.assertThrows(ApiException.class, () -> this.usersService.create(userRequest));
//-------------------------------------------------------------------------------------------------------------

//-------------Caso de password con formato incorrecto-------------------------------------------------
        CreateUserRequest userRequest1 = createUserRequest("Roli","roli@gmail.cl","Roli2",phoneRequestCollection);
        Assertions.assertThrows(ApiException.class, () -> this.usersService.create(userRequest1));

//------------------------------------------------------------------------------------------------------------------

//-------------Caso de creacion satisfactorio-------------------------------------------------
        CreateUserRequest userRequest2 = createUserRequest("Roli","roli@gmail.cl","Roli23",phoneRequestCollection);
        this.usersService.create(userRequest2);

        final int ALL = this
                .usersService
                .getAll()
                .size();

        assertThat(ALL)
                .isEqualTo(1);

//------------------------------------------------------------------------------------------------------------------

//-------------Caso de correo ya existente-------------------------------------------------
        CreateUserRequest userRequest3 = createUserRequest("Roli","roli@gmail.cl","Roli23",phoneRequestCollection);
        Assertions.assertThrows(ApiException.class, () -> this.usersService.create(userRequest3));


//------------------------------------------------------------------------------------------------------------------

//-------------Caso de creacion satisfactorio otro usuario-------------------------------------------------
        CreateUserRequest userRequest4 = createUserRequest("Pepe","pepe@gmail.cl","Pepe56",phoneRequestCollection);
        this.usersService.create(userRequest4);

        final int ALL1 = this
                .usersService
                .getAll()
                .size();

        assertThat(ALL1)
                .isEqualTo(2);

//------------------------------------------------------------------------------------------------------------------



    }




    private CreateUserRequest createUserRequest(String name, String email, String password, Collection<CreatePhoneRequest> phoneRequests) {
        CreateUserRequest toReturn = new CreateUserRequest();
        toReturn.setName(name);
        toReturn.setEmail(email);
        toReturn.setPassword(password);
        toReturn.setPhones(phoneRequests);
        return toReturn;
    }
    private CreatePhoneRequest createPhoneRequest(Integer city, Integer contry, Long number) {
        CreatePhoneRequest toReturn = new CreatePhoneRequest();
        toReturn.setCity_code(city);
        toReturn.setContry_code(contry);
        toReturn.setNumber(number);

        return toReturn;
    }

}
