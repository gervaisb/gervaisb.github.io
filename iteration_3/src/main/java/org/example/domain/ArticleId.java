package org.example.domain;

import java.util.Objects;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArticleId implements Comparable<ArticleId> {
    private static final Pattern PATTERN = Pattern.compile("A-(?<uuid>[a-z0-9]{8}-[a-z0-9]{4}-[a-z0-9]{4}-[a-z0-9]{4}-[a-z0-9]{12})");

    public static ArticleId valueOf(String string) {
        Matcher matcher = PATTERN.matcher(string);
        if ( matcher.matches() ) {
            return new ArticleId(UUID.fromString(matcher.group("uuid")));
        } else {
            throw new IllegalArgumentException("Malformed article id");
        }
    }

    public static ArticleId next() {
        return new ArticleId(UUID.randomUUID());
    }


    private final UUID uuid;

    private ArticleId(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public int compareTo(ArticleId that) {
        return this.uuid.compareTo(that.uuid);
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
