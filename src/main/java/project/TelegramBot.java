package project;


import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import project.patterns.Triangle;

public class TelegramBot extends TelegramLongPollingBot {

    private static TelegramBot bot;

    @Override
    public String getBotUsername() {
        return "@t0ken_helper_bot";
    }

    @Override
    public String getBotToken() {
        return "5912191695:AAEbZq_jsfPbLNYGrcStrwekrzn1a6inuAg";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            System.out.println(message.getText());
            
            String finalText = null;
            if (message.hasText()) {
                String text = message.getText();
                String token = null;
                if (text.equalsIgnoreCase("eth")) {
                    token = "ethereum";
                }
                else if (text.equalsIgnoreCase("btc")) {
                    token = "bitcoin";
                }
                else if (text.equalsIgnoreCase("ltc")) {
                    token = "litecoin";
                }
                else if (text.equalsIgnoreCase("ada")) {
                    token = "cardano";
                }
                else if (text.equalsIgnoreCase("usdt")) {
                    token = "tether";
                }
                else if (text.equalsIgnoreCase("etc")) {
                    token = "ethereum-classic";
                }
                else if (text.equalsIgnoreCase("/start")) {
                    token = "start";
                }

                if (token == "start") {
                    finalText = "Введите одну из предложенных монет: (ETC, LTC, BTC, USDT)";
                }
                else if (token != null) {
                    finalText = new Triangle().checkPattern(token);
                    System.out.println(finalText);
                }
                else {
                    finalText = "Вы ввели неверное название токена";
                    System.out.println(message.getChatId());
                }
                System.out.println("fin text: "+finalText);
            }

            try {
                bot.execute(
                        SendMessage.builder()
                                .chatId(message.getChatId().toString())
                                .text(finalText)
                                .build());
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
            System.out.println(finalText+"\n"+message.getChatId());

        }
    }


    public static void main(String[] args) {
        bot = new TelegramBot();
        try {
            TelegramBotsApi telegramBotsApi  = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }


}
