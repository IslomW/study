package com.sharom.repository;

import com.sharom.entity.Example;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@ApplicationScoped
public class ExampleRepository implements PanacheRepository<Example> {

    public Optional<Example> findRandomByStudentLearnedWord(Long wordId) {
        List<Example> examples = list("translation.vocabularyWord.id", wordId);
        if (examples.isEmpty()) {
            return Optional.empty();
        }
        Example randomExample = examples.get(new Random().nextInt(examples.size()));
        return Optional.of(randomExample);
    }
}
