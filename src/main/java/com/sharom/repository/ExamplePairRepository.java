package com.sharom.repository;

import com.sharom.entity.ExamplePair;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ExamplePairRepository implements PanacheRepository<ExamplePair> {
}
