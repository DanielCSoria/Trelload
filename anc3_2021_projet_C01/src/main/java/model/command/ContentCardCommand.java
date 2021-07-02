package model.command;

import model.Card;

import java.sql.SQLException;

public class ContentCardCommand implements Command {

    private Card card;
    private String oldContent;
    private String newContent;

    public ContentCardCommand(Card card, String oldContent) {
        this.card = card;
        this.oldContent = oldContent;
        this.newContent = card.getContentProperty().getValue();
    }

    @Override
    public void undo() {
        card.setContent(oldContent);
    }

    @Override
    public void redo(){
        card.setContent(newContent);
    }

    @Override
    public String getName() {
        return "edit " + card.getContentProperty().getValue();
    }
}
