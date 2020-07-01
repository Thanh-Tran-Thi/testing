package com.smartosc.training.service;

import com.smartosc.training.dto.AppCodeDTO;
import com.smartosc.training.entity.AppCode;
import com.smartosc.training.repositories.AppCodeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

/**
 * fres-parent
 *
 * @author Namtt
 * @created_at 27/04/2020 - 3:55 PM
 * @created_by Namtt
 * @since 27/04/2020
 */
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class AppCodeServiceTest {

    @InjectMocks
    private AppCodeService appCodeService;

    @Mock
    private AppCodeRepository appCodeRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createAppCode_Success() {
        AppCodeDTO requestData = new AppCodeDTO("001","alo",1,"Admin","Admin", LocalDateTime.now(),LocalDateTime.now());
        AppCodeDTO responseData = null;
        lenient().when(appCodeRepository.save(any(AppCode.class))).thenAnswer((Answer<AppCode>) invocationOnMock ->{
            Object[] arguments = invocationOnMock.getArguments();
            if(arguments != null && arguments.length > 0 && arguments[0] != null){
                AppCode appCode = (AppCode) arguments[0];
                appCode.setAppCodeId(1);
                return appCode;
            }
            return null;
        });

        responseData = appCodeService.addAppCode(requestData);
        requestData.setAppCodeId(1);
        Assertions.assertEquals(requestData,responseData);
    }
}
