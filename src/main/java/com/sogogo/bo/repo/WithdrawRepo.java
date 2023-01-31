package com.sogogo.bo.repo;

import com.sogogo.bo.model.Deposit;
import com.sogogo.bo.model.Withdraw;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WithdrawRepo extends JpaRepository<Withdraw, Long> {
    Long countAllByStatus(Integer status);
    List<Withdraw> findAllByStatus(Integer status);
    Optional<Withdraw> findByIdAndStatus(Long id, Integer status);
}
