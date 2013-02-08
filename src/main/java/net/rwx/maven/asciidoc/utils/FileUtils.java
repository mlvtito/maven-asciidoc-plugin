/*
 * This file is part of "Maven Asciidoc Plugin".
 * 
 * "Maven Asciidoc Plugin" is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * "Maven Asciidoc Plugin" is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Maven Asciidoc Plugin.  If not, see <http://www.gnu.org/licenses/>
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
        
        File destFile = FileUtils.getFile( directory, file.getName() );
        if ( destFile.exists() ) {
            forceDelete( destFile );
        }
        
        moveFileToDirectory( file, directory, true );
    }
    
    public static String getTemporaryDirectory() throws IOException {
        StringBuilder builder = new StringBuilder();
        
        builder.append( getTempDirectoryPath() );
        builder.append( File.separator );
        builder.append( "asciidoc-maven-plugin" );
        builder.append( Long.toString(System.nanoTime()) );
        
        File directory = new File( builder.toString() );
        // forceMkdir( directory );
        // forceDeleteOnExit( directory );
        
        return directory.getAbsolutePath();
    }
    public static String getTemporayAsciidoc( ) throws IOException {

        if( temporaryDirectory == null )
        {
            StringBuilder builder = new StringBuilder();
        
            builder.append( getTempDirectoryPath() );
            builder.append( File.separator );
            builder.append( "asciidoc-maven-plugin" );
            builder.append( Long.toString(System.nanoTime()) );
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
