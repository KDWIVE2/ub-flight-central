package com.indigo.controller;

import com.indigo.dto.PassesngerDto;
import com.indigo.exception.BusinessException;
import com.indigo.service.FlightCentralService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flight-central")
public class FlightCentralController {

    private final FlightCentralService flightCentralService;

    @Autowired
    public FlightCentralController(FlightCentralService flightCentralService) {
        this.flightCentralService = flightCentralService;
    }

    @GetMapping("/passenger/{passengerId}")
    public ResponseEntity<PassesngerDto> getPassengerDetailsById(@PathVariable String passengerId) throws BusinessException {
        return ResponseEntity.ok().body(flightCentralService.getPassengerDetailsById(passengerId));
    }

    @GetMapping("/passengers")
    public ResponseEntity<List<PassesngerDto>> getAllPassengers() throws BusinessException {
        return ResponseEntity.ok().body(flightCentralService.getAllPassengers());
    }

    @PostMapping("/passenger")
    public ResponseEntity<String> createPassenger(@RequestBody @Valid PassesngerDto passengerDto) throws BusinessException {
        return ResponseEntity.ok().body(flightCentralService.createPassenger(passengerDto));
    }

    @DeleteMapping("/passenger/{passengerId}")
    public ResponseEntity<Void> deletePassenger(@PathVariable String passengerId) throws BusinessException {
        flightCentralService.deletePassenger(passengerId);
        return ResponseEntity.noContent().build();
    }

}
