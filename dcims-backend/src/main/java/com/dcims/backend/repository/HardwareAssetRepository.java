package com.dcims.backend.repository;

import com.dcims.backend.model.HardwareAsset;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HardwareAssetRepository extends JpaRepository<HardwareAsset, Long> {
}
