
package var.web.ws.poll;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import javax.json.Json;
//https://repo1.maven.org/maven2/javax/json/javax.json-api/1.1.2/
//https://repo1.maven.org/maven2/org/glassfish/javax.json/1.1.2/
public class DataEncoder implements Encoder.Text<BallotBox>{
    @Override
    public String encode(BallotBox ballotBox) throws EncodeException {
        System.out.println("Encoder called.");
        return Json.createObjectBuilder().add("votes", "" + ballotBox.countVotes()).build().toString();
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
