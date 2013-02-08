/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.rwx.maven.asciidoc.services.impl;

import net.rwx.maven.asciidoc.services.RootService;
import org.apache.maven.plugin.logging.Log;

/**
 *
 * @author Arnaud Fonce <arnaud.fonce@r-w-x.net>
 */
public class RootServiceImpl implements RootService {

    protected Log logger;
    
    @Override
    public void setLogger(Log logger) {
        this.logger = logger;
    }
    
}
