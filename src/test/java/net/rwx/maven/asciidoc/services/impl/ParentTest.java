/*
 * Copyright (C) 2013 Room Work eXperience
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
