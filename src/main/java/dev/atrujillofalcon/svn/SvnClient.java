package dev.atrujillofalcon.svn;

import java.io.File;

public interface SvnClient {

    public boolean checkoutRepositoryPath(File destinyFile, String svnProtocol, String svnHost, int svnPort, String rootRepositoryPath, String relativePath);
}
