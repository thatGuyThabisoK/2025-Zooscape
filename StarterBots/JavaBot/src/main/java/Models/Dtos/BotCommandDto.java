package Models.Dtos;

import Enums.BotAction;

public class BotCommandDto {
    private int Action;

    public BotCommandDto() {}

    public BotCommandDto(BotAction action) {
        this.Action = action.getValue();
    }

    public int getAction() {
        return Action;
    }

    public void setAction(int action) {
        this.Action = action;
    }
}
