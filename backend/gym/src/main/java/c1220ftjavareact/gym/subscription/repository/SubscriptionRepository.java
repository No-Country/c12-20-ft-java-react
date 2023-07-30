package c1220ftjavareact.gym.subscription.repository;

import c1220ftjavareact.gym.subscription.other.SubscribedSessionDTO;
import c1220ftjavareact.gym.subscription.other.SubscriptionInfoDTO;
import c1220ftjavareact.gym.subscription.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    /// MARCOS
    @Query(value = "SELECT " +
            "s.id AS id, " +
            "u.picture, " +
            "concat(u.name,' ',u.lastname) AS fullName, " +
            "u.email, a.name AS activity, " +
            "r.name AS roomName, " +
            "concat(ts.time_start,' - ',ts.time_end) AS classTime, " +
            "s.state " +
            "FROM subscription s " +
            "JOIN user u ON s.customer_id = u.id " +
            "JOIN training_session ts ON s.training_session_id = ts.id " +
            "JOIN activity a ON ts.activity_id = a.id " +
            "JOIN room r ON ts.room_id = r.id", nativeQuery = true)
    Set<SubscriptionInfoDTO> listSubscriptions();


    @Query(value = """
            SELECT
                s.id AS subscriptionId,
                p.payment_at AS paymentAt,
                p.expired_at AS expiredAt,
                s.state,
                a.name AS activity,
                a.description,
                r.name AS room,
                CONCAT(ts.time_start, ' - ', ts.time_end) AS classTime,
                ts.monday,
                ts.tuesday,
                ts.wednesday,
                ts.thursday,
                ts.friday,
                ts.saturday,
                ts.sunday
            FROM subscription s
            JOIN payment AS p ON s.id = p.subscription_id
            JOIN training_session ts ON s.training_session_id = ts.id
            JOIN activity a ON ts.activity_id = a.id
            JOIN room r ON ts.room_id = r.id
            WHERE s.customer_id = :id
            """, nativeQuery = true)
    Set<SubscribedSessionDTO> findUserSubscriptions(@Param("id") Long id);

}
