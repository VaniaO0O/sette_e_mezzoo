package Mazzo;

import java.util.NoSuchElementException;

public class MazzoFinito extends NoSuchElementException {
    public MazzoFinito(String message) {
        super(message);
    }
}
