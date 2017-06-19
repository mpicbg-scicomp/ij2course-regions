package de.mpicbg.scf.rhaase.fiji.ij2course.regions;

import ij.gui.Roi;
import java.awt.*;
import net.imglib2.*;
import net.imglib2.roi.util.Contains;
import net.imglib2.roi.util.ContainsRealRandomAccess;
import net.imglib2.type.logic.BoolType;
import net.imglib2.util.Intervals;

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
public class RoiRealRandomAccessibleRealInterval implements RealRandomAccessibleRealInterval<BoolType>, Contains<RealLocalizable> {
    Roi roi;
    RealInterval boundingBox;

    public RoiRealRandomAccessibleRealInterval(Roi roi) {
        this.roi = roi;

        final Rectangle bounds = roi.getBounds();
        boundingBox = Intervals.createMinMaxReal(new double[]{bounds.getX(), bounds.getY(), bounds.getX() + bounds.getWidth() - 1, bounds.getY() + bounds.getHeight() - 1});
    }

    @Override
    public boolean contains(RealLocalizable realLocalizable) {
        if (Intervals.contains(boundingBox, realLocalizable)) {
            if (roi.contains((int) realLocalizable.getDoublePosition(0), (int) realLocalizable.getDoublePosition(1))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public double realMin(int i) {
        return boundingBox.realMin(i);
    }

    @Override
    public void realMin(double[] doubles) {
        boundingBox.realMin(doubles);
    }

    @Override
    public void realMin(RealPositionable realPositionable) {
        boundingBox.realMin(realPositionable);
    }

    @Override
    public double realMax(int i) {
        return boundingBox.realMax(i);
    }

    @Override
    public void realMax(double[] doubles) {
        boundingBox.realMax(doubles);
    }

    @Override
    public void realMax(RealPositionable realPositionable) {
        boundingBox.realMax(realPositionable);
    }

    @Override
    public RealRandomAccess<BoolType> realRandomAccess() {
        return new ContainsRealRandomAccess(this);
    }

    @Override
    public RealRandomAccess<BoolType> realRandomAccess(RealInterval realInterval) {
        return realRandomAccess();
    }

    @Override
    public Contains<RealLocalizable> copyContains() {
        return this;
    }

    @Override
    public int numDimensions() {
        return boundingBox.numDimensions();
    }
}
