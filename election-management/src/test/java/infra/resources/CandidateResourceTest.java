package infra.resources;

import api.CandidateApi;
import api.dto.in.CreateCandidate;

import api.dto.out.CandidateDTOout;
import domain.Candidate;
import io.quarkus.test.InjectMock;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.MediaType;
import org.instancio.Instancio;
import org.jboss.resteasy.reactive.RestResponse;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@QuarkusTest
@TestHTTPEndpoint(CandidateResource.class)
class CandidateResourceTest {

    @InjectMock
    CandidateApi candidateApi;

    @Test
    void create() {
        CreateCandidate dto = Instancio.create(CreateCandidate.class);

        given().contentType(MediaType.APPLICATION_JSON).body(dto)
                .when()
                .post()
                .then()
                .statusCode(201);

        verify(candidateApi).create(dto);
        verifyNoMoreInteractions(candidateApi);
    }

    @Test
    void list(){
        var out = Instancio.stream(CandidateDTOout.class).limit(4).toList();

        when(candidateApi.list()).thenReturn(out);

        var response = given()
                .when().get()
                .then().statusCode(RestResponse.StatusCode.OK).extract().as(CandidateDTOout[].class);

        verify(candidateApi).list();
        verifyNoMoreInteractions(candidateApi);

        assertEquals(out, Arrays.stream(response).toList());
    }




}