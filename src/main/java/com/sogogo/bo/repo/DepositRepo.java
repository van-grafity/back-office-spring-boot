package com.sogogo.bo.repo;

import com.sogogo.bo.model.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepositRepo extends JpaRepository<Deposit,Long> {
    Long countAllByStatus(Integer status);
    Optional<Deposit> findByIdAndStatus(Long id, Integer status);
    List<Deposit> findAllByStatus(Integer status);
}
