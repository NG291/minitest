package com.document.documentTest.service;

import com.document.documentTest.model.Document;

import java.util.List;

public interface DocumentServiceInterface {
    List<Document> findAll();

    void save(Document document);

    Document findById(long id);

    void remove(long id);
}
