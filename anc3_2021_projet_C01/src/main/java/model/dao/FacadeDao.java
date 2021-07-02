package model.dao;

import model.Board;
import model.Card;
import model.Column;
import model.Mode;

public class FacadeDao {

    private CardDao dbCard;
    private BoardDao dbBoard;
    private ColumnDao dbColumn;


    public FacadeDao(){
        dbCard = new CardDao();
        dbColumn = new ColumnDao();
        dbBoard = new BoardDao();
    }

    // Board

    public void saveBoard(Board b){
        dbBoard.save(b);
    }

    public void deleteBoard(Board b){
        dbBoard.delete(b);
    }

    // Column

    public void saveColumn(Column c){
        dbColumn.save(c);
        dbColumn.update(c.getBoard());
    }


    public void deleteColumn(Column c){
        dbCard.deleteCardsColumn(c);
        dbColumn.delete(c);
        dbColumn.update(c.getBoard());
    }

    // Card

    public void saveCard(Card c){
        dbCard.save(c);
        dbCard.update(c.getColumn());
    }

    public void deleteCard(Card c){
        dbCard.delete(c);
        dbCard.update(c.getColumn());
    }

    public void updateSideCards(Card c, Mode d){
        dbCard.updateSideCards(d, c);
    }








}
