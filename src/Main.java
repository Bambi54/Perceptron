import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {

        System.out.println("Podaj stala uczenia:");

        Scanner s = new Scanner(System.in);
        double alpha = s.nextDouble();

        System.out.println("Podaj sciezke do pliku treningowego .csv:");
        String pathTrain = s.next();

        Perceptron p = train(alpha, pathTrain);

        System.out.println("==========================================");
        System.out.println("\t\t\tKONIEC TRENINGU");
        System.out.println("==========================================\n");

        int choice = 0;

        while (choice != 3) {

            System.out.println("Wybierz opcje: ");
            System.out.println("1. Podanie sciezki do pliku testowego");
            System.out.println("2. Reczne podanie wektorow do klasyfikacji");
            System.out.println("3. Zamkniecie programu");

            choice = s.nextInt();

            switch (choice) {
                case 1: {
                    System.out.println("Podaj sciezke do pliku testowego .csv:");
                    String pathTest = s.next();
                    test(p, pathTest);
                    break;
                }
                case 2: {
                    enterElem(p);
                    break;
                }
                case 3 : {
                    break;
                }
                default: {
                    System.out.println("Niepoprawna opcja, sprobuj jeszcze raz.\n");
                }
            }
        }


    }

    public static void test(Perceptron p, String path) throws IOException {

        List<Element> test = getElems(path);

        p.differ(test);

        double acc = 0;

        for (int i = 0; i < test.size(); i++) {

            if (p.getResult(i) == test.get(i).getExpected_output())
                acc++;


            System.out.println(
                    "Dane: " + test.get(i) +
                    " || Klasa przyporzadkowana: " + Element.values.get(p.getResult(i) == 0 ? 1 : 0) +
                            " || Klasa rzeczywista: " + Element.values.get(test.get(i).getExpected_output() == 0 ? 1 : 0)
            );


        }

        acc /= test.size();

        System.out.println("Dokladnosc: " + acc + "\n");

    }

    public static Perceptron train(double alpha, String path) throws IOException {

        List<Element> training = getElems(path);

        Perceptron p = Perceptron.createPerceptron(training.get(0).getInputs().size(), alpha, training.size());

        for (int i = 1; p.getError() > 0.03 || i  < 1_000; i++) {

            p.differ(training);

            System.out.println("Iteracja: " + i + "\n"
                    + "Blad: " + p.getError() + "\n");

        }

        p.setTrained();
        return p;

    }

    public static List<Element> getElems(String path) throws IOException {

        return Files.lines(Paths.get(path)).map(
                e -> {
                    List<String> tmp = List.of(e.split(","));
                    return new Element(
                            tmp.subList(0, tmp.size()-1).stream().map(Double::parseDouble).collect(Collectors.toList()),
                            tmp.get(tmp.size()-1)
                    );
                }
        ).collect(Collectors.toList());

    }

    public static void enterElem (Perceptron p){

        System.out.println("Podaj dokladnie " + p.SIZE + " wartosci");
        Scanner s = new Scanner(System.in);
        double x1 = s.nextDouble();
        double x2 = s.nextDouble();
        double x3 = s.nextDouble();
        double x4 = s.nextDouble();

        Element elem = new Element(
                List.of(x1,x2,x3,x4), ""
        );

        p.differ(List.of(elem));

        System.out.println(
                "Dane: " + elem +
                        " || Klasa przyporzadkowana: " + Element.values.get(p.getResult(0) == 0 ? 1 : 0)
        );

    }

}
