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
package net.rwx.maven.asciidoc.services.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import javax.xml.transform.TransformerException;
import net.rwx.maven.asciidoc.utils.FileUtils;
import org.apache.fop.apps.FOPException;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for FOP service.
 * 
 * @author Arnaud Fonce <arnaud.fonce@r-w-x.net>
 */
public class FopServiceImplTest extends ParentTest {
    
    byte[] pdfKey = {'%','P','D','F','-','1','.','4'};
    String input, output;
    
    @Before
    public void setUp() throws Exception {
        String temporaryDirectory = FileUtils.getTemporaryDirectory();
        output = temporaryDirectory + File.separator + "output.pdf";
        input = temporaryDirectory + File.separator + "test.fo";
        writeFileFromResource( "test.fo", input );
    }

    @Test
    public void testExecute() throws Exception {
        FopServiceImpl instance = new FopServiceImpl();
        instance.execute( input, output );
        
        byte[] buffer = FileUtils.readFileToByteArray( new File( output ) );
        for( int i=0; i < pdfKey.length; i++ ) {
            assertEquals( pdfKey[i], buffer[i] );
        }
    }
    
    @Test(expected = FileNotFoundException.class)
    public void testExecuteNoInput() throws IOException, FOPException, TransformerException {
        FopServiceImpl instance = new FopServiceImpl();
        String fakeInput = new File( input ).getParent() + File.separator + "fake.fo";
        instance.execute( fakeInput, output );
    }
    
    @Test(expected = TransformerException.class)
    public void testExecuteBadInputFormat() throws IOException, FOPException, TransformerException {
        FopServiceImpl instance = new FopServiceImpl();
        String fakeInput = new File( input ).getParent() + File.separator + "bad.fo";
        Writer writer = new FileWriter( new File( fakeInput ) );
        writer.write( "hopefully, I don't have to bother" );
        writer.close();
        instance.execute( fakeInput, output );
    }
    
    @Test
    public void testOutputAlreadyExists() throws IOException, FOPException, TransformerException {
        FopServiceImpl instance = new FopServiceImpl();
        String fakeOutput = new File( input ).getParent() + File.separator + "existingOutput.pdf";
        Writer writer = new FileWriter( new File( fakeOutput ) );
        writer.write( "hopefully, I don't have to bother" );
        writer.close();
        instance.execute( input, fakeOutput );
        
        byte[] buffer = FileUtils.readFileToByteArray( new File( fakeOutput ) );
        for( int i=0; i < pdfKey.length; i++ ) {
            assertEquals( pdfKey[i], buffer[i] );
        }
    }
}