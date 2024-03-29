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
package net.rwx.maven.asciidoc.configuration;

/**
 * A configuration bean to describe a document to generate.
 * 
 * @author Arnaud Fonce <arnaud.fonce@r-w-x.net>
 */
public class Document {

    /** 
     * Document title. 
     * @parameter
     */
    private String title;
    
    /** 
     * Document source path. 
     * @parameter
     */
    private String path;
    
    /** 
     * Document type. 
     * Sould be article or book. 
     * @parameter
     */
    private String documentType;
    
    /** 
     * Document backend. 
     * Should be one of backend defined in AsciidocBackendSingleton. 
     * @parameter
     */
    private String backend;
    
    /** 
     * Output path for the generated document. 
     * @parameter
     */
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
