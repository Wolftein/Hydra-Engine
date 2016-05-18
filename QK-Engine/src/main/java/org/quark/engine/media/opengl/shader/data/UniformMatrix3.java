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
package org.quark.engine.media.opengl.shader.data;

import org.quark.engine.mathematic.Matrix3f;
import org.quark.engine.mathematic.MutableMatrix3f;
import org.quark.engine.media.opengl.shader.Uniform;
import org.quark.engine.media.opengl.shader.UniformType;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * <code>UniformMatrix3</code> represent an {@link Uniform} for {@link UniformType#Matrix3f}.
 *
 * @author Agustin L. Alvarez (wolftein1@gmail.com)
 */
public final class UniformMatrix3 extends Uniform {
    private final FloatBuffer mBuffer
            = ByteBuffer.allocateDirect((3 * 3) * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();

    /**
     * <p>Default constructor</p>
     */
    public UniformMatrix3(Matrix3f matrix) {
        super(UniformType.Matrix3f);
        matrix.store(mBuffer);
    }

    /**
     * <p>Empty constructor</p>
     */
    public UniformMatrix3() {
        super(UniformType.Matrix3f);
    }

    /**
     * <p>Get the value of the uniform</p>
     *
     * @return the value of the uniform
     */
    public FloatBuffer getValue() {
        return mBuffer;
    }

    /**
     * <p>Get the value of the uniform to the given matrix</p>
     *
     * @param outValue the matrix to retrieve the value from
     *
     * @return the value of the uniform
     */
    public MutableMatrix3f getValue(MutableMatrix3f outValue) {
        return outValue.set(mBuffer);
    }

    /**
     * <p>Change the value of the uniform</p>
     *
     * @param newValue the new value of the uniform
     */
    public void setValue(Matrix3f newValue) {
        mBuffer.rewind();

        newValue.store(mBuffer);
        setUpdate(CONCEPT_VALUE);
    }
}