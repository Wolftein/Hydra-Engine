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
package org.quark_engine.render.storage;

import org.quark_engine.render.storage.factory.FactoryStorageIndices;
import org.quark_engine.render.storage.factory.FactoryStorageVertices;

import java.util.Collections;
import java.util.List;

import static org.quark_engine.Quark.QkRender;

/**
 * A <code>Mesh</code> consists of vertices and optionally indices which specify which
 * vertices define a primitive.
 * <p>
 * Each vertex is composed of attribute(s) such as position, normal, color or texture coordinate (all attribute(s)
 * may not be present, except for position).
 *
 * @author Agustin L. Alvarez (wolftein1@gmail.com)
 */
public class Mesh {
    /**
     * Hold the descriptor of the mesh.
     */
    protected final VertexDescriptor mDescriptor;

    /**
     * <p>Constructor</p>
     */
    public Mesh(VertexDescriptor descriptor) {
        mDescriptor = descriptor;
    }

    /**
     * <p>Constructor</p>
     */
    public Mesh(List<FactoryStorageVertices<?>> vertices, FactoryStorageIndices indices) {
        this(new VertexDescriptor(vertices, indices));
    }

    /**
     * <p>Constructor</p>
     */
    public Mesh(FactoryStorageVertices<?> vertices, FactoryStorageIndices indices) {
        this(new VertexDescriptor(Collections.singletonList(vertices), indices));
    }

    /**
     * <p>Constructor</p>
     */
    public Mesh(FactoryStorageVertices<?> vertices) {
        this(new VertexDescriptor(Collections.singletonList(vertices)));
    }

    /**
     * <p>Get the descriptor of the mesh</p>
     *
     * @return the descriptor of the mesh
     */
    public final VertexDescriptor getDescriptor() {
        return mDescriptor;
    }

    /**
     * <p>Get the vertices of the mesh</p>
     *
     * @return the vertices of the mesh
     */
    public final List<FactoryStorageVertices<?>> getVertices() {
        return mDescriptor.getVertices();
    }

    /**
     * <p>Get the vertices of the mesh</p>
     *
     * @param index the index of the vertices storage
     *
     * @return the vertices of the mesh
     */
    public final FactoryStorageVertices<?> getVertices(int index) {
        final List<FactoryStorageVertices<?>> vertices = mDescriptor.getVertices();

        if (vertices == null || index < 0 || index >= vertices.size()) {
            throw new IllegalArgumentException("Invalid vertices storage");
        }
        return vertices.get(index);
    }

    /**
     * <p>Get the indices of the mesh</p>
     *
     * @return the indices of the mesh
     */
    public final FactoryStorageIndices<?> getIndices() {
        return mDescriptor.getIndices();
    }

    /**
     * <p>Draw the mesh</p>
     *
     * @param primitive the render primitive
     * @param offset    the render offset
     * @param count     the render primitive count
     */
    public final void draw(Primitive primitive, long offset, long count) {
        mDescriptor.acquire();
        {
            QkRender.draw(primitive, offset, count);
        }
        mDescriptor.release();
    }

    /**
     * <p>Draw the mesh (using indices)</p>
     *
     * @param primitive the render primitive
     * @param offset    the render offset
     * @param count     the render primitive count
     * @param format    the indices format
     */
    public final void draw(Primitive primitive, long offset, long count, VertexFormat format) {
        if (!mDescriptor.hasIndices()) {
            throw new IllegalStateException("Cannot draw the mesh without an indices storage");
        }
        mDescriptor.acquire();
        {
            QkRender.draw(primitive, offset, count, format);
        }
        mDescriptor.release();
    }
}
