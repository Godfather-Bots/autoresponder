package org.sadtech.autoresponder.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.sadtech.autoresponder.util.Description;

import java.util.Optional;
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
public abstract class Unit {

    @Description("Ключевые слова")
    protected Set<String> keyWords;

    @Description("Регулярное выражение")
    protected Pattern pattern;

    @Description("Значение минимального отношения количества найденых ключевых слов, к количеству ключевых слов Unit-а")
    protected Integer matchThreshold;

    @Description("Значение приоритета")
    protected Integer priority;

    @Description("Множество следующих Unit в сценарии")
    protected Set<Unit> nextUnits;

    protected Unit(Set<String> keyWords, Pattern pattern, Integer matchThreshold, Integer priority, Set<Unit> nextUnits) {
        this.keyWords = keyWords;
        this.pattern = pattern;
        this.matchThreshold = Optional.ofNullable(matchThreshold).orElse(10);
        this.priority = Optional.ofNullable(priority).orElse(10);
        this.nextUnits = nextUnits;
    }
}
