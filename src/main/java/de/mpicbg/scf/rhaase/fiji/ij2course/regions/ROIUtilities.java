package de.mpicbg.scf.rhaase.fiji.ij2course.regions;

import ij.ImagePlus;
import ij.gui.Roi;
import ij.plugin.filter.ThresholdToSelection;
import ij.process.ImageProcessor;
import net.imglib2.Interval;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.RealRandomAccessibleRealInterval;
import net.imglib2.img.Img;
import net.imglib2.img.display.imagej.ImageJFunctions;
import net.imglib2.type.BooleanType;
import net.imglib2.type.logic.BoolType;
import net.imglib2.util.Intervals;
import net.imglib2.view.Views;

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
public class ROIUtilities {
    /**
     * Transfer a continous region to a pixelated one (note: information loss!
     * @param continuousRegion Continuous region
     * @return pixelated region
     */
    public static <B extends BooleanType<B>>  RandomAccessibleInterval<B> raster(RealRandomAccessibleRealInterval<B> continuousRegion) {
        int n = continuousRegion.numDimensions();

        long[] minmax = new long[n * 2];
        for (int d = 0; d < n; d++) {
            minmax[d] = (long) Math.floor(continuousRegion.realMin(d));
            minmax[d + n] = (long) Math.ceil(continuousRegion.realMax(d));
        }

        Interval interval = Intervals.createMinMax(minmax);

        return Views.interval(Views.raster(continuousRegion), interval);
    }

    public static <B extends BooleanType<B>> Roi regionToRoi(RandomAccessibleInterval<B> originalMask) {
        ImagePlus maskImp = ImageJFunctions.wrap(originalMask, "mask");

        ImageProcessor imageProcessor = maskImp.getProcessor();
        imageProcessor.setThreshold(128, 258, ImageProcessor.NO_LUT_UPDATE);
        Roi roi = new ThresholdToSelection().convert(imageProcessor);
        return roi;
    }
}
