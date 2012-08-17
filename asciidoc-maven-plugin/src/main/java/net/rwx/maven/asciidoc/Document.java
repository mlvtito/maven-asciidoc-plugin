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

/**
 * A configuration bean for this Maven mojo.
 * 
 * @author Arnaud Fonce <arnaud.fonce@r-w-x.net>
 */
public class Document {

    /** Document title. */
    private String title;
    
    /** Docuement source path. */
    private String path;
    
    /** Document type. Sould be article or book. */
    private String documentType;
    
    /** Document backend. Should be one of backend defined in AsciidocBackendSingleton. */
    private String backend;
    
    /** Output path for the generated document. */
    private String outputPath;

    public String getBackend() {
        return backend;
    }

    public void setBackend( String backend ) {
        this.backend = backend;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType( String documentType ) {
        this.documentType = documentType;
    }

    public String getPath() {
        return path;
    }

    public void setPath( String path ) {
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle( String title ) {
        this.title = title;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath( String outputPath ) {
        this.outputPath = outputPath;
    }
    
    @Override
    public String toString() {
        
        StringBuilder builder = new StringBuilder();
        
        builder.append( "[title : ");
        builder.append( title );
        builder.append( ",path : ");
        builder.append( path );
        builder.append( ",backend : ");
        builder.append( backend );
        builder.append( ",documentType : ");
        builder.append( documentType );
        builder.append( ",outputPath : ");
        builder.append( outputPath );
        builder.append( "]");
        
        return builder.toString();
    }
}
