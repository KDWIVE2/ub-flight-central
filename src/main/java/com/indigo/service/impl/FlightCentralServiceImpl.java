package com.indigo.service.impl;

import com.indigo.dto.PassesngerDto;
import com.indigo.entity.Passenger;
import com.indigo.exception.BusinessException;
import com.indigo.repository.FlightCentralRepository;
import com.indigo.service.FlightCentralService;
import com.indigo.util.MapperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightCentralServiceImpl implements FlightCentralService {
    private static final Logger log = LoggerFactory.getLogger(FlightCentralServiceImpl.class);

    private final FlightCentralRepository flightCentralRepository;

    @Autowired
    public FlightCentralServiceImpl(FlightCentralRepository flightCentralRepository) {
        this.flightCentralRepository = flightCentralRepository;
    }

    @Cacheable(value = "passenger", key = "#passengerId") // Cache individual passenger details by ID
    @Override
    public PassesngerDto getPassengerDetailsById(String passengerId) throws BusinessException {
        try {
            Passenger passenger = flightCentralRepository.findById(passengerId)
                    .orElseThrow(() -> new BusinessException("Passenger not found with id: " + passengerId, "PASSENGER_NOT_FOUND", HttpStatus.NOT_FOUND));
            return MapperUtil.getMapper().convertValue(passenger, PassesngerDto.class);
        } catch (Exception e) {
            log.info("Error fetching passenger details for ID {}: {}", passengerId, e.getMessage());
            throw new BusinessException("Something went wrong while fetching passenger details: " + e.getLocalizedMessage(), "INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Cacheable(value = "passengers") // Cache the list of all passengers
    @Override
    public List<PassesngerDto> getAllPassengers() throws BusinessException {
        try {
            List<Passenger> passengers = flightCentralRepository.findAll();
            if (passengers.isEmpty()) {
                throw new BusinessException("No passengers found", "NO_PASSENGERS", HttpStatus.NOT_FOUND);
            }
            return passengers.stream().map(passenger -> MapperUtil.getMapper().convertValue(passenger, PassesngerDto.class)).collect(Collectors.toList());
        } catch (Exception e) {
            log.info("Error fetching passengers: {}", e.getMessage());
            throw new BusinessException("Something went wrong while fetching passengers: " + e.getLocalizedMessage(), "INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CacheEvict(cacheNames = "passengers", allEntries = true) // Clear all cached passengers on new addition
    @Override
    public String createPassenger(PassesngerDto passengerDto) throws BusinessException {
        try {
            Passenger passenger = MapperUtil.getMapper().convertValue(passengerDto, Passenger.class);
            flightCentralRepository.saveAndFlush(passenger);
            return "Passenger added successfully";
        } catch (Exception e) {
            log.info("Error creating passenger: {}", e.getMessage());
            throw new BusinessException("Something went wrong while creating passenger: " + e.getLocalizedMessage(), "INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Caching(evict = {
            @CacheEvict(value = "passenger", key = "#passengerId"), // Evict specific passenger cache
            @CacheEvict(value = "passengers", allEntries = true) // Clear all cached passengers
    })
    @Override
    public void deletePassenger(String passengerId) throws BusinessException {
        try {
            Passenger passenger = flightCentralRepository.findById(passengerId)
                    .orElseThrow(() -> new BusinessException("Passenger not found with id: " + passengerId, "PASSENGER_NOT_FOUND", HttpStatus.NOT_FOUND));
            flightCentralRepository.delete(passenger);
        } catch (Exception e) {
            log.info("Error deleting passenger with ID {}: {}", passengerId, e.getMessage());
            throw new BusinessException("Something went wrong while deleting passenger: " + e.getLocalizedMessage(), "INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
