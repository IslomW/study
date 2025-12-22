package com.sharom.repository;

import com.sharom.entity.ExamplePair;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@ApplicationScoped
public class ExamplePairRepository implements PanacheRepository<ExamplePair> {

    public Optional<ExamplePair> findRandomByStudentLearnedWord(Long wordId) {
        List<ExamplePair> examples = list("translation.vocabularyWord.id", wordId);
        if (examples.isEmpty()) {
            return Optional.empty();
        }
        ExamplePair randomExample = examples.get(new Random().nextInt(examples.size()));
        return Optional.of(randomExample);
    }
}
