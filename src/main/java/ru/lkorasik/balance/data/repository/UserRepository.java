package ru.lkorasik.balance.data.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.lkorasik.balance.data.entity.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u JOIN u.emails e WHERE e.email = :email")
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u JOIN u.phones e WHERE e.phone = :phone")
    Optional<User> findByPhone(String phone);

    @Query("""
            SELECT DISTINCT u
            FROM User u
            JOIN u.emails e
            JOIN u.phones p
            WHERE TRUE
                AND (CAST(:dateOfBirth AS DATE) IS NOT NULL OR u.dateOfBirth > CAST(:dateOfBirth AS DATE))
                AND (:name IS NOT NULL OR u.name LIKE CONCAT(:name, '%'))
                AND (:phone IS NOT NULL OR p.phone = :phone)
                AND (:email IS NOT NULL OR e.email = :email)
            """)
    List<User> findAllFiltered(@Param("dateOfBirth") LocalDate dateOfBirth, String name, String phone, String email, Pageable pageable);

    @Modifying
    @Query(value = """
            UPDATE account
            SET balance = CASE WHEN balance * 1.1 <= initial_balance * 2.07
                THEN FLOOR(balance * 1.1 * 100) / 100
                ELSE FLOOR(initial_balance * 2.07 * 100) / 100
            END
            """, nativeQuery = true)
    void updateBalance();
}
