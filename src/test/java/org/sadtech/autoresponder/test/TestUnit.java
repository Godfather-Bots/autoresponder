package org.sadtech.autoresponder.test;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Singular;
import lombok.ToString;
import org.sadtech.autoresponder.entity.Unit;

import java.util.Set;
import java.util.regex.Pattern;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString
public class TestUnit extends Unit<TestUnit> {

    private String message;

    @Builder
    public TestUnit(@Singular Set<String> keyWords,
                    String phrase,
                    Pattern pattern,
                    Integer matchThreshold,
                    Integer priority,
                    @Singular Set<TestUnit> nextUnits,
                    String message) {
        super(keyWords, phrase, pattern, matchThreshold, priority, nextUnits);
        this.message = message;
    }

}
