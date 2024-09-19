package ch.wiss.m294_doc_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@Valid
@RequestMapping("/{collectionName}/documents")
public class GenericDocumentController {

    @Autowired
    private GenericDocumentService genericDocumentService;

    @PostMapping
    public ResponseEntity<Object> createDocument(@PathVariable String collectionName, @RequestBody GenericDocument document, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        if (document.getContent() == null || document.getContent().isEmpty()) {
            return ResponseEntity.badRequest().body("User error: content attribute must not be empty");
        }
        return ResponseEntity.ok(genericDocumentService.save(collectionName, document));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateDocument(@PathVariable String collectionName,@PathVariable String id, @RequestBody GenericDocument document, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        if (document.getContent()==null || document.getContent().isEmpty()) {
            return ResponseEntity.badRequest().body("User error: content attribute must not be empty");
        }
        if (id == null || id.isEmpty() || document.getId() == null) {
            throw new IllegalArgumentException("Document ID must not be provided in URL and in request body");
        }
        return ResponseEntity.ok().body(genericDocumentService.save(collectionName, document));
    }

    @GetMapping
    public ResponseEntity<List<GenericDocument>> getAllDocuments(@PathVariable String collectionName) {
        return ResponseEntity.ok().body(genericDocumentService.findAll(collectionName));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericDocument> getDocumentById(@PathVariable String collectionName, @PathVariable String id) {
        return genericDocumentService.findById(collectionName, id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDocument(@PathVariable String collectionName, @PathVariable String id) {
        int result = genericDocumentService.deleteById(collectionName, id);
        return ResponseEntity.status(result == 1 ? 200 : 404)
            .body(result == 1 ? "Document deleted" : "Document not found");
    }
}
