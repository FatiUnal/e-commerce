package com.example.ecommerce.repository.user.auth;

import com.example.ecommerce.entity.user.User;
import com.example.ecommerce.entity.user.auth.Otp;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OtpRepository extends CrudRepository<Otp, Integer> {

    Optional<Otp> findById(Integer id);

    List<Otp> findAllByUser(User user);

    @Query(value = "SELECT * FROM otps WHERE user_id = :userId ORDER BY created DESC LIMIT 1",nativeQuery = true)
    Optional<Otp> findTopByUserOrderByCreatedAtDesc(@Param("userId") Integer userId);
    Optional<Otp> findByOtp(String otp);

    @Query("SELECT o FROM Otp o WHERE o.user = :customer AND o.created >= :startOfDay AND o.created <= :endOfDay")
    List<Otp> findAllByUserAndCreatedToday(@Param("customer") User user,
                                           @Param("startOfDay") LocalDateTime startOfDay,
                                           @Param("endOfDay") LocalDateTime endOfDay);

}
