package day19;

import utils.ReadFiles;
import day19.Workflow.WorkflowInstruction;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day19 {
    static Map<String, Workflow> workflowMap = new HashMap<>();
    static LinkedList<Part> parts = new LinkedList<>();
    private static final Pattern pattern = Pattern.compile("(\\w+)|(\\W+)");

    public static void main(String... args) {

        boolean analyzeParts = false;
        for (String line : ReadFiles.getInputData("day19/input.txt")) {
            if (line.equals("")) {
                analyzeParts = true;
                continue;
            }
            if (!analyzeParts) {
                Workflow w = new Workflow(line);
                workflowMap.put(w.getName(), w);
            } else {
                parts.add(new Part(line));
            }
        }
        Long total = 0L;
        var pathsToA = createPathsToA();
        int i = 0;
        for (Part part : parts) {
            if (pathsToA.stream().anyMatch(path -> path.checkPathForPart(part)))
                total += part.sumCategoriesRating();
        }
        System.out.println(String.format("Part 1: %d", total));
        total = 0L;
        for(WorkflowPath path : pathsToA) {
            total += countAcceptedPathCombinations(path);
        }
        System.out.println(String.format("Part 2: %d", total));
    }

    private static List<WorkflowPath> createPathsToA() {

        List<WorkflowPath> pathsToA = new ArrayList<>();
        List<WorkflowPath> wp = new ArrayList<>(List.of(new WorkflowPath("in")));
        List<WorkflowPath> newWP = new ArrayList<>();
        while (!wp.isEmpty()) {
            while (!wp.isEmpty()) {
                WorkflowPath workflowPath = wp.remove(0);
                Workflow wf = workflowMap.get(workflowPath.current);
                for (int i = 0; i < wf.getRules().size(); i++) {
                    WorkflowInstruction wi = wf.getRules().get(i);
                    if (!wi.destination.equals("R")) {
                        WorkflowPath newWorkflowPath = workflowPath.copyWithNewCurrent(wi.destination);
                        newWorkflowPath.addRule(wf, i);
                        if (wi.destination.equals("A"))
                            pathsToA.add(newWorkflowPath);
                        else
                            newWP.add(newWorkflowPath);
                    }
                }
                if (wf.getDefaultTarget() != null && !wf.getDefaultTarget().equals("R")) {
                    WorkflowPath newWorkflowPath = workflowPath.copyWithNewCurrent(wf.getDefaultTarget());
                    newWorkflowPath.addDefaultRule(wf);
                    if (wf.getDefaultTarget().equals("A"))
                        pathsToA.add(newWorkflowPath);
                    else
                        newWP.add(newWorkflowPath);
                }
            }
            wp = newWP.stream().collect(Collectors.toList());
            newWP.clear();
        }
        return pathsToA;
    }

    private static Long countAcceptedPathCombinations(WorkflowPath path) {

        List<Range> ranges = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            ranges.add(new Range(0, 4000));
        }
        for (String rule : path.rules) {
            adaptRangesToRule(ranges, rule);
        }
        Long value = 1L;
        for (Range range : ranges) {
            value = value * (range.maxValue - range.minValue);
        }
        return value;
    }

    private static void adaptRangesToRule(List<Range> ranges, String rule) {

        boolean negated = rule.contains("!");
        Matcher m = pattern.matcher(rule.replace("!", ""));
        m.find();
        Integer value1 = switch (m.group(0)) {
            case "m" -> 1;
            case "a" -> 2;
            case "s" -> 3;
            default -> 0;
        };
        m.find();
        String comparator = negated ? "!" + m.group(0) : m.group(0);
        m.find();
        Integer value2 = Integer.parseInt(m.group(0));
        Range range = ranges.get(value1);
        switch (comparator) {
            case ">" -> {
                if (!(range.minValue > value2))
                    range.minValue = value2;
            }
            case ">=" -> {
                if (!(range.minValue >= value2))
                    range.minValue = value2 - 1;
            }
            case "<" -> {
                if(!(range.maxValue < value2))
                    range.maxValue = value2 - 1;
            }
            case "<=" -> {
                if(!(range.maxValue <= value2))
                    range.maxValue = value2;
            }
        }
    }
}
