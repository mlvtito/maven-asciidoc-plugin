package net.rwx.maven.asciidoc.backends;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author Arnaud Fonce <arnaud.fonce@r-w-x.net>
 */
public class AsciidocBackendTransformation {

    private String extension;
    private String xsl;

    public AsciidocBackendTransformation( String extension, String xsl ) {
        
        this.extension = extension;
        this.xsl = xsl;
    }
    
    public String getOutputFile( String inputName ) throws IOException {

        String atomicName = FilenameUtils.removeExtension( inputName );
        return net.rwx.maven.asciidoc.utils.FileUtils.getAsciidocTemporaryPath( atomicName, extension );
    }

    
    public String getExtension() {
        return extension;
    }

    public void setExtension( String extension ) {
        this.extension = extension;
    }

    public String getXsl() {
        return xsl;
    }

    public void setXsl( String xsl ) {
        this.xsl = xsl;
    }
}
