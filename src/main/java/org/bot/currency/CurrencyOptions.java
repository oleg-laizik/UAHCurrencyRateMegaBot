package org.bot.currency;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.DecimalFormat;

import org.bot.currency.dto.Currency;
import org.bot.currency.monobank.CurrencyParser;

public class CurrencyOptions {
    public static void main(String[] args) throws URISyntaxException, IOException {
        System.out.println(display("Monobank", "USD", 3));
    }

    // Метод на вывод информации по валюте, где принимает бакн, валюту, знаки после
    // запятой
    public static String display(String bank, String currency, int decimal) throws URISyntaxException, IOException {
        // Переменная куда получим значение валюті банка
        double rate = 0;
        // Проходимся по банкам
        switch (bank) {
            case "ПриватБанк":
                rate = new PrivatCurrencyService().getRate(Currency.valueOf(currency));
                break;
            case "НБУ":
                rate = new NbuCurrencyService().getRate(Currency.valueOf(currency));
                break;
            case "Монобанк":
                if (currency.equals("USD") || currency.equals("EUR"))
                    rate = new CurrencyParser().getCurrency(currency).getRateBuy();
                else
                    rate = new CurrencyParser().getCurrency(currency).getRateCross();
                break;
        }
        // форматирование количества значений после запятой
        DecimalFormat df = new DecimalFormat("#." + "0".repeat(decimal));
        String rateVal = df.format(rate);
        // Возврат информации ввиде строки
        return "Курс в " + bank + ":" + currency + "/UAH\nПокупка: " + rateVal.toString() + "";

    }
}
