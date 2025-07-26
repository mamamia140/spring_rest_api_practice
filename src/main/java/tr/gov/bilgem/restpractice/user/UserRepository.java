package tr.gov.bilgem.restpractice.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tr.gov.bilgem.restpractice.model.User;
import tr.gov.bilgem.restpractice.repository.AbstractRepository;

@Repository
public interface UserRepository extends AbstractRepository<User, Long> {
    @Override
    @Query("SELECT u FROM users u WHERE " +
            "LOWER(u.username) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
            "LOWER(u.email) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
            "LOWER(u.password) LIKE LOWER(CONCAT('%', :q, '%'))")
    Page<User> search(@Param("q") String keyword, Pageable pageable);

}
