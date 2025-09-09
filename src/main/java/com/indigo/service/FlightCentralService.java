package com.indigo.service;

import com.indigo.dto.PassesngerDto;
import com.indigo.exception.BusinessException;

import java.util.List;

public interface FlightCentralService {
    PassesngerDto getPassengerDetailsById(String passengerId) throws BusinessException;

    List<PassesngerDto> getAllPassengers() throws BusinessException;

    String createPassenger(PassesngerDto passengerDto) throws BusinessException;

    void deletePassenger(String passengerId) throws BusinessException;
}
