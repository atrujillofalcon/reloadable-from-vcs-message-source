package dev.atrujillofalcon.svn;

import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNRevision;

import java.io.File;

public class SvnClientImpl implements SvnClient {


    private String user;
    private String pass;

    public SvnClientImpl(String user, String pass) {
        this.user = user;
        this.pass = pass;
    }


    public boolean checkoutRepositoryPath(File destinyFile, String svnProtocol, String svnHost, int svnPort, String rootRepositoryPath, String relativePath) {
        SVNClientManager clientManager = SVNClientManager.newInstance(new DefaultSVNOptions(), user, pass);
        SVNRevision revision = SVNRevision.HEAD;
        //SVN PUERTO POR DEFECTO 3690
        boolean success = false;
        try {
            if (clientManager.getUpdateClient().doExport(SVNURL.create(svnProtocol, null, svnHost, 3690, rootRepositoryPath + relativePath, false)
                    , destinyFile, null, revision,null,true, SVNDepth.INFINITY) >= 0)
                success = true;
        } catch (SVNException e) {
            System.err.println("Error doing checkout. Returning not acomplished");
        }
        return success;
    }




}
