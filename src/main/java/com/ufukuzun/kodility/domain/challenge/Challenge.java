package com.ufukuzun.kodility.domain.challenge;

import com.ufukuzun.kodility.enums.Lingo;
import com.ufukuzun.kodility.enums.ProgrammingLanguage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Challenge {

    private Long id;

    private Map<Lingo, String> descriptions = new HashMap<Lingo, String>();

    private List<Solution> solutions = new ArrayList<Solution>();

    private List<List<String>> inputs = new ArrayList<List<String>>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Map<Lingo, String> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(Map<Lingo, String> descriptions) {
        this.descriptions = descriptions;
    }

    public List<Solution> getSolutions() {
        return solutions;
    }

    public void setSolutions(List<Solution> solutions) {
        this.solutions = solutions;
    }

    public List<List<String>> getInputs() {
        return inputs;
    }

    public void setInputs(List<List<String>> inputs) {
        this.inputs = inputs;
    }

    // TODO ufuk: add test for this method
    public List<ProgrammingLanguage> getProgrammingLanguages() {
        List<ProgrammingLanguage> programmingLanguages = new ArrayList<ProgrammingLanguage>();
        for (Solution solution : solutions) {
            programmingLanguages.add(solution.getProgrammingLanguage());
        }
        return programmingLanguages;
    }

    // TODO ufuk: add test for this method
    public String getDescriptionFor(String lingoShortName) {    // TODO ufuk: get parameter as Lingo after i18n configuration
        Lingo lingo = Lingo.getLingo(lingoShortName);
        return descriptions.get(lingo);
    }

    // TODO ufuk: add test for this method
    public Solution getSolutionFor(ProgrammingLanguage programmingLanguage) {
        for (Solution solution : solutions) {
            if (solution.getProgrammingLanguage() == programmingLanguage) {
                return solution;
            }
        }
        return null;
    }

}
