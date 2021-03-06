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
package ar.com.quark.audio;

import ar.com.quark.system.utility.array.Float32Array;
import ar.com.quark.system.utility.array.Int8Array;

/**
 * <code>AudioManager</code> encapsulate a service for managing {@link Audio}.
 */
public interface AudioManager {
    /**
     * <code>ALES10</code> encapsulate all feature(s) supported by OpenAL 1.0.
     */
    interface ALES10 {
        int AL_BUFFER = 0x1009;
        int AL_BUFFERS_PROCESSED = 0x1016;
        int AL_CONE_INNER_ANGLE = 0x1001;
        int AL_CONE_OUTER_ANGLE = 0x1002;
        int AL_CONE_OUTER_GAIN = 0x1022;
        int AL_DIRECTION = 0x1005;
        int AL_FALSE = 0x0000;
        int AL_GAIN = 0x100A;
        int AL_LOOPING = 0x1007;
        int AL_MAX_DISTANCE = 0x1023;
        int AL_FORMAT_MONO8 = 0x1100;
        int AL_FORMAT_MONO16 = 0x1101;
        int AL_NONE = 0x0000;
        int AL_ORIENTATION = 0x100F;
        int AL_PAUSED = 0x1013;
        int AL_PITCH = 0x1003;
        int AL_PLAYING = 0x1012;
        int AL_POSITION = 0x1004;
        int AL_REFERENCE_DISTANCE = 0x1020;
        int AL_SOURCE_RELATIVE = 0x0202;
        int AL_SOURCE_STATE = 0x1010;
        int AL_FORMAT_STEREO16 = 0x1103;
        int AL_FORMAT_STEREO8 = 0x1102;
        int AL_STOPPED = 0x1014;
        int AL_TRUE = 0x0001;
        int AL_VELOCITY = 0x1006;

        boolean alcCreateContext();

        void alcDestroyContext();

        int alGenBuffers();

        int alGenSources();

        void alDeleteBuffers(int name);

        void alDeleteSources(int name);

        void alSourcePause(int name);

        void alSourcePlay(int name);

        void alSourceStop(int name);

        void alSourcei(int name, int type, int value);

        void alSourcef(int name, int type, float value);

        void alSourcef(int name, int type, float value1, float value2, float value3);

        void alListenerf(int type, float value);

        void alListenerf(int type, float value1, float value2, float value3);

        void alListenerf(int type, Float32Array value);

        int alGetSourcei(int name, int type);

        int alSourceUnqueueBuffers(int name);

        void alSourceQueueBuffers(int name, int id);

        void alBufferData(int name, int format, Int8Array data, int rate);
    }

    /**
     * <code>ALESExtension</code> encapsulate all extension(s) supported.
     */
    interface ALESExtension {
        int AL_FORMAT_MONO_FLOAT32 = 0x10010;
        int AL_FORMAT_STEREO_FLOAT32 = 0x10011;
    }

    /**
     * <p>Play the given {@link AudioSource}</p>
     *
     * @param source the source
     */
    void play(AudioSource source);

    /**
     * <p>Pause the given {@link AudioSource}</p>
     *
     * @param source the source
     */
    void pause(AudioSource source);

    /**
     * <p>Pause all {@link AudioSource}</p>
     */
    void pause();

    /**
     * <p>Resume the given {@link AudioSource}</p>
     *
     * @param source the source
     */
    void resume(AudioSource source);

    /**
     * <p>Resume all {@link AudioSource}</p>
     */
    void resume();

    /**
     * <p>Stop the given {@link AudioSource}</p>
     *
     * @param source the source
     */
    void stop(AudioSource source);

    /**
     * <p>Stop all {@link AudioSource}</p>
     */
    void stop();

    /**
     * <p>Update the listener of the manager</p>
     *
     * @param listener the listener
     */
    void update(AudioListener listener);

    /**
     * <p>Delete the given {@link Audio}</p>
     *
     * @param audio the audio
     */
    void delete(Audio audio);
}
