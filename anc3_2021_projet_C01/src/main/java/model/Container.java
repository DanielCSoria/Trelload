package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Collections;

public abstract class Container<E> {
    private ObservableList<E> children = FXCollections.observableArrayList();

    public ObservableList<E> getChildren() {
        return FXCollections.unmodifiableObservableList(children);
    }


    protected void moveTo(Mode mode, E child) {
        int idx = this.children.indexOf(child);
        if (idx < 0)
            throw new RuntimeException("parent doesn't own this child");
        if (mode == Mode.NEXT)
            moveToNext(idx);
        else
            moveToPrevious(idx);

    }

    private void moveToNext(int idx) {
        if (idx == this.children.size() - 1)
            throw new RuntimeException("Can't perform next move");
        Collections.swap(children, idx, idx + 1);
    }

    private void moveToPrevious(int idx) {
        if (idx == 0)
            throw new RuntimeException("Can't perform previous move");
        Collections.swap(children, idx, idx - 1);
    }

    public void add(E child) {
        children.add(child);
    }

    public void set(int index, E child) {
        children.set(index, child);
    }


    public void add(int pos, E child) {
        if (pos < 0 && pos > children.size() - 1)
            throw new RuntimeException("Position incorrect");
        children.add(pos, child);
    }

    public void delete(E child) {
        children.remove(child);
    }

    // here we need to give BooleanProperty instead of boolean
    public boolean canChildMovePrevious(E child) {
        return this.children.indexOf(child) > 0;
    }

    public boolean canChildMoveNext(E child) {
        int idx = this.children.indexOf(child);
        return idx > -1 && idx < this.children.size() - 1;
    }


}
