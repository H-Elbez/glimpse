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
package com.metsci.glimpse.dnc;

import static com.metsci.glimpse.dnc.util.Shaders.compileShader;
import static com.metsci.glimpse.dnc.util.Shaders.getProgramInfoLog;
import static com.metsci.glimpse.util.StringUtils.join;
import static javax.media.opengl.GL.GL_TRUE;
import static javax.media.opengl.GL2ES2.GL_FRAGMENT_SHADER;
import static javax.media.opengl.GL2ES2.GL_LINK_STATUS;
import static javax.media.opengl.GL2ES2.GL_VERTEX_SHADER;
import static javax.media.opengl.GL3.GL_GEOMETRY_SHADER;

import javax.media.opengl.GL3;

import com.metsci.glimpse.util.primitives.IntsArray;

public class DncIconShaders
{

    public static final String iconVertShader_GLSL = join( "\n",
        "                                                                                    ",
        "  #version 150                                                                      ",
        "  #extension GL_EXT_gpu_shader4 : enable                                       ",
        "                                                                                    ",
        "  vec2 axisMin( vec4 axisRect )                                                     ",
        "  {                                                                                 ",
        "      return axisRect.xy;                                                           ",
        "  }                                                                                 ",
        "                                                                                    ",
        "  vec2 axisSize( vec4 axisRect )                                                    ",
        "  {                                                                                 ",
        "      return axisRect.zw;                                                           ",
        "  }                                                                                 ",
        "                                                                                    ",
        "  vec2 axisXyToNdc( vec2 xy_AXIS, vec4 axisRect )                                   ",
        "  {                                                                                 ",
        "      return ( ( xy_AXIS - axisMin( axisRect ) ) / axisSize( axisRect ) );          ",
        "  }                                                                                 ",
        "                                                                                    ",
        "  bool setContains( isampler2D setTexture, float index )                            ",
        "  {                                                                                 ",
        "      ivec2 textureSize = textureSize2D( setTexture, 0 );                           ",
        "      if ( textureSize.x == 0 || textureSize.y == 0 )                               ",
        "      {                                                                             ",
        "          return false;                                                             ",
        "      }                                                                             ",
        "      else                                                                          ",
        "      {                                                                             ",
        "          float j = floor( index / textureSize.x );                                 ",
        "          float i = index - ( j * textureSize.x );                                  ",
        "          vec2 st = vec2( i, j ) / textureSize;                                     ",
        "          int value = texture2D( setTexture, st ).r;                                ",
        "          return ( value != 0 );                                                    ",
        "      }                                                                             ",
        "  }                                                                                 ",
        "                                                                                    ",
        "  uniform vec4 AXIS_RECT;                                                           ",
        "  uniform isampler2D HIGHLIGHT_SET;                                                 ",
        "                                                                                    ",
        "  in vec4 inIconVertex;                                                             ",
        "                                                                                    ",
        "  out float vHighlight;                                                             ",
        "  out float vRotation_CCWRAD;                                                       ",
        "                                                                                    ",
        "  void main( )                                                                      ",
        "  {                                                                                 ",
        "      float featureNum = inIconVertex.z;                                            ",
        "      vHighlight = ( setContains( HIGHLIGHT_SET, featureNum ) ? 1.0 : 0.0 );        ",
        "                                                                                    ",
        "      vRotation_CCWRAD = inIconVertex.w;                                            ",
        "                                                                                    ",
        "      vec2 xy_AXIS = inIconVertex.xy;                                               ",
        "      gl_Position.xy = axisXyToNdc( xy_AXIS, AXIS_RECT );                           ",
        "      gl_Position.zw = vec2( 0.0, 1.0 );                                            ",
        "  }                                                                                 ",
        "                                                                                    " );

    public static final String iconGeomShader_GLSL = join( "\n",
        "                                                                                      ",
        "  #version 150                                                                        ",
        "                                                                                      ",
        "  layout( points ) in;                                                                ",
        "  layout( triangle_strip, max_vertices = 4 ) out;                                     ",
        "                                                                                      ",
        "  vec2 rotate( float x, float y, float cosR, float sinR )                             ",
        "  {                                                                                   ",
        "      return vec2( x*cosR - y*sinR, x*sinR + y*cosR );                                ",
        "  }                                                                                   ",
        "                                                                                      ",
        "  uniform vec2 VIEWPORT_SIZE_PX;                                                      ",
        "  uniform vec2 IMAGE_SIZE_PX;                                                         ",
        "  uniform vec2 IMAGE_ALIGN;                                                           ",
        "  uniform vec4 IMAGE_BOUNDS;                                                          ",
        "  uniform float HIGHLIGHT_SCALE;                                                      ",
        "                                                                                      ",
        "  in float vHighlight[];                                                              ",
        "  in float vRotation_CCWRAD[];                                                        ",
        "                                                                                      ",
        "  out vec2 gAtlasCoords;                                                              ",
        "                                                                                      ",
        "  void main( )                                                                        ",
        "  {                                                                                   ",
        "      vec2 p = gl_in[ 0 ].gl_Position.xy;                                             ",
        "                                                                                      ",
        "      float rotation_CCWRAD = vRotation_CCWRAD[ 0 ];                                  ",
        "      float cosR = cos( rotation_CCWRAD );                                            ",
        "      float sinR = sin( rotation_CCWRAD );                                            ",
        "                                                                                      ",
        "      vec2 offsetA_PX = -IMAGE_ALIGN * IMAGE_SIZE_PX;                                 ",
        "      vec2 offsetB_PX = offsetA_PX + IMAGE_SIZE_PX;                                   ",
        "      bool highlight = ( vHighlight[ 0 ] >= 0.5 );                                    ",
        "      if ( highlight )                                                                ",
        "      {                                                                               ",
        "          offsetA_PX *= HIGHLIGHT_SCALE;                                              ",
        "          offsetB_PX *= HIGHLIGHT_SCALE;                                              ",
        "      }                                                                               ",
        "                                                                                      ",
        "      vec2 pxToNdc = 2.0 / VIEWPORT_SIZE_PX;                                          ",
        "                                                                                      ",
        "      float sMin = IMAGE_BOUNDS.s;                                                    ",
        "      float tMin = IMAGE_BOUNDS.t;                                                    ",
        "      float sMax = IMAGE_BOUNDS.p;                                                    ",
        "      float tMax = IMAGE_BOUNDS.q;                                                    ",
        "                                                                                      ",
        "                                                                                      ",
        "      gl_Position.xy = p + rotate( offsetA_PX.x, offsetB_PX.y, cosR, sinR )*pxToNdc;  ",
        "      gl_Position.zw = vec2( 0.0, 1.0 );                                              ",
        "      gAtlasCoords = vec2( sMin, tMin );                                              ",
        "      EmitVertex( );                                                                  ",
        "                                                                                      ",
        "      gl_Position.xy = p + rotate( offsetB_PX.x, offsetB_PX.y, cosR, sinR )*pxToNdc;  ",
        "      gl_Position.zw = vec2( 0.0, 1.0 );                                              ",
        "      gAtlasCoords = vec2( sMax, tMin );                                              ",
        "      EmitVertex( );                                                                  ",
        "                                                                                      ",
        "      gl_Position.xy = p + rotate( offsetA_PX.x, offsetA_PX.y, cosR, sinR )*pxToNdc;  ",
        "      gl_Position.zw = vec2( 0.0, 1.0 );                                              ",
        "      gAtlasCoords = vec2( sMin, tMax );                                              ",
        "      EmitVertex( );                                                                  ",
        "                                                                                      ",
        "      gl_Position.xy = p + rotate( offsetB_PX.x, offsetA_PX.y, cosR, sinR )*pxToNdc;  ",
        "      gl_Position.zw = vec2( 0.0, 1.0 );                                              ",
        "      gAtlasCoords = vec2( sMax, tMax );                                              ",
        "      EmitVertex( );                                                                  ",
        "                                                                                      ",
        "                                                                                      ",
        "      EndPrimitive( );                                                                ",
        "  }                                                                                   ",
        "                                                                                      " );

    public static final String iconFragShader_GLSL = join( "\n",
        "                                                               ",
        "  #version 150                                                 ",
        "                                                               ",
        "  uniform sampler2D ATLAS;                                     ",
        "                                                               ",
        "  in vec2 gAtlasCoords;                                        ",
        "                                                               ",
        "  out vec4 outRgba;                                            ",
        "                                                               ",
        "  void main( )                                                 ",
        "  {                                                            ",
        "      outRgba = texture2D( ATLAS, gAtlasCoords );              ",
        "  }                                                            ",
        "                                                               " );


    protected static final int inIconVertex = 1;


    public static int buildIconProgram( GL3 gl )
    {
        IntsArray shaders = new IntsArray( );
        try
        {
            shaders.append( compileShader( gl, GL_VERTEX_SHADER,   iconVertShader_GLSL ) );
            shaders.append( compileShader( gl, GL_GEOMETRY_SHADER, iconGeomShader_GLSL ) );
            shaders.append( compileShader( gl, GL_FRAGMENT_SHADER, iconFragShader_GLSL ) );

            int program = gl.glCreateProgram( );
            for ( int s : shaders.a ) gl.glAttachShader( program, s );
            try
            {
                gl.glBindAttribLocation( program, inIconVertex, "inIconVertex" );

                gl.glLinkProgram( program );
                int[] linkStatus = new int[ 1 ];
                gl.glGetProgramiv( program, GL_LINK_STATUS, linkStatus, 0 );
                if ( linkStatus[ 0 ] != GL_TRUE ) throw new RuntimeException( getProgramInfoLog( gl, program ) );

                return program;
            }
            finally
            {
                for ( int s : shaders.a ) gl.glDetachShader( program, s );
            }
        }
        finally
        {
            for ( int i = 0; i < shaders.n; i++ )
            {
                gl.glDeleteShader( shaders.v( i ) );
            }
        }
    }


    public static class DncIconProgram
    {
        public final int programHandle;

        public final int AXIS_RECT;
        public final int VIEWPORT_SIZE_PX;

        public final int ATLAS;
        public final int IMAGE_BOUNDS;
        public final int IMAGE_SIZE_PX;
        public final int IMAGE_ALIGN;

        public final int HIGHLIGHT_SET;
        public final int HIGHLIGHT_SCALE;

        public final int inIconVertex;

        public DncIconProgram( GL3 gl )
        {
            this.programHandle = buildIconProgram( gl );

            this.AXIS_RECT = gl.glGetUniformLocation( programHandle, "AXIS_RECT" );
            this.VIEWPORT_SIZE_PX = gl.glGetUniformLocation( programHandle, "VIEWPORT_SIZE_PX" );

            this.ATLAS = gl.glGetUniformLocation( programHandle, "ATLAS" );
            this.IMAGE_BOUNDS = gl.glGetUniformLocation( programHandle, "IMAGE_BOUNDS" );
            this.IMAGE_SIZE_PX = gl.glGetUniformLocation( programHandle, "IMAGE_SIZE_PX" );
            this.IMAGE_ALIGN = gl.glGetUniformLocation( programHandle, "IMAGE_ALIGN" );

            this.HIGHLIGHT_SET = gl.glGetUniformLocation( programHandle, "HIGHLIGHT_SET" );
            this.HIGHLIGHT_SCALE = gl.glGetUniformLocation( programHandle, "HIGHLIGHT_SCALE" );

            this.inIconVertex = DncIconShaders.inIconVertex;
        }
    }

}
