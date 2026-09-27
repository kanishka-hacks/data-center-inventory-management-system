//Frontend se Hardware Asset ka data lena aur database mein save karna, aur database se Hardware Assets nikal kar frontend ko dena
package com.dcims.backend.controller;

import com.dcims.backend.model.HardwareAsset; //Ye class basically define karti hai ki Hardware Asset ke andar kaun-kaun se fields hain
import com.dcims.backend.repository.HardwareAssetRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //frontend is controller ko HTTP requests bhej sakta hai
@RequestMapping("/api/hardware-assets")
@CrossOrigin(origins = "http://localhost:5173")
public class HardwareAssetController {

    private final HardwareAssetRepository repository;

    public HardwareAssetController(HardwareAssetRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public HardwareAsset save(@RequestBody HardwareAsset hardwareAsset) {
        return repository.save(hardwareAsset);
    }

    @GetMapping
    public List<HardwareAsset> getAll() {
        return repository.findAll();
    }
}