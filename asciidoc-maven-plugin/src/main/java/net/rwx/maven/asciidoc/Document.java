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
