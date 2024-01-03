package com.zohocrm.zoho.Repository;

import com.zohocrm.zoho.Entity.Lead;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface leadRepository extends JpaRepository<Lead, String> {

    Optional<Lead> findByEmail(String email);
    Optional<Lead> findByMobile(long mobile);
    boolean  existsByEmail(String email);
    boolean existsByMobile(long mobile);

}
