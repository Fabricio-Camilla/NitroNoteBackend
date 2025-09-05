package ar.edu.unq.spring.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/vehiculos")
public class VehiculoControllerREST {

    @GetMapping()
    public List<String> getAllVehiculos(){
        return new ArrayList<String>();
    }

}
