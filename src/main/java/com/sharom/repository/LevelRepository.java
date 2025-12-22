package com.sharom.repository;

import com.sharom.entity.Level;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class LevelRepository implements PanacheRepository<Level> {

    public Optional<Level> findByLevel(Integer level) {
        return find("level", level).firstResultOptional();
    }
}
