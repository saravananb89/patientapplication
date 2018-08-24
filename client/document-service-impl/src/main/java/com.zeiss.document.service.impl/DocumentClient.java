package com.zeiss.document.service.impl;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.zeiss.document.service.api.Document;
import com.zeiss.patient.service.api.PatientService;
import com.zeiss.patient.service.api.PatientVisit;
import com.zeiss.patient.service.impl.PatientImpl;
import org.owasp.encoder.Encode;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

public class DocumentClient {

    private static final String REST_URI = "http://localhost:1234/document";
    private ClientConfig cc = new DefaultClientConfig();

    @Inject
    private PatientService patientService;

    public void upload(Document document) {

        byte[] payload = document.getFileContent();

        Client client = Client.create(cc);
        String encodedFileName = Encode.forHtml(document.getFileName());
        System.out.println(encodedFileName);
        WebResource webResource = client.resource("http://localhost:1234/document").path("/" + document.getPatientId()
                + "/" + document.getPatientVisitId() + "/" + encodedFileName + "/" + document.getFileExtension());

        webResource
                .entity(payload, MediaType.APPLICATION_OCTET_STREAM)
                .post();

    }

    public List<Document> getDocuments(Integer patientId, Integer patientVisitId) {
        String uri = "/" + patientId + "/" + patientVisitId;
        Client client = Client.create();
        WebResource webResource = client
                .resource("http://localhost:1234/document" + uri);

        ClientResponse response = webResource.accept("application/json")
                .get(ClientResponse.class);

        GenericType<List<DocumentImpl>> generic = new GenericType<List<DocumentImpl>>() {
            // No thing
        };

        List<? extends Document> output = webResource.get(new GenericType<List<DocumentImpl>>() {
        });


        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus());
        }

        System.out.println("Output from Server .... \n" + output.size());
        System.out.println(output.toString());


        return (List<Document>) output;
    }

    public List<Document> getDocuments(String patientId) {

        List<Document> documentList = new ArrayList<>();
        PatientImpl patient = patientService.getPatientsById(patientId);

        List<? extends PatientVisit> patientVisits = patientService.getVisitPatientsByFirstNameAndLastName(patient.getFirstName(), patient.getLastName());

        patientVisits.forEach(o -> {
            Integer patientVisitId = Integer.parseInt(o.getId());
            documentList.addAll(getDocuments(Integer.parseInt(patient.getId()), patientVisitId));
        });

        return documentList;
    }

}
