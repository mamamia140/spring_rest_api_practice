package tr.gov.bilgem.restpractice.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tr.gov.bilgem.restpractice.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
