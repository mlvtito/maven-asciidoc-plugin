package net.rwx.maven.asciidoc.utils;

import java.io.*;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;

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
    
    public static String getTemporayAsciidoc( ) throws IOException {
        
        return File.createTempFile("temp", Long.toString(System.nanoTime())).getName();

    }
    public static String uncompress( InputStream is ) throws IOException {
        
        BufferedInputStream in = new BufferedInputStream( is );
        GzipCompressorInputStream gzIn = new GzipCompressorInputStream(in);
        TarArchiveInputStream tarInput = new TarArchiveInputStream( gzIn );
        
        String tempDir = FileUtils.getTemporayAsciidoc();
        
        TarArchiveEntry entry = tarInput.getNextTarEntry();
        do {
            File f = new File( tempDir + "/" + entry.getName() );
            FileUtils.forceMkdir( f.getParentFile() );
            
            OutputStream os = new FileOutputStream( f );
            byte[] content = new byte[ (int)entry.getSize() ];
            int byteRead = 0;
            while( byteRead < entry.getSize() ) {
                byteRead += tarInput.read(content, byteRead, content.length - byteRead);
                os.write( content, 0, byteRead );
            }
            
            os.close();
            entry = tarInput.getNextTarEntry();
        }while( entry != null );

        gzIn.close();
        
        return tempDir;
    }
}
