package com.test.service.business;

import com.test.service.external.RestTemplateService;
import com.test.service.external.dto.MockTestVo;
import com.test.service.input_dto.CreateBatchInputDto;
import com.test.service.input_dto.CreateTestInputDto;
import com.test.service.model.PseudoTest;
import com.test.service.model.PseudotestJeenitestMapping;
import com.test.service.repository.PseudoTestRepository;
import com.test.service.repository.PseudotestJeenitestMappingRepository;
import com.test.service.resp_dto.ApiResponse;
import com.test.service.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class TestBusiness {
    @Autowired
    private RestTemplateService restTemplateService;

    @Autowired
    private PseudoTestRepository pseudoTestRepository;

    @Autowired
    private TestService testService;

    @Autowired
    private PseudotestJeenitestMappingRepository pseudotestJeenitestMappingRepository;

    public ApiResponse assignMarkingTemplateToTest(int testId, int pseudoTestId, String sessionHeader) {

        log.info("assignMarkingTemplateToTest business method called");
        // Call getQuestionByMockTest api from Jeeni
        MockTestVo mockTestVo = restTemplateService.getQuestionByMockTest(testId, sessionHeader);

        // logic to assign marking template
        mockTestVo = testService.assignMarkingTemplateToTest(mockTestVo, pseudoTestId);

        // Call assignMarkingTemplate api from Jeeni
//        mockTestVo = restTemplateService.assignMarkingTemplatesToTest(mockTestVo);
        return restTemplateService.assignMarkingTemplatesToTest(mockTestVo, sessionHeader);

//        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK, " ",mockTestVo);
//        return apiResponse;
    }

    public ApiResponse createTest(CreateTestInputDto createTestInputDto, String sessionHeader) {
        log.info("createTest business method called");

        // assign current timestamp to test name if it is already present in Jeeni test table
        if (createTestInputDto.getIsDuplicateTest().equals("true")){
            createTestInputDto.setName(createTestInputDto.getName()+ "_" + System.currentTimeMillis());
        }
//        return restTemplateService.createTest(createTestInputDto);
        ApiResponse apiResponse = restTemplateService.createTest(createTestInputDto, sessionHeader);

        System.out.println("apiResponse : " + apiResponse.getHttpStatusCode());
        if (apiResponse.getHttpStatusCode() == HttpStatus.OK){
//            Object responseBody = apiResponse.getResult();

            PseudotestJeenitestMapping pseudotestJeenitestMapping = new PseudotestJeenitestMapping();
            pseudotestJeenitestMapping.setPseudoTestId(createTestInputDto.getPseudoTestId());

            Object testId = null;
            log.info("Inside if block");
            if (apiResponse.getResult() instanceof Map) {
                Map<String, Object> bodyMap = (Map<String, Object>) apiResponse.getResult();
                testId = bodyMap.get("testId");
                log.info("testId : " + testId);
                pseudotestJeenitestMapping.setJeeniTestId(((Integer) testId));

            }

            ApiResponse testResponse = restTemplateService.getTest((Integer) testId, sessionHeader);
            if (testResponse.getResult() instanceof MockTestVo) {
                MockTestVo mockTestVo = (MockTestVo) testResponse.getResult();

                log.info("mockTestVo : "+mockTestVo.getOrgId());
                pseudotestJeenitestMapping.setOrgId(mockTestVo.getOrgId());
                pseudotestJeenitestMapping.setStartDateTime(mockTestVo.getStartTime());
                pseudotestJeenitestMapping.setEndDateTime(mockTestVo.getEndTime());
                pseudotestJeenitestMapping.setJeeniTestName(mockTestVo.getName());
            }

            Optional<PseudoTest> byTestId = pseudoTestRepository.findByTestId((int) createTestInputDto.getPseudoTestId());
            pseudotestJeenitestMapping.setPseudoTestName(byTestId.get().getTestName());

            log.info("add data in mapping table");
            pseudotestJeenitestMappingRepository.save(pseudotestJeenitestMapping);
            log.info("data added");
        }

        return apiResponse;
//        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK, " ",response);
//        return apiResponse;
    }

   /* public ApiResponse createBatch(CreateBatchInputDto createBatchInputDto, String orgName, String sessionHeader) {
        log.info("createBatch business method called");
        return restTemplateService.createBatch(createBatchInputDto, orgName, sessionHeader);

//        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK, " ",response);
//        return apiResponse;
    }*/
}
