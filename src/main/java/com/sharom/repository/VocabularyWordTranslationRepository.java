package com.sharom.repository;

import com.sharom.entity.VocabularyWordTranslation;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class VocabularyWordTranslationRepository implements PanacheRepository<VocabularyWordTranslation> {
}
