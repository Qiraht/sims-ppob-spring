package com.qiraht.ppob_sims_spring.repository;

import com.qiraht.ppob_sims_spring.entity.User;
import com.qiraht.ppob_sims_spring.entity.UserWallet;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserWalletRepository extends JpaRepository<UserWallet, UUID> {
    Optional<UserWallet> findByUserId(UUID userId);

    Optional<UserWallet> findByUser(User user);


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT u FROM UserWallet u WHERE u.user = :user")
    Optional<UserWallet> findByUserForUpdate(User user);
}
