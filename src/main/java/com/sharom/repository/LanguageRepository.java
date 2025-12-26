package com.sharom.repository;

import com.sharom.entity.Language;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class LanguageRepository implements PanacheRepository<Language> {

    public Optional<Language> findByCode(String code){
        return find("code", code).firstResultOptional();
    }
}
