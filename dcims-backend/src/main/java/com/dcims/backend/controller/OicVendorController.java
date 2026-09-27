package com.dcims.backend.controller;

import com.dcims.backend.model.OicVendor;
import com.dcims.backend.repository.OicVendorRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/oic-vendor")
@CrossOrigin(origins = "http://localhost:5173")
public class OicVendorController {
    private final OicVendorRepository repository;

    public OicVendorController(OicVendorRepository repository){
        this.repository = repository;
    }
    @PostMapping
    public OicVendor save(@RequestBody OicVendor oicVendor){
        return repository.save(oicVendor);
    }

    @GetMapping
    public List<OicVendor> getAll(){
        return repository.findAll();
    }
}

