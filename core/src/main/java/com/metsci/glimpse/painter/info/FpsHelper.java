/*
 * Copyright (c) 2016, Metron, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of Metron, Inc. nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL METRON, INC. BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.metsci.glimpse.painter.info;

/**
 * Helper data structure for FpsPainter.
 *
 * @see FpsPainter
 * @author sindhwani
 */
public class FpsHelper
{
    private float lastFpsEstimate = -1;
    private long timeOfLastCounterReset = -1;
    private float frameCount = 0;

    public FpsHelper( )
    {
    }

    public FpsHelper( float lastEstimate, long timeOfLastCounterReset, float frameCount )
    {
        lastFpsEstimate = lastEstimate;
        this.timeOfLastCounterReset = timeOfLastCounterReset;
        this.frameCount = frameCount;
    }

    public float getLastFpsEstimate( )
    {
        return lastFpsEstimate;
    }

    public long getTimeOfLastCounterReset( )
    {
        return timeOfLastCounterReset;
    }

    public float getFrameCount( )
    {
        return frameCount;
    }

    public void setFpsEstimate( float estimate )
    {
        lastFpsEstimate = estimate;
    }

    public void setTimeOfCounterReset( long timeOfReset )
    {
        timeOfLastCounterReset = timeOfReset;
    }

    public void setFrameCount( float frameCount )
    {
        this.frameCount = frameCount;
    }
}