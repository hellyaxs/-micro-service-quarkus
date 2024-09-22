package infra.resources;


import api.ElectionApi;
import api.dto.out.ElectionDto;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.ResponseStatus;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/volting")
public class VoltingResources {

    private final ElectionApi electionApi;

    public VoltingResources(ElectionApi electionApi) {
        this.electionApi = electionApi;
    }

    @GET
    public List<ElectionDto> electionDtoList() {
        return electionApi.findAll();
    }

    @POST
    @Path("/elections/{electionId}/candidates/{candidateId}")
    @ResponseStatus(RestResponse.StatusCode.ACCEPTED)
    @Transactional
    public void vote(@PathParam("electionId") String electionId, @PathParam("candidateId") String candidateId) {
        electionApi.vote(electionId, candidateId);
    }

}
