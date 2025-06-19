package tr.gov.bilgem.restpractice.location;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tr.gov.bilgem.restpractice.model.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

}
