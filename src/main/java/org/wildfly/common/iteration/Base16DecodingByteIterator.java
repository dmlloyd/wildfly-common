package org.wildfly.common.iteration;

import static org.wildfly.common._private.CommonMessages.msg;

import java.util.NoSuchElementException;

/**
 */
final class Base16DecodingByteIterator extends ByteIterator {
    private final CodePointIterator iter;
    private int b;
    private long offset;
    private boolean havePair;

    Base16DecodingByteIterator(final CodePointIterator iter) {
        this.iter = iter;
    }

    private int calc(final int b0, final int b1) {
        int d0 = Character.digit(b0, 16);
        int d1 = Character.digit(b1, 16);
        if (d0 == - 1 || d1 == - 1) throw msg.invalidHexCharacter();
        return ((d0 << 4) | d1) & 0xff;
    }

    public boolean hasNext() {
        if (havePair) {
            return true;
        }
        if (! iter.hasNext()) {
            return false;
        }
        int b0 = iter.next();
        if (! iter.hasNext()) {
            throw msg.expectedEvenNumberOfHexCharacters();
        }
        int b1 = iter.next();
        b = calc(b0, b1);
        havePair = true;
        return true;
    }

    public boolean hasPrevious() {
        return offset > 0;
    }

    public int next() throws NoSuchElementException {
        if (! hasNext()) {
            throw new NoSuchElementException();
        }
        offset++;
        havePair = false;
        return b;
    }

    public int peekNext() throws NoSuchElementException {
        if (! hasNext()) {
            throw new NoSuchElementException();
        }
        return b;
    }

    public int previous() throws NoSuchElementException {
        if (! hasPrevious()) {
            throw new NoSuchElementException();
        }
        int b1 = iter.previous();
        int b0 = iter.previous();
        b = calc(b0, b1);
        offset--;
        havePair = true;
        return b;
    }

    public int peekPrevious() throws NoSuchElementException {
        if (! hasPrevious()) {
            throw new NoSuchElementException();
        }
        int b1 = iter.previous();
        int b0 = iter.peekPrevious();
        iter.next();
        return calc(b0, b1);
    }

    public long getIndex() {
        return offset;
    }
}
