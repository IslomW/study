package com.sharom.repository;

import com.sharom.entity.Level;
import com.sharom.enums.PosType;
import com.sharom.entity.VocabularyWord;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
public class VocabularyRepository implements PanacheRepository<VocabularyWord> {

    public List<VocabularyWord> findFiltered(
            Level level,
            PosType posType,
            int page,
            int size,
            String sortBy
    ) {
        StringBuilder query = new StringBuilder("1=1");
        Map<String, Object> params = new HashMap<>();

        if (level != null) {
            query.append(" and level = :level");
            params.put("level", level);
        }

        if (posType != null) {
            query.append(" and posType = :posType");
            params.put("posType", posType);
        }

        Sort sort = (sortBy != null && !sortBy.isBlank())
                ? Sort.by(sortBy)
                : Sort.by("id");

        return find(query.toString(), sort, params)
                .page(Page.of(page, size))
                .list();
    }

    public long countByLevel(Level level) {
        return count("level", level);
    }

    public VocabularyWord findRandomByLevel(Level level, int index) {
        return find("level", level)
                .page(Page.of(index, 1))
                .firstResult();
    }

    public List<String> findRandomWordsExcluding(Long excludeWordId, int count) {
        List<VocabularyWord> words = list("id != ?1", excludeWordId);
        return words.stream()
                .map(VocabularyWord::getWord)
                .sorted((a, b) -> (int) (Math.random() * 1000)) // перемешиваем
                .limit(count)
                .collect(Collectors.toList());
    }

    public List<String> findRandomTranslationsExcluding(
            Long excludeWordId,
            String lang,
            int limit
    ) {
        return getEntityManager()
                .createQuery("""
                SELECT t.translation
                FROM VocabularyWordTranslation t
                WHERE t.vocabularyWord.id <> :wordId
                  AND t.lang = :lang
                ORDER BY RANDOM()
                """, String.class)
                .setParameter("wordId", excludeWordId)
                .setParameter("lang", lang)
                .setMaxResults(limit)
                .getResultList();
    }


    public List<VocabularyWord> findLearnedWords(Long userId) {
        return list(
                "SELECT w FROM VocabularyWord w JOIN UserLearnedWord ulw ON ulw.word = w " +
                        "WHERE ulw.user.id = ?1 AND ulw.learned = true",
                userId
        );
    }


}
