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
package org.quark.engine.media.opengl.texture;

/**
 * <code>TextureFormat</code> enumerate {@link Texture} format(s).
 *
 * @author Agustin L. Alvarez (wolftein1@gmail.com)
 */
public enum TextureFormat {
    /**
     * Color value represented as red/green/blue in 8-bit integer(s).
     * <p>
     * {@since OpenGL 1.1}
     */
    RGB8(0x8051, false, 0x1401),

    /**
     * Color value represented as red/green/blue/alpha in 8-bit integers(s).
     * <p>
     * {@since OpenGL 1.1}
     */
    RGBA8(0x8058, false, 0x1401),

    /**
     * Color value represented as red/green/blue in 16-bit integer(s).
     * <p>
     * {@since OpenGL 1.1}
     */
    RGB16(32852, false, 0x1403),

    /**
     * Color value represented as red/green/blue/alpha in 16-bit integer(s).
     * <p>
     * {@since OpenGL 1.1}
     */
    RGBA16(0x805B, false, 0x1403),

    /**
     * Color value represented as depth in 16-bit integer.
     * <p>
     * {@since OpenGL 1.4}
     */
    DEPTH_COMPONENT16(0x81A5, false, 0x1403),

    /**
     * Color value represented as depth in 24-bit integer.
     * <p>
     * {@since OpenGL 1.4}
     */
    DEPTH_COMPONENT24(0x81A6, false, 0x1405),

    /**
     * Color value represented as depth in 32-bit integer.
     * <p>
     * {@since OpenGL 1.4}
     */
    DEPTH_COMPONENT32(0x81A7, false, 0x1405),

    /**
     * Color value represented as red in 8-bit integer(s).
     * <p>
     * {@since OpenGL 3.0}
     */
    R8(0x8229, false, 0x1401),

    /**
     * Color value represented as red in 16-bit integer(s).
     * <p>
     * {@since OpenGL 3.0}
     */
    R16(0x822A, false, 0x1403),

    /**
     * Color value represented as red/green in 8-bit integer(s).
     * <p>
     * {@since OpenGL 3.0}
     */
    RG8(0x822B, false, 0x1401),

    /**
     * Color value represented as red/green in 16-bit integer(s).
     * <p>
     * {@since OpenGL 3.0}
     */
    RG16(0x822C, false, 0x1403),

    /**
     * Color value represented as red in 16-bit float(s).
     * <p>
     * {@since OpenGL 3.0}
     */
    R16F(0x822D, false, 0x140B),

    /**
     * Color value represented as red in 32-bit float(s).
     * <p>
     * {@since OpenGL 3.0}
     */
    R32F(0x822E, false, 0x1406),

    /**
     * Color value represented as red/green in 16-bit float(s).
     * <p>
     * {@since OpenGL 3.0}
     */
    RG16F(0x822F, false, 0x140B),

    /**
     * Color value represented as red/green in 32-bit float(s).
     * <p>
     * {@since OpenGL 3.0}
     */
    RG32F(0x8230, false, 0x1406),

    /**
     * Color value represented as red/green/blue/alpha in 32-bit float(s).
     * <p>
     * {@since OpenGL 3.0}
     */
    RGBA32F(0x8814, false, 0x1406),

    /**
     * Color value represented as red/green/blue in 32-bit float(s).
     * <p>
     * {@since OpenGL 3.0}
     */
    RGB32F(0x8815, false, 0x1406),

    /**
     * Color value represented as red/green/blue/alpha in 16-bit float(s).
     * <p>
     * {@since OpenGL 3.0}
     */
    RGBA16F(0x881A, false, 0x140B),

    /**
     * Color value represented as red/green/blue in 16-bit float(s).
     * <p>
     * {@since OpenGL 3.0}
     */
    RGB16F(0x881B, false, 0x140B),

    /**
     * Color value represented as red compressed.
     * <p>
     * {@since OpenGL 3.0}
     */
    COMPRESSED_RED(0x8225, true, 0x1401),

    /**
     * Color value represented as red/green/ compressed.
     * <p>
     * {@since OpenGL 3.0}
     */
    COMPRESSED_RG(0x8226, true, 0x1401),

    /**
     * Color value represented as red/green/blue compressed.
     * <p>
     * {@since OpenGL 1.3}
     */
    COMPRESSED_RGB(0x84ED, true, 0x1401),

    /**
     * Color value represented as red/green/blue/alpha compressed.
     * <p>
     * {@since OpenGL 1.3}
     */
    COMPRESSED_RGBA(0x84EE, true, 0x1401);

    public final int eValue;
    public final boolean eCompressed;
    public final int eType;

    /**
     * <p>Constructor</p>
     */
    TextureFormat(int value, boolean compressed, int type) {
        eValue = value;
        eCompressed = compressed;
        eType = type;
    }
}
