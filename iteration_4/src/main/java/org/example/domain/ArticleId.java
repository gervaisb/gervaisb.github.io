package org.example.domain;

import java.util.Objects;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArticleId {
    public static ArticleId next() {
        return new ArticleId(UUID.randomUUID());
    }


    private final UUID uuid;

    private ArticleId(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArticleId articleId = (ArticleId) o;
        return uuid.equals(articleId.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    @Override
    public String toString() {
        return "A-"+ uuid;
    }
}
