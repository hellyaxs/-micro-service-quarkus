package infra.resources;

import api.CandidateApi;
import api.dto.in.CreateCandidate;
import api.dto.in.UpdateCandidate;
import api.dto.out.CandidateDTOout;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.ResponseStatus;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/candidates")
public class CandidateResource {

    private final CandidateApi candidateApi;

    public CandidateResource(CandidateApi candidateApi) {
        this.candidateApi = candidateApi;
    }

    @POST
    @ResponseStatus(201)
    @Transactional
    public void create(CreateCandidate dto) {
        candidateApi.create(dto);
    }

    @PUT
    @Path("{id}")
    @ResponseStatus(RestResponse.StatusCode.OK)
    @Transactional
    public CandidateDTOout update(@PathParam("id") String id, UpdateCandidate dto) {
        return candidateApi.update(id, dto);
    }

    @GET
    @ResponseStatus(RestResponse.StatusCode.OK)
    public List<CandidateDTOout> list() {
        return candidateApi.list();
    }
}
