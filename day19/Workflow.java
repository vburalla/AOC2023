package day19;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Workflow {

    private String name;
    private List<WorkflowInstruction> rules = new ArrayList<>();
    private String defaultTarget;

    public Workflow(String workflow) {
        var workflowParts = workflow.replace("}","").split("\\{");
        this.name = workflowParts[0];
        createRules(workflowParts[1]);
    }

    public List<WorkflowInstruction> getRules() {
        return this.rules;
    }

    public String getDefaultTarget() {
        return this.defaultTarget;
    }

    public String getName() {
        return name;
    }

    private void createRules(String workflow) {

        var wf = workflow.replace("{","").replace("}","").split(",");
        for(String f : wf) {
            if(f.contains(":")) {
                this.rules.add(new WorkflowInstruction(f));
            } else {
                defaultTarget = f;
            }
        }
    }

    class WorkflowInstruction {
        String destination;
        String rule;
        public WorkflowInstruction(String instruction) {
            this.destination = instruction.split(":")[1];
            this.rule = instruction.split(":")[0];
        }
    }

}
