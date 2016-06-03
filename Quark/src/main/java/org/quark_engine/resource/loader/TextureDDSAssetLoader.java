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
package org.quark_engine.resource.loader;

import org.quark_engine.render.storage.VertexFormat;
import org.quark_engine.render.texture.*;
import org.quark_engine.resource.AssetKey;
import org.quark_engine.resource.AssetLoader;
import org.quark_engine.resource.AssetManager;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

/**
 * Encapsulate an {@link AssetLoader} for loading DDS texture(s).
 * <p>
 * {@link Texture}
 * {@link Texture1D}
 * {@link Texture2D}
 * {@link ImageFormat#RED}
 * {@link ImageFormat#RG}
 * {@link ImageFormat#RGB}
 * {@link ImageFormat#RGBA}
 * {@link ImageFormat#RGB_DXT1}
 * {@link ImageFormat#RGBA_DXT1}
 * {@link ImageFormat#RGBA_DXT3}
 * {@link ImageFormat#RGBA_DXT5}
 *
 * @author Agustin L. Alvarez (wolftein1@gmail.com)
 */
public final class TextureDDSAssetLoader implements AssetLoader<Texture, Texture.Descriptor> {
    private final static int DDSD_MIPMAP_COUNT = 0x00020000;

    private final static int DDPF_FOUR_CC = 0x00000004;
    private final static int DDPF_ALPHA_PIXELS = 0x00000001;
    private final static int DDPF_ALPHA = 0x00000002;
    private final static int DDPF_RGB = 0x00000040;
    private final static int DDPF_GRAY_SCALE = 0x00020000;

    private final static int DDSCAPS2_CUBEMAP = 0x00000200;
    private final static int DDSCAPS2_VOLUME = 0x00200000;

    /**
     * <code>ImageHeader</code> represent the file format of a DDS image.
     */
    private final static class ImageHeader {
        public int mImageSize;
        public int mImageFlag;
        public int mImageWidth;
        public int mImageHeight;
        public int mImagePitchOrLinear;
        public int mImageDepth;
        public int mImageMipmapCount;
        public int mPixelFormatSize;
        public int mPixelFormatFlags;
        public String mPixelFormatFourCC;
        public int mPixelFormatRGBBitCount;
        public int mPixelFormatRBitMask;
        public int mPixelFormatGBitMask;
        public int mPixelFormatBBitMask;
        public int mPixelFormatABitMask;
        public int mImageCaps;
        public int mImageCaps2;
        public int mImageCaps3;
        public int mImageCaps4;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AssetKey<Texture, Texture.Descriptor> load(AssetManager manager, InputStream input,
            Texture.Descriptor descriptor) throws IOException {
        return new AssetKey<>(readTexture(descriptor, new DataInputStream(input)), descriptor);
    }

    /**
     * <p>Read a {@link Texture} from the {@link InputStream} given</p>
     *
     * @param descriptor the texture descriptor
     * @param input      the input-stream that contain(s) the texture
     *
     * @return the texture
     *
     * @throws IOException indicates failing loading the texture
     */
    private Texture readTexture(Texture.Descriptor descriptor, DataInputStream input) throws IOException {
        if (readIntLittleEndian(input) != 0x20534444) {
            throw new IOException("Trying to read an invalid <DDS> texture");
        }

        //!
        //! According to DDS documentation, the first chunk should be the header of the image
        //!
        final ImageHeader header = readHeader(input);

        if ((header.mImageCaps2 & DDSCAPS2_CUBEMAP) != 0) {
            throw new IOException("DDS Cube-map texture(s) are not supported yet.");
        } else if ((header.mImageCaps2 & DDSCAPS2_VOLUME) != 0) {
            return new Texture3D(
                    descriptor.getFormat(),
                    descriptor.getFilter(),
                    descriptor.getBorderX(),
                    descriptor.getBorderY(),
                    descriptor.getBorderZ(), readImages(header, input));
        } else if (header.mImageHeight > 0) {
            return new Texture2D(
                    descriptor.getFormat(),
                    descriptor.getFilter(),
                    descriptor.getBorderX(),
                    descriptor.getBorderY(), readImages(header, input));
        } else {
            return new Texture1D(
                    descriptor.getFormat(),
                    descriptor.getFilter(),
                    descriptor.getBorderX(), readImages(header, input));
        }
    }

    /**
     * <p>Read image header from the {@link DataInputStream} given</p>
     *
     * @param input the input that contain(s) the header
     *
     * @throws IOException indicates if the image has invalid header
     */
    private ImageHeader readHeader(DataInputStream input) throws IOException {
        final ImageHeader header = new ImageHeader();

        //!
        //! Texture common data
        //!
        header.mImageSize = readIntLittleEndian(input);
        header.mImageFlag = readIntLittleEndian(input);
        header.mImageHeight = readIntLittleEndian(input);
        header.mImageWidth = Math.max(1, readIntLittleEndian(input));
        header.mImagePitchOrLinear = readIntLittleEndian(input);
        header.mImageDepth = Math.max(1, readIntLittleEndian(input));
        header.mImageMipmapCount = Math.max(1, readIntLittleEndian(input));

        //!
        //! Reserve field
        //!
        input.skipBytes(0x04 * 0x0B);

        //!
        //! Texture pixel format data
        //!
        header.mPixelFormatSize = readIntLittleEndian(input);
        header.mPixelFormatFlags = readIntLittleEndian(input);

        if ((header.mPixelFormatFlags & DDPF_FOUR_CC) == 0) {
            header.mPixelFormatFourCC = null;
        } else {
            final byte[] name = new byte[0x04];
            input.readFully(name);
            header.mPixelFormatFourCC = new String(name, "US-ASCII");
        }
        header.mPixelFormatRGBBitCount = readIntLittleEndian(input);
        header.mPixelFormatRBitMask = readIntLittleEndian(input);
        header.mPixelFormatGBitMask = readIntLittleEndian(input);
        header.mPixelFormatBBitMask = readIntLittleEndian(input);
        header.mPixelFormatABitMask = readIntLittleEndian(input);
        header.mImageCaps = readIntLittleEndian(input);
        header.mImageCaps2 = readIntLittleEndian(input);
        header.mImageCaps3 = readIntLittleEndian(input);
        header.mImageCaps4 = readIntLittleEndian(input);

        //!
        //! Reserve field
        //!
        input.skipBytes(0x04);
        return header;
    }

    /**
     * <p>Parse the image(s) from the {@link DataInputStream} given</p>
     *
     * @param header the header of the image
     * @param input  the stream of the image
     *
     * @return a collection that contain(s) all image(s)
     *
     * @throws IOException indicates failing loading the image(s)
     */
    private List<Image> readImages(ImageHeader header, DataInputStream input) throws IOException {
        int imageDepth = header.mImageDepth;
        int imageWidth = header.mImageWidth;
        int imageHeight = header.mImageHeight;

        //!
        //! Translate DDS format to native format
        //!
        final ImageFormat imageFormat = header.mPixelFormatFourCC == null
                ? getUncompressedFormat(header)
                : getCompressedFormat(header);
        final int imageFormatScanLength = (imageFormat == ImageFormat.RGB_DXT1 ? 0x08 : 0x10);
        final int imageCount = ((header.mImageFlag & DDSD_MIPMAP_COUNT) == 0 ? 1 : header.mImageMipmapCount);

        //!
        //! Parse each mip-map of the image
        //!
        final List<Image> imageList = new ArrayList<>(imageCount);

        for (int i = 0; i < imageCount; ++i) {
            //!
            //! Calculate the length of the mip-map
            //!
            final int imageLength = imageFormat.eCompressed
                    ? ((imageWidth + 3) / 4 * imageFormatScanLength) * ((imageHeight + 3) / 4) * ((imageDepth + 3) / 4)
                    : ((imageWidth * imageFormat.eComponent * imageHeight * imageDepth));

            //!
            //! Read the image
            //!
            final byte data[] = new byte[imageLength];
            input.readFully(data, 0, imageLength);

            final ByteBuffer buffer = ByteBuffer.allocateDirect(imageLength).order(ByteOrder.nativeOrder());
            buffer.put(data, 0, imageLength).flip();
            imageList.add(new Image(imageFormat, imageWidth, imageHeight, VertexFormat.BYTE, imageDepth, i, buffer));

            imageDepth = Math.max(imageDepth / 2, 1);
            imageWidth = Math.max(imageWidth / 2, 1);
            imageHeight = Math.max(imageHeight / 2, 1);
        }
        return imageList;
    }

    /**
     * <p>Get the uncompressed format of the image</p>
     *
     * @param header the header of the image
     *
     * @return the uncompressed format of the image
     *
     * @throws IOException indicates if the format is unsupported
     */
    private ImageFormat getUncompressedFormat(ImageHeader header) throws IOException {
        if ((header.mImageFlag & DDPF_RGB) != 0) {
            return (header.mPixelFormatFlags & DDPF_ALPHA_PIXELS) == 0 ? ImageFormat.RGB : ImageFormat.RGBA;
        } else if ((header.mImageFlag & DDPF_ALPHA) != 0 || (header.mImageFlag & DDPF_GRAY_SCALE) != 0) {
            return (header.mPixelFormatFlags & DDPF_ALPHA_PIXELS) == 0 ? ImageFormat.RED : ImageFormat.RG;
        }
        throw new IOException("Uncompressed format not supported.");
    }

    /**
     * <p>Get the compressed format of the image</p>
     *
     * @param header the header of the image
     *
     * @return the compressed format of the image
     *
     * @throws IOException indicates if the format is unsupported
     */
    private ImageFormat getCompressedFormat(ImageHeader header) throws IOException {
        switch (header.mPixelFormatFourCC) {
            case "DXT1":
                return (header.mPixelFormatFlags & DDPF_ALPHA_PIXELS) == 0
                        ? ImageFormat.RGB_DXT1
                        : ImageFormat.RGBA_DXT1;
            case "DXT3":
                return ImageFormat.RGBA_DXT3;
            case "DXT5":
                return ImageFormat.RGBA_DXT5;
        }
        throw new IOException(header.mPixelFormatFourCC + " compressed format not supported.");
    }

    /**
     * <b>Read a little endian integer (Required for DDS format)</b>
     */
    private int readIntLittleEndian(DataInputStream in) throws IOException {
        final int int1 = in.readByte();
        final int int2 = in.readByte();
        final int int3 = in.readByte();
        final int int4 = in.readByte();

        return (int4 << 24 | (int3 & 0xFF) << 16 | (int2 & 0xFF) << 8 | (int1 & 0xFF));
    }
}
