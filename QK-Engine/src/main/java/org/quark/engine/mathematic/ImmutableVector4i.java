/*
 * This file is part of Quark Engine, licensed under the APACHE License.
 *
 * Copyright (c) 2014-2016 Agustin L. Alvarez <wolftein1@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.quark.engine.mathematic;

/**
 * Represent an immutable {@linkplain Vector4i}.
 *
 * @author Agustin L. Alvarez (wolftein1@gmail.com)
 */
public final class ImmutableVector4i extends Vector4i {
    /**
     * <p>Default constructor</p>
     */
    public ImmutableVector4i(int x, int y, int z, int w) {
        super(x, y, z, w);
    }

    /**
     * <p>Creates a new vector where each component value is 0</p>
     *
     * @return a new vector
     */
    public static ImmutableVector4i zero() {
        return new ImmutableVector4i(0, 0, 0, 0);
    }

    /**
     * <p>Creates a new vector where each component value is 1</p>
     *
     * @return a new vector
     */
    public static ImmutableVector4i one() {
        return new ImmutableVector4i(1, 1, 1, 1);
    }

    /**
     * <p>Creates a new vector of length one in the x-axis</p>
     *
     * @return a new vector
     */
    public static ImmutableVector4i unitX() {
        return new ImmutableVector4i(1, 0, 0, 0);
    }

    /**
     * <p>Creates a new vector of length one in the y-axis</p>
     *
     * @return a new vector
     */
    public static ImmutableVector4i unitY() {
        return new ImmutableVector4i(0, 1, 0, 0);
    }

    /**
     * <p>Creates a new vector of length one in the z-axis</p>
     *
     * @return a new vector
     */
    public static ImmutableVector4i unitZ() {
        return new ImmutableVector4i(0, 0, 1, 0);
    }

    /**
     * <p>Creates a new vector of length one in the w-axis</p>
     *
     * @return a new vector
     */
    public static ImmutableVector4i unitW() {
        return new ImmutableVector4i(0, 0, 0, 1);
    }
}