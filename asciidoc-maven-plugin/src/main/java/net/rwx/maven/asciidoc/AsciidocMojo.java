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
package net.rwx.maven.asciidoc;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the
 * License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
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
