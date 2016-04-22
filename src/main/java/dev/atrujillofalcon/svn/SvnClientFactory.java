package dev.atrujillofalcon.svn;

public class SvnClientFactory {

    public static SvnClient createSvnClient(String user, String pass) {
        SvnClient clientToReturn = new SvnClientImpl(user, pass);
        return clientToReturn;
    }

}
