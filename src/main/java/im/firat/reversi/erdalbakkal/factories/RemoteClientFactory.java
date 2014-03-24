
package im.firat.reversi.erdalbakkal.factories;


import im.firat.reversi.erdalbakkal.algorithms.Algorithm;
import im.firat.reversi.erdalbakkal.core.GameClient;
import java.util.Arrays;
import java.util.List;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;



public final class RemoteClientFactory {



    //~ --- [CONSTRUCTORS] ---------------------------------------------------------------------------------------------

    private RemoteClientFactory() {

    }



    //~ --- [METHODS] --------------------------------------------------------------------------------------------------

    public static GameClient createInstance(final String baseAddress, final int player, final Algorithm algorithm) {

        List<JacksonJsonProvider> providers = Arrays.asList(new JacksonJsonProvider());
        GameClient                client    = JAXRSClientFactory.create(baseAddress, GameClient.class, providers);

        return client;
    }


}
