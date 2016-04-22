package dev.atrujillofalcon.svn;

import dev.atrujillofalcon.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.context.support.ServletContextResource;

import java.io.File;

/**
 * Bean para que recargue los bundle en consonancia con lo que esté actualizado en el repositorio del SVN
 * En la configuración del bean es necesario setear <b>obligatoriamente</b> <u>svnProjectRoot</u>,<u>svnWebappPath</u> and <u>svnHost</u>
 * Los ficheros de idioma es necesario que sean almacenarlo bajo la carpeta webapps, sino al ser parte del classpath se cachean por siempre.
 *
 * @author Arnaldo Trujillo
 */
public class SVNReloadableResourceBundleMessageSource extends ReloadableResourceBundleMessageSource {

    private String svnWebappPath;
    private String svnHost;
    private String svnProtocol = "svn";
    private String tmpFolderName = "svn_co";
    private String svnUser;
    private String svnPass;
    private boolean nativeToAscii;
    private int svnPort = 3690;

    @Autowired
    private ResourceLoader resourceLoader;

    @Override
    protected PropertiesHolder refreshProperties(String filename, PropertiesHolder propHolder) {
        final Resource resourceLang = resourceLoader.getResource(filename + ".properties");
        doCheckoutAndCopy(resourceLang);
        return super.refreshProperties(filename, propHolder);
    }

    private synchronized void doCheckoutAndCopy(Resource resourceLang) {
        SvnClient svnClient = SvnClientFactory.createSvnClient(svnUser, svnPass);
        try {
            if (resourceLang != null && resourceLang.exists() && resourceLang instanceof ServletContextResource) {
                String relativePath = svnWebappPath + ((ServletContextResource) resourceLang).getPath();
                relativePath = relativePath.substring(0, relativePath.lastIndexOf(File.separator));
                File svnTempFolder = FileUtil.createCleanTempFolder(tmpFolderName);
                svnClient.checkoutRepositoryPath(svnTempFolder, svnProtocol, svnHost, svnPort, svnWebappPath);

                File pulledFile = new File(svnTempFolder.getAbsolutePath().concat(File.separator).concat(resourceLang.getFilename()));
                if (pulledFile.exists() && pulledFile.isFile())
                    FileUtil.nativeToAsciiFileCopy(pulledFile, resourceLang.getFile());
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }


    public String getSvnProtocol() {
        return svnProtocol;
    }

    public void setSvnProtocol(String svnProtocol) {
        this.svnProtocol = svnProtocol;
    }

    public String getSvnHost() {
        return svnHost;
    }

    @Required
    public void setSvnHost(String svnHost) {
        this.svnHost = svnHost;
    }

    public boolean isNativeToAscii() {
        return nativeToAscii;
    }

    public void setNativeToAscii(boolean nativeToAscii) {
        this.nativeToAscii = nativeToAscii;
    }

    public int getSvnPort() {
        return svnPort;
    }

    public void setSvnPort(int svnPort) {
        this.svnPort = svnPort;
    }

    public String getSvnWebappPath() {
        return svnWebappPath;
    }

    @Required
    public void setSvnWebappPath(String svnWebappPath) {
        this.svnWebappPath = svnWebappPath;
    }

    public String getTmpFolderName() {
        return tmpFolderName;
    }

    public void setTmpFolderName(String tmpFolderName) {
        this.tmpFolderName = tmpFolderName;
    }

    public String getSvnUser() {
        return svnUser;
    }

    @Required
    public void setSvnUser(String svnUser) {
        this.svnUser = svnUser;
    }

    public String getSvnPass() {
        return svnPass;
    }

    @Required
    public void setSvnPass(String svnPass) {
        this.svnPass = svnPass;
    }
}
