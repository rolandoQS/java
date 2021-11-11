package com.example.java.mappers;


import com.example.java.Request.CreatePhoneRequest;
import com.example.java.entities.PhoneEntity;
import com.example.java.models.Phone;
import com.example.java.response.PhoneResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.List;

@Mapper(uses = {CommonMapper.class})
public interface PhoneMapper {
    PhoneMapper INSTANCE = Mappers.getMapper(PhoneMapper.class);

    Phone phoneEntityToPhone(PhoneEntity phoneEntity);
    PhoneEntity phoneToPhoneEntity(Phone phone);
    List<Phone> listPhoneEntityToListPhone(List<PhoneEntity> phoneEntities);
    List<PhoneEntity> listPhoneToListPhoneEntity(Collection<Phone> phones);
    PhoneResponse phoneToPhoneResponse(Phone phone);
    List<PhoneResponse> phoneListToListPhoneResponse(List<Phone> phones);
    Phone createPhoneRequestToPhone(CreatePhoneRequest createPhoneRequest);
    List<Phone> createPhoneRequestListToListPhone(Collection<CreatePhoneRequest> createPhoneRequestList);


}
