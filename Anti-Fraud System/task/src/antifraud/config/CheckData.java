package antifraud.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CheckData {
    // Check IP correctness
    public static boolean isIPcorrect(String ip) {
        if (ip == null) return false;

        String regexp = "\\d+(\\.\\d+){3}";
        if (!ip.matches(regexp)) return false;

        String[] split = ip.split("\\.");
        boolean isCorrect = true;
        for (String part : split) {
            if (Integer.parseInt(part) >= 256) {
                isCorrect = false;
                break;
            }
        }

        return isCorrect;
    }

    public static boolean isCardNumberCorrect(String card) {
        if (card == null) return false;

        String visaRegex = "4\\d{15}";
        String masterCardRegex = "((5[1-5]\\d{2})|((222[1-9])|(22[3-9]\\d)|(2[3-6]\\d{2})|(27[01]\\d)|(2720)))\\d{12}";
        String americanExpressRegex = "(34|37)\\d{13}";

        String cardType;
        if (card.matches(visaRegex)) {
            cardType = "Visa";
        } else if (card.matches(masterCardRegex)) {
            cardType = "MasterCard";
        } else if (card.matches(americanExpressRegex)) {
            cardType = "AmericanExpress";
        } else {
            return false;
        }

        return isValidLuhn(card);
    }

    private static boolean isValidLuhn(String value) {
        int sum = Character.getNumericValue(value.charAt(value.length() - 1));
        int parity = value.length() % 2;
        for (int i = value.length() - 2; i >= 0; i--) {
            int summand = Character.getNumericValue(value.charAt(i));
            if (i % 2 == parity) {
                int product = summand * 2;
                summand = (product > 9) ? (product - 9) : product;
            }
            sum += summand;
        }
        return (sum % 10) == 0;
    }

    public static boolean isRegionCorrect(String region) {
        if (region == null) return false;

        final List<String> regions = new ArrayList<>(Arrays.asList("EAP", "ECA", "HIC", "LAC", "MENA", "SA", "SSA"));
        return regions.contains(region);
    }
}
