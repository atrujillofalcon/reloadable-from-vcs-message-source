package dev.atrujillofalcon.svn;

public class SvnClientFactory {

    public static SvnClient createSvnClient() {
        //TODO setear user and password mediante properties

        SvnClient clientToReturn = new SvnClientImpl("vnitat", "Cubano4ever");
        return clientToReturn;
    }

}
