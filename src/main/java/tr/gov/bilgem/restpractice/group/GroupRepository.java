package tr.gov.bilgem.restpractice.group;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tr.gov.bilgem.restpractice.model.Group;
@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

}
