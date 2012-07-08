package net.rwx.maven.asciidoc;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the
 * License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
import java.io.IOException;
import java.util.List;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

/**
 * Goal to compile Asciidoc documents.
 *
 * @goal asciidoc
 */
public class AsciidocMojo extends AbstractMojo {

    /**
     * @parameter default-value="article"
     */
    private String defaultDocumentType;
    /**
     * @parameter default-value="${project.build.directory}/asciidoc"
     */
    private String defaultOutputPath;
    /**
     * @parameter default-value="html5"
     */
    private String defaultBackend;
    /**
     * @parameter
     */
    private List<Document> documents;
    
    private void computeDocument( Document document ) {
        
        if ( document.getBackend() == null ) {
            document.setBackend( defaultBackend );
        }
        
        if ( document.getDocumentType() == null ) {
            document.setDocumentType( defaultDocumentType );
        }
        
        if ( document.getOutputPath() == null ) {
            document.setOutputPath( defaultOutputPath );
        }
    }
    
    @Override
    public void execute() throws MojoExecutionException {
        
        
        getLog().info( "Starting asciidoc compilation" );
        getLog().info( "Default document type : " + defaultDocumentType );
        getLog().info( "Default output path : " + defaultOutputPath );
        getLog().info( "Default backend : " + defaultBackend );
        getLog().info( "There is  " + documents.size() + " documents to compile" );
        
        AsciidocCompiler compiler = new AsciidocCompiler();
        for ( Document document : documents ) {
            
            computeDocument( document );
            getLog().info( "Starting compilation of " + document );
            
            try {
                compiler.setDocument( document );
                compiler.execute();
            } catch ( Exception ex ) {
                getLog().error( "Unable to compile a document", ex );
            }
        }
        /*
         * PySystemState state = new PySystemState(); state.argv.append( new PyString( "-b" ) ); state.argv.append( new PyString( "docbook" ) );
         *
         * state.argv.append( new PyString( "/workspaces/netbeans/rwx/maven-asciidoc-plugin/test-project/test.txt" ) ); PythonInterpreter interp = new
         * PythonInterpreter( null, state );
         *
         * ClassLoader loader = this.getClass().getClassLoader(); InputStream is = loader.getResourceAsStream( "asciidoc/asciidoc.py" ); InputStream isFo =
         * loader.getResourceAsStream( "asciidoc/docbook-xsl/fo.xsl" );
         *
         * interp.execfile( is );
         *
         * File xmlFile = new File( "/workspaces/netbeans/rwx/maven-asciidoc-plugin/test-project/test.xml" ); File xsltFile = new File(
         * "/etc/asciidoc/docbook-xsl/fo.xsl" ); File resultFile = new File( "/workspaces/netbeans/rwx/maven-asciidoc-plugin/test-project/test.fo" );
         *
         * Source xmlSource = new StreamSource( xmlFile ); Source xsltSource = new StreamSource( xsltFile ); Result result = null; try { result = new
         * StreamResult( new FileOutputStream( resultFile ) ); } catch ( FileNotFoundException ex ) { Logger.getLogger( AsciidocMojo.class.getName() ).log(
         * Level.SEVERE, null, ex ); }
         *
         * // create an instance of TransformerFactory TransformerFactory transFact = TransformerFactory.newInstance();
         *
         * Transformer trans; try { trans = transFact.newTransformer( xsltSource ); trans.transform( xmlSource, result );
         *
         * } catch ( TransformerConfigurationException ex ) { Logger.getLogger( AsciidocMojo.class.getName() ).log( Level.SEVERE, null, ex ); } catch (
         * TransformerException ex ) { Logger.getLogger( AsciidocMojo.class.getName() ).log( Level.SEVERE, null, ex );
        }
         */





        // Step 1: Construct a FopFactory
// (reuse if you plan to render multiple documents!)
       /*
         * FopFactory fopFactory = FopFactory.newInstance();
         *
         * // Step 2: Set up output stream. // Note: Using BufferedOutputStream for performance reasons (helpful with FileOutputStreams). OutputStream out =
         * null; try { out = new BufferedOutputStream( new FileOutputStream( new File( "/workspaces/netbeans/rwx/maven-asciidoc-plugin/test-project/test.pdf" )
         * ) ); } catch ( FileNotFoundException ex ) { Logger.getLogger( AsciidocMojo.class.getName() ).log( Level.SEVERE, null, ex ); }
         *
         * try { // Step 3: Construct fop with desired output format Fop fop = fopFactory.newFop( MimeConstants.MIME_PDF, out );
         *
         * // Step 4: Setup JAXP using identity transformer TransformerFactory factory = TransformerFactory.newInstance(); Transformer transformer =
         * factory.newTransformer(); // identity transformer
         *
         * // Step 5: Setup input and output for XSLT transformation // Setup input stream Source src = new StreamSource( new File(
         * "/workspaces/netbeans/rwx/maven-asciidoc-plugin/test-project/test.fo" ) );
         *
         * // Resulting SAX events (the generated FO) must be piped through to FOP Result res = new SAXResult( fop.getDefaultHandler() );
         *
         * // Step 6: Start XSLT transformation and FOP processing transformer.transform( src, res );
         *
         * } catch( Exception ex ) { Logger.getLogger( AsciidocMojo.class.getName() ).log( Level.SEVERE, null, ex ); } finally { try { //Clean-up out.close(); }
         * catch ( IOException ex ) { Logger.getLogger( AsciidocMojo.class.getName() ).log( Level.SEVERE, null, ex ); }
        }
         */
    }
}
