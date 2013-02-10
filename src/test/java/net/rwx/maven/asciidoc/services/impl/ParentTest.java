/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.rwx.maven.asciidoc.services.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author Arnaud Fonce <arnaud.fonce@r-w-x.net>
 */
public class ParentTest {
    protected void writeFileFromResource( String resourceName, String fileName ) throws IOException {
        ClassLoader loader = this.getClass().getClassLoader();
        InputStream is = loader.getResourceAsStream( resourceName );
        
        File outputFile = new File( fileName );
        outputFile.createNewFile();
        OutputStream os = new FileOutputStream( outputFile );
        
        byte[] buffer = new byte[1024];
        int nbBytes = is.read( buffer );
        do {
            os.write( buffer, 0, nbBytes );
            nbBytes = is.read( buffer );
        }while( nbBytes > 0 );
        
        os.close();
        is.close();
    }
}
