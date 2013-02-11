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
 * along with Maven Asciidoc Plugin.  If not, see <http://www.gnu.org/licenses/>
 */
package net.rwx.maven.asciidoc.backends;

import java.io.File;
import java.io.IOException;
import net.rwx.maven.asciidoc.utils.FileUtils;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author Arnaud Fonce <arnaud.fonce@r-w-x.net>
 */
public class AsciidocBackend {

    private String name;
    private String extension;
    private AsciidocBackendTransformation[] transformations;
    private File temporaryDirectory;

    public AsciidocBackend( String name, String extension ) throws IOException {

        this.name = name;
        this.extension = extension;
        this.transformations = new AsciidocBackendTransformation[0];

        
        temporaryDirectory = File.createTempFile( "temp", Long.toString( System.nanoTime() ) );
        temporaryDirectory.delete();
        temporaryDirectory.mkdir();
        //temporaryDirectory.deleteOnExit();
    }

    public AsciidocBackend( String name, String extension, String extension2, String stylesheet ) throws IOException {

        this.name = name;
        this.extension = extension;
        this.transformations = new AsciidocBackendTransformation[1];
        this.transformations[0] = new AsciidocBackendTransformation( extension2, stylesheet );

        temporaryDirectory = File.createTempFile( "temp", Long.toString( System.nanoTime() ) );
        temporaryDirectory.delete();
        temporaryDirectory.mkdir();
        //temporaryDirectory.deleteOnExit();
    }

    public AsciidocBackend( String name, String extension, String extension2, String stylesheet, String extension3, String stylesheet2 ) throws IOException {

        this.name = name;
        this.extension = extension;
        this.transformations = new AsciidocBackendTransformation[2];
        this.transformations[0] = new AsciidocBackendTransformation( extension2, stylesheet );
        this.transformations[1] = new AsciidocBackendTransformation( extension3, stylesheet2 );

        temporaryDirectory = File.createTempFile( "temp", Long.toString( System.nanoTime() ) );
        temporaryDirectory.delete();
        temporaryDirectory.mkdir();
        //temporaryDirectory.deleteOnExit();
    }

    public String getOutputFile( String inputName ) throws IOException {

        String atomicName = FilenameUtils.removeExtension( inputName );
        return FileUtils.getAsciidocTemporaryPath( atomicName, extension );
    }

    public String getOutputFilePDF( String inputName ) throws IOException {
        
        String atomicName = FilenameUtils.removeExtension( inputName );
        return FileUtils.getAsciidocTemporaryPath( atomicName, ".pdf" );
    }
    
    public AsciidocBackendTransformation[] getTransformations() {
        return transformations;
    }

    public void setTransformations( AsciidocBackendTransformation[] transformations ) {
        this.transformations = new AsciidocBackendTransformation[ transformations.length];
        for( int i=0; i < transformations.length; i++ ) {
            this.transformations[i] = transformations[i];
        }
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension( String extension ) {
        this.extension = extension;
    }
}
