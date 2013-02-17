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
package net.rwx.maven.asciidoc.services.modules;

import com.google.inject.AbstractModule;
import net.rwx.maven.asciidoc.services.AsciidocService;
import net.rwx.maven.asciidoc.services.FopService;
import net.rwx.maven.asciidoc.services.ServiceOrchestrator;
import net.rwx.maven.asciidoc.services.TransformationService;
import net.rwx.maven.asciidoc.services.impl.AsciidocServiceImpl;
import net.rwx.maven.asciidoc.services.impl.FopServiceImpl;
import net.rwx.maven.asciidoc.services.impl.ServiceOrchestratorImpl;
import net.rwx.maven.asciidoc.services.impl.TransformationServiceImpl;

/**
 * Configuration for dependency injection.
 * 
 * @author Arnaud Fonce <arnaud.fonce@r-w-x.net>
 */
public class AsciidocModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ServiceOrchestrator.class).to(ServiceOrchestratorImpl.class);
        bind(AsciidocService.class).to(AsciidocServiceImpl.class);
        bind(TransformationService.class).to(TransformationServiceImpl.class);
        bind(FopService.class).to(FopServiceImpl.class);
    }
    
}
