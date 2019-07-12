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
public class TestUnit extends Unit {

    private String message;

    @Builder
    public TestUnit(@Singular(value = "keyWord") Set<String> keyWords,
                    Pattern pattern,
                    Integer matchThreshold,
                    Integer priority,
                    @Singular(value = "nextUnit") Set<Unit> nextUnits,
                    String message) {
        super(keyWords, pattern, matchThreshold, priority, nextUnits);
        this.message = message;
    }
}
