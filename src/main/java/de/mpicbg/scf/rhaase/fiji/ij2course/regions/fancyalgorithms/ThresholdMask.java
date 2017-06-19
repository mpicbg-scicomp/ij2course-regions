package de.mpicbg.scf.rhaase.fiji.ij2course.regions.fancyalgorithms;
/**
 * Author: Robert Haase, Scientific Computing Facility, MPI-CBG Dresden,
 * rhaase@mpi-cbg.de
 * Date: May 2017
 * <p>
 * Copyright 2017 Max Planck Institute of Molecular Cell Biology and Genetics,
 * Dresden, Germany
 * <p>
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 1. Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * 3. Neither the name of the copyright holder nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

import net.imglib2.Cursor;
import net.imglib2.img.Img;
import net.imglib2.img.array.ArrayImgs;
import net.imglib2.type.logic.BitType;
import net.imglib2.type.numeric.RealType;
import net.imglib2.view.Views;

/**
 * This is just a simple threshold based segmentation algorithm for educational purposes.
 * @param <T>
 */
public class ThresholdMask<T extends RealType<T>> {

    Img<T> input;
    double threshold;

    public ThresholdMask(Img<T> input, double threshold) {
        this.input = input;
        this.threshold = threshold;
    }

    public Img<BitType> getMask() {
        long[] dimensions = new long[input.numDimensions()];
        input.dimensions(dimensions);
        Img<BitType> output = ArrayImgs.bits(dimensions);

        // normalize all pixels
        Cursor<T> inputCursor = Views.flatIterable(input).cursor();
        Cursor<BitType> outputCursor = output.cursor();

        while (inputCursor.hasNext() && outputCursor.hasNext()) {
            BitType pixel = outputCursor.next();
            pixel.set(inputCursor.next().getRealFloat() > threshold);
        }
        return output;
    }

    public static <T extends RealType<T>> Img<BitType> threshold(Img<T> img, double threshold) {
        ThresholdMask<T> thresholdMask = new ThresholdMask<T>(img, threshold);
        return thresholdMask.getMask();
    }
}
