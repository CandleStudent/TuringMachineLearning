import java.util.Scanner;

//  Все задачи реализованы внутри класса TuringMachine и помечены обрамляющими комментариями
public class Tasks {

    public static void main(String[] args) {

        System.out.println("Введите количество тактов: ");
        Scanner scan = new Scanner(System.in);
        int numOfStrokes = scan.nextInt();
        TuringMachine tm = new TuringMachine(1);
        System.out.println("Введите исходное слово (оно может содержать 0, 1, 2, 3, * или % в качестве пустого символа)");
        String input = scan.next();
        System.out.println("Получившаяся на выходе строка");
        System.out.println(tm.doOperation(input, numOfStrokes));

        // Задачи
        System.out.println("ЗАДАЧИ");
        System.out.println("а) сколько раз устройство оказывалось в в состоянии q1: " + tm.machineInState1);
        System.out.println("б) Сколько раз устройство находилось в каждом из своих состояний: ");
        System.out.println("state 0: " + tm.machineInState0);
        System.out.println("state 1: " + tm.machineInState1);
        System.out.println("state 2: " + tm.machineInState2);
        System.out.println("в) сколько ячеек обозревалось устройством: " + tm.usedMachineCells);
        System.out.println("г) количество разрядов ячейки, содержащих не пустой символ, – по окончании работы машины: " + tm.resultStringLen);
        System.out.println("д) окажется ли устройство во время работы машины в каждом из своих состояний " + tm.isMachineWasInEveryState);
        System.out.println("е) какие символы окажутся в разряде x0 после 10-го, 20-го, 30-го такта (если машина ранее не остановится. Если остановиться, то будет выведено !) ");
        System.out.println("после 10: " + tm.symbolOn10Stroke);
        System.out.println("После 20: " + tm.symbolOn20Stroke);
        System.out.println("После 30:  " + tm.symbolOn30Stroke);
        System.out.println("ж) сколько раз выбиралась клетка таблицы, соответствующая символу a1 и состоянию q2");
        System.out.println(tm.countFieldSymbol0InState2);
        System.out.println("з)  сколько поворотов совершило устройство (поворот – это изменение направления движения устройства вдоль ячейки)");
        System.out.println(tm.turnCount);
    }
}
