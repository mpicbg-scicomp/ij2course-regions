package de.mpicbg.scf.rhaase.fiji.ij2course.regions;

import de.mpicbg.scf.rhaase.fiji.ij2course.regions.fancyalgorithms.ThresholdMask;
import ij.IJ;
import ij.ImagePlus;
import ij.gui.Roi;
import ij.plugin.filter.ThresholdToSelection;
import ij.process.ImageProcessor;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.img.Img;
import net.imglib2.img.display.imagej.ImageJFunctions;
import net.imglib2.type.logic.BitType;
import net.imglib2.type.logic.BoolType;
import net.imglib2.type.numeric.RealType;

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
public class RegionsMain {
    public static <T extends RealType<T>> void main(String... args) {
        new ij.ImageJ();

        // load and wrap the image
        ImagePlus input = IJ.openImage("src/main/resources/blobs.gif");
        Img<T> inputImg = ImageJFunctions.wrapReal(input);

        // threshold the image to create a mask image
        Img<BitType> mask = ThresholdMask.threshold(inputImg, 128);

        // visualise the original image
        ImageJFunctions.show(inputImg);
        ImagePlus image = IJ.getImage();
        image.setTitle("image");

        Roi roi = ROIUtilities.regionToRoi(mask);
        image.setRoi(roi);

        // -------------------------------
        // now the way back: transform an ROI to a Region
        RoiRealRandomAccessibleRealInterval rrrari = new RoiRealRandomAccessibleRealInterval(roi);
        RandomAccessibleInterval<BoolType> binaryImage = ROIUtilities.raster(rrrari);

        ImageJFunctions.show(binaryImage);
        IJ.getImage().setTitle("Mask from custom made RealRandomAccessibleRealInterval");

    }
}
