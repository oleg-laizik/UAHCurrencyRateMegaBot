package org.bot.buttons;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bot.model.UserSettings;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ButtonFactory {

    public static InlineKeyboardMarkup createCurrencyOptions(UserSettings userSettings) {
        return createButtons(
                "settings.currency.data",
                Arrays.asList("USD", "EUR", "PLN"),
                (String buttonName) -> Arrays.asList(userSettings.getCurrencies()).contains(buttonName)
        );
    }

    public static InlineKeyboardMarkup createPrecisionOptions(UserSettings userSettings) {
        return createButtons(
                "settings.precision.data",
                Arrays.asList("2", "3", "4"),
                (String buttonName) -> userSettings.getDecimals() == Integer.parseInt(buttonName)
        );
    }

    public static InlineKeyboardMarkup createBankOptions(UserSettings userSettings) {
        return createButtons(
                "settings.bank.data",
                Arrays.asList("НБУ", "ПриватБанк", "Монобанк"),
                (String buttonName) -> userSettings.getBank().equals(buttonName)
        );
    }


    private static InlineKeyboardMarkup createButtons(String path, List<String> buttonNames,
                                                      Function<String, Boolean> function) {
        List<String> options = new ArrayList<>();
        for (String buttonName : buttonNames) {
            String option = path + ":" + buttonName;
            if (function.apply(buttonName)) {
                option += " ✅";
            }
            options.add(option);
        }

        return CommandFactory.dataButtons(options);
    }
}