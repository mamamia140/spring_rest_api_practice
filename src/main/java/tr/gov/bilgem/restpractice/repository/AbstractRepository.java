package tr.gov.bilgem.restpractice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface AbstractRepository<T,ID> extends JpaRepository<T, ID> {

    Page<T> search(String keyword, Pageable pageable);
}
