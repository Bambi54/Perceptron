import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Perceptron {

    public final int SIZE;

    private final List<Double> weights;
    private double tetha;
    private final double alpha;
    private final List<Integer> results;
    private double error;
    private boolean trained;

    public Perceptron(int size, double alpha, int resSize) {
        this.SIZE = size;

        this.weights = new ArrayList<>();

        for (int i = 0; i < SIZE; i++)
            weights.add(i, Math.random());


        this.tetha = 0;
        this.alpha = alpha;
        this.results = new ArrayList<>(Collections.nCopies(resSize, 0));
        this.error = 100;
        this.trained = false;
    }

    public static Perceptron createPerceptron(int size, double alpha, int resSize){
        return new Perceptron(size, alpha, resSize);
    }

    public void differ(List<Element> elems){

        for (int i = 0; i < elems.size(); i++)
            setResult(i, calcOutput(elems.get(i)));

        if(elems.size() != 1)
            countError(elems);

    }

    public int calcOutput(Element elem){

        double net = 0;

        for (int i = 0; i < SIZE; i++)
            net += weights.get(i) * elem.getInputs().get(i);

        net -= getTetha();

        int y = net >= 0 ? 1 : 0;

        if (y - elem.getExpected_output() != 0 && !trained)
            newWeights(elem, y);

        return y;

    }

    public void newWeights(Element elem, int exp){

        double scalar = getAlpha() * (elem.getExpected_output() - exp);

        List<Double> temp = elem.getInputs().stream()
                .map(e -> e * scalar)
                .collect(Collectors.toList());

        for (int i = 0; i < SIZE; i++)
            weights.set(i, weights.get(i) + temp.get(i));

        setTetha(getTetha() - scalar);

    }

    private void countError(List<Element> elems){

        double tmp = 0;

        for (int i = 0; i < elems.size(); i++)
            tmp += Math.pow(
                    elems.get(i).getExpected_output() - results.get(i), 2
            );

        setError(tmp / elems.size());

    }

    public void setResult(int i, int result) {
        results.set(i,result);
    }

    public int getResult(int i){
        return results.get(i);
    }

    public double getTetha() {
        return tetha;
    }

    public void setTetha(double tetha) {
        this.tetha = tetha;
    }

    public double getAlpha() {
        return alpha;
    }

    public void setError(double error) {
        this.error = error;
    }

    public double getError() {
        return error;
    }

    public void setTrained() {
        this.trained = true;
    }
}
