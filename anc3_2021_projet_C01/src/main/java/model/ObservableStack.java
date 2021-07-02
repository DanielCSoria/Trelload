package model;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ObservableStack<E> {
    private final ObservableList<E> stack = FXCollections.observableArrayList();
    private final IntegerBinding sizeBinding = Bindings.size(stack);
    private final BooleanBinding isEmptyBinding = sizeBinding.greaterThan(0);
    private final BooleanProperty isEmptyProperty = new SimpleBooleanProperty();

    public ObservableStack() {
        isEmptyProperty.bind(isEmptyBinding);
    }

    public E pop() {
        if (stack.isEmpty())
            throw new IndexOutOfBoundsException();
        return stack.remove(stack.size() - 1);
    }

    public E peek() {
        if (stack.isEmpty())
            return null;
        return stack.get(stack.size() - 1);
    }

    public void push(E elem) {
        stack.add(elem);
    }

    public BooleanProperty isEmptyProperty() {
        return isEmptyProperty;
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public int size() {
        return stack.size();
    }

    public void emptyAll(){
        stack.clear();;
    }
}
