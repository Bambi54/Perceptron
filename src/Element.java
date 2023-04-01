import java.util.ArrayList;
import java.util.List;

public class Element {

    private final List<Double> inputs;
    private final int expected_output;
    public static final List<String> values = new ArrayList<>();


    public Element(List<Double> inputs, String value) {
        this.inputs = inputs;

        if(value.equals("")){
            expected_output = -1;
        } else if (values.size() == 0){
            values.add(value);
            expected_output = 1;
        } else if (values.stream().noneMatch(e->e.equals(value))){
            values.add(value);
            expected_output = 0;
        } else {
            expected_output = values.indexOf(value) == 0 ? 1 : 0;
        }

    }

    public List<Double> getInputs() {
        return inputs;
    }

    public int getExpected_output() {
        return expected_output;
    }


    @Override
    public String toString() {
        return inputs.toString();
    }
}
