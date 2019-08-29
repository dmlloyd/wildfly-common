package org.wildfly.common.math;

import org.wildfly.common.Assert;

/**
 * Routines which are useful for hashcode computation, among other things.
 *
 * @author <a href="mailto:david.lloyd@redhat.com">David M. Lloyd</a>
 */
public final class HashMath {

    private static final int PRESELECTED_PRIME = 1299827;

    private HashMath() {
    }

    /**
     * Round the given value up to the next positive power of two.
     *
     * @param value the value (must not be negative and must be less than or equal to {@code 2^31})
     * @return the rounded power of two value
     */
    public static int roundToPowerOfTwo(int value) {
        Assert.checkMinimumParameter("value", 0, value);
        Assert.checkMaximumParameter("value", 0x4000_0000, value);
        return value <= 1 ? value : Integer.highestOneBit(value - 1) << 1;
    }

    /**
     * A hash function which combines an accumulated hash with a next hash such that {@code f(f(k, p2, b), p1, a) ≠ₙ f(f(k, p1, a), p2, b)}.
     * This function is suitable for object chains whose order affects the overall equality of the hash code.
     * <p>
     * The exact algorithm is not specified and is therefore subject to change and should not be relied upon for hash
     * codes that persist outside of the JVM process.
     *
     * @param accumulatedHash the accumulated hash code of the previous stage
     * @param prime a prime multiplier
     * @param nextHash the hash code of the next single item
     * @return the new accumulated hash code
     */
    public static int multiHashOrdered(int accumulatedHash, int prime, int nextHash) {
        return multiplyWrap(accumulatedHash, prime) + nextHash;
    }

    /**
     * A hash function which combines an accumulated hash with a next hash such that {@code f(f(k, p2, b), p1, a) = f(f(k, p1, a), p2, b)}.
     * This function is suitable for object chains whose order does not affect the overall equality of the hash code.
     * <p>
     * The exact algorithm is not specified and is therefore subject to change and should not be relied upon for hash
     * codes that persist outside of the JVM process.
     *
     * @param accumulatedHash the accumulated hash code of the previous stage
     * @param prime a prime multiplier
     * @param nextHash the hash code of the next single item
     * @return the new accumulated hash code
     */
    public static int multiHashUnordered(int accumulatedHash, int prime, int nextHash) {
        return multiplyWrap(nextHash, prime) + accumulatedHash;
    }

    /**
     * A hash function which combines an accumulated hash with a next hash such that {@code f(f(k, b), a) ≠ₙ f(f(k, a), b)}.
     * This function is suitable for object chains whose order affects the overall equality of the hash code.
     * <p>
     * The exact algorithm is not specified and is therefore subject to change and should not be relied upon for hash
     * codes that persist outside of the JVM process.
     *
     * @param accumulatedHash the accumulated hash code of the previous stage
     * @param nextHash the hash code of the next single item
     * @return the new accumulated hash code
     */
    public static int multiHashOrdered(int accumulatedHash, int nextHash) {
        return multiHashOrdered(accumulatedHash, PRESELECTED_PRIME, nextHash);
    }

    /**
     * A hash function which combines an accumulated hash with a next hash such that {@code f(f(k, b), a) = f(f(k, a), b)}.
     * This function is suitable for object chains whose order does not affect the overall equality of the hash code.
     * <p>
     * The exact algorithm is not specified and is therefore subject to change and should not be relied upon for hash
     * codes that persist outside of the JVM process.
     *
     * @param accumulatedHash the accumulated hash code of the previous stage
     * @param nextHash the hash code of the next single item
     * @return the new accumulated hash code
     */
    public static int multiHashUnordered(int accumulatedHash, int nextHash) {
        return multiHashUnordered(accumulatedHash, PRESELECTED_PRIME, nextHash);
    }

    /**
     * Multiply two unsigned integers together.  If the result overflows a 32-bit number, XOR the overflowed bits back into the result.
     * This operation is commutative, i.e. if we designate the {@code ⨰} symbol to represent this operation, then {@code a ⨰ b = b ⨰ a}.
     * This operation is <em>not</em> associative, i.e. {@code (a ⨰ b) ⨰ c ≠ₙ a ⨰ (b ⨰ c)} (the symbol {@code ≠ₙ} meaning "not necessarily equal to"),
     * therefore this operation is suitable for ordered combinatorial hash functions.
     *
     * @param a the first number to multiply
     * @param b the second number to multiply
     * @return the wrapped multiply result
     */
    public static int multiplyWrap(int a, int b) {
        long r1 = (long) a * b;
        return (int) r1 ^ (int) (r1 >>> 32);
    }
}
