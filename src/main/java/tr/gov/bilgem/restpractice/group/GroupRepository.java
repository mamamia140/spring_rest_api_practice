package tr.gov.bilgem.restpractice.group;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tr.gov.bilgem.restpractice.model.Group;
import tr.gov.bilgem.restpractice.repository.AbstractRepository;

@Repository
public interface GroupRepository extends AbstractRepository<Group, Long> {
    @Override
    @Query("SELECT g FROM groups g WHERE " +
            "LOWER(g.name) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
            "LOWER(g.description) LIKE LOWER(CONCAT('%', :q, '%'))")
    Page<Group> search(@Param("q") String keyword, Pageable pageable);

}