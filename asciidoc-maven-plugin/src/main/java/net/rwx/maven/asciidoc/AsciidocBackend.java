package net.rwx.maven.asciidoc;

/**
 *
 * @author Arnaud Fonce <arnaud.fonce@r-w-x.net>
 */
public class AsciidocBackend {

    private String name;
    private Integer numberOfXSLTransformation;
    private String[] extensions;

    public AsciidocBackend( String name, String extension ) {
        
        this.name = name;
        this.numberOfXSLTransformation = 0;
        this.extensions = new String[1];
        this.extensions[0] = extension;
    }
    
    public AsciidocBackend( String name, String extension, String extension2 ) {
        
        this.name = name;
        this.numberOfXSLTransformation = 1;
        this.extensions = new String[2];
        this.extensions[0] = extension;
        this.extensions[1] = extension2;
    }
    
    public AsciidocBackend( String name, String extension, String extension2, String extension3 ) {
        
        this.name = name;
        this.numberOfXSLTransformation = 2;
        this.extensions = new String[3];
        this.extensions[0] = extension;
        this.extensions[1] = extension2;
        this.extensions[2] = extension3;
    }
    
    public String[] getExtensions() {
        return extensions;
    }

    public void setExtensions( String[] extensions ) {
        this.extensions = extensions;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public Integer getNumberOfXSLTransformation() {
        return numberOfXSLTransformation;
    }

    public void setNumberOfXSLTransformation( Integer numberOfXSLTransformation ) {
        this.numberOfXSLTransformation = numberOfXSLTransformation;
    }
}
