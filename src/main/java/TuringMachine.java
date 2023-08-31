import java.util.*;

public class TuringMachine {

    //  Существенные поля
    private int currentCell;
    private List<State> states;
    private List<Character> alphabet;
    private State currentState;

    //  Поля для задач из пункта Г
    int machineInState0 = 0;
    int machineInState1 = 0;
    int machineInState2 = 0;
    int usedMachineCells = 0;
    int resultStringLen = 0;
    boolean isMachineWasInEveryState = false;
    char symbolOn10Stroke = '!';
    char symbolOn20Stroke = '!';
    char symbolOn30Stroke = '!';
    int countFieldSymbol0InState2 = 0;
    int turnCount = 0;

    //  указываем стартовое состояние для машины. > 0 && < 3
    public TuringMachine(int startStateNum) {
        currentCell = 0;
        // Создание состояний
        State state0 = null;
        State state1 = new State(this);
        State state2 = new State(this);

        state1.addChar('%', new State.Field('1', state0, Move.STAY));
        state1.addChar('*', new State.Field('*', state1, Move.LEFT));
        state1.addChar('0', new State.Field('1', state2, Move.STAY));
        state1.addChar('1', new State.Field('2', state1, Move.STAY));
        state1.addChar('2', new State.Field('3', state0, Move.STAY));
        state1.addChar('3', new State.Field('0', state1, Move.LEFT));

        state2.addChar('%', new State.Field('*', state1, Move.RIGHT));
        state2.addChar('*', new State.Field('%', state1, Move.LEFT));
        state2.addChar('0', new State.Field('1', state1, Move.LEFT));
        state2.addChar('1', new State.Field('0', state2, Move.RIGHT));
        state2.addChar('2', new State.Field('1', state2, Move.RIGHT));
        state2.addChar('3', new State.Field('0', state1, Move.RIGHT));

        states = new ArrayList<>();
        states.add(state0);
        states.add(state1);
        states.add(state2);
        currentState = states.get(startStateNum);
        alphabet = Arrays.asList('%', '0', '1', '2', '3', '*'); // % — пустой символ
    }

    public List<State> getStates() {
        return states;
    }

    public List<Character> getAlphabet() {
        return alphabet;
    }

    public int getCurrentCell() {
        return currentCell;
    }

    public void setCurrentCell(int currentCell) {
        this.currentCell = currentCell;
    }

    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }

    // numOfStrokes - число тактов, в течение которых работает машина
    // метод возвращает переработанную строку
    public String doOperation(String input, int numOfStrokes) {
        if (isCorrectInput(input)) {
            StringBuilder result = new StringBuilder(input);
            int i = 0;
            int leftNew = 0; // количество новых элементов слева (имитация бесконечной ленты)
            int rightNew = 0; // колво новых элементов справа

            // Для задач Г
            Set<Integer> usedMachineCellsSet = new HashSet<>();
            Move currDirectionStart = currentState.direction;
            /////////////////

            // пока не прошли все такты или пока текущее состояние не нулевое (т.е. не завершает работу)
            while (i < numOfStrokes && currentState != null) {
                //////////////////
                // ДЛЯ ЗАДАЧ Г ///

                if (currentState == states.get(1)) {
                    machineInState1++;
                } else if (currentState == states.get(2)) {
                    machineInState2++;
                }
                usedMachineCellsSet.add(currentCell);

                if (currentState == states.get(2) && result.charAt(currentCell + leftNew) == '*') {
                    countFieldSymbol0InState2++;
                }

                //////////////////

                int resultPosition = currentCell;
                if (currentCell < 0) {
                    result.insert(0, '%');
                    leftNew++;
                } else if (currentCell >= result.length()) {
                    result.insert(result.length(), '%');
                    rightNew++;
                }
                resultPosition += leftNew;
                char currentChar = result.charAt(resultPosition);
                result.setCharAt(resultPosition,
                        currentState.move(currentChar)
                );
                i++;

                //////////////
                // Для Г задачи
                /////////////
                if (i == 10) {
                    symbolOn10Stroke = result.charAt(leftNew);
                } else if (i == 20) {
                    symbolOn20Stroke = result.charAt(leftNew);
                } else if (i == 30) {
                    symbolOn30Stroke = result.charAt(leftNew);
                }
                if (currentState != null) {
                    Move currDirectionEnd = currentState.direction;
                    if ((currDirectionEnd == Move.LEFT && currDirectionStart == Move.RIGHT) ||
                            currDirectionEnd == Move.RIGHT && currDirectionStart == Move.LEFT) {
                        turnCount++;
                    }
                }
                //////////
            }
            // для задачи Г
            usedMachineCells = usedMachineCellsSet.size();
            if (currentState == null) {
                machineInState0++;
            }
            for (int j = 0; j < result.length(); j++) {
                if (result.charAt(i) != '%') {
                    resultStringLen++;
                }
            }
            if (machineInState2 > 0 && machineInState1 > 0 && machineInState0 > 0) {
                isMachineWasInEveryState = false;
            }
            /////////////
            return result.toString();
        } else {
            throw new UnsupportedOperationException();
        }
    }

    // проверка, состоит ли входная строка из символов дозволенного алфавита
    private boolean isCorrectInput(String input) {
        for (int i = 0; i < input.length(); i++) {
            if (!alphabet.contains(input.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    static class State {
        private TuringMachine turingMachine;
        private Map<Character, Field> reaction;
        // для задачи Г
        Move direction;
        /////

        public State(TuringMachine turingMachine) {
            this.turingMachine = turingMachine;
            reaction = new HashMap<>();
        }

        // производит изменение состояния, положения головки и вовзращает новый символ
        public char move(char c) {
            Field field = reaction.get(c);
            Move move = field.getMove();
            turingMachine.setCurrentCell(turingMachine.getCurrentCell() + move.getStep());
            turingMachine.setCurrentState(field.getNextState());
            return field.getSymbol();
        }

        public void addChar(char c, Field field) {
            reaction.put(c, field);
        }

        static class Field {

            private final char symbol;
            private final State nextState;
            private final Move move;

            public Field(char symbol, State nextState, Move move) {
                this.symbol = symbol;
                this.nextState = nextState;
                this.move = move;
            }

            public char getSymbol() {
                return symbol;
            }

            public State getNextState() {
                return nextState;
            }

            public Move getMove() {
                return move;
            }
        }
    }
}
