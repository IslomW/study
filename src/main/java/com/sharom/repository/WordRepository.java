package com.sharom.repository;

import com.sharom.entity.Level;
import com.sharom.entity.Word;
import com.sharom.enums.PosType;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
public class WordRepository implements PanacheRepository<Word> {

    public List<Word> findAllPaged(int page, int size) {
        return findAll()
                .page(Page.of(page, size))  // Page.of(pageIndex, pageSize)
                .list();
    }

    public long countWords() {
        return count();
    }

    public List<Word> findFiltered(
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

    public Word findRandomByLevel(Level level, int index) {
        return find("level", level)
                .page(Page.of(index, 1))
                .firstResult();
    }

    public List<String> findRandomWordsExcluding(Long excludeWordId, int count) {
        List<Word> words = list("id != ?1", excludeWordId);
        return words.stream()
                .map(Word::getText)
                .sorted((a, b) -> (int) (Math.random() * 1000)) // перемешиваем
                .limit(count)
                .collect(Collectors.toList());
    }



    public List<Word> findLearnedWords(Long userId) {
        return list(
                "SELECT w FROM Word w JOIN UserLearnedWord ulw ON ulw.word = w " +
                        "WHERE ulw.user.id = ?1 AND ulw.learned = true",
                userId
        );
    }


}
