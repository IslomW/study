package com.sharom.repository;

import com.sharom.entity.User;
import com.sharom.entity.UserVocabularyProgress;
import com.sharom.entity.VocabularyWord;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import java.util.List;

public class UserVocabularyProgressRepository
        implements PanacheRepository<UserVocabularyProgress> {

    public List<VocabularyWord> findLearnedWords(User user) {
        return find("user = ?1 and learned = true", user)
                .project(VocabularyWord.class)
                .list();
    }

}
