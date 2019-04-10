package yoyo.app.android.com.yoyoapp.Flight.Utils;


// for checking if the traveller national code is valid
public class NationalCodeUtil {

    private final static String notNationalCode[] = new String[]{
            "0000000000",
            "1111111111",
            "2222222222",
            "3333333333",
            "4444444444",
            "5555555555",
            "6666666666",
            "7777777777",
            "8888888888",
            "9999999999",
    };

    public  boolean checkNationalCode(String nationalCode) {
        if (nationalCode == null)
            return false;
        if (nationalCode.isEmpty())
            return false;
        if (nationalCode.length() < 8 || nationalCode.length() > 10)
            return false;
        if (!nationalCode.matches("[0-9]+"))
            return false;
        if (nationalCode.length() < 10 && nationalCode.length() >= 8)
            nationalCode = String.format("%010d", Integer.parseInt(nationalCode));

        for (String s : notNationalCode) {
            if (s.equalsIgnoreCase(nationalCode))
                return false;
        }

        String nationalCodeWithoutControlDigit = nationalCode.substring(0, nationalCode.length() - 1);
        String controlDigit = nationalCode.substring(nationalCode.length() - 1, nationalCode.length());
        int sum = 0;
        int i = 10;
        for (char c : nationalCodeWithoutControlDigit.toCharArray()) {
            int temp = Integer.parseInt("" + c) * i;
            i--;
            sum += temp;
        }
        int modBy11 = sum % 11;
        if (modBy11 < 2) {
            if (modBy11 == Integer.parseInt(controlDigit))
                return true;
        } else if (11 - modBy11 == Integer.parseInt(controlDigit))
            return true;

        return false;
    }


}