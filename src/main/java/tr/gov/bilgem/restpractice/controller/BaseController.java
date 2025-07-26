package tr.gov.bilgem.restpractice.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import tr.gov.bilgem.restpractice.model.User;
import tr.gov.bilgem.restpractice.service.AbstractService;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public class BaseController<T, ID> {

    public AbstractService<T, ID> entityService;

    public BaseController(AbstractService<T, ID> entityService) {
        this.entityService = entityService;
    }

    public List<T> getContent(Page<T> entityPage) {
        return entityPage.getContent();
    }

    public Page<T> getEntityPage(int page, int size, String sort, String mode) {
        return entityService.getAllPaged(page, size, sort, mode);
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

    public ResponseEntity<T> getEntityById(Long id){
        Optional<T> entity = entityService.getById((ID) id);
        if(entity.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(entity.get(),HttpStatus.OK);
    }

    public ResponseEntity<List<T>> getAllPagedEntity(int page, int size, String sort, String mode, HttpServletRequest request){
        Page<T> userPage = getEntityPage(page, size, sort, mode);
        List<T> content = getContent(userPage);
        HttpHeaders headers = new HttpHeaders();
        String baseUrl = request.getRequestURL().toString();
        int totalPages = userPage.getTotalPages();
        if (totalPages > 0) {
            headers.add(HttpHeaders.LINK, createLinkHeader(baseUrl, page, size, sort, mode, totalPages));
        }
        return new ResponseEntity<>(content, headers, HttpStatus.OK);
    }
}
