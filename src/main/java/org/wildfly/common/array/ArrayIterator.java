package org.wildfly.common.array;

import java.util.ListIterator;
import java.util.NoSuchElementException;

import org.wildfly.common.Assert;
import org.wildfly.common.iteration.EnumerationIterator;

/**
 * A read-only iterator over an array.
 *
 * @author <a href="mailto:david.lloyd@redhat.com">David M. Lloyd</a>
 */
public final class ArrayIterator<E> implements ListIterator<E>, EnumerationIterator<E> {
    private final E[] elements;
    private final boolean descending;
    private int idx;

    /**
     * Construct a new instance.
     *
     * @param elements the elements to iterate over
     */
    public ArrayIterator(final E[] elements) {
        this(elements, false);
    }

    /**
     * Construct a new instance.
     *
     * @param elements the elements to iterate over
     * @param descending {@code true} to iterate in descending order, {@code false} otherwise
     */
    public ArrayIterator(final E[] elements, final boolean descending) {
        this(elements, descending, descending ? elements.length : 0);
    }

    /**
     * Construct a new instance.
     *
     * @param elements the elements to iterate over
     * @param startIdx the starting index (must be within the bounds of {@code elements})
     */
    public ArrayIterator(final E[] elements, final int startIdx) {
        this(elements, false, startIdx);
    }

    /**
     * Construct a new instance.
     *
     * @param elements the elements to iterate over
     * @param descending {@code true} to iterate in descending order, {@code false} otherwise
     * @param startIdx the starting index (must be within the bounds of {@code elements})
     */
    public ArrayIterator(final E[] elements, final boolean descending, final int startIdx) {
        Assert.checkNotNullParam("elements", elements);
        Assert.checkMinimumParameter("startIdx", 0, startIdx);
        Assert.checkMaximumParameter("startIdx", elements.length, startIdx);
        this.elements = elements;
        this.descending = descending;
        this.idx = startIdx;
    }

    // ListIterator methods

    /**
     * Determine if there are more elements to iterate over in the reverse direction of this iterator.
     *
     * @return {@code true} if there are more elements, {@code false} otherwise
     */
    public boolean hasPrevious() {
        return descending ? idx < elements.length : idx > 0;
    }

    /**
     * Determine if there are more elements to iterate over in the direction of this iterator.
     *
     * @return {@code true} if there are more elements, {@code false} otherwise
     */
    public boolean hasNext() {
        return descending ? idx > 0 : idx < elements.length;
    }

    /**
     * Get the next element in the reverse direction of this iterator.
     *
     * @return the next element
     */
    public E previous() {
        if (! hasPrevious()) throw new NoSuchElementException();
        return elements[descending ? idx ++ : -- idx];
    }

    /**
     * Get the next element in the direction of this iterator.
     *
     * @return the next element
     */
    public E next() {
        if (! hasNext()) throw new NoSuchElementException();
        return elements[descending ? -- idx : idx ++];
    }

    /**
     * Get the next index in the direction of this iterator.
     *
     * @return the next index
     */
    public int nextIndex() {
        return descending ? idx - 1 : idx;
    }

    /**
     * Get the next index in the reverse direction of this iterator.
     *
     * @return the next index
     */
    public int previousIndex() {
        return descending ? idx : idx - 1;
    }

    // Unsupported stuff

    /**
     * Unsupported.
     */
    public void remove() {
        throw Assert.unsupported();
    }

    /**
     * Unsupported.
     *
     * @param e ignored
     */
    public void set(final E e) {
        throw Assert.unsupported();
    }

    /**
     * Unsupported.
     *
     * @param e ignored
     */
    public void add(final E e) {
        throw Assert.unsupported();
    }
}
