package infra.resources;

import api.ElectionApi;
import api.dto.out.ElectionDTO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.ResponseStatus;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.List;

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

    @GET
    public List<ElectionDTO> findAll(){
       return electionApi.findAll();
    }


}
