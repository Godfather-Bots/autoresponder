package org.sadtech.autoresponder.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.sadtech.autoresponder.util.Description;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Абстрактная сущность, отвечающая за хранение данных, необходимая для обработки запроса.
 *
 * @author upagge [07/07/2019]
 */
@Getter
@EqualsAndHashCode
public abstract class Unit {

    @Description("Ключевые слова")
    private Set<String> keyWords;

    @Setter
    @Description("Регулярное выражение")
    private Pattern pattern;

    @Setter
    // todo [upagge] [07/07/2019]: Придумать нормальное описание
    private Integer matchThreshold = 10;

    @Setter
    @Description("Значение приоритета")
    private Integer priority = 10;

    @Description("Множество следующих Unit в сценарии")
    private Set<Unit> nextUnits;

    public void setKeyWord(String... keyWord) {
        if (this.keyWords == null) {
            this.keyWords = new HashSet<>();
        }
        this.keyWords.addAll(Arrays.asList(keyWord));
    }

    public void setKeyWords(Set<String> keyWords) {
        if (this.keyWords == null) {
            this.keyWords = new HashSet<>();
        }
        this.keyWords.addAll(keyWords);
    }

    public void setNextUnit(Unit... unit) {
        if (nextUnits == null) {
            nextUnits = new HashSet<>();
        }
        nextUnits.addAll(Arrays.asList(unit));
    }

    public void setNextUnits(Set<Unit> nextUnits) {
        if (nextUnits == null) {
            nextUnits = new HashSet<>();
        }
        this.nextUnits.addAll(nextUnits);
    }

}
