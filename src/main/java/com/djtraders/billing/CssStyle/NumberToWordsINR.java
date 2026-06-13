package com.djtraders.billing.CssStyle;

public class NumberToWordsINR {

    private static final String[] units = {
            "", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine",
            "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen",
            "Sixteen", "Seventeen", "Eighteen", "Nineteen"
    };

    private static final String[] tens = {
            "", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"
    };

    public static String convert(double amount) {

        long rupees = (long) amount;
        long paise = Math.round((amount - rupees) * 100);

        String result = "";

        if (rupees > 0) {
            result += convertNumber(rupees) + " Rupees ";
        }

        if (paise > 0) {
            result += convertNumber(paise) + " Paise ";
        }

        return result.trim() + " Only";
    }

    private static String convertNumber(long number) {

        String result = "";

        if (number / 10000000 > 0) {
            result += convertNumber(number / 10000000) + " Crore ";
            number %= 10000000;
        }

        if (number / 100000 > 0) {
            result += convertNumber(number / 100000) + " Lakh ";
            number %= 100000;
        }

        if (number / 1000 > 0) {
            result += convertNumber(number / 1000) + " Thousand ";
            number %= 1000;
        }

        if (number / 100 > 0) {
            result += convertNumber(number / 100) + " Hundred ";
            number %= 100;
        }

        if (number > 0) {
            if (number < 20) {
                result += units[(int) number] + " ";
            } else {
                result += tens[(int) number / 10] + " " + units[(int) number % 10] + " ";
            }
        }

        return result;
    }
}