package spb.iac.model;

import java.util.Comparator;
import java.util.Locale;

public class SimpleComparator implements Comparator<SubDirectory> {

    @Override
    public int compare(SubDirectory o1, SubDirectory o2) {
        String q = o1.getSize();
        String s = o2.getSize();
        if (o1.getSize().equals("DIR")) {
            return -1;
        }
        if (o2.getSize().equals("DIR")) {
            return 1;
        }

        String firstStr = o1.getPath().toLowerCase(Locale.ROOT);
        String secondStr = o2.getPath().toLowerCase(Locale.ROOT);

        int firstCursor = 0;
        int secondCursor = 0;

        while (firstCursor < firstStr.length() && secondCursor < secondStr.length()) {
            // получаем кусочки строки, либо с числами, либо буквы, либо крест на крест
            String pathOfFirstStr = get(firstStr, firstCursor);
            String pathOfSecondStr = get(secondStr, secondCursor);
            firstCursor += pathOfFirstStr.length();
            secondCursor += pathOfSecondStr.length();

            int result = 0;
            // если и там и там числа, то сравниваем их именно как числа
            if (isDigit(pathOfFirstStr.charAt(0)) && isDigit(pathOfSecondStr.charAt(0))) {
                result = pathOfFirstStr.length() - pathOfSecondStr.length();
                if (result == 0) {
                    for (int i = 0; i < pathOfFirstStr.length(); i++) {
                        result = pathOfFirstStr.charAt(i) - pathOfSecondStr.charAt(i);
                        if (result != 0) {
                            return result;
                        }
                    }
                }
            } else {
                // если бы мы тут, значит у нас либо буквы-буквы, либо буква/точка-число
                // так может получится например когда у нас "f1.txt" и "f.txt"
                result = pathOfFirstStr.compareTo(pathOfSecondStr);
            }
            if (result != 0) {
                return result;
            }
        }

        return firstStr.length() - secondStr.length();
    }

    // проверяет на число
    private boolean isDigit(char ch) {
        return ((ch >= 48) && (ch <= 57));
    }

    // метод идет по строке и записывает либо буквы, пока не дойдет до цифры,
    // либо цифры, пока не дойдет до буквы
    private String get(String str, int cursor) {
        StringBuilder sb = new StringBuilder();
        char ch = str.charAt(cursor++);
        if (isDigit(ch)) {
            if (ch != 0) {
                sb.append(ch);
            }
            while (cursor < str.length()) {

                ch = str.charAt(cursor++);
                if (!isDigit(ch)) {
                    return String.valueOf(Integer.parseInt(sb.toString()));
                }
                if (ch != 0) {
                    sb.append(ch);
                }
            }
        } else if (ch == 46) {
            sb.append(ch);
        } else {
            sb.append(ch);
            while (cursor < str.length()) {
                ch = str.charAt(cursor++);
                if (isDigit(ch) || ch == 46)
                    break;
                sb.append(ch);
            }
        }
        return sb.toString();
    }
}
