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
 * along with Foobar.  If not, see <http://www.gnu.org/licenses/>
 * 
 * Copyright 2012 Arnaud Fonce <arnaud.fonce@r-w-x.net>
 */
package net.rwx.maven.asciidoc;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

/**
 * Goal to compile Asciidoc documents.
 * 
 * @author Arnaud Fonce <arnaud.fonce@r-w-x.net>
 * 
 * @goal asciidoc
 */
public class AsciidocMojo extends AbstractMojo {

    /**
     * @parameter default-value="article"
     */
    private String defaultDocumentType;
    /**
     * @parameter default-value="${project.build.directory}/asciidoc"
     */
    private String defaultOutputPath;
    /**
     * @parameter default-value="html5"
     */
    private String defaultBackend;
    /**
     * @parameter default-value="${project.file}"
     */
    private File projectFile;
    /**
     * @parameter
     */
    private List<Document> documents;
    
    private void computeDocument( Document document ) {
        
        if ( document.getBackend() == null ) {
            document.setBackend( defaultBackend );
        }
        
        if ( document.getDocumentType() == null ) {
            document.setDocumentType( defaultDocumentType );
        }
        
        if ( document.getOutputPath() == null ) {
            document.setOutputPath( defaultOutputPath );
        }
        
        String realPath = projectFile.getParent() + File.separator + document.getPath();
        document.setPath( realPath );
    }
    
    private AsciidocCompiler getCompiler() throws MojoExecutionException {
        
        try {
            getLog().info( "Unpacking Asciidoc" );
            return new AsciidocCompiler();
        }catch( IOException ioe ) {
            throw new MojoExecutionException( ioe.getMessage(), ioe);
        }
    }
    
    @Override
    public void execute() throws MojoExecutionException {
        
        
        getLog().info( "Starting asciidoc compilation" );
        getLog().info( "Default document type : " + defaultDocumentType );
        getLog().info( "Default output path : " + defaultOutputPath );
        getLog().info( "Default backend : " + defaultBackend );
        getLog().info( "Project file : " + projectFile );
        
        if( documents == null ) {
            getLog().info( "Nothing to be done" );
            return;
        }
        
        getLog().info( "There is  " + documents.size() + " documents to compile" );
        
        AsciidocCompiler compiler = getCompiler();
        for ( Document document : documents ) {
            
            computeDocument( document );
            getLog().info( "Starting compilation of " + document );
            
            try {
                compiler.setDocument( document );
                compiler.execute();
            } catch ( Exception ex ) {
                getLog().error( "Unable to compile a document", ex );
            }
        }
    }
}
