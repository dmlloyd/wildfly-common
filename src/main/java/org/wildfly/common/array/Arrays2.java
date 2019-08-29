package org.wildfly.common.array;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Useful general array manipulations beyond what {@link Arrays} provides.
 *
 * @author <a href="mailto:david.lloyd@redhat.com">David M. Lloyd</a>
 */
public final class Arrays2 {

    private Arrays2() {
    }

    /**
     * Compare two sub-regions of the given arrays.
     *
     * @param a1 the first array (must not be {@code null})
     * @param offs1 the offset into the first array
     * @param a2 the second array (must not be {@code null})
     * @param offs2 the offset into the second array
     * @param len the length to compare
     * @return {@code true} if the regions are equal, or {@code false} if the regions are not equal or a length or offset
     *  is out of bounds for an array
     */
    public static boolean equals(byte[] a1, int offs1, byte[] a2, int offs2, int len) {
        if (offs1 < 0 || offs1 + len > a1.length) return false;
        if (offs2 < 0 || offs2 + len > a2.length) return false;
        for (int i = 0; i < len; i ++) {
            if (a1[i + offs1] != a2[i + offs2]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Compare two sub-regions of the given arrays.
     *
     * @param a1 the first array (must not be {@code null})
     * @param offs1 the offset into the first array
     * @param a2 the second array (must not be {@code null})
     * @return {@code true} if the regions are equal, or {@code false} if the regions are not equal or a length or offset
     *  is out of bounds for an array
     */
    public static boolean equals(byte[] a1, int offs1, byte[] a2) {
        return equals(a1, offs1, a2, 0, a2.length);
    }

    /**
     * Compare two sub-regions of the given arrays.
     *
     * @param a1 the first array (must not be {@code null})
     * @param offs1 the offset into the first array
     * @param a2 the second array (must not be {@code null})
     * @param offs2 the offset into the second array
     * @param len the length to compare
     * @return {@code true} if the regions are equal, or {@code false} if the regions are not equal or a length or offset
     *  is out of bounds for an array
     */
    public static boolean equals(char[] a1, int offs1, char[] a2, int offs2, int len) {
        if (offs1 + len > a1.length) return false;
        if (offs2 + len > a2.length) return false;
        for (int i = 0; i < len; i ++) {
            if (a1[i + offs1] != a2[i + offs2]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Compare two sub-regions of the given arrays.
     *
     * @param a1 the first array (must not be {@code null})
     * @param offs1 the offset into the first array
     * @param a2 the second array (must not be {@code null})
     * @return {@code true} if the regions are equal, or {@code false} if the regions are not equal or a length or offset
     *  is out of bounds for an array
     */
    public static boolean equals(char[] a1, int offs1, char[] a2) {
        return equals(a1, offs1, a2, 0, a2.length);
    }

    /**
     * Compare two sub-regions of the given array and string.
     *
     * @param a1 the array (must not be {@code null})
     * @param offs1 the offset into the array
     * @param a2 the string (must not be {@code null})
     * @param offs2 the offset into the string
     * @param len the length to compare
     * @return {@code true} if the regions are equal, or {@code false} if the regions are not equal or a length or offset
     *  is out of bounds for the array or string
     */
    public static boolean equals(char[] a1, int offs1, String a2, int offs2, int len) {
        if (offs1 + len > a1.length) return false;
        if (offs2 + len > a2.length()) return false;
        for (int i = 0; i < len; i ++) {
            if (a1[i + offs1] != a2.charAt(i + offs2)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Compare two sub-regions of the given array and string.
     *
     * @param a1 the array (must not be {@code null})
     * @param offs1 the offset into the array
     * @param a2 the string (must not be {@code null})
     * @return {@code true} if the regions are equal, or {@code false} if the regions are not equal or a length or offset
     *  is out of bounds for the array or string
     */
    public static boolean equals(char[] a1, int offs1, String a2) {
        return equals(a1, offs1, a2, 0, a2.length());
    }

    /**
     * Compare two sub-regions of the given array and string.
     *
     * @param a1 the string (must not be {@code null})
     * @param offs1 the offset into the string
     * @param a2 the array (must not be {@code null})
     * @return {@code true} if the regions are equal, or {@code false} if the regions are not equal or a length or offset
     *  is out of bounds for the array or string
     */
    public static boolean equals(String a1, int offs1, char[] a2) {
        return equals(a2, 0, a1, offs1, a2.length);
    }

    /**
     * Compare the given array and string.
     *
     * @param a1 the string (must not be {@code null})
     * @param a2 the array (must not be {@code null})
     * @return {@code true} if the regions are equal, or {@code false} if the regions are not equal or a length or offset
     *  is out of bounds for the array or string
     */
    public static boolean equals(String a1, char[] a2) {
        return equals(a1, 0, a2);
    }

    /**
     * Return the varargs list as its array representation.
     *
     * @param items the items
     * @param <T> the item type
     * @return the array
     */
    @SafeVarargs
    public static <T> T[] of(final T... items) {
        return items;
    }

    private static char hex(int v) {
        return (char) (v < 10 ? '0' + v : 'a' + v - 10);
    }

    /**
     * Render the given byte array as a hexadecimal string in big-endian order.
     *
     * @param bytes the byte array (must not be {@code null})
     * @return the string
     */
    public static String toString(final byte[] bytes) {
        final StringBuilder b = new StringBuilder(bytes.length * 2);
        for (byte x : bytes) {
            b.append(hex((x & 0xf0) >> 4)).append(hex(x & 0x0f));
        }
        return b.toString();
    }

    /**
     * Find the first occurrence of a byte in a byte array.
     *
     * @param array the array to search
     * @param search the byte to search for
     * @param offs the offset in the array to start searching
     * @param len the length of the segment to search
     * @return the index, or -1 if the byte is not found
     */
    public static int indexOf(byte[] array, int search, int offs, int len) {
        for (int i = 0; i < len; i ++) {
            if (array[offs + i] == (byte) search) {
                return offs + i;
            }
        }
        return -1;
    }

    /**
     * Find the first occurrence of a byte in a byte array.
     *
     * @param array the array to search
     * @param search the byte to search for
     * @param offs the offset in the array to start searching
     * @return the index, or -1 if the byte is not found
     */
    public static int indexOf(byte[] array, int search, int offs) {
        return indexOf(array, search, offs, array.length - offs);
    }

    /**
     * Find the first occurrence of a byte in a byte array.
     *
     * @param array the array to search
     * @param search the byte to search for
     * @return the index, or -1 if the byte is not found
     */
    public static int indexOf(byte[] array, int search) {
        return indexOf(array, search, 0, array.length);
    }

    /**
     * Create an array of the given size, ensuring type safety.
     *
     * @param elementType the element type class
     * @param size the array size
     * @param <E> the element type
     * @return the array
     */
    @SuppressWarnings("unchecked")
    public static <E> E[] createArray(Class<E> elementType, int size) {
        return (E[]) Array.newInstance(elementType, size);
    }

    /**
     * Create a new array from the original which contains no {@code null} values, possibly <em>destroying</em> the
     * contents of the original array.  If the original contains no {@code null} values, the original array is returned.
     *
     * @param original the original array (not {@code null}, will be modified)
     * @param <E> the element type
     * @return the compacted (possibly empty) array
     */
    public static <E> E[] compactNulls(E[] original) {
        int r = 0;
        E item;
        for (;;) {
            item = original[r++];
            if (item == null) {
                break;
            }
            if (r == original.length) {
                // already null-free
                return original;
            }
        }
        // we must destroy the original array
        int w = r - 1;
        for (;;) {
            item = original[r++];
            if (item != null) {
                original[w++] = item;
            }
            if (r == original.length) {
                return Arrays.copyOf(original, w);
            }
        }
    }

    /**
     * Deeply convert an object to a string, expanding arrays as they are encountered.
     *
     * @param value the value to convert
     * @return the string
     */
    public static String objectToString(Object value) {
        if (value == null) {
            return "null";
        } else if (value instanceof Object[]) {
            return Arrays.deepToString((Object[]) value);
        } else if (value.getClass().isArray()) {
            StringBuilder sb = new StringBuilder();
            sb.append('[');
            for (int i = 0; i < Array.getLength(value); i++) {
                if (i != 0) sb.append(", ");
                sb.append(String.valueOf(Array.get(value, i)));
            }
            sb.append(']');
            return sb.toString();
        } else {
            return value.toString();
        }
    }
}
