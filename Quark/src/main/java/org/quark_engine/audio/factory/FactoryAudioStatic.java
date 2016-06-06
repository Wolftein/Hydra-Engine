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
package org.quark_engine.audio.factory;

import org.quark_engine.audio.Audio;
import org.quark_engine.audio.AudioFormat;
import org.quark_engine.system.utility.Disposable;

import java.nio.ByteBuffer;

import static org.quark_engine.Quark.QkAudioManager;

/**
 * Specialised implementation for {@link Audio} that are being loaded at once.
 *
 * @author Agustin L. Alvarez (wolftein1@gmail.com)
 */
public final class FactoryAudioStatic extends Audio {
    private final ByteBuffer mData;

    /**
     * <p>Constructor</p>
     */
    public FactoryAudioStatic(ByteBuffer data, AudioFormat format, int duration, int rate) {
        super(format, duration, rate);
        mData = data;
    }

    /**
     * <p>Get the data of the audio</p>
     *
     * @return the data of the audio
     */
    public ByteBuffer getData() {
        return mData;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isStreaming() {
        return false;
    }

    /**
     * @see Disposable#dispose()
     */
    @Override
    public void dispose() {
        QkAudioManager.delete(this);
    }
}
