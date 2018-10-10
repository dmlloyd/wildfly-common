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
public final class GeneralFlags extends Flags<GeneralFlag, GeneralFlags> {

    private GeneralFlags(int bits) {
        super(bits);
    }

    GeneralFlags[] values() {
        return values;
    }

    GeneralFlags this_() {
        return this;
    }

    private static final GeneralFlags[] values;

    static {
        final int cnt = 1 << GeneralFlag.values().length;
        GeneralFlags[] array = new GeneralFlags[cnt];
        for (int i = 0; i < cnt; i ++) {
            array[i] = new GeneralFlags(i);
        }
        values = array;
    }

    public static final GeneralFlags NONE = values[0];
}
