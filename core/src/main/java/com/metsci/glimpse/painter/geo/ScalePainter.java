/*
 * Copyright (c) 2012, Metron, Inc.
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
package com.metsci.glimpse.painter.geo;

import static com.metsci.glimpse.support.font.FontUtils.getDefaultBold;

import java.awt.geom.Rectangle2D;
import java.text.NumberFormat;

import javax.media.opengl.GL;
import javax.media.opengl.GLContext;

import com.metsci.glimpse.axis.Axis1D;
import com.metsci.glimpse.axis.painter.label.AxisUnitConverter;
import com.metsci.glimpse.context.GlimpseBounds;
import com.metsci.glimpse.context.GlimpseContext;
import com.metsci.glimpse.painter.base.GlimpsePainterImpl;
import com.metsci.glimpse.support.color.GlimpseColor;
import com.metsci.glimpse.util.units.Length;
import com.sun.opengl.util.j2d.TextRenderer;

/**
 * Displays a simple distance scale in the lower right corner of the plot.
 *
 * @author ulman
 */
public class ScalePainter extends GlimpsePainterImpl
{
    protected float[] borderColor = GlimpseColor.fromColorRgba( 0.0f, 0.0f, 0.0f, 1.0f );
    protected float[] primaryColor = GlimpseColor.fromColorRgba( 1.0f, 1.0f, 1.0f, 0.5f );
    protected float[] secondaryColor = GlimpseColor.fromColorRgba( 0.6f, 0.6f, 0.6f, 0.5f );
    protected float[] textColor = GlimpseColor.fromColorRgba( 0.0f, 0.0f, 0.0f, 1.0f );

    protected int bufferX;
    protected int bufferY;
    protected int pixelHeight;
    protected int pixelWidth;

    protected Axis1D axis;

    protected AxisUnitConverter converter;

    protected String unitLabel;

    protected TextRenderer textRenderer;

    protected NumberFormat formatter;

    public ScalePainter( Axis1D axis )
    {
        this.axis = axis;

        this.textRenderer = createTextRenderer( );

        this.converter = new AxisUnitConverter( )
        {

            @Override
            public double fromAxisUnits( double value )
            {
                return Length.fromNauticalMiles( value );
            }

            @Override
            public double toAxisUnits( double value )
            {
                return Length.toNauticalMiles( value );
            }

        };

        this.formatter = NumberFormat.getNumberInstance( );
        this.formatter.setGroupingUsed( false );

        this.unitLabel = "nmi";
        this.bufferX = 0;
        this.bufferY = 0;
        this.pixelHeight = 20;
        this.pixelWidth = 300;
    }

    protected TextRenderer createTextRenderer( )
    {
        return new TextRenderer( getDefaultBold( 16 ), false, false );
    }

    public float[] getBorderColor( )
    {
        return borderColor;
    }

    public void setBorderColor( float[] borderColor )
    {
        this.borderColor = borderColor;
    }

    public float[] getPrimaryColor( )
    {
        return primaryColor;
    }

    public void setPrimaryColor( float[] primaryColor )
    {
        this.primaryColor = primaryColor;
    }

    public float[] getSecondaryColor( )
    {
        return secondaryColor;
    }

    public void setSecondaryColor( float[] secondaryColor )
    {
        this.secondaryColor = secondaryColor;
    }

    public float[] getTextColor( )
    {
        return textColor;
    }

    public void setTextColor( float[] textColor )
    {
        this.textColor = textColor;
    }

    public int getPixelBufferX( )
    {
        return bufferX;
    }

    public void setPixelBufferX( int buffer )
    {
        this.bufferX = buffer;
    }

    public int getPixelBufferY( )
    {
        return bufferY;
    }

    public void setPixelBufferY( int buffer )
    {
        this.bufferY = buffer;
    }

    public int getScaleHeight( )
    {
        return pixelHeight;
    }

    public void setPixelHeight( int pixelHeight )
    {
        this.pixelHeight = pixelHeight;
    }

    public int getPixelHeight( )
    {
        return pixelWidth;
    }

    public void setPixelWidth( int pixelWidth )
    {
        this.pixelWidth = pixelWidth;
    }

    public AxisUnitConverter getUnitConverter( )
    {
        return converter;
    }

    public void setUnitConverter( AxisUnitConverter converter )
    {
        this.converter = converter;
    }

    public String getUnitLabel( )
    {
        return unitLabel;
    }

    public void setUnitLabel( String unitLabel )
    {
        this.unitLabel = unitLabel;
    }

    @Override
    public void dispose( GLContext context )
    {
        if ( textRenderer != null ) textRenderer.dispose( );
        textRenderer = null;
    }

    @Override
    protected void paintTo( GlimpseContext context, GlimpseBounds bounds )
    {
        int width = bounds.getWidth( );
        int height = bounds.getHeight( );

        double diff = axis.getMax( ) - axis.getMin( );
        double ratio = axis.getSizePixels( ) / converter.toAxisUnits( diff );

        double hintValue = pixelWidth / ratio;
        double hintOrder = Math.log10( hintValue );

        int order = ( int ) Math.ceil( hintOrder );

        double scaleValueSize = Math.pow( 10.0, order - 1 );
        double scalePixelSize = scaleValueSize * ratio;
        int tickCount = ( int ) Math.floor( pixelWidth / scalePixelSize );

        if ( tickCount < 5 )
        {
            scaleValueSize = scaleValueSize / 2;
            scalePixelSize = scaleValueSize * ratio;
            tickCount = ( int ) Math.floor( pixelWidth / scalePixelSize );
        }

        double totalSize = scalePixelSize * tickCount;

        GL gl = context.getGL( );

        gl.glMatrixMode( GL.GL_PROJECTION );
        gl.glLoadIdentity( );
        gl.glOrtho( 0, width, 0, height, -1, 1 );
        gl.glMatrixMode( GL.GL_MODELVIEW );
        gl.glLoadIdentity( );

        gl.glBlendFunc( GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA );
        gl.glEnable( GL.GL_BLEND );

        gl.glBegin( GL.GL_QUADS );
        try
        {
            for ( int i = 0; i < tickCount; i++ )
            {
                if ( i % 2 == 0 )
                    gl.glColor4fv( secondaryColor, 0 );
                else
                    gl.glColor4fv( primaryColor, 0 );

                double offset1 = totalSize * ( i / ( double ) tickCount );
                double offset2 = totalSize * ( ( i + 1 ) / ( double ) tickCount );

                gl.glVertex2d( width - bufferX - offset1, bufferY );
                gl.glVertex2d( width - bufferX - offset2, bufferY );
                gl.glVertex2d( width - bufferX - offset2, bufferY + pixelHeight );
                gl.glVertex2d( width - bufferX - offset1, bufferY + pixelHeight );
            }
        }
        finally
        {
            gl.glEnd( );
        }

        gl.glLineWidth( 1f );
        gl.glColor4fv( borderColor, 0 );

        gl.glBegin( GL.GL_LINE_LOOP );
        try
        {
            gl.glVertex2d( width - bufferX, bufferY );
            gl.glVertex2d( width - bufferX - totalSize, bufferY );
            gl.glVertex2d( width - bufferX - totalSize, bufferY + pixelHeight );
            gl.glVertex2d( width - bufferX, bufferY + pixelHeight );
        }
        finally
        {
            gl.glEnd( );
        }

        gl.glDisable( GL.GL_BLEND );
        gl.glTranslatef( 0.375f, 0.375f, 0 );

        if ( order < 2 )
        {
            formatter.setMaximumFractionDigits( Math.abs( order - 2 ) );
        }
        else
        {
            formatter.setMaximumFractionDigits( 0 );
        }

        String text = formatter.format( scaleValueSize ) + " " + unitLabel;

        Rectangle2D textBounds = textRenderer.getBounds( text );

        int posX = ( int ) ( width - 1 - bufferX - textBounds.getWidth( ) - 1 );
        int posY = ( int ) ( bufferY + pixelHeight / 2 - textBounds.getHeight( ) / 2 );

        textRenderer.beginRendering( width, height );
        try
        {
            GlimpseColor.setColor( textRenderer, textColor );
            textRenderer.draw( text, posX, posY );
        }
        finally
        {
            textRenderer.endRendering( );
        }
    }
}
