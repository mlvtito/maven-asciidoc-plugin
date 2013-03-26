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

import com.google.inject.Inject;
import net.rwx.maven.asciidoc.configuration.Document;
import net.rwx.maven.asciidoc.backends.Backend;
import net.rwx.maven.asciidoc.backends.BackendService;
import net.rwx.maven.asciidoc.services.AsciidocService;
import net.rwx.maven.asciidoc.services.FopService;
import net.rwx.maven.asciidoc.services.ServiceOrchestrator;
import net.rwx.maven.asciidoc.services.TransformationService;
import net.rwx.maven.asciidoc.utils.FileUtils;
import org.apache.maven.plugin.logging.Log;

/**
 *
 * @author Arnaud Fonce <arnaud.fonce@r-w-x.net>
 */
public class ServiceOrchestratorImpl implements ServiceOrchestrator {
    
    private Log logger;
    
    @Inject
    private BackendService backendService;
    
    @Inject
    private AsciidocService asciidocService;
    
    @Inject
    private TransformationService transformationService;
    
    @Inject
    private FopService fopService;
    
    @Override
    public void execute( Document document ) {
        try {
            processDocument( document );
        } catch ( Exception ex ) {
            logger.error( "Unable to compile a document", ex );
        }
    }
    
    private void processDocument( Document document ) throws Exception {
        logger.info( String.format( "Starting Asciidoc compilation for document [%s]", document.getTitle() ) );
        
        String backendName = document.getBackend();
        Backend backend = backendService.getBackend( backendName );

        String input = document.getPath();
        asciidocService.execute( input, document, backend );
        transformationService.execute( asciidocService.getOuputPath(), document, backend );
        fopService.execute( transformationService.getOuputPath(), document, backend);
        
        FileUtils.moveFileToDirectory( fopService.getOuputPath(), document.getOutputPath() );
    }
    
    @Override
    public void setLogger( Log logger ) {
        this.logger = logger;
        asciidocService.setLogger( logger );
        transformationService.setLogger( logger );
        fopService.setLogger( logger );
    }
}
