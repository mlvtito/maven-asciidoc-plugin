package net.rwx.maven.asciidoc.utils;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author Arnaud Fonce <a.fonce@ideotechnologies.com>
 */
public class FileUtils extends org.apache.commons.io.FileUtils {

    private static File temporaryDirectory;
    
    public static String getAsciidocTemporaryPath( String filename, String extension ) throws IOException {
    
        File file = new File( filename );
        
        StringBuilder builder = new StringBuilder();
        
        builder.append( getTempDirectoryPath() );
        builder.append( File.separator );
        builder.append( "asciidoc-maven-plugin" );
        
        if( temporaryDirectory == null )
        {
            temporaryDirectory = new File( builder.toString() );
            forceMkdir( temporaryDirectory );
            forceDeleteOnExit( temporaryDirectory );
        }
        
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
}
