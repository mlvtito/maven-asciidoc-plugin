package net.rwx.maven.asciidoc;

/**
 *
 * @author Arnaud Fonce <arnaud.fonce@r-w-x.net>
 */
public class Document {

    private String title;
    private String path;
    private String documentType;
    private String backend;
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
