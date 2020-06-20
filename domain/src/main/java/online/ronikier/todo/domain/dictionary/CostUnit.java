package online.ronikier.todo.domain.dictionary;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import online.ronikier.todo.Messages;

//TODO: Implement on front-end.
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum CostUnit {
    PLN(Messages.COST_UNIT_PLN),
    DAY(Messages.COST_UNIT_DAY),
    SOLDIER(Messages.COST_UNIT_SOLDIER);

    private String label;

    public String getLabel() {
        return label;
    }

}
