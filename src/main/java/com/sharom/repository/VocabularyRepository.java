package com.sharom.repository;

import com.sharom.entity.Level;
import com.sharom.entity.PosType;
import com.sharom.entity.VocabularyWord;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}
