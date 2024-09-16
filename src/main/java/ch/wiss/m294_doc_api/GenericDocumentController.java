package ch.wiss.m294_doc_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/{collectionName}/documents")
public class GenericDocumentController {

    @Autowired
    private GenericDocumentService genericDocumentService;

    @PostMapping
    public GenericDocument createDocument(@PathVariable String collectionName, @RequestBody GenericDocument document) {
        return genericDocumentService.save(collectionName, document);
    }

    @PutMapping
    public GenericDocument updateDocument(@PathVariable String collectionName, @RequestBody GenericDocument document) {
        if (document.getId() == null) {
            throw new IllegalArgumentException("Document ID must not be null");
        }
        return genericDocumentService.save(collectionName, document);
    }

    @GetMapping
    public List<GenericDocument> getAllDocuments(@PathVariable String collectionName) {
        return genericDocumentService.findAll(collectionName);
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
