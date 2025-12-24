package com.sharom.repository;

import com.sharom.entity.VocabularyWordTranslation;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class VocabularyWordTranslationRepository implements PanacheRepository<VocabularyWordTranslation> {

    public Optional<String> findTranslation(Long wordId, String lang) {
        return find("""
            SELECT t.translation
            FROM VocabularyWordTranslation t
            WHERE t.vocabularyWord.id = ?1
              AND t.lang = ?2
        """, wordId, lang)
                .project(String.class)
                .firstResultOptional();
    }
}
