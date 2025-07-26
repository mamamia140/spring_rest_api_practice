package tr.gov.bilgem.restpractice.audit;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tr.gov.bilgem.restpractice.controller.AbstractController;
import tr.gov.bilgem.restpractice.model.Audit;
import tr.gov.bilgem.restpractice.service.AbstractService;

@RestController
@RequestMapping("${api.root}/audits")
class AuditController extends AbstractController<Audit, Long> {

    protected AuditController(AbstractService<Audit, Long> auditService) {
        super(auditService);
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@RequestParam long time) {
        try{
            ((AuditService) entityService).delete(time);
            return new ResponseEntity<>("Deleted audit records", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Couldn't delete audit records", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
