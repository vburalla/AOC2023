package day19;

import utils.NumberUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class WorkflowPath {

    String current;
    List<String> rules = new ArrayList<>();
    private static final Pattern pattern = Pattern.compile("(\\w+)|(\\W+)");

    public WorkflowPath(String current, List<String> rules) {
        this.current = current;
        this.rules = rules;
    }

    public WorkflowPath(String current) {
        this.current = current;
    }

    public WorkflowPath copyWithNewCurrent(String newCurrent) {
        return new WorkflowPath(newCurrent, this.rules.stream().collect(Collectors.toList()));
    }

    public void addDefaultRule(Workflow w) {
        w.getRules().stream().forEach(r -> rules.add(r.rule.contains(">")? r.rule.replace(">","<=") : r.rule.replace("<",">=")));
    }

    public void addRule(Workflow w, int position) {
        if (position == 0)
            this.rules.add(w.getRules().get(position).rule);
        else {
            for (int i = 0; i < position; i++) {
                String rule = w.getRules().get(i).rule;
                this.rules.add(rule.contains(">")? rule.replace(">","<=") : rule.replace("<",">="));
            }
            this.rules.add(w.getRules().get(position).rule);
        }
    }

    public boolean checkPathForPart(Part part) {

        return this.rules.stream().allMatch(r -> this.checkRule(r, part));
    }

    private boolean checkRule(String rule, Part part) {

        Matcher m = pattern.matcher(rule);
        m.find();
        Integer value1 = part.getCategory(m.group(0));
        m.find();
        String comparator = m.group(0);
        m.find();
        Integer value2 = Integer.parseInt(m.group(0));

        return NumberUtils.eval(value1, value2, comparator);
    }
}
