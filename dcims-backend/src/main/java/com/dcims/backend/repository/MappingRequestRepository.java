package com.dcims.backend.repository;

import com.dcims.backend.model.MappingRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface MappingRequestRepository extends JpaRepository<MappingRequest, Long> {
    List<MappingRequest> findBySsoIdOrderBySubmittedAtDesc(String ssoId);
    List<MappingRequest> findByStatusOrderBySubmittedAtDesc(String status);
    List<MappingRequest> findAllByOrderBySubmittedAtDesc();
}