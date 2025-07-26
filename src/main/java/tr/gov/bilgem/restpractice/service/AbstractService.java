package tr.gov.bilgem.restpractice.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import tr.gov.bilgem.restpractice.repository.AbstractRepository;
import org.apache.commons.logging.Log;

import java.util.Optional;

public abstract class AbstractService<T, ID> {

    public final AbstractRepository<T, ID> repository;

    public AbstractService(AbstractRepository<T, ID> repository) {
        this.repository = repository;
    }

    public Page<T> getAllPaged(int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return repository.findAll(pageable);
    }

    public Optional<T> getById(ID id) {
        return repository.findById(id);
    }

    public Page<T> search(String query, int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return repository.search(query, pageable);
    }

    public void deleteById(ID id) throws EntityNotFoundException {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Entity with id " + id + " not found");
        }
        deleteEntity(id);
    }

    public void deleteEntity(ID id){
        Log logger = getServiceLoggerByEntity();
        repository.deleteById(id);
        if(logger.isDebugEnabled()){
            logger.debug("Deleted entity with id: " + id);
        }
    }

    public abstract Log getServiceLoggerByEntity();

}
