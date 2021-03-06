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
package ar.com.quark.backend.teavm.opengl;

import ar.com.quark.render.Render;
import org.teavm.jso.dom.html.HTMLCanvasElement;

/**
 * Implementation for {@link Render.GLES32}.
 */
public final class WebOpenGLES32 extends WebOpenGLES31 implements Render.GLES32 {
    /**
     * <p>Constructor</p>
     */
    public WebOpenGLES32(HTMLCanvasElement canvas) {
        super(canvas);
    }
}
