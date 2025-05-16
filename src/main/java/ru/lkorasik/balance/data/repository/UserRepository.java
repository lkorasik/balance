package ru.lkorasik.balance.data.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
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

    @Query(value = """
            SELECT DISTINCT u.*
            FROM "user" u
            LEFT JOIN email_data e ON e.user_id = u.id
            LEFT JOIN phone_data p ON p.user_id = u.id
            WHERE TRUE
                AND (COALESCE(CAST(:dateOfBirth AS DATE), NULL) IS NULL OR u.date_of_birth > CAST(:dateOfBirth AS DATE))
                AND (:name IS NULL OR u.name LIKE CONCAT(:name, '%'))
                AND (:phone IS NULL OR p.phone = :phone)
                AND (:email IS NULL OR e.email = :email)
            """, nativeQuery = true)
    List<User> findAllFiltered(LocalDate dateOfBirth, String name, String phone, String email, Pageable pageable);

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
