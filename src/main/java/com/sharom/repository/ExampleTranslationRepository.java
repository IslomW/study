package com.sharom.repository;

import com.sharom.entity.ExampleTranslation;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ExampleTranslationRepository implements PanacheRepository<ExampleTranslation> {
}
