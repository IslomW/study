package com.sharom.repository;

import com.sharom.entity.Language;
import com.sharom.entity.Word;
import com.sharom.entity.WordTranslation;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class WordTranslationRepository implements PanacheRepository<WordTranslation> {

    public Optional<String> findTranslation(Long wordId, String lang) {
        return find("""
            SELECT t.translation
            FROM VocabularyWordTranslation t
            WHERE t.word.id = ?1
              AND t.lang = ?2
        """, wordId, lang)
                .project(String.class)
                .firstResultOptional();
    }

    public boolean existsByWordAndLanguage(Word word, Language language) {
        return count("word = ?1 and language = ?2", word, language) > 0;
    }

}
