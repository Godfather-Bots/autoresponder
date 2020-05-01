package org.sadtech.autoresponder.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.sadtech.autoresponder.util.Description;

import java.util.Set;
import java.util.regex.Pattern;

/**
 * Абстрактная сущность, отвечающая за хранение данных, необходимая для обработки запроса.
 *
 * @author upagge [07/07/2019]
 */
@Getter
@EqualsAndHashCode
@ToString
@Setter
public abstract class Unit<U extends Unit> {

    @Description("Ключевые слова")
    protected Set<String> keyWords;

    @Description("Точная фраза")
    protected String phrase;

    @Description("Регулярное выражение")
    protected Pattern pattern;

    @Description("Значение минимального отношения количества найденых ключевых слов, к количеству ключевых слов Unit-а")
    protected Integer matchThreshold;

    @Description("Значение приоритета")
    protected Integer priority;

    @Description("Множество следующих Unit в сценарии")
    protected Set<U> nextUnits;

    protected Unit(Set<String> keyWords,
                   String phrase,
                   Pattern pattern,
                   Integer matchThreshold,
                   Integer priority,
                   Set<U> nextUnits) {
        this.keyWords = keyWords;
        this.phrase = phrase;
        this.pattern = pattern;
        this.matchThreshold = matchThreshold == null ? 10 : matchThreshold;
        this.priority = priority == null ? 10 : matchThreshold;
        this.nextUnits = nextUnits;
    }

}
