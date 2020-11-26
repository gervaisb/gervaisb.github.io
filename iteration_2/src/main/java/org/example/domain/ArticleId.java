package org.example.domain;

import java.util.Objects;
import java.util.UUID;

public class ArticleId implements Comparable<ArticleId> {

    private final String value;

    public ArticleId(UUID uuid) {
        this.value = uuid.toString();
    }

    @Override
    public int compareTo(ArticleId o) {
        UUID mine = UUID.fromString(value);
        UUID his = UUID.fromString(o.value);
        return mine.compareTo(his);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArticleId articleId = (ArticleId) o;
        return value.equals(articleId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
