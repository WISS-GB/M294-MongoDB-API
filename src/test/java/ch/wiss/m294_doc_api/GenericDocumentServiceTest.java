package ch.wiss.m294_doc_api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

@SpringBootTest
public class GenericDocumentServiceTest {

  @Autowired
  private MongoTemplate mongoTemplate;  // Mocking MongoTemplate

  @Autowired
  private GenericDocumentService service;  // Service under test



  @Test
  void testSaveAndFindById() {
    mongoTemplate.remove(new Query(), "test");
    GenericDocument document = new GenericDocument();
    document.setId("123");
    Map<String, Object> content = new HashMap<>();
    content.put("field1", "value1");
    content.put("field2", "value2");
    document.setContent(content);

    GenericDocument created = service.save("test", document);
    GenericDocument found = service.findById("test", created.getId()).get();
    assert found != null;
    assertNotNull(found.getId());
    content = found.getContent();
    assert content.get("field1").equals("value1");
    assert content.get("field2").equals("value2");
  }

  @Test
  void testCreateFindDeleteById() {
    mongoTemplate.remove(new Query(), "test");
    GenericDocument document = new GenericDocument();
    Map<String, Object> content = new HashMap<>();
    content.put("field1", "value1");
    content.put("field2", "value2");
    document.setContent(content);

    GenericDocument created = service.save("test", document);
    assertNotNull(created);

    GenericDocument found = service.findById("test", created.getId()).get();
    assert found != null;
    assertNotNull(found.getId());
    content = found.getContent();
    assert content.get("field1").equals("value1");
    assert content.get("field2").equals("value2");


    int deleted = service.deleteById("test", created.getId());
    assertEquals(deleted, 1);

    assertThrows(NoSuchElementException.class,
      () -> service.findById("test", created.getId()).get()
      );

  }


  @Test
  void testCreateFindUpdateAndDeleteById() {
    mongoTemplate.remove(new Query(), "test");
    GenericDocument document = new GenericDocument();
    Map<String, Object> content = new HashMap<>();
    content.put("field1", "value1");
    content.put("field2", "value2");
    document.setContent(content);

    GenericDocument created = service.save("test", document);
    assertNotNull(created);
    content = created.getContent();
    content.put("update", "hello world");
    service.save("test", created);

    GenericDocument found = service.findById("test", created.getId()).get();
    assert found != null;
    assertNotNull(found.getId());
    content = found.getContent();
    assert content.get("field1").equals("value1");
    assert content.get("field2").equals("value2");
    assert content.get("update").equals("hello world");

    int deleted = service.deleteById("test", created.getId());
    assertEquals(deleted, 1);
  }



}
