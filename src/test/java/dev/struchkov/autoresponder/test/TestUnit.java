package dev.struchkov.autoresponder.test;

import dev.struchkov.autoresponder.entity.Unit;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class TestUnit extends Unit<TestUnit> {

    private String message;

    public TestUnit(Set<String> keyWords,
                    String phrase,
                    Pattern pattern,
                    Integer matchThreshold,
                    Integer priority,
                    Set<TestUnit> nextUnits,
                    String message) {
        super(keyWords, phrase, pattern, matchThreshold, priority, nextUnits);
        this.message = message;
    }

    private TestUnit(Builder builder) {
        super(builder.keyWords, builder.phrase, builder.pattern, builder.matchThreshold, builder.priority, builder.nextUnits);
        message = builder.message;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "TestUnit{" +
                "keyWords=" + keyWords +
                ", phrase='" + phrase + '\'' +
                ", pattern=" + pattern +
                ", matchThreshold=" + matchThreshold +
                ", priority=" + priority +
                ", message='" + message + '\'' +
                '}';
    }


    public static final class Builder {
        private Set<String> keyWords = new HashSet<>();
        private String phrase;
        private Pattern pattern;
        private Integer matchThreshold;
        private Integer priority;
        private Set<TestUnit> nextUnits = new HashSet<>();
        private String message;

        public Builder keyWords(Set<String> val) {
            keyWords = val;
            return this;
        }

        public Builder keyWord(String val) {
            keyWords.add(val);
            return this;
        }

        public Builder phrase(String val) {
            phrase = val;
            return this;
        }

        public Builder pattern(Pattern val) {
            pattern = val;
            return this;
        }

        public Builder matchThreshold(Integer val) {
            matchThreshold = val;
            return this;
        }

        public Builder priority(Integer val) {
            priority = val;
            return this;
        }

        public Builder nextUnits(Set<TestUnit> val) {
            nextUnits = val;
            return this;
        }

        public Builder nextUnit(TestUnit val) {
            nextUnits.add(val);
            return this;
        }

        public Builder message(String val) {
            message = val;
            return this;
        }

        public TestUnit build() {
            return new TestUnit(this);
        }
    }
}
