package utils;

import java.util.LinkedList;
import java.util.List;

public class CarouselList<T> extends LinkedList<T> {

    private int currentIndex;

    public CarouselList() {
        super();
        currentIndex = 0;
    }

    public CarouselList(List<T> list) {
        super();
        for(T t : list)
            this.add(t);
        currentIndex = 0;
    }

    public T getNext() {
        T returned = this.get(currentIndex);
        currentIndex++;
        if(currentIndex == this.size()) currentIndex = 0;
        return returned;
    }

    public T getPrev() {
        currentIndex--;
        if(currentIndex < 0) currentIndex = this.size()-1;
        return this.get(currentIndex);
    }

    public int getCurrentIndex() {
        return this.currentIndex;
    }

    public void reset() {
        this.currentIndex = 0;
    }
}
