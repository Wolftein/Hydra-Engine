/*
 * This file is part of Quark Framework, licensed under the APACHE License.
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
package org.quark.backend.teavm.utility.array;

import org.quark.system.utility.array.Array;
import org.teavm.jso.JSBody;
import org.teavm.jso.JSMethod;
import org.teavm.jso.JSObject;
import org.teavm.jso.JSProperty;
import org.teavm.jso.typedarrays.ArrayBuffer;

/**
 * <a href="http://teavm.org/">TeaVM</a> implementation for {@link Array}.
 */
public abstract class TeaVMArray<A extends Array> implements Array<A> {
    /**
     * Hold a reference to the typed-array.
     */
    private final DataView mBuffer;

    private int mPosition;
    private int mLimit;

    /**
     * <p>Constructor</p>
     */
    public TeaVMArray(ArrayBuffer array) {
        mBuffer = create(array);

        mLimit = capacity();
        mPosition = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int capacity() {
        return mBuffer.getByteLength();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int limit() {
        return mLimit;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public A limit(int limit) {
        mLimit = limit;
        return (A) this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int position() {
        return mPosition;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public A position(int position) {
        mPosition = position;
        return (A) this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int remaining() {
        return mLimit - mPosition;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasRemaining() {
        return remaining() > 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public A clear() {
        mPosition = 0;
        mLimit = mBuffer.getByteLength();
        return (A) this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public A flip() {
        mLimit = mPosition;
        mPosition = 0;
        return (A) this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public A rewind() {
        mPosition = 0;
        return (A) this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <B> B data() {
        return (B) mBuffer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public A writeInt8(byte value) {
        mBuffer.setInt8(mPosition++, value);
        return (A) this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public A writeInt8(int index, byte value) {
        mBuffer.setInt8(index, value);
        return (A) this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public A writeInt16(short value) {
        mBuffer.setInt16(mPosition, value);

        mPosition += 2;

        return (A) this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public A writeInt16(int index, short value) {
        mBuffer.setInt16(index, value);
        return (A) this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public A writeInt32(int value) {
        mBuffer.setInt32(mPosition, value);

        mPosition += 4;

        return (A) this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public A writeInt32(int index, int value) {
        mBuffer.setInt32(index, value);
        return (A) this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public A writeInt64(long value) {
        writeFloat64(Double.longBitsToDouble(value));
        return (A) this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public A writeInt64(int index, long value) {
        writeFloat64(index, Double.longBitsToDouble(value));
        return (A) this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public A writeFloat32(float value) {
        mBuffer.setFloat32(mPosition, value);

        mPosition += 4;

        return (A) this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public A writeFloat32(int index, float value) {
        mBuffer.setFloat32(index, value);
        return (A) this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public A writeFloat64(double value) {
        mBuffer.setFloat64(mPosition, value);

        mPosition += 8;

        return (A) this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public A writeFloat64(int index, double value) {
        mBuffer.setFloat64(index, value);
        return (A) this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public byte readInt8() {
        return mBuffer.getInt8(mPosition++);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public byte readInt8(int index) {
        return mBuffer.getInt8(index);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public short readInt16() {
        final short value = mBuffer.getInt16(mPosition);

        mPosition += 0x02;

        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public short readInt16(int index) {
        return mBuffer.getInt16(index);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int readInt32() {
        final int value = mBuffer.getInt32(mPosition);

        mPosition += 0x04;

        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int readInt32(int index) {
        return mBuffer.getInt32(index);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long readInt64() {
        return Double.doubleToLongBits(readFloat64());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long readInt64(int index) {
        return Double.doubleToLongBits(readFloat64(index));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float readFloat32() {
        final float value = mBuffer.getFloat32(mPosition);

        mPosition += 0x04;

        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float readFloat32(int index) {
        return mBuffer.getFloat32(index);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double readFloat64() {
        final double value = mBuffer.getFloat64(mPosition);

        mPosition += 0x08;

        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double readFloat64(int index) {
        return mBuffer.getFloat64(index);
    }

    /**
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/DataView">Reference</a>
     */
    @JSBody(params = {"buffer"}, script = "return new DataView(buffer)")
    public static native DataView create(ArrayBuffer buffer);

    /**
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/DataView">Reference</a>
     */
    public interface DataView extends JSObject {
        @JSProperty
        ArrayBuffer getBuffer();

        @JSProperty
        int getByteLength();

        @JSProperty
        int getByteOffset();

        @JSMethod
        byte getInt8(int index);

        @JSMethod
        short getUInt8(int index);

        @JSMethod
        short getInt16(int index);

        @JSMethod
        int getUInt16(int index);

        @JSMethod
        int getInt32(int index);

        @JSMethod
        long getUInt32(int index);

        @JSMethod
        float getFloat32(int index);

        @JSMethod
        double getFloat64(int index);

        @JSMethod
        void setInt8(int index, int value);

        @JSMethod
        void setUInt8(int index, int value);

        @JSMethod
        void setInt16(int index, int value);

        @JSMethod
        void setUInt16(int index, int value);

        @JSMethod
        void setInt32(int index, int value);

        @JSMethod
        void setUInt32(int index, long value);

        @JSMethod
        void setFloat32(int index, float value);

        @JSMethod
        void setFloat64(int index, double value);
    }
}
