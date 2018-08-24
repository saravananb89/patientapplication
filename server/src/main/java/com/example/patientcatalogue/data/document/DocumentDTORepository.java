package com.example.patientcatalogue.data.document;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DocumentDTORepository implements DocumentDTORepositoryInterface {


    final String rootPath = "c:\\file-repository";

    @Override
    public void uploadDocument(DocumentDTO documentDTO) {

        String fileDest = rootPath + "\\" + documentDTO.getPatientId() + "\\" + documentDTO.getPatientVisitId() + "\\" + documentDTO.getFileName();

        try {
            FileUtils.writeByteArrayToFile(new File(fileDest + "." + documentDTO.getFileExtension()), documentDTO.getFileContent(), false);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<DocumentDTO> getDocuments(int patientId, int patientVisitId) {

        String path = rootPath + "\\" + patientId + "\\" + patientVisitId;

        List<File> filesInFolder = null;
        try {
            filesInFolder = Files.walk(Paths.get(path))
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<DocumentDTO> documentDTOS = new ArrayList<>();

        if (filesInFolder != null && filesInFolder.size() > 0) {
            filesInFolder.forEach(file -> {
                DocumentDTO documentDTO = new DocumentDTO();
                documentDTO.setPatientId(patientId);
                documentDTO.setPatientVisitId(patientVisitId);
                documentDTO.setFileName(file.getName());
                try {
                    documentDTO.setFileContent(Files.readAllBytes(file.toPath()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                documentDTOS.add(documentDTO);
            });
        }

        return documentDTOS;
    }

}
