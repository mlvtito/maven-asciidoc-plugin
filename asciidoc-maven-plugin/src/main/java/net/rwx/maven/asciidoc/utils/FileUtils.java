/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package net.rwx.maven.asciidoc.utils;

import java.io.*;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;

/**
 *
 * @author Arnaud Fonce <arnaud.fonce@r-w-x.net>
 */
public class FileUtils extends org.apache.commons.io.FileUtils {

    private static File temporaryDirectory;
    
    public static String getAsciidocTemporaryPath( String filename, String extension ) throws IOException {
    
        File file = new File( filename );
        
        StringBuilder builder = new StringBuilder();
        
        builder.append( getTemporayAsciidoc() );
        builder.append( File.separator );
        builder.append( file.getName() );
        builder.append( extension );
        
        return builder.toString();
    }
    
    public static void moveFileToDirectory( String fileName, String directoryName ) throws IOException {
        
        File file = new File( fileName );
        File directory = new File( directoryName );
        
        File destFile = FileUtils.getFile( directory, fileName );
        if ( destFile.exists() )
        {
            destFile.delete();
        }
        
        moveFileToDirectory( file, directory, true );
    }
    
    public static String getTemporayAsciidoc( ) throws IOException {

        if( temporaryDirectory == null )
        {
            StringBuilder builder = new StringBuilder();
        
            builder.append( getTempDirectoryPath() );
            builder.append( File.separator );
            builder.append( "asciidoc-maven-plugin" );
            temporaryDirectory = new File( builder.toString() );
            forceMkdir( temporaryDirectory );
            forceDeleteOnExit( temporaryDirectory );
        }
        // File f = File.createTempFile("tempAsciidoc", Long.toString(System.nanoTime()));
        // forceMkdir( f );
        // forceDeleteOnExit( f );
        return temporaryDirectory.getAbsolutePath();

    }
    
    public static String uncompress( InputStream is, String destination ) throws IOException {
        
        BufferedInputStream in = new BufferedInputStream( is );
        GzipCompressorInputStream gzIn = new GzipCompressorInputStream(in);
        TarArchiveInputStream tarInput = new TarArchiveInputStream( gzIn );
        
        TarArchiveEntry entry = tarInput.getNextTarEntry();
        do {
            File f = new File( destination + "/" + entry.getName() );
            FileUtils.forceMkdir( f.getParentFile() );
            
            if( ! f.isDirectory() ) {
                OutputStream os = new FileOutputStream( f );
                byte[] content = new byte[ (int)entry.getSize() ];
                int byteRead = 0;
                while( byteRead < entry.getSize() ) {
                    byteRead += tarInput.read(content, byteRead, content.length - byteRead);
                    os.write( content, 0, byteRead );
                }

                os.close();
                forceDeleteOnExit( f );
            }
            entry = tarInput.getNextTarEntry();
        }while( entry != null );

        gzIn.close();
        
        return destination;
    }
}
