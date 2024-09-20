package infra.resources;

import api.ElectionApi;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.ResponseStatus;
import org.jboss.resteasy.reactive.RestResponse;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/election")
public class ElectionResource {

    private final ElectionApi electionApi;

    public ElectionResource(ElectionApi electionApi) {
        this.electionApi = electionApi;
    }

    @POST
    @ResponseStatus(RestResponse.StatusCode.CREATED)
    public void submit(){
        electionApi.submit();
    }


}
