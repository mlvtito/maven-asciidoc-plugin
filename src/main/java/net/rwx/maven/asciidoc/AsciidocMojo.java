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
package net.rwx.maven.asciidoc;

import net.rwx.maven.asciidoc.configuration.Document;
import com.google.inject.Guice;
import com.google.inject.Injector;
import java.io.File;
import java.util.List;
import net.rwx.maven.asciidoc.services.ServiceOrchestrator;
import net.rwx.maven.asciidoc.services.modules.AsciidocModule;
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
     * Default document type which apply on each document.
     * @parameter default-value="article"
     * @since 0.1
     */
    private String defaultDocumentType;
    /**
     * Default target path for generated documents.
     * @parameter default-value="${project.build.directory}/asciidoc"
     * @since 0.1
     */
    private String defaultOutputPath;
    /**
     * Default backend which apply on each document.
     * @parameter default-value="html5"
     * @since 0.1
     */
    private String defaultBackend;
    /**
     * @parameter default-value="${project.file}"
     * @readonly
     */
    private File projectFile;
    
    /**
     * The documents list to generate.
     * @parameter
     * @since 0.1
     */
    private List<Document> documents;
    
    @Override
    public void execute() throws MojoExecutionException {

        getLog().info( "Starting asciidoc compilation" );
        getLog().info( "Default document type : " + defaultDocumentType );
        getLog().info( "Default output path : " + defaultOutputPath );
        getLog().info( "Default backend : " + defaultBackend );
        
        if( documents == null ) {
            getLog().info( "Nothing to be done" );
            return;
        }

        ServiceOrchestrator orchestrator = getServiceOrchestrator();
        orchestrator.setLogger( getLog() );
        
        for ( Document document : documents ) {
            computeDocument( document );
            orchestrator.execute( document );
        }
    }
    
    private String determineValue( String inputValue, String defaultValue ) {
        return (inputValue == null)?defaultValue:inputValue;
    }
    
    private void computeDocument( Document document ) {
        document.setBackend( determineValue( document.getBackend(), defaultBackend) );
        document.setDocumentType( determineValue( document.getDocumentType(), defaultDocumentType ) );
        document.setOutputPath( determineValue( document.getOutputPath(), defaultOutputPath ) );
        
        String realPath = projectFile.getParent() + File.separator + document.getPath();
        document.setPath( realPath );
    }
    
    private ServiceOrchestrator getServiceOrchestrator() throws MojoExecutionException {
        Injector injector = Guice.createInjector( new AsciidocModule() );
        return injector.getInstance( ServiceOrchestrator.class );
    }

    public String getDefaultDocumentType() {
        return defaultDocumentType;
    }

    public void setDefaultDocumentType(String defaultDocumentType) {
        this.defaultDocumentType = defaultDocumentType;
    }

    public String getDefaultOutputPath() {
        return defaultOutputPath;
    }

    public void setDefaultOutputPath(String defaultOutputPath) {
        this.defaultOutputPath = defaultOutputPath;
    }

    public String getDefaultBackend() {
        return defaultBackend;
    }

    public void setDefaultBackend(String defaultBackend) {
        this.defaultBackend = defaultBackend;
    }

    public File getProjectFile() {
        return projectFile;
    }

    public void setProjectFile(File projectFile) {
        this.projectFile = projectFile;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }
    
    
}
