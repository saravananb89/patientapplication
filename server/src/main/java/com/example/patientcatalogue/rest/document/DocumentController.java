package com.example.patientcatalogue.rest.document;

import com.example.patientcatalogue.data.document.DocumentDTO;
import com.example.patientcatalogue.service.document.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/document")
public class DocumentController {

    private DocumentService repository;

    @Autowired
    public DocumentController(DocumentService repository) {
        this.repository = repository;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{patientId}/{patientVisitId}/{fileName}/{fileExtension}", consumes = "application/octet-stream")

    public void upload(@RequestBody byte[] payload, @PathVariable Integer patientId, @PathVariable Integer patientVisitId,
                       @PathVariable String fileName, @PathVariable String fileExtension) {

        repository.uploadDocument(patientId, patientVisitId, payload,
                fileName, fileExtension);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{patientId}/{patientVisitId}")
    public Set<DocumentDTO> getDocuments(@PathVariable Integer patientId, @PathVariable Integer patientVisitId) {
        System.out.println("i am here" + patientId + "visit" + patientVisitId);
        return repository.getDocuments(patientId, patientVisitId).stream().collect(Collectors.toSet());
    }

}

@ControllerAdvice(assignableTypes = DocumentController.class)
class DocumentControllerExceptionHandler {

    @ResponseBody
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String handleIllegalArgumentException(IllegalArgumentException e) {
        return e.getMessage();
    }
}
