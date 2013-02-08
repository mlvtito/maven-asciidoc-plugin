/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.rwx.maven.asciidoc.services;

import net.rwx.maven.asciidoc.Document;
import org.apache.maven.plugin.logging.Log;

/**
 *
 * @author Arnaud Fonce <arnaud.fonce@r-w-x.net>
 */
public interface ServiceOrchestrator {
    
    void setLogger( Log logger );
    
    void execute( Document document );
    
}
