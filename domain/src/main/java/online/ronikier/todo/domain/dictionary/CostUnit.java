package online.ronikier.todo.domain.dictionary;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import online.ronikier.todo.Messages;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum CostUnit {
    PLN(Messages.COST_UNIT_PLN),
    HOUR(Messages.COST_UNIT_HOUR),
    DAY(Messages.COST_UNIT_DAY),
    SOLDIER(Messages.COST_UNIT_SOLDIER),
    LUCK(Messages.COST_UNIT_LUCK);;

    private String label;

    public String getLabel() {
        return label;
    }

}
