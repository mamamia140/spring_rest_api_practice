package tr.gov.bilgem.restpractice.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.message.StringFormattedMessage;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import tr.gov.bilgem.restpractice.audit.AuditService;
import tr.gov.bilgem.restpractice.service.AbstractService;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public abstract class AbstractController<T, ID> {

    public AbstractService<T, ID> entityService;

    public AbstractController(AbstractService<T, ID> entityService) {
        this.entityService = entityService;
    }

    public String createLinkHeader(String baseUrl, int page, int size, String sort, String mode, int totalPages) {
        StringBuilder linkHeader = new StringBuilder();

        String middle = String.format("&size=%d&sort=%s&mode=%s", size, sort, mode);

        linkHeader.append("<").append(baseUrl).append("?page=0").append(middle).append(">; rel=\"first\",");
        linkHeader.append("<").append(baseUrl).append("?page=").append(Math.max(page - 1, 0)).append(middle).append(">; rel=\"prev\",");
        linkHeader.append("<").append(baseUrl).append("?page=").append(Math.min(page + 1, totalPages - 1)).append(middle).append(">; rel=\"next\",");
        linkHeader.append("<").append(baseUrl).append("?page=").append(totalPages - 1).append(middle).append(">; rel=\"last\"");

        return linkHeader.toString();
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<T> getEntityById(@PathVariable ID id) {
        Optional<T> entity = entityService.getById(id);
        return entity
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<T>> getAllPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String mode,
            HttpServletRequest request
    ) {
        Page<T> entityPage = entityService.getAllPaged(page, size, sort, mode);
        return getPagedEntityListResponse(page, size, sort, mode, request, entityPage);
    }

    @GetMapping(path = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<T>> searchPagedEntity(
            @RequestParam(defaultValue = "") String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String mode,
            @RequestParam(required = false) Long start,
            @RequestParam(required = false) Long end,
            HttpServletRequest request
    ) {
        Page<T> entityPage;
        if (start != null && end != null) {
            entityPage = (Page<T>) ((AuditService) entityService).search(q, page, size, sort, mode, Instant.ofEpochSecond(start), Instant.ofEpochSecond(end));
        } else {
            entityPage = entityService.search(q, page, size, sort, mode);
        }

        return getPagedEntityListResponse(page, size, sort, mode, request, entityPage);
    }

    protected ResponseEntity<List<T>> getPagedEntityListResponse(int page, int size, String sort, String mode, HttpServletRequest request, Page<T> entityPage) {
        List<T> content = entityPage.getContent();

        HttpHeaders headers = new HttpHeaders();
        int totalPages = entityPage.getTotalPages();
        if (totalPages > 0) {
            String baseUrl = request.getRequestURL().toString();
            headers.add(HttpHeaders.LINK, createLinkHeader(baseUrl, page, size, sort, mode, totalPages));
        }

        return new ResponseEntity<>(content, headers, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteEntityById(@PathVariable ID id) {
        try{
            entityService.deleteById(id);
            return new ResponseEntity<>(String.format("Entity with id:%s deleted successfully", id),HttpStatus.OK);
        }
        catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(String.format("Entity couldn't be deleted. Internal server error\n\nError:%s",e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
