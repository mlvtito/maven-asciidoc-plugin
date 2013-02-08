/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.rwx.maven.asciidoc.services.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import net.rwx.maven.asciidoc.services.FopService;
import net.rwx.maven.asciidoc.utils.FileUtils;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;

/**
 *
 * @author mlvtito
 */
public class FopServiceImpl extends RootServiceImpl implements FopService {
    
    @Override
    public void execute( String input, String output ) throws FileNotFoundException, FOPException, TransformerConfigurationException, TransformerException, IOException {
        OutputStream out = null;
        try {
            
            FopFactory fopFactory = FopFactory.newInstance();

            File outputFile = new File( output );
            if( outputFile.exists() ) {
                FileUtils.forceDelete( outputFile );
            }
            out = new BufferedOutputStream( new FileOutputStream( outputFile) );

            Fop fop = fopFactory.newFop( MimeConstants.MIME_PDF, out );

            TransformerFactory factory = TransformerFactory.newInstance(); 
            Transformer transformer = factory.newTransformer(); 

            File inputFile = new File( input );
            FileUtils.copyFileToDirectory( inputFile, new File("/tmp") );
            Source src = new StreamSource( inputFile );
            Result res = new SAXResult( fop.getDefaultHandler() );

            transformer.transform( src, res );
        } finally { 
            if( out != null ) {
                out.close();
            }
        }
    }
}
