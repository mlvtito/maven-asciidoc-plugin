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

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import net.rwx.maven.asciidoc.backends.Backend;
import net.rwx.maven.asciidoc.configuration.Document;
import net.rwx.maven.asciidoc.constants.Extension;
import net.rwx.maven.asciidoc.services.FopService;
import net.rwx.maven.asciidoc.utils.FileUtils;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;

/**
 *
 * @author Arnaud Fonce <arnaud.fonce@r-w-x.net>
 */
public class FopServiceImpl extends RootServiceImpl implements FopService {
    
    @Override
    public void execute( String inputPath, Document document, Backend backend ) throws Exception {
        setOuputPath(inputPath, backend);
        if( backend.isIsFopTransformation() ) {
            OutputStream out = null;
            try {

                FopFactory fopFactory = FopFactory.newInstance();

                File outputFile = new File( getOuputPath() );
                if( outputFile.exists() ) {
                    FileUtils.forceDelete( outputFile );
                }
                out = new BufferedOutputStream( new FileOutputStream( outputFile) );

                Fop fop = fopFactory.newFop( MimeConstants.MIME_PDF, out );

                TransformerFactory factory = TransformerFactory.newInstance(); 
                Transformer transformer = factory.newTransformer(); 

                File inputFile = new File( inputPath );
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

    @Override
    protected void setOuputPath(String inputPath, Backend backend) {
        if( backend.isIsFopTransformation() ) {
            setOutputPath(inputPath, Extension.PDF);
        }else {
            setOutputPath(inputPath);
        }
    }
}
