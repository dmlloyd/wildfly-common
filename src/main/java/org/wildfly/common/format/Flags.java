/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2018 Red Hat, Inc., and individual contributors
 * as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wildfly.common.format;

/**
 */
abstract class Flags<E extends Enum<E>, This extends Flags<E, This>> {
    final int bits;

    Flags(final int bits) {
        this.bits = bits;
    }

    abstract This[] values();

    abstract This this_();

    static int bitOf(Enum<?> item) {
        return 1 << item.ordinal();
    }

    public final boolean contains(E flag) {
        return flag != null && (bits & bitOf(flag)) != 0;
    }

    public final boolean containsAny(This flags) {
        return flags != null && (bits & flags.bits) != 0;
    }

    public final boolean containsAny(E flag1, E flag2) {
        return contains(flag1) || contains(flag2);
    }

    public final boolean containsAny(E flag1, E flag2, E flag3) {
        return contains(flag1) || contains(flag2) || contains(flag3);
    }

    public final This complement() {
        final This[] values = values();
        return values[~bits & values.length - 1];
    }

    public final This with(E flag) {
        return flag == null ? this_() : values()[bits | bitOf(flag)];
    }

    public final This without(E flag) {
        return flag == null ? this_() : values()[bits & ~bitOf(flag)];
    }

    public final void forbidAll() {
        if (bits != 0) {
            throw notAllowed(this);
        }
    }

    public final void forbidAllBut(final E flag) {
        without(flag).forbidAll();
    }

    private static IllegalArgumentException notAllowed(final Flags<?, ?> flags) {
        return new IllegalArgumentException("Flags " + flags + " not allowed here");
    }

    public void forbid(final E flag) {
        if (contains(flag)) {
            throw notAllowed(values()[0].with(flag));
        }
    }
}
