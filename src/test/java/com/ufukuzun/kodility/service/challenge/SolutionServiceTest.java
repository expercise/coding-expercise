package com.ufukuzun.kodility.service.challenge;

import com.ufukuzun.kodility.dao.challenge.SolutionDao;
import com.ufukuzun.kodility.domain.challenge.Solution;
import com.ufukuzun.kodility.testutils.builder.SolutionBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SolutionServiceTest {

    @InjectMocks
    private SolutionService service;

    @Mock
    private SolutionDao solutionDao;

    @Test
    public void shouldSaveSolution() {
        Solution solution = new SolutionBuilder().build();

        service.saveSolution(solution);

        verify(solutionDao).save(solution);
    }

}
