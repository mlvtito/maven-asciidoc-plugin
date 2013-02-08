/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.rwx.maven.asciidoc.services;

import org.apache.maven.plugin.logging.Log;

/**
 *
 * @author Arnaud Fonce <arnaud.fonce@r-w-x.net>
 */
public interface RootService {
    
    /**
     * Set logger
     * 
     * @param logger 
     */
    void setLogger( Log logger );
}
