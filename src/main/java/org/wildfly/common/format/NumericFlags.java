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
public final class NumericFlags extends Flags<NumericFlag, NumericFlags> {

    private NumericFlags(int bits) {
        super(bits);
    }

    NumericFlags[] values() {
        return values;
    }

    NumericFlags this_() {
        return this;
    }

    private static final NumericFlags[] values;

    static {
        final int cnt = 1 << NumericFlag.values().length;
        NumericFlags[] array = new NumericFlags[cnt];
        for (int i = 0; i < cnt; i ++) {
            array[i] = new NumericFlags(i);
        }
        values = array;
    }

    public static final NumericFlags NONE = values[0];
}
